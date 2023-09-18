package com.jica.dangam.map;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jica.dangam.R;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.util.FusedLocationSource;

public class MapBottomSheetDialog extends BottomSheetDialogFragment implements OnMapReadyCallback {
	private static final int PERMISSION_REQUEST_CODE = 100;
	private static final String[] PERMISSION = {
		Manifest.permission.ACCESS_FINE_LOCATION,
		Manifest.permission.ACCESS_COARSE_LOCATION
	};
	private FusedLocationSource locationSource;
	private NaverMap naverMap;

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
		MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
		if (mapFragment == null) {
			mapFragment = MapFragment.newInstance();
			fragmentManager.beginTransaction().add(R.id.map, mapFragment).commit();
		}

		mapFragment.getMapAsync(this);
		locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

		return view;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
		@NonNull int[] grantResults) {

		if (!locationSource.isActivated()) {
			naverMap.setLocationTrackingMode(LocationTrackingMode.None);
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override
	public void onMapReady(@NonNull NaverMap naverMap) {
		this.naverMap = naverMap;
		naverMap.setLocationSource(locationSource);
		naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

		UiSettings uiSettings = this.naverMap.getUiSettings();
		uiSettings.setLocationButtonEnabled(true);

		LocationOverlay locationOverlay = this.naverMap.getLocationOverlay();
		locationOverlay.setVisible(true);

		ActivityCompat.requestPermissions(getActivity(), PERMISSION, PERMISSION_REQUEST_CODE);
	}

	public int dpToPx(int dp) {
		return (int)(dp * getResources().getDisplayMetrics().density);
	}
}
