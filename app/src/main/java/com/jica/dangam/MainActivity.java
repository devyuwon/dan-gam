package com.jica.dangam;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

		//업로드 테스트 및 초기 문서 추가
		/*for (int i=1;i<=10;i++){
			String title = "init_title"+i;
			String contents = "init_contents"+i;
			PostProfile upload = new PostProfile(title,contents);
			db.collection("post_gam").document(title).set(upload);
		}*/



		db.collection("post_gam").orderBy("pdate")
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
