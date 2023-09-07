package com.jica.dangam.database;

import java.util.ArrayList;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jica.dangam.PostProfile;
import com.jica.dangam.PostProfileAdapter;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

public class Database {
	private PostProfileAdapter adapter;
	public Database(PostProfileAdapter adapter) {
		this.adapter=adapter;
	}

	private ArrayList<PostProfile> list;
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReference();
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	UploadTask uploadTask;


	public void uploadImage(Uri uri){
		uploadTask = storageRef.child(uri.getLastPathSegment()).putFile(uri);
		uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

			}
		});
	}

	public void downloadDB() {
		db.collection("post_gam").orderBy("pdate")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							PostProfile profile = document.toObject(PostProfile.class);
							adapter.addItem(profile);
						}
						adapter.notifyDataSetChanged();
					} else {
						Log.d("DB", "Error getting documents: ", task.getException());
					}
				}
			});
	}
	public void searchDB(String keyWord,int mode) {
		Log.d("TAG","searchDB start... keyword : "+keyWord);
		db.collection("post_gam").orderBy("pdate")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						Log.d("TAG","search complete");
						for (QueryDocumentSnapshot document : task.getResult()) {
							PostProfile profile = document.toObject(PostProfile.class);
							switch (mode){
								case 0:
									if(hasText(profile.getTitle(),keyWord)){
										adapter.addItem(profile);
										Log.d("TAG","add search complete");
									}
									break;
								case 1:
									if(hasText(profile.getContents(),keyWord)){
										adapter.addItem(profile);
										Log.d("TAG","add search complete");
									}
									break;
							}
							adapter.notifyDataSetChanged();
						}
						Log.d("TAG",".notifyDataSetChanged()작동");
						Log.d("TAG","adapter내의 자료 개수 : "+adapter.getItemCount());
					} else {
						Log.d("DB", "Error getting documents: ", task.getException());
					}
				}
			});
	}
	private boolean hasText(String data, String word) {
		return data.contains(word);
	}
}
