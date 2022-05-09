package com.example.notionhelper;

import static com.example.notionhelper.common.Constants.DAILY_TASK_DATABASE_NAME;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_DATABASE;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.notionhelper.utilities.TaskHelper;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TO-DO: Clean this class up
public class AddDailyTaskWidget extends AppWidgetProvider {

    private static Map<String, String> currentTask = new HashMap();

    private static RemoteViews views;
    private static AppWidgetManager widgetManager;
    private static ComponentName addDailyTaskWidget;

    private static final String refreshAction = "refresh";
    private static final String completeAction = "complete";
    private static final TaskHelper taskHelper = new TaskHelper();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        widgetManager = appWidgetManager;
        addDailyTaskWidget = new ComponentName(context, AddDailyTaskWidget.class);
        currentTask.put("currentTaskName", null);
        currentTask.put("currentTaskId", null);

        Intent addDailyTaskIntent = new Intent(context, ItemActivity.class);
        addDailyTaskIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        addDailyTaskIntent.putExtra("id", "addDailyTask");
        addDailyTaskIntent.putExtra("title", "Add Daily Task");
        addDailyTaskIntent.putExtra("description", "Add a custom to-do item to your daily task database with date set as today or tomorrow");

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(buildOpenURI());

        PendingIntent pendingAddDailyTaskIntent = PendingIntent.getActivity(context, 0, addDailyTaskIntent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingBrowserIntent = PendingIntent.getActivity(context, 0, browserIntent, PendingIntent.FLAG_IMMUTABLE);

        views = new RemoteViews(context.getPackageName(), R.layout.adddailytask_widget);
        views.setOnClickPendingIntent(R.id.adddailytask_widget_button, pendingAddDailyTaskIntent);
        views.setOnClickPendingIntent(R.id.adddailytask_widget_refreshbutton, getPendingSelfIntent(context, refreshAction));
        views.setOnClickPendingIntent(R.id.adddailytask_widget_openbutton, pendingBrowserIntent);
        views.setOnClickPendingIntent(R.id.adddailytask_widget_completebutton, getPendingSelfIntent(context, completeAction));

        refreshTask();

        appWidgetManager.updateAppWidget(addDailyTaskWidget, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (refreshAction.equals(intent.getAction())) {
            refreshTask();
        } else if (completeAction.equals(intent.getAction())) {
            completeTask();
        }
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, this.getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private void refreshTask() {
        Call<JsonObject> nextTaskCall = taskHelper.getTask();

        nextTaskCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                String newTaskName = taskHelper.extractTaskName(response);
                if (newTaskName == null) {
                    return;
                }
                String newTaskId = taskHelper.extractTaskId(response);
                storeTaskVariables(newTaskName, newTaskId);

                views.setTextViewText(R.id.adddailytask_widget_taskname, newTaskName);
                widgetManager.updateAppWidget(addDailyTaskWidget, views);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {}
        });
    }

    private void completeTask() {
        Call<JsonObject> completeTaskCall = taskHelper.completeTask(currentTask.get("currentTaskId"));

        completeTaskCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    refreshTask();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {}
        });
    }

    private Uri buildOpenURI() {
        return Uri.parse("https://www.notion.so/" + DAILY_TASK_DATABASE_NAME + "-" + DAILY_TASK_DATABASE);
    }

    // Maybe not necessary
    private void storeTaskVariables(String name, String id) {
        currentTask.put("currentTaskName", name);
        currentTask.put("currentTaskId", id);
    }
}