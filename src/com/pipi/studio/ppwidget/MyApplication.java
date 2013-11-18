package com.pipi.studio.ppwidget;

import android.app.Application;
import android.content.SharedPreferences;


public class MyApplication extends Application {
	private static final String TAG = "MyApplication";
	
	private int mImageIndex = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initWidgetData();
	}
	
	private void initWidgetData() {
		SharedPreferences sharePref = getSharedPreferences(Constants.IMAGE_URLS, MODE_PRIVATE);
		String urls = sharePref.getString(Constants.KEY_URLS, null);
		
		if (urls == null) {
			urls = "";
		}
		
	}
	
	public void setImageIndex(int index) {
		synchronized(this) {
			mImageIndex = index;
		}
	}
	
	public int getImageIndex() {
		synchronized(this) {
			return mImageIndex;
		}
	}
}