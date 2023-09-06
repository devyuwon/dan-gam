package com.jica.dangam;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PostGpsActivity extends AppCompatActivity {

	Button btn_completion, btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_gps);

		btn_back = findViewById(R.id.btnPostBack);
		btn_completion = findViewById(R.id.btnGpsCompletion);

		Intent intent = getIntent();

		//뒤로가기 버튼 클릭시
		btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
				//Intent intent = new Intent(PostGpsActivity.this, PostWriteActivity.class);

				//startActivity(intent);
			}
		});

		//확인 버튼 클릭시
		btn_completion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
				//Intent intent = new Intent(PostGpsActivity.this, PostWriteActivity.class);
				//startActivity(intent);
			}
		});

	}
}