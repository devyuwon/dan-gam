package com.jica.dangam.main;

import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jica.dangam.R;
import com.jica.dangam.list.ListAdapter;
import com.jica.dangam.list.ListModel;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainFragment extends Fragment {
	RecyclerView recyclerView;
	ListAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<ListModel> list;
	Context context;
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	private FragmentManager fragmentManager;
	private SwipeRefreshLayout scroll;
	String modString = "post_gam";
	Date lastDoc;
	Query next;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		Toolbar tb = view.findViewById(R.id.mainToolbar);
		((AppCompatActivity)getActivity()).setSupportActionBar(tb);

		recyclerView = view.findViewById(R.id.main_recyclerview);
		linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);
		list = new ArrayList<>();
		adapter = new ListAdapter(context, list);
		loadList();

		recyclerView.setAdapter(adapter);

		fragmentManager = getChildFragmentManager();

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if(!recyclerView.canScrollVertically(1)){
					Log.d("TAG", "스크롤 최하단");
					loadNext();
				}
			}
		});

		scroll = (SwipeRefreshLayout)view.findViewById(R.id.mainRefresh);
		scroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				adapter.clearData();
				loadList();
				scroll.setRefreshing(false);
			}
		});


		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.common_toolbar, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
	public void loadList(){
		db.collection(modString).orderBy("pdate")
			.limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							ListModel profile = document.toObject(ListModel.class);
							adapter.addItem(profile);
							lastDoc = profile.getPdate();
							Log.d("TAG","lastDoc 값 저장");
						}
						adapter.notifyDataSetChanged();
					} else {
						Log.d("DB", "Error getting documents: ", task.getException());
					}
				}
			});
		next = db.collection(modString).orderBy("pdate").startAfter(lastDoc).limit(10);

	}

	public void loadNext(){
		Log.d("TAG","loadNext called");
		next.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							ListModel profile = document.toObject(ListModel.class);
							adapter.addItem(profile);
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
