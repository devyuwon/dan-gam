package com.jica.dangam.map;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jica.dangam.R;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

public class MapBottomSheetDialog extends BottomSheetDialogFragment {
	@Override
	public void onStart() {
		super.onStart();

		Dialog dialog = getDialog();
		if (dialog != null) {
			View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
			if (bottomSheet != null) {
				BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
				behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
			}
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_map_bottom_sheet, container, false);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int pageHeight = displayMetrics.heightPixels;
		int px = dpToPx(75);

		view.setMinimumHeight(pageHeight - px);

		return view;
	}

	public int dpToPx (int dp) {
		return (int) (dp * getResources().getDisplayMetrics().density);
	}
}
