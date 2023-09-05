package com.jica.dangam;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
	private FragmentManager fragmentManager = getSupportFragmentManager();
	private MainFragment mainFragment = new MainFragment();
	private MyPageFragment myPageFragment = new MyPageFragment();

	@Override
	protected void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.menuFrameLayout, mainFragment).commitAllowingStateLoss();

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListner());
	}

	class ItemSelectedListner implements BottomNavigationView.OnNavigationItemSelectedListener {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();

			int itemId = item.getItemId();
			if (itemId == R.id.menu_home) {
				transaction.replace(R.id.menuFrameLayout, mainFragment).commitAllowingStateLoss();
			} else if (itemId == R.id.menu_my_page) {
				transaction.replace(R.id.menuFrameLayout, myPageFragment).commitAllowingStateLoss();
			}
			return true;
		}
	}

}

// import java.util.ArrayList;
//
// import com.google.android.gms.auth.api.signin.GoogleSignIn;
// import com.google.android.gms.auth.api.signin.GoogleSignInClient;
// import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
// import com.google.android.gms.tasks.OnCompleteListener;
// import com.google.android.gms.tasks.Task;
// import com.google.android.material.bottomnavigation.BottomNavigationView;
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.firestore.FirebaseFirestore;
// import com.google.firebase.firestore.QueryDocumentSnapshot;
// import com.google.firebase.firestore.QuerySnapshot;
//
// import android.content.Intent;
// import android.util.Log;
// import android.view.Menu;
// import android.widget.Button;
// import android.widget.Toast;
// import androidx.annotation.NonNull;
// import androidx.appcompat.app.AppCompatActivity;
// import android.os.Bundle;
// import androidx.appcompat.widget.Toolbar;
// import androidx.fragment.app.FragmentManager;
// import androidx.fragment.app.FragmentTransaction;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
//
// public class MainActivity extends AppCompatActivity {
//
// 	RecyclerView recyclerView;
// 	PostProfileAdapter adapter;
// 	LinearLayoutManager linearLayoutManager;
// 	ArrayList<PostProfile> list;
// 	FirebaseFirestore db = FirebaseFirestore.getInstance();
//
// 	// 테스트용 임시 로그아웃 버튼
// 	private Button btnGoogleLogout;
// 	private GoogleSignInClient mGoogleSignInClient;
// 	private FirebaseAuth mAuth;
//
// 	// MyPageFragment
// 	private MyPageFragment myPageFragment;
// 	private FragmentManager fragmentManager = getSupportFragmentManager();
//
// 	@Override
// 	protected void onCreate(Bundle savedInstanceState) {
// 		super.onCreate(savedInstanceState);
// 		setContentView(R.layout.activity_main);
//
// 		Toolbar tb = (Toolbar) findViewById(R.id.mainToolbar) ;
// 		setSupportActionBar(tb) ;
//
// 		recyclerView = findViewById(R.id.main_recyclerview);
//
// 		linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
// 		recyclerView.setLayoutManager(linearLayoutManager);
// 		list = new ArrayList<>();
// 		adapter = new PostProfileAdapter(list);
//
// 		//업로드 테스트 및 초기 문서 추가
// 		/*for (int i=1;i<=10;i++){
// 			String title = "init_title"+i;
// 			String contents = "init_contents"+i;
// 			PostProfile upload = new PostProfile(title,contents);
// 			db.collection("post_gam").document(title).set(upload);
// 		}*/
//
//
//
// 		db.collection("post_gam").orderBy("pdate")
// 			.get()
// 			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
// 				@Override
// 				public void onComplete(@NonNull Task<QuerySnapshot> task) {
// 					if (task.isSuccessful()) {
// 						for (QueryDocumentSnapshot document : task.getResult()) {
// 							PostProfile profile = document.toObject(PostProfile.class);
// 							adapter.addItem(profile);
// 							Log.d("firestore", document.getId() + " => " + document.getData());
// 							Log.d("object test",profile.getTitle()+" "+profile.getContents());
// 						}
// 						adapter.notifyDataSetChanged();
// 					} else {
// 						Log.d("firestore", "Error getting documents: ", task.getException());
// 					}
// 				}
// 			});
//
//
//
// 		recyclerView.setAdapter(adapter);
//
//
// 		// 테스트용 임시 로그아웃
// 		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
// 			.requestIdToken(getString(R.string.default_web_client_id))
// 			.requestEmail()
// 			.build();
//
// 		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
// 		mAuth = FirebaseAuth.getInstance();
//
// 		btnGoogleLogout = findViewById(R.id.btnGoogleLogout);
// 		btnGoogleLogout.setOnClickListener(view -> {
// 			signOut();
// 		});
//
// 		// MyPageFragment
// 		// FragmentTransaction transaction = fragmentManager.beginTransaction();
// 		// transaction.replace(R.id.menuFrameLayout,)
//
// 	}
//
// 	private void signOut() {
// 		mAuth.signOut();
//
// 		mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
// 			@Override
// 			public void onComplete(@NonNull Task<Void> task) {
// 				FirebaseUser user = mAuth.getCurrentUser();
//
// 				if (user == null) {
// 					startActivity(new Intent(MainActivity.this, LoginActivity.class));
// 					Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
// 					finish();
// 				}
// 			}
// 		});
// 	}
//
// 	@Override
// 	public boolean onCreateOptionsMenu(Menu menu) {
// 		getMenuInflater().inflate(R.menu.main_toolbar, menu) ;
// 		return super.onCreateOptionsMenu(menu);
// 	}
//
//
// }
