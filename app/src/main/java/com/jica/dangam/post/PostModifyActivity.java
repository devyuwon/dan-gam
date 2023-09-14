package com.jica.dangam.post;

import com.jica.dangam.R;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.content.res.AppCompatResources;

public class PostModifyActivity extends AppCompatActivity {

	Button btn_post_back;
	Button btn_ilgam, btn_ilgun;
	Button btn_plus_gps;
	Button btn_post_complete;
	EditText etPostTitle;
	EditText etPostContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_modify);

		Intent intent = getIntent();
		PostModel post = (PostModel)intent.getSerializableExtra("post");

		//UI객체찾기
		btn_post_back = findViewById(R.id.btnPostBack);
		btn_ilgam = findViewById(R.id.btnIlgam);
		btn_ilgun = findViewById(R.id.btnIlgun);
		btn_plus_gps = findViewById(R.id.btnPlusGps);
		btn_post_complete = findViewById(R.id.btnPostComplete);
		etPostTitle = findViewById(R.id.etPostTitle);
		etPostContent = findViewById(R.id.etPostContent);

		//글정보 뿌려주기
		etPostTitle.setText(post.getTitle());
		etPostContent.setText(post.getContents());

		//뒤로가기 버튼
		btn_post_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(PostModifyActivity.this, PostItemActivity.class);
				startActivity(intent);
			}
		});

		//유형 선택 - 일감
		btn_ilgam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.orange_secondary));
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
			}
		});

		//유형 선택 - 일꾼
		btn_ilgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.green_light));
			}
		});

		//위치추가 버튼
		btn_plus_gps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PostModifyActivity.this, PostGpsActivity.class);
				startActivity(intent);
			}
		});

		//작성완료 버튼
		btn_post_complete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PostModifyActivity.this, PostItemActivity.class);
				startActivity(intent);
			}
		});

	}

}
