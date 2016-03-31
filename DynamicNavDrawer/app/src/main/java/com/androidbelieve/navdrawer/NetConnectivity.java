package com.androidbelieve.navdrawer;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetConnectivity {

	public static boolean isOnline(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
