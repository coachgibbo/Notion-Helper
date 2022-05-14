package com.example.notionhelper.utilities;

import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_DATABASE;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public final class JsonBodyHelper {

    private static final JsonParser jsonParser = new JsonParser();

    private JsonBodyHelper() {

    }

    public static JsonObject updatePageBody(ArrayList<String> inputs) {
        JsonObject body = new JsonObject();
        JsonObject properties = new JsonObject();

        JsonObject date = new JsonObject();
        JsonObject dateProps = new JsonObject();
        dateProps.addProperty("start", inputs.get(0));
        dateProps.add("end", null);
        dateProps.add("time_zone", null);
        date.add("date", dateProps);

        JsonObject status = new JsonObject();
        status.addProperty("type", "select");
        JsonObject statusProps = new JsonObject();
        statusProps.addProperty("id", "1");
        statusProps.addProperty("name", "Not started");
        statusProps.addProperty("color", "red");
        status.add("select", statusProps);

        properties.add("Date", date);
        properties.add("Status", status);
        body.add("properties", properties);
        return body;
    }

    public static JsonObject getPageFromDatabaseBody() {
        JsonObject body = new JsonObject();
        JsonArray sorts = new JsonArray();
        JsonObject sortProps = new JsonObject();
        JsonObject select = new JsonObject();
        JsonObject filter = new JsonObject();

        sortProps.addProperty("property", "Order");
        sortProps.addProperty("direction", "ascending");
        sorts.add(sortProps);

        select.addProperty("equals", "Not started");
        filter.addProperty("property", "Status");
        filter.add("select", select);

        body.add("sorts", sorts);
        body.add("filter", filter);
        body.addProperty("page_size", 1);
        return body;
    }

    public static JsonObject completeTaskBody() {
        JsonObject body = new JsonObject();
        JsonObject properties = new JsonObject();
        JsonObject status = new JsonObject();
        JsonObject select = new JsonObject();

        select.addProperty("id", "3");
        select.addProperty("name", "Completed");
        select.addProperty("color", "green");

        status.addProperty("type", "select");
        status.add("select", select);

        properties.add("Status", status);
        body.add("properties", properties);
        return body;
    }

    // Not doing this one programmatically
    public static JsonObject createPageBody(ArrayList<String> inputs) {
        return jsonParser.parse("{\n" +
                "    \"parent\": {\n" +
                "        \"database_id\": \"" + DAILY_TASK_DATABASE + "\"\n" +
                "    },\n" +
                "    \"properties\": {\n" +
                "        \"title\": {\n" +
                "            \"title\": [\n" +
                "                {\n" +
                "                    \"text\": {\n" +
                "                        \"content\": \"" + inputs.get(0) + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"Date\": {\n" +
                "            \"date\": {\n" +
                "                \"start\": \"" + inputs.get(1) + "\",\n" +
                "                \"end\": null,\n" +
                "                \"time_zone\": null\n" +
                "            }\n" +
                "        }," +
                "        \"Status\": {\n" +
                "            \"select\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Not started\",\n" +
                "                \"color\": \"red\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"Order\": {\n" +
                "            \"number\": 900\n" +
                "        }" +
                "    }\n" +
                "}").getAsJsonObject();
    }
}
