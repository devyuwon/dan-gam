package com.jica.dangam.mypage;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.jica.dangam.R;
import com.jica.dangam.login.GoogleAccountHelper;
import com.jica.dangam.login.GoogleAuthHelper;
import com.jica.dangam.main.MainActivity;
import com.jica.dangam.map.MapBottomSheetDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageFragment extends Fragment {
	View.OnClickListener logoutListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			AlertDialog.Builder logoutDialog = new AlertDialog.Builder(getContext());
			logoutDialog.setIcon(R.drawable.img_symbol02);
			logoutDialog.setTitle("로그아웃");
			logoutDialog.setMessage("로그아웃 할까요?");

			logoutDialog.setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					GoogleAuthHelper.accountLogout(getContext(), (MainActivity)getActivity());
				}
			});

			logoutDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
			logoutDialog.show();
		}
	};
	View.OnClickListener deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
			deleteDialog.setIcon(R.drawable.img_symbol02);
			deleteDialog.setTitle("회원 탈퇴");
			deleteDialog.setMessage("회원 탈퇴 하시겠습니까?");

			deleteDialog.setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					GoogleAuthHelper.accountDelete(getContext(), (MainActivity)getActivity());
				}
			});

			deleteDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
			deleteDialog.show();
		}
	};

	private CircleImageView ivUserProfilePhoto;
	private TextView tvUserName;
	private Button btnLogout, btnDelete, btnLocation;
	private List<String> userInfo = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_page, container, false);

		ivUserProfilePhoto = view.findViewById(R.id.ivUserProfilePhoto);
		tvUserName = view.findViewById(R.id.tvUserName);
		btnLogout = view.findViewById(R.id.btnLogout);
		btnDelete = view.findViewById(R.id.btnDelete);
		btnLocation = view.findViewById(R.id.btnLocation);

		initView();

		btnLogout.setOnClickListener(logoutListener);
		btnDelete.setOnClickListener(deleteListener);

		btnLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MapBottomSheetDialog bottomSheet = new MapBottomSheetDialog();
				bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
			}
		});

		return view;
	}

	private void initView() {
		GoogleAccountHelper googleAccountHelper = new GoogleAccountHelper();
		userInfo = GoogleAccountHelper.getGoogleUserLoginInfo();

		Glide.with(this)
				.load(userInfo.get(2))
					.error(R.drawable.img_profile_sample)
						.into(ivUserProfilePhoto);

		tvUserName.setText(userInfo.get(1));
	}
}
