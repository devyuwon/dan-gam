package com.jica.dangam;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
	private static final String Tag = "SplashActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startLoading();


		// setContentView(R.layout.activity_splash);
	}

	private void startLoading() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				finish();
			}
		}, 2000);
	}
}
