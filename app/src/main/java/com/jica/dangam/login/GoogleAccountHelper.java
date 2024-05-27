package com.jica.dangam.login;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class GoogleAccountHelper extends Application {
	private static volatile GoogleAccountHelper mInstance = null;
	private static List<String> googleUserLoginInfo = new ArrayList<>();

	public static List<String> getGoogleUserLoginInfo() {
		return googleUserLoginInfo;
	}

	public static void setGoogleUserLoginInfo(List<String> userLoginInfo) {
		googleUserLoginInfo = userLoginInfo;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
}
