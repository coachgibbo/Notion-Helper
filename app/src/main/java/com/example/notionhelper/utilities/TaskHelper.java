package com.example.notionhelper.utilities;

import android.util.Log;

import com.example.notionhelper.infrastructure.notion.NotionClient;
import com.example.notionhelper.infrastructure.notion.NotionInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public final class TaskHelper {

    private static final NotionInterface notionInterface = NotionClient.getNotionInterface();

    private TaskHelper() {

    }

    public static Call<JsonObject> getTask() {
        return notionInterface.getPageFromDatabase(JsonBodyHelper.getPageFromDatabaseBody());
    }

    public static Call<JsonObject> completeTask(String taskId) {
        if (taskId == null) {
            return null;
        }

        return notionInterface.updatePage(taskId, JsonBodyHelper.completeTaskBody());
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
        assert response.body() != null; // Only called after extractTaskName

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

}
