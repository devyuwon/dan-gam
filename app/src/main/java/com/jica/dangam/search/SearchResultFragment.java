package com.jica.dangam.search;

import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jica.dangam.R;
import com.jica.dangam.list.ListModel;
import com.jica.dangam.list.ListAdapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SearchResultFragment extends Fragment {
	RecyclerView recyclerView;
	ListAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<ListModel> list = new ArrayList<>();
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	String modString = "post_gam";
	Date lastDoc;
	Query next;
	SwipeRefreshLayout scroll;
	Button btnGam;
	Button btnGgun;

	public SearchResultFragment() {
		Log.d("TAG", "SearchListFragment()...");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		Log.d("TAG", "SearchListFragment()...onCreateView()");

		View view = inflater.inflate(R.layout.fragment_search_list, container, false);

		scroll = (SwipeRefreshLayout)view.findViewById(R.id.searchRefresh);
		recyclerView = (RecyclerView)view.findViewById(R.id.rv_search_list);
		linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),
			LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);

		// 어탭터 생성
		adapter = new ListAdapter(getActivity(), list);

		recyclerView.setAdapter(adapter);

		Bundle bundle = getArguments();
		String searchWord;
		searchWord = bundle.getString("SearchWord");
		loadResult(searchWord);

		btnGam = view.findViewById(R.id.btnSearchResultGam);
		btnGgun = view.findViewById(R.id.btnSearchResultGgun);

		btnGam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				modString = "post_gam";
				btnGam.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF8000")));
				btnGgun.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DFE2E4")));
				adapter.clearData();
				loadResult(searchWord);
			}
		});
		btnGgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				modString = "post_ggun";
				btnGam.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DFE2E4")));
				btnGgun.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF8000")));
				adapter.clearData();
				loadResult(searchWord);
			}
		});

		scroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				adapter.clearData();
				loadResult(searchWord);
				scroll.setRefreshing(false);
			}
		});

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if(!recyclerView.canScrollVertically(1)){
					loadNext(searchWord);
				}
			}
		});


		// Inflate the layout for this fragment
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	private boolean hasText(String data, String word) {
		return data.contains(word);
	}

	public void loadResult(String keyWord){
		db.collection(modString).orderBy("pdate", Query.Direction.DESCENDING).limit(10)
			.get()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							ListModel profile = document.toObject(ListModel.class);
							if(hasText(profile.getTitle(),keyWord)){
								adapter.addItem(profile);
							}
							lastDoc = profile.getPdate();
						}
						adapter.notifyDataSetChanged();
					}
				}
			});
		next = db.collection(modString).orderBy("pdate", Query.Direction.DESCENDING).startAfter(lastDoc).limit(10);

	}

	public void loadNext(String keyWord){
		Log.d("TAG","loadNext called");
		next.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {
				if (task.isSuccessful()) {
					for (QueryDocumentSnapshot document : task.getResult()) {
						ListModel profile = document.toObject(ListModel.class);
						if(hasText(profile.getTitle(),keyWord)){
							adapter.addItem(profile);
						}
						lastDoc = profile.getPdate();
					}
					adapter.notifyDataSetChanged();
				} else {
					Log.d("DB", "Error getting documents: ", task.getException());
				}
			}
		});
		next = db.collection(modString).orderBy("pdate").startAfter(lastDoc).limit(10);
	}
}
