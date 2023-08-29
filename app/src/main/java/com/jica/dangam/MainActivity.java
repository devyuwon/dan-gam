package com.jica.dangam;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.util.Log;
import android.view.Menu;
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
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	String sort = "pdate";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list_liner);

		Toolbar tb = (Toolbar) findViewById(R.id.mainToolbar) ;
		setSupportActionBar(tb) ;

		recyclerView = findViewById(R.id.main_recyclerview);

		linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
		recyclerView.setLayoutManager(linearLayoutManager);

		adapter = new PostProfileAdapter();


		//test용 더미데이터

		adapter.addItem(new PostProfile("제목1","내용1"));
		adapter.addItem(new PostProfile("제목2","내용2"));
		adapter.addItem(new PostProfile("제목3","내용3"));
		adapter.addItem(new PostProfile("제목4","내용4"));
		adapter.addItem(new PostProfile("제목5","내용5"));
		adapter.addItem(new PostProfile("제목6","내용6"));
		adapter.addItem(new PostProfile("제목7","내용7"));
		adapter.addItem(new PostProfile("제목8","내용8"));
		adapter.addItem(new PostProfile("제목9","내용9"));
		adapter.addItem(new PostProfile("제목10","내용10"));
		adapter.addItem(new PostProfile("제목11","내용11"));

		adapter.addAll(getDbData());



		recyclerView.setAdapter(adapter);

		//db 추가코드 예제(정상작동)
	/*	CollectionReference cities = db.collection("post_gam");

		Map<String, Object> data1 = new HashMap<>();
		data1.put("name", "San Francisco");
		data1.put("state", "CA");
		data1.put("country", "USA");
		data1.put("capital", false);
		data1.put("population", 860000);
		data1.put("regions", Arrays.asList("west_coast", "norcal"));
		cities.document("SF").set(data1);
*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_toolbar, menu) ;
		return super.onCreateOptionsMenu(menu);
	}

	public ArrayList<PostProfile> getDbData(){
		ArrayList<PostProfile> result = new ArrayList<>();
		db.collection("post_gam")
			.orderBy(sort, Query.Direction.DESCENDING).limit(1)
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							result.add(document.toObject(PostProfile.class));
							Log.d("getFirestore","getting complete"+document.getId());
						}
					} else {
						Log.d("getFirestore", "Error getting documents: ", task.getException());
					}
				}
			});
		return result;
	}
}
