package com.jica.dangam;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	private FirebaseAuth oAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (FirebaseAuth.getInstance().getCurrentUser() == null) {
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
