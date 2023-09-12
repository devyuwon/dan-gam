package com.jica.dangam.search;

import com.jica.dangam.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SearchFragment extends Fragment {
	SearchView searchView;
	SearchHistoryFragment searchHistoryFragment;
	SearchResultFragment searchResultFragment;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		
		searchView = view.findViewById(R.id.search);

		searchHistoryFragment = new SearchHistoryFragment();
		searchResultFragment = new SearchResultFragment();
		FrameLayout layout = view.findViewById(R.id.container);

		FragmentManager manager = getChildFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		transaction.add(R.id.container, searchHistoryFragment, "History");
		transaction.commit();

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				if (s != null) {
					Bundle bundle = new Bundle(1);
					bundle.putString("SearchWord", s);
					searchResultFragment.setArguments(bundle);
					FragmentTransaction transaction = manager.beginTransaction();

					Log.d("TAG", "Convert to SearchListFragment");
					transaction.replace(R.id.container, searchResultFragment, "List");
					transaction.commit();
				}
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				return false;
			}
		});
		return view;
	}
}
