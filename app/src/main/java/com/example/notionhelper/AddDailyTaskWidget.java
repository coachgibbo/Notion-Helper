package com.example.notionhelper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class AddDailyTaskWidget extends AppWidgetProvider {

    private static final String refreshAction = "refresh";
    private static final String openAction = "open";
    private static final String completeAction = "complete";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName addDailyTaskWidget = new ComponentName(context, AddDailyTaskWidget.class);

        for (int appWidgetId : appWidgetIds) {
            Intent addDailyTaskIntent = new Intent(context, ItemActivity.class);
            addDailyTaskIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            addDailyTaskIntent.putExtra("id", "addDailyTask");
            addDailyTaskIntent.putExtra("title", "Add Daily Task");
            addDailyTaskIntent.putExtra("description", "Add a custom to-do item to your daily task database with date set as today or tomorrow");

            PendingIntent pendingAddDailyTaskIntent = PendingIntent.getActivity(context, 0, addDailyTaskIntent, PendingIntent.FLAG_IMMUTABLE);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.adddailytask_widget);
            views.setOnClickPendingIntent(R.id.adddailytask_widget_button, pendingAddDailyTaskIntent);
            views.setOnClickPendingIntent(R.id.adddailytask_widget_refreshbutton, getPendingSelfIntent(context, refreshAction));
            views.setOnClickPendingIntent(R.id.adddailytask_widget_openbutton, getPendingSelfIntent(context, openAction));
            views.setOnClickPendingIntent(R.id.adddailytask_widget_completebutton, getPendingSelfIntent(context, completeAction));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (refreshAction.equals(intent.getAction())) {
            Log.i("WIDGETTEST", "Caught the refresh button press");
        } else if (openAction.equals(intent.getAction())) {
            Log.i("WIDGETTEST", "Caught the open button press");
        } else if (completeAction.equals(intent.getAction())) {
            Log.i("WIDGETTEST", "Caught the complete button press");
        }
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, this.getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }
}