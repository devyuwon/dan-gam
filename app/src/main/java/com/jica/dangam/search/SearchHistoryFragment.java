package com.jica.dangam.search;

import com.jica.dangam.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchHistoryFragment extends Fragment {



	String[] history;
	TextView history1;
	TextView history2;
	TextView history3;
	TextView history4;
	TextView history5;


	public SearchHistoryFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_history, container, false);
		return view;
	}
}
