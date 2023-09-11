package com.jica.dangam.main;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jica.dangam.R;
import com.jica.dangam.list.ListAdapter;
import com.jica.dangam.list.ListModel;
import com.jica.dangam.mypage.MyPageFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment {
	RecyclerView recyclerView;
	ListAdapter adapter;
	LinearLayoutManager linearLayoutManager;
	ArrayList<ListModel> list;
	Context context;
	FirebaseFirestore db = FirebaseFirestore.getInstance();

	// MyPageFragment
	private MyPageFragment myPageFragment;
	private FragmentManager fragmentManager;

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

		db.collection("post_gam").orderBy("pdate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {
				if (task.isSuccessful()) {
					for (QueryDocumentSnapshot document : task.getResult()) {
						ListModel profile = document.toObject(ListModel.class);
						adapter.addItem(profile);
						Log.d("firestore", document.getId() + " => " + document.getData());
						Log.d("object test", profile.getTitle() + " " + profile.getContents());
					}
					adapter.notifyDataSetChanged();
				} else {
					Log.d("firestore", "Error getting documents: ", task.getException());
				}
			}
		});
		recyclerView.setAdapter(adapter);

		fragmentManager = getChildFragmentManager();

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.common_toolbar, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
