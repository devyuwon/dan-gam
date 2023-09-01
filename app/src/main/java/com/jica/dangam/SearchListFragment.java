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



		//더미데이터 영역
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));
		adapter.addItem(new PostProfile("더미제목1","더미제목2"));


		adapter.notifyDataSetChanged();



		recyclerView.setAdapter(adapter);

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		adapter = new PostProfileAdapter(getActivity(),arrayList);
		linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
		View view = inflater.inflate(R.layout.fragment_search_list,container,false);
		recyclerView = (RecyclerView) view.findViewById(R.id.rv_search_list);





		// Inflate the layout for this fragment
		return view;
	}
	private boolean hasText(String data, String word){
		return data.contains(word);
	}
}