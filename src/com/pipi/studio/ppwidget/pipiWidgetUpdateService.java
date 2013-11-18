package com.pipi.studio.ppwidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class pipiWidgetUpdateService extends Service {
	private static final String TAG = "pipiWidgetUpdateService";
	
	private UpdateThread mUpdateThread;
    private Context mContext;
    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
    public void onCreate() {
		if (Constants.DEBUG) Log.d(TAG, "onCreate");
        mUpdateThread = new UpdateThread();
        mUpdateThread.start();
        
        mContext = this.getApplicationContext();

        super.onCreate();
    }
	
	@Override
    public void onDestroy(){
		if (Constants.DEBUG) Log.d(TAG, "onDestroy");
		
        if (mUpdateThread != null) {
            mUpdateThread.interrupt();
        }
        
        super.onDestroy();
    }
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Constants.DEBUG) Log.d(TAG, "onStartCommand");        
        super.onStartCommand(intent, flags, startId);
        
        return START_STICKY;
    }
	
	private class UpdateThread extends Thread {
		private static final int UPDATE_TIME = 5000;
		
        @Override
        public void run() {
            super.run();

            try {
                while (true) {
                    Intent updateIntent=new Intent(Constants.ACTION_UPDATE_WIDGET);
                    mContext.sendBroadcast(updateIntent);
                    
                    Thread.sleep(UPDATE_TIME);
                } 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}