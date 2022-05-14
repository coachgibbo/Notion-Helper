package com.example.notionhelper.utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notionhelper.infrastructure.NotionClient;
import com.example.notionhelper.infrastructure.config.NotionInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public final class TaskHelper {

    private static final NotionInterface notionInterface = NotionClient.getNotionInterface();

    private TaskHelper() {

    }

    public static Call<JsonObject> getTask() {
        return notionInterface.getPageFromDatabase(createRequestBody());
    }

    public static Call<JsonObject> completeTask(String taskId) {
        if (taskId == null) {
            return null;
        }

        return notionInterface.updatePage(taskId, createCompleteTaskRequestBody());
    }

    public static String extractTaskName(Response<JsonObject> response) {
        if (response.body() == null) {
            Log.e("TASKRETRIEVER", "Response body in extractTaskName is null");
            return "Error retrieving task";
        }

        if (response.body().getAsJsonArray("results").size() == 0) {
            return "All tasks for today completed :)";
        }

        String rawString = response.body()
                .getAsJsonArray("results")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("properties")
                .getAsJsonObject("Name")
                .getAsJsonArray("title")
                .get(0)
                .getAsJsonObject()
                .get("plain_text")
                .toString();

        return rawString.substring(1, rawString.length()-1);
    }

    public static String extractTaskId(Response<JsonObject> response) {
        assert response.body() != null; // ATM only called after extractTaskName

        if (response.body().getAsJsonArray("results").size() == 0) {
            return null;
        }

        String rawString =  response.body()
                .getAsJsonArray("results")
                .get(0)
                .getAsJsonObject()
                .get("id")
                .toString();

        return rawString.substring(1, rawString.length()-1).replace("-", "");
    }

    private static JsonObject createRequestBody() {
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

    private static JsonObject createCompleteTaskRequestBody() {
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
}
