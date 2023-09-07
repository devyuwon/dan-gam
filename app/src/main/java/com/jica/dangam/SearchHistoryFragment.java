package com.jica.dangam;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchHistoryFragment extends Fragment {

	public SearchHistoryFragment() {
		// Required empty public constructor
		Log.d("TAG", "SearchHistoryFragment()...");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("TAG", "SearchHistoryFragment(). onCreate()..");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		Log.d("TAG", "SearchHistoryFragment(). onCreateView()..");
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_search_history, container, false);
	}
}