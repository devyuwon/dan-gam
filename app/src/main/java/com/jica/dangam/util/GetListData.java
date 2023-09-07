package com.jica.dangam.util;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jica.dangam.list.ListModel;
import com.jica.dangam.list.ListAdapter;
import android.util.Log;
import androidx.annotation.NonNull;

public class GetListData {
	private ListAdapter adapter;
	public GetListData(ListAdapter adapter) {
		this.adapter=adapter;
	}

	private ArrayList<ListModel> list;

	public void downloadDB() {
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection("post_gam").orderBy("pdate")
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
		Log.d("TAG","searchDB start... keyword : "+keyWord);
		db.collection("post_gam").orderBy("pdate")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						Log.d("TAG","search complete");
						for (QueryDocumentSnapshot document : task.getResult()) {
							ListModel profile = document.toObject(ListModel.class);
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
