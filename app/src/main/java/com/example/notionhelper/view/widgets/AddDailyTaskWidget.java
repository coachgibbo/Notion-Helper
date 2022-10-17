package com.example.notionhelper.view.widgets;

import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_DATABASE;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.notionhelper.BuildConfig;
import com.example.notionhelper.R;
import com.example.notionhelper.utilities.TaskHelper;
import com.example.notionhelper.view.activities.ItemActivity;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDailyTaskWidget extends AppWidgetProvider {

    private static RemoteViews views = new RemoteViews(BuildConfig.APPLICATION_ID, R.layout.adddailytask_widget);
    private static AppWidgetManager widgetManager;
    private static ComponentName addDailyTaskWidget;

    private static final HashMap<String, String> currentTask = new HashMap<>();

    private static final String refreshAction = "refresh";
    private static final String completeAction = "complete";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews updatedView = new RemoteViews(BuildConfig.APPLICATION_ID, R.layout.adddailytask_widget);
        widgetManager = appWidgetManager;
        if (addDailyTaskWidget == null) {
            addDailyTaskWidget = new ComponentName(context, AddDailyTaskWidget.class);
        }

        Intent addDailyTaskIntent = new Intent(context, ItemActivity.class);
        addDailyTaskIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        addDailyTaskIntent.putExtra("id", "addDailyTask");
        addDailyTaskIntent.putExtra("title", "Add Daily Task");
        addDailyTaskIntent.putExtra("description", "Add a custom to-do item to your daily task database with date set as today or tomorrow");

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(buildOpenURI());

        PendingIntent pendingAddDailyTaskIntent = PendingIntent.getActivity(context, 0, addDailyTaskIntent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingBrowserIntent = PendingIntent.getActivity(context, 0, browserIntent, PendingIntent.FLAG_IMMUTABLE);

        updatedView.setOnClickPendingIntent(R.id.adddailytask_widget_button, pendingAddDailyTaskIntent);
        updatedView.setOnClickPendingIntent(R.id.adddailytask_widget_openbutton, pendingBrowserIntent);
        updatedView.setOnClickPendingIntent(R.id.adddailytask_widget_refreshbutton, getPendingSelfIntent(context, refreshAction));
        updatedView.setOnClickPendingIntent(R.id.adddailytask_widget_completebutton, getPendingSelfIntent(context, completeAction));

        refreshTask();

        appWidgetManager.updateAppWidget(addDailyTaskWidget, updatedView);
        views = updatedView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (refreshAction.equals(intent.getAction())) {
            refreshTask();
        } else if (completeAction.equals(intent.getAction())) {
            try {
                completeTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshTask() {
        Call<JsonObject> nextTaskCall = TaskHelper.getTask();

        nextTaskCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                String newTaskName = TaskHelper.extractTaskName(response);
                String newTaskId = TaskHelper.extractTaskId(response);
                storeTaskVariables(newTaskName, newTaskId);

                views.setTextViewText(R.id.adddailytask_widget_taskname, newTaskName);
                widgetManager.updateAppWidget(addDailyTaskWidget, views);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {}
        });
    }

    private void completeTask() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<JsonObject> completeTaskCall = TaskHelper.completeTask(currentTask.getOrDefault("currentTaskId", TaskHelper.extractTaskId(TaskHelper.getTask().execute())));

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

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, this.getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private Uri buildOpenURI() {
        return Uri.parse("https://www.notion.so/" + DAILY_TASK_DATABASE);
    }

    private void storeTaskVariables(String name, String id) {
        currentTask.put("currentTaskName", name);
        currentTask.put("currentTaskId", id);
    }

}