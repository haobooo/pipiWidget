package com.pipi.studio.ppwidget;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;


public class pipiWidgetProvider extends AppWidgetProvider {
	private static final String TAG = "pipiWidgetProvider";
	private static final boolean DEBUG = true;
	
	private static Set appIds = new HashSet();
	
	private final Intent FLIP_SERVICE_INTENT = 
            new Intent("com.pipi.studio.ppwidget.action.FLIP_SERVICE");
	
	private static final int[] IMAGE_IDS = {
		R.drawable.aaaa,
		R.drawable.bbbb,
		R.drawable.cccc,
		R.drawable.dddd,
		R.drawable.eeee,
		R.drawable.ffff,
		R.drawable.gggg,
		R.drawable.hhhh
	};
	
	private int widgetImgIndex = 0;
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		if (DEBUG) Log.d(TAG, "onUpdate(): appWidgetIds.length="+appWidgetIds.length);
		
        final int N = appWidgetIds.length;// Perform this loop procedure for each App Widget that belongs to this provider          
          
        for (int i=0; i<N; i++) {              
            int appWidgetId = appWidgetIds[i];                         
            
            appIds.add(Integer.valueOf(appWidgetId));
        }      
    }
	
	@Override  
    public void onDeleted(Context context, int[] appWidgetIds) {
		if (DEBUG) Log.d(TAG, "onUpdate(): appWidgetIds.length="+appWidgetIds.length);
		
		for (int appWidgetId : appWidgetIds) {
			appIds.remove(Integer.valueOf(appWidgetId));
		}
		
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override  
    public void onEnabled(Context context) {
		if (DEBUG) Log.d(TAG, "onEnabled");
		
		context.startService(FLIP_SERVICE_INTENT);
		
		super.onEnabled(context);
	}
	
	@Override  
    public void onDisabled(Context context) {
		if (DEBUG) Log.d(TAG, "onDisabled");
		
		context.stopService(FLIP_SERVICE_INTENT);
		
		super.onDisabled(context);
	}
	
	@Override  
    public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if (DEBUG) Log.d(TAG, "action=" + action);
		
		if (Constants.ACTION_UPDATE_WIDGET.equals(action)) {
			updateWidgets(context, AppWidgetManager.getInstance(context));
		}
		
		super.onReceive(context, intent);
	}
	
	private void updateWidgets(Context context, AppWidgetManager appWidgetManager) {
		if (DEBUG) Log.d(TAG, "updateAllAppWidgets(): size="+appIds.size());
		
		int appID;
		Iterator it = appIds.iterator();
		while (it.hasNext()) {
            appID = ((Integer)it.next()).intValue();
            
            int index = ((MyApplication)context.getApplicationContext()).getImageIndex();
            if (DEBUG) Log.d(TAG, "onUpdate(): index="+index);
            
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.pipi_appwidget);
            
            remoteView.setImageViewResource(R.id.flipper, IMAGE_IDS[index]);
            
            appWidgetManager.updateAppWidget(appID, remoteView);
            
            index = index + 1;
            if (index >= IMAGE_IDS.length) {
            	index = 0;
            }
            ((MyApplication)context.getApplicationContext()).setImageIndex(index);
        }  
	}
}