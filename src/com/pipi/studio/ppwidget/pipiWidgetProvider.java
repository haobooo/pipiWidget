package com.pipi.studio.ppwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class pipiWidgetProvider extends AppWidgetProvider {
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {  
        final int N = appWidgetIds.length;        // Perform this loop procedure for each App Widget that belongs to this provider          
          
        for (int i=0; i<N; i++) {              
            int appWidgetId = appWidgetIds[i];                         
          
            Intent intent = new Intent();              
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);                          
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pipi_appwidget);
            //views.setOnClickPendingIntent(R.id.wap_app, pendingIntent);               
            appWidgetManager.updateAppWidget(appWidgetId, views);          
        }      
    }  
}