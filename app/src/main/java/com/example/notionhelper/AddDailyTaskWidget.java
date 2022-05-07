package com.example.notionhelper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class AddDailyTaskWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent addDailyTaskIntent = new Intent(context, ItemActivity.class);
            addDailyTaskIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            addDailyTaskIntent.putExtra("id", "addDailyTask");
            addDailyTaskIntent.putExtra("title", "Add Daily Task");
            addDailyTaskIntent.putExtra("description", "Add a custom to-do item to your daily task database with date set as today or tomorrow");

            PendingIntent pendingAddDailyTaskIntent = PendingIntent.getActivity(context, 0, addDailyTaskIntent, PendingIntent.FLAG_IMMUTABLE);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.adddailytask_widget);
            views.setOnClickPendingIntent(R.id.adddailytask_widget_button, pendingAddDailyTaskIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}
}