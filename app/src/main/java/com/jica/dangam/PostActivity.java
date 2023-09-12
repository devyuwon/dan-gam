package com.jica.dangam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

	Button btn_post_modify;
	ImageButton btn_post_delete;
	Button btn_post_menu;
	RadioGroup rg_post_state_modify;
	Button rg_post_close;
	TextView tv_post_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		//UI객체 찾기
		btn_post_modify = findViewById(R.id.btnPostModify);
		btn_post_delete = findViewById(R.id.btnPostDelete);
		btn_post_menu = findViewById(R.id.btn_post_menu);
		rg_post_state_modify = findViewById(R.id.rgPostStateModify);
		rg_post_close = findViewById(R.id.rgPostClose);
		tv_post_title = findViewById(R.id.tvPostTitle);

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
	}

	//activity_post.xml 에서 show_dialog_btn 의 onclick 메소드
	public void show_default_dialog(View v) {
		//클릭시 defaultDialog 를 띄워준다
		CustomDialog.getInstance(this).showDefaultDialog();
	}
}