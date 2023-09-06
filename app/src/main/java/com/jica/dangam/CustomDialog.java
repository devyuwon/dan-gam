package com.jica.dangam;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

/*
직접 커스텀한 다이얼로그들을 띄워주고 다이얼로그 안에서의 동작을 정의하는 클래스 (싱글톤)
 */
public class CustomDialog extends Dialog {

	private static CustomDialog customDialog;

	private CustomDialog(@NonNull Context context) {
		super(context);
	}

	public static CustomDialog getInstance(Context context) {
		customDialog = new CustomDialog(context);

		return customDialog;
	}

	//다이얼로그 생성하기
	public void showDefaultDialog() {
		//참조할 다이얼로그 화면을 연결한다.
		customDialog.setContentView(R.layout.activity_post_dialog);

		//다이얼로그의 구성요소들이 동작할 코드작성

		Button btn_delete_yes3 = customDialog.findViewById(R.id.btn_delete_yes3);
		btn_delete_yes3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		Button btn_delete_no2 = customDialog.findViewById(R.id.btn_delete_no2);
		btn_delete_no2.setOnClickListener(clickCancel);
		customDialog.show();
	}

	//취소버튼을 눌렀을때 일반적인 클릭리스너
	View.OnClickListener clickCancel = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getContext(), "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
			customDialog.dismiss();
		}
	};

}