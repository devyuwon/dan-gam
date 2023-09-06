package com.jica.dangam;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PostActivity extends AppCompatActivity {

	Button btn_post_modify;
	ImageButton btn_post_delete;
	Button btn_post_menu;
	ConstraintLayout delete_popup;
	Button btn_delete_yes, btn_delete_no;
	RadioGroup rg_post_state_modify;
	Button rg_post_close;
	FrameLayout lo_post_main;
	TextView tv_post_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		String test = getIntent().getStringExtra("푸바오");

		btn_post_modify = findViewById(R.id.btn_post_modify);
		//btn_post_modify.setText(test);
		btn_post_delete = findViewById(R.id.btn_post_delete);

		btn_post_menu = findViewById(R.id.btn_post_menu);
		rg_post_state_modify = findViewById(R.id.rg_post_state_modify);
		rg_post_close = findViewById(R.id.rg_post_close);

		delete_popup = (ConstraintLayout)findViewById(R.id.delete_popup);
		btn_delete_no = findViewById(R.id.btn_delete_yes);
		btn_delete_no = findViewById(R.id.btn_delete_no);

		lo_post_main = findViewById(R.id.lo_post_main);

		tv_post_title = findViewById(R.id.tv_post_title);

		//메뉴버튼
		btn_post_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				rg_post_state_modify.setVisibility(view.VISIBLE);
			}
		});
		//라디오 그룹 닫기
		rg_post_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				rg_post_state_modify.setVisibility(view.INVISIBLE);
			}
		});

		//수정 버튼
		btn_post_modify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//글 수정하기 화면 전환
				Intent intent = new Intent(PostActivity.this, PostModifyActivity.class);
				startActivity(intent);

			}
		});


		/*
		//삭제버튼
		btn_post_delete.setOnClickListener(new View.OnClickListener() {
			@Override


				//삭제 팝업 띄우기
				delete_popup.setVisibility(view.VISIBLE);

				//삭제팝업 - 아니요
				btn_delete_no.setOnClickListener(new View.OnClickListener() {

					//팝업닫기
					@Override
					public void onClick(View view) {
						delete_popup.setVisibility(view.INVISIBLE);
					}

				});
				//	lo_post_main.setVisibility(view.VISIBLE);

			}

		*/

	}

	;

	//activity_main.xml 에서 show_dialog_btn 의 onclick 메소드
	public void show_default_dialog(View v) {
		//클릭시 defaultDialog 를 띄워준다
		CustomDialog.getInstance(this).showDefaultDialog();
	}

}