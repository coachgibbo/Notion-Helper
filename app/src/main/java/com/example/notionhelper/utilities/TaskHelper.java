package com.example.notionhelper.utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notionhelper.infrastructure.NotionClient;
import com.example.notionhelper.infrastructure.config.NotionInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

// Utility class to hold task retrieving functionality for both widgets and items
public class TaskHelper {

    private final NotionInterface notionInterface;

    public TaskHelper() {
        this.notionInterface = NotionClient.getNotionInterface();
    }

    public Call<JsonObject> getTask() {
        return this.notionInterface.getPageFromDatabase(createRequestBody());
    }

    public Call<JsonObject> completeTask(String taskId) {
        return this.notionInterface.updatePage(taskId, createCompleteTaskRequestBody());
    }

    public String extractTaskName(Response<JsonObject> response) {
        if (response.body() == null) {
            Log.e("TASKRETRIEVER", "Response body in extractTaskName is null");
            return "Error retrieving task";
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

    public String extractTaskId(Response<JsonObject> response) {
        assert response.body() != null; // ATM only called after extractTaskName

        String rawString =  response.body()
                .getAsJsonArray("results")
                .get(0)
                .getAsJsonObject()
                .get("id")
                .toString();

        return rawString.substring(1, rawString.length()-1).replace("-", "");
    }

    private JsonObject createRequestBody() {
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

    private JsonObject createCompleteTaskRequestBody() {
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
