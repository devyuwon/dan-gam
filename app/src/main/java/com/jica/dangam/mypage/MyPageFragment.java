package com.jica.dangam.mypage;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jica.dangam.R;
import com.jica.dangam.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyPageFragment extends Fragment {
	private Button btnLogout;
	private GoogleSignInClient mGoogleSignInClient;
	private FirebaseAuth mAuth;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_page, container, false);

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build();

		mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
		mAuth = FirebaseAuth.getInstance();

		btnLogout = view.findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(view1 -> {
			signOut();
		});
		return view;
	}

	private void signOut() {
		mAuth.signOut();

		mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				FirebaseUser user = mAuth.getCurrentUser();

				if (user == null) {
					startActivity(new Intent(getContext(), LoginActivity.class));
					Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
			}
		});
	}
}
