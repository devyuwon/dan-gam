package com.jica.dangam.main;

import java.util.ArrayList;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jica.dangam.mypage.MyPageFragment;
import com.jica.dangam.list.ListModel;
import com.jica.dangam.list.ListAdapter;
import com.jica.dangam.R;
import com.jica.dangam.login.LoginActivity;
import com.jica.dangam.util.GetListData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment {
	RecyclerView recyclerView;
	ListAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<ListModel> list;
	Context context;
	FirebaseFirestore db = FirebaseFirestore.getInstance();

	// 테스트용 임시 로그아웃 버튼
	private Button btnGoogleLogout;
	private GoogleSignInClient mGoogleSignInClient;
	private FirebaseAuth mAuth;

	// MyPageFragment
	private MyPageFragment myPageFragment;
	private FragmentManager fragmentManager;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		Toolbar tb = view.findViewById(R.id.mainToolbar);
		((AppCompatActivity)getActivity()).setSupportActionBar(tb);

		recyclerView = view.findViewById(R.id.main_recyclerview);
		linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);
		list = new ArrayList<>();
		adapter = new ListAdapter(context, list);

		GetListData database = new GetListData(adapter);
		database.downloadDB();
		recyclerView.setAdapter(adapter);

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build();

		mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
		mAuth = FirebaseAuth.getInstance();

		btnGoogleLogout = view.findViewById(R.id.btnGoogleLogout);
		btnGoogleLogout.setOnClickListener(view1 -> {
			signOut();
		});

		fragmentManager = getChildFragmentManager();

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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.common_toolbar, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
