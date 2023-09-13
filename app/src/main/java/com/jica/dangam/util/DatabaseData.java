package com.jica.dangam.util;

import java.util.ArrayList;

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
import com.jica.dangam.list.ListModel;
import com.jica.dangam.list.ListAdapter;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

public class DatabaseData {
	private ListAdapter adapter;
	public DatabaseData(ListAdapter adapter) {
		this.adapter=adapter;
	}
	private int completeCount = 0;

	public void downloadDB(boolean mod) {
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		String modString;
		if(mod){
			modString = "post_gam";
		}else {
			modString = "post_ggun";
		}
		db.collection(modString).orderBy("pdate")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							ListModel profile = document.toObject(ListModel.class);
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
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection("post_gam").orderBy("pdate")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							ListModel profile = document.toObject(ListModel.class);
							switch (mode){
								case 0:
									if(hasText(profile.getTitle(),keyWord)){
										adapter.addItem(profile);
									}
									break;
								case 1:
									if(hasText(profile.getContents(),keyWord)){
										adapter.addItem(profile);
									}
									break;
							}
							adapter.notifyDataSetChanged();
						}
					} else {
					}
				}
			});
	}

	//코드 수정중(호출하여 사용 불가)
	public void Upload(ArrayList<Uri> uriList, ListModel post){
		FirebaseStorage storage = FirebaseStorage.getInstance();
		StorageReference storageRef = storage.getReference();
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		final int uploadCount = uriList.size();
		for(Uri uri : uriList){
			UploadTask uploadTask = storageRef.child(uri.getLastPathSegment()).putFile(uri);
			uploadTask.addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception exception) {
					// 이미지 업로드 실패시
					completeCount++;
					if(completeCount == uploadCount){

					}
				}
			}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
				//이미지 업로드 성공시
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
					completeCount++;
					post.setImageUrl1(taskSnapshot.getMetadata().getReference().toString());
					if(completeCount == uploadCount){
						//이미지URL을 객체에 저장하여 데이터베이스에 객체 저장
						db.collection("post_gam")
							.document(post.getTitle())
							.set(post)
							.addOnSuccessListener(new OnSuccessListener<Void>() {
								@Override
								public void onSuccess(Void unused) {
								}
							}).addOnFailureListener(new OnFailureListener() {
								@Override
								public void onFailure(@NonNull Exception e) {
								}
							});
					}
				}
			});

		}


	}
	private boolean hasText(String data, String word) {
		return data.contains(word);
	}
}
