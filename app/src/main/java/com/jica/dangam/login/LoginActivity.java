package com.jica.dangam.login;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.jica.dangam.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
	private static final int RC_SIGN_IN = 9001;
	private static final String TAG = "LoginActivity";
	View.OnClickListener GoogleLoginListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(LoginActivity.this, GoogleLogin.class);
			startActivity(intent);
			finish();
		}
	};

	private FirebaseAuth mAuth;
	private SignInButton btnGoogleLogin;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
		btnGoogleLogin.setSize(1);
		btnGoogleLogin.setOnClickListener(GoogleLoginListener);

		mAuth = FirebaseAuth.getInstance();
		if (HasGoogleSession()) {
			Intent intent = new Intent(LoginActivity.this, GoogleLogin.class);
			startActivity(intent);
			finish();
		}
	}

	private boolean HasGoogleSession() {
		if (mAuth.getCurrentUser() == null) {
			return false;
		}
		return true;
	}
}
