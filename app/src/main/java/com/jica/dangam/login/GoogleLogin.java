package com.jica.dangam.login;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jica.dangam.R;
import com.jica.dangam.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GoogleLogin extends Activity {
	private static final int RC_SIGN_IN = 9001;
	private GoogleSignInClient mGoogleSignInClient;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAuth = FirebaseAuth.getInstance();

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build();

		mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

		if (mAuth.getCurrentUser() == null) {
			Intent intent = mGoogleSignInClient.getSignInIntent();
			startActivityForResult(intent, RC_SIGN_IN);
		} else if (mAuth.getCurrentUser() != null) {
			List<String> userInfo = new ArrayList<>();
			GoogleAccountHelper mGoogleAccountHelper = (GoogleAccountHelper)getApplication();

			userInfo.add(String.format("%s-%s", "GOOGLE", mAuth.getCurrentUser().getUid()));
			userInfo.add(mAuth.getCurrentUser().getDisplayName());

			if (mAuth.getCurrentUser().getPhotoUrl() != null) {
				userInfo.add(mAuth.getCurrentUser().getPhotoUrl().toString());
			}

			GoogleAccountHelper.setGoogleUserLoginInfo(userInfo);
			Intent intent = new Intent(GoogleLogin.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

			if (result.isSuccess()) {
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else {
				Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(GoogleLogin.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		mAuth.signInWithCredential(credential)
			.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						if (mAuth.getCurrentUser() != null) {
							List<String> userInfo = new ArrayList<>();
							GoogleAccountHelper mGoogleAccountHelper = (GoogleAccountHelper)getApplication();

							userInfo.add(mAuth.getCurrentUser().getUid());
							userInfo.add(mAuth.getCurrentUser().getDisplayName());

							if (mAuth.getCurrentUser().getPhotoUrl() != null) {
								userInfo.add(mAuth.getCurrentUser().getPhotoUrl().toString());
							}

							GoogleAccountHelper.setGoogleUserLoginInfo(userInfo);
							Intent intent = new Intent(GoogleLogin.this, MainActivity.class);
							startActivity(intent);
							Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
							finish();
						}
					} else {
						Toast.makeText(getApplicationContext(), "Google Authentication Failed", Toast.LENGTH_SHORT)
							.show();
					}
				}
			});
	}
}
