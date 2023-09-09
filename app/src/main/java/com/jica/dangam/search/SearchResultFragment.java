package com.jica.dangam.search;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jica.dangam.R;
import com.jica.dangam.list.ListModel;
import com.jica.dangam.list.ListAdapter;
import com.jica.dangam.util.GetListData;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultFragment extends Fragment {
	RecyclerView recyclerView;
	ListAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<ListModel> list = new ArrayList<>();

	public SearchResultFragment() {
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
		adapter = new ListAdapter(getActivity(), list);  //list는 데이타가 없다.

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
		GetListData database = new GetListData(adapter);
		database.searchDB(searchWord,1);

	}

	private boolean hasText(String data, String word) {
		return data.contains(word);
	}
}
