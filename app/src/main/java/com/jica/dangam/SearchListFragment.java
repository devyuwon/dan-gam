package com.jica.dangam;

import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
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
	FirebaseFirestore db = FirebaseFirestore.getInstance();


	public SearchListFragment() {

	}

	@Override
	public void onStart() {
		super.onStart();
		adapter = new PostProfileAdapter();
		RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(manager);
		//더미데이터
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
}