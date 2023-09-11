package com.jica.dangam.login;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jica.dangam.main.MainActivity;
import com.jica.dangam.R;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
	private GoogleSignInClient mGoogleSignInClient;
	private FirebaseAuth mAuth;
	private static final int RC_SIGN_IN = 9001;
	private static final String TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		SignInButton btnGoogleLogin = findViewById(R.id.btnGoogleLogin);

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build();

		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
		mAuth = FirebaseAuth.getInstance();

		btnGoogleLogin.setSize(1);

		btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				signIn();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		FirebaseUser currentUser = mAuth.getCurrentUser();

		if (currentUser != null) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			updateUI(currentUser);
			Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

			try {
				GoogleSignInAccount account = task.getResult(ApiException.class);
				Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
				firebaseAuthWithGoogle(account.getIdToken());
			} catch (ApiException e) {
				Log.w(TAG, "Google Sign in failed", e);
			}
		}
	}

	private void firebaseAuthWithGoogle(String idToken) {
		AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
		mAuth.signInWithCredential(credential)
			.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						Log.d(TAG, "signInWithCredential: Success");
						FirebaseUser user = mAuth.getCurrentUser();

						if (user != null) {
							startActivity(new Intent(LoginActivity.this, MainActivity.class));
							Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
							updateUI(user);
							finish();
						}
					} else {
						Log.w(TAG, "signInWithCredential: Failure", task.getException());
						updateUI(null);
					}
				}
			});
	}


	private void signIn() {
		Intent sighInIntent = mGoogleSignInClient.getSignInIntent();
		startActivityForResult(sighInIntent, RC_SIGN_IN);
	}


	private void updateUI(FirebaseUser user) {

	}

}
