package com.jica.dangam;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.util.Log;
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
	ArrayList<PostProfile> list = new ArrayList<>();

	public SearchListFragment() {
		Log.d("TAG", "SearchListFragment()...");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		Log.d("TAG", "SearchListFragment()...onCreateView()");

		View view = inflater.inflate(R.layout.fragment_search_list, container, false);

		recyclerView = (RecyclerView)view.findViewById(R.id.rv_search_list);
		linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),
			LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);

		// 어탭터 생성
		adapter = new PostProfileAdapter(getActivity(), list);  //list는 데이타가 없다.

		recyclerView.setAdapter(adapter);

		// Inflate the layout for this fragment
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("TAG", "SearchListFragment()..onStart().");
		FirebaseFirestore db = FirebaseFirestore.getInstance();

		Bundle bundle = getArguments();
		String searchWord;
		searchWord = bundle.getString("SearchWord");
		Log.d("TAG", searchWord);

		db.collection("post_gam").orderBy("pdate")
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						Log.d("TAG", "Success getting documents: ");
						for (QueryDocumentSnapshot document : task.getResult()) {
							PostProfile profile = document.toObject(PostProfile.class);
							if (hasText(profile.getContents(), searchWord)) {
								adapter.addItem(profile);
								Log.d("TAG", "adapter에 데이터 추가됨");
							}
							Log.d("TAG", document.getId() + " => " + document.getData());
							Log.d("TAG", profile.getTitle() + " " + profile.getContents());
						}
						adapter.notifyDataSetChanged();
					} else {
						Log.d("TAG", "Error getting documents: ", task.getException());
					}
				}
			});
	}

	private boolean hasText(String data, String word) {
		return data.contains(word);
	}
}