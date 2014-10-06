package com.xzheng.znews.util;

import com.xzheng.znews.MainApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	
	public static boolean checkInternetConenction(){
		Context context = MainApplication.getContext();
		ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (check != null) 
		{
			NetworkInfo[] info = check.getAllNetworkInfo();
			if (info != null) 
				for (int i = 0; i <info.length; i++) { 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
				}
		}
		return false;
	}
}
