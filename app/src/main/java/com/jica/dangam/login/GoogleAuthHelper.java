package com.jica.dangam.login;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.jica.dangam.main.MainActivity;

import android.content.Context;
import androidx.annotation.NonNull;

public class GoogleAuthHelper {
	public static void accountLogout(Context context, MainActivity activity) {
		if (FirebaseAuth.getInstance().getCurrentUser() != null) {
			FirebaseAuth.getInstance().signOut();
			GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
			activity.logoutToLoginActivity(true);
		}
	}

	public static void accountDelete(final Context context, final MainActivity activity) {
		if (FirebaseAuth.getInstance().getCurrentUser() != null) {
			try {
				FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(
					new OnCompleteListener<Void>() {
						@Override
						public void onComplete(@NonNull Task<Void> task) {
							if (task.isSuccessful()) {
								activity.deleteToLoginActivity(true);
							} else {
								activity.deleteToLoginActivity(false);
							}
						}
					});
				GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).revokeAccess();
			} catch (Exception e) {
				activity.deleteToLoginActivity(false);
			}
		}
	}
}
