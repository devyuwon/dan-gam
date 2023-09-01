package com.jica.dangam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

public class PostWriteActivity extends AppCompatActivity {
	String state;
	Button btn_ilgam, btn_ilgun;
	Button btn_plus_gps;
	RadioGroup rg_post_state_modify;
	Button btn_post_complete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_write);

		btn_ilgam = findViewById(R.id.btn_ilgam);
		btn_ilgun = findViewById(R.id.btn_ilgun);

		btn_plus_gps = findViewById(R.id.btn_plus_gps);

		btn_post_complete = findViewById(R.id.btn_post_complete);

		// 유형 선택시 색상 변경
		btn_ilgam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.secondary));
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey));
			}
		});
		btn_ilgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.ligthgreen));
				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey));
			}
		});


	/*	//모집 상태
		radioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				state = "모집전";
				radioButton.isChecked();
			}
		});*/

		//모집 희망 장소
		btn_plus_gps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PostWriteActivity.this, PostGpsActivity.class);
				startActivity(intent);
			}
		});

		//작성완료
		btn_post_complete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), PostActivity.class);
				startActivity(intent);
			}
		});

	}

}
