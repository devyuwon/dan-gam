package com.jica.dangam.map;

import java.util.Arrays;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jica.dangam.R;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;

public class MapBottomSheetDialog extends BottomSheetDialogFragment implements OnMapReadyCallback {
	private FusedLocationSource locationSource;
	private NaverMap mNaverMap;
	private static final int PERMISSION_REQUEST_CODE = 100;
	private static final String[] PERMISSION = {
		Manifest.permission.ACCESS_FINE_LOCATION,
		Manifest.permission.ACCESS_COARSE_LOCATION
	};

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

		FragmentManager fragmentManager = getChildFragmentManager();
		MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
		if (mapFragment == null) {
			mapFragment = MapFragment.newInstance();
			fragmentManager.beginTransaction().add(R.id.map, mapFragment).commit();
		}

		mapFragment.getMapAsync(this);
		locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

		return view;
	}

	@Override
	public void onMapReady(@NonNull NaverMap naverMap) {
		mNaverMap = naverMap;
		mNaverMap.setLocationSource(locationSource);
		ActivityCompat.requestPermissions(getActivity(), PERMISSION, PERMISSION_REQUEST_CODE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
		@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
			}
		}
	}

	public int dpToPx (int dp) {
		return (int) (dp * getResources().getDisplayMetrics().density);
	}
}
