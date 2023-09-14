package com.jica.dangam.post;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.protobuf.Value;
import com.jica.dangam.R;
import com.jica.dangam.main.MainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

public class PostItemActivity extends AppCompatActivity {

	Button btnPostModify;
	ImageButton btnPostDelete;
	Button btnPostMenu;
	RadioGroup rg_post_state_modify;
	Button rg_post_close;
	TextView tv_post_title;
	TextView tv_post_content;
	Button btnPostBack;

	TextView tvDelete, tvPostModify;

	private ViewPager2 sliderViewPager;
	private LinearLayout layoutIndicator;
	private String[] images = new String[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_item);

		FirebaseAuth mAuth = FirebaseAuth.getInstance();
		//FirebaseStorage db = FirebaseStorage.getInstance();
		FirebaseFirestore db = FirebaseFirestore.getInstance();

		Intent intent = getIntent();
		PostModel post = (PostModel)intent.getSerializableExtra("post");

		images[0] = post.getImageUrl1();
		images[1] = post.getImageUrl2();
		images[2] = post.getImageUrl3();

		sliderViewPager = findViewById(R.id.vpPhoto);
		layoutIndicator = findViewById(R.id.layoutIndicators);

		sliderViewPager.setOffscreenPageLimit(1);
		PostItemImageSliderAdapter postItemImageSliderAdapter = new PostItemImageSliderAdapter(this);

		for (int i = 0; i < 3; i++) {
			if (!images[i].equals("")) {
				postItemImageSliderAdapter.addItem(images[i]);
			}
		}
		sliderViewPager.setAdapter(postItemImageSliderAdapter);

		sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				setCurrentIndicator(position);
			}
		});

		setupIndicators(images.length);

		//UI객체 찾기
		btnPostModify = findViewById(R.id.btnPostModify);
		btnPostDelete = findViewById(R.id.btnPostDelete);
		btnPostMenu = findViewById(R.id.btnPostMenu);
		rg_post_state_modify = findViewById(R.id.rgPostStateModify);
		rg_post_close = findViewById(R.id.rgPostClose);
		tv_post_title = findViewById(R.id.tvPostTitle);
		tv_post_content = findViewById(R.id.tv_post_content);
		btnPostBack = findViewById(R.id.btnPostBack);
		tvDelete = findViewById(R.id.tvDelete);
		tvPostModify = findViewById(R.id.tvPostModify);

		//DB 연결

		//글정보 뿌려주기
		tv_post_title.setText(post.getTitle());
		tv_post_content.setText(post.getContents());

		//현재 로그인 Uid와 글작성 Uid가 같을 시 delete 버튼 생성

		if (post.getUid().equals(mAuth.getCurrentUser().getUid())) {
			//삭제
			btnPostDelete.setVisibility(View.VISIBLE);
			tvDelete.setVisibility(View.VISIBLE);

			//수정
			btnPostModify.setVisibility(View.VISIBLE);
			tvPostModify.setVisibility(View.VISIBLE);

			//라디오버튼
			btnPostMenu.setVisibility(View.VISIBLE);

		}

		//메뉴버튼
		btnPostMenu.setOnClickListener(new View.OnClickListener() {
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
		btnPostModify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//수정하기
				Intent intent = new Intent(PostItemActivity.this, PostModifyActivity.class);
				startActivity(intent);
			}
		});

		//뒤로 가기
		btnPostBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		//삭제
		btnPostDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//삭제 데이터
				Map<String, Object> delete = new HashMap<>();
				delete.put("deleted", true);

				AlertDialog.Builder builder = new AlertDialog.Builder(PostItemActivity.this);
				builder.setMessage("정말로 삭제하시겠습니까?");
				//긍정적 성격의 버튼 이벤트 핸들러              ButtonPositive(-1)
				builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						db.collection("post_gam").document(String.valueOf(post.getId())).update(delete)
							.addOnCompleteListener(task -> {
								if (task.isSuccessful()) {
								}
							});
						Intent intent = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(intent);
					}
				});
				//부정적 성격의 버튼                ButtonNegative(-2)
				builder.setNegativeButton("아니오", null);
				// true일 경우  : 대화상자 버튼이 아닌 배경 및 back 버튼 눌렀을때도 종료하도록 하는 기능
				// false일 경우 : 대화상자의 버튼으로만 대화상자가 종료하도록 하는 기능
				builder.setCancelable(false);

				//대화상자 만들기
				//주의사항 : 대화상자가 보여진 이후의 코드가 실행된다.
				AlertDialog alertDialog = builder.create();
				alertDialog.show(); //대화상자 보이기

			}
		});

	}

	//슬라이더 인디케이터
	private void setupIndicators(int count) {
		ImageView[] indicators = new ImageView[count];
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		params.setMargins(16, 8, 16, 8);

		for (int i = 0; i < indicators.length; i++) {
			indicators[i] = new ImageView(this);
			indicators[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.custom_bg_indicator_inactive));
			indicators[i].setLayoutParams(params);
			layoutIndicator.addView(indicators[i]);
		}
		setCurrentIndicator(0);

	}

	private void setCurrentIndicator(int position) {
		int childCount = layoutIndicator.getChildCount();
		for (int i = 0; i < childCount; i++) {
			ImageView imageView = (ImageView)layoutIndicator.getChildAt(i);
			if (i == position) {
				imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.custom_bg_indicator_active));
			} else {
				imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.custom_bg_indicator_inactive));
			}
		}
	}
	/*
	//activity_post.xml 에서 show_dialog_btn 의 onclick 메소드
	public void show_default_dialog(View v) {
		//클릭시 defaultDialog 를 띄워준다
		PostDeleteDialog.getInstance(this).showDefaultDialog();
	}

	 */
}


