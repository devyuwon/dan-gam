package com.jica.dangam.post;

import static android.content.Intent.*;
import static androidx.concurrent.futures.AbstractResolvableFuture.*;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.jica.dangam.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.util.Log;

public class PostDeleteDialog extends Dialog {

	private static PostDeleteDialog customDialog;

	private PostDeleteDialog(@NonNull Context context) {
		super(context);
	}

	public static PostDeleteDialog getInstance(Context context) {
		customDialog = new PostDeleteDialog(context);

		return customDialog;
	}

	//다이얼로그 생성하기
	public void showDefaultDialog() {

		//참조할 다이얼로그 화면을 연결한다.
		customDialog.setContentView(R.layout.activity_post_dialog);

		//다이얼로그의 구성요소들이 동작할 코드작성

		//삭제 버튼
		Button btn_delete_yes = customDialog.findViewById(R.id.btnDeleteYes);
		Button btn_delete_no = customDialog.findViewById(R.id.btnDeleteNo);
		btn_delete_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btn_delete_no.setOnClickListener(clickCancel);
		customDialog.show();
	}

	//취소버튼을 눌렀을때 일반적인 클릭리스너
	View.OnClickListener clickCancel = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			//토스트
			onBtn_delete_no2Clicked(v);
			customDialog.dismiss();
		}
	};

	//커스텀 토스트 띄우기
	public void onBtn_delete_no2Clicked(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getContext());
		text11.setText("아니요를 눌렀습니다");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
}
