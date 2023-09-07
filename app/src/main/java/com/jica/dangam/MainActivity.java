package com.jica.dangam;

import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jica.dangam.database.Database;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	PostProfileAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<PostProfile> list;
	FirebaseFirestore db = FirebaseFirestore.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar)findViewById(R.id.mainToolbar);
		setSupportActionBar(toolbar);
		BottomNavigationView btNavi = findViewById(R.id.bottomNavigationView);

		recyclerView = findViewById(R.id.main_recyclerview);

		linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);

		//업로드 테스트 및 초기 문서 추가
		/*for (int i=1;i<=10;i++){
			String title = "init_title"+i;
			String contents = "init_contents"+i;
			PostProfile upload = new PostProfile(title,contents);
			db.collection("post_gam").document(title).set(upload);
		}*/

		btNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				if (item.getItemId() == R.id.menuSearch) {
					Intent intent = new Intent(MainActivity.this, SearchActivity.class);
					startActivity(intent);
					return true;
				}
				return false;
			}
		});
		list = new ArrayList<PostProfile>();
		adapter = new PostProfileAdapter(this,list);

		Database dbDownload = new Database(adapter);
		dbDownload.downloadDB();

		recyclerView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_toolbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
