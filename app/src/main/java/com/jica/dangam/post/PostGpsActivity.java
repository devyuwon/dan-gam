package com.jica.dangam.post;

import com.jica.dangam.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PostGpsActivity extends AppCompatActivity {

	Button btn_completion, btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_gps);

		//UI객체 찾기
		btn_back = findViewById(R.id.btnPostBack);
		btn_completion = findViewById(R.id.btnGpsCompletion);

		//Intent intent = getIntent();

		//뒤로가기 버튼 클릭시
		btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		//확인 버튼 클릭시
		btn_completion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
}
