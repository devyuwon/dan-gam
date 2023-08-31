package com.jica.dangam;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchListFragment extends Fragment {

	RecyclerView recyclerView;
	PostProfileAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<PostProfile> arrayList = new ArrayList<>();


	public SearchListFragment() {

	}

	@Override
	public void onStart() {
		super.onStart();
		FirebaseFirestore db = FirebaseFirestore.getInstance();

		Bundle bundle = getArguments();
		String searchWord;
		searchWord = bundle.getString("SearchWord");
		Log.d("searchWord",searchWord);
		adapter = new PostProfileAdapter();

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_list,container,false);
		recyclerView = (RecyclerView) view.findViewById(R.id.rv_search_list);





		// Inflate the layout for this fragment
		return view;
	}
	private boolean hasText(String data, String word){
		return data.contains(word);
	}
}