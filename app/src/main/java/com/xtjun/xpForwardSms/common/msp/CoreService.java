package com.xtjun.xpForwardSms.common.msp;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.IBinder;

public class CoreService extends Service {
	private static final String TAG = "CoreService";
	public static final String SP_NAME = "com.xtjun.xpForwardSms";
	public static final String SP_KEY_WXCP_EXPDATE = "wxcp_expDate";
	public static final String SP_KEY_WXCP_TOKEN = "excp_token";
	private SharedPreferences mSharedPreferences;
	private OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mSharedPreferences = MultiProcessSharedPreferences.getSharedPreferences(this, SP_NAME, Context.MODE_PRIVATE);
		mOnSharedPreferenceChangeListener = (sharedPreferences, key) -> {
		};
		mSharedPreferences.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}