package com.jica.dangam;

import static android.content.ContentValues.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

		db.collection("post_gam")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							PostProfile profile = document.toObject(PostProfile.class);
							adapter.addItem(profile);
							Log.d("firestore", document.getId() + " => " + document.getData());
							Log.d("object test",profile.getTitle()+" "+profile.getContents());
						}
						adapter.notifyDataSetChanged();
					} else {
						Log.d("firestore", "Error getting documents: ", task.getException());
					}
				}
			});



		recyclerView.setAdapter(adapter);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_toolbar, menu) ;
		return super.onCreateOptionsMenu(menu);
	}
}
