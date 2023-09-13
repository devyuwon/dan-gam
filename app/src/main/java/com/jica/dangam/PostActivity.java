package com.jica.dangam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

public class PostActivity extends AppCompatActivity {

	Button btn_post_modify;
	ImageButton btn_post_delete;
	Button btn_post_menu;
	RadioGroup rg_post_state_modify;
	Button rg_post_close;
	TextView tv_post_title;
	TextView tv_post_content;

	private ViewPager2 sliderViewPager;
	private LinearLayout layoutIndicator;

	private String[] images = new String[] {
		"https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
		"https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
		"https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
		"https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
		"https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		Intent intent = getIntent();
		PostProfile post = (PostProfile)intent.getSerializableExtra("post");

		sliderViewPager = findViewById(R.id.vpPhoto);
		layoutIndicator = findViewById(R.id.layoutIndicators);

		sliderViewPager.setOffscreenPageLimit(1);
		sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

		sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				setCurrentIndicator(position);
			}
		});

		setupIndicators(images.length);

		//UI객체 찾기
		btn_post_modify = findViewById(R.id.btnPostModify);
		btn_post_delete = findViewById(R.id.btnPostDelete);
		btn_post_menu = findViewById(R.id.btn_post_menu);
		rg_post_state_modify = findViewById(R.id.rgPostStateModify);
		rg_post_close = findViewById(R.id.rgPostClose);
		tv_post_title = findViewById(R.id.tvPostTitle);
		tv_post_content = findViewById(R.id.tv_post_content);

		//글정보 뿌려주기
		tv_post_title.setText(post.getTitle());
		tv_post_content.setText(post.getContents());

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

	//activity_post.xml 에서 show_dialog_btn 의 onclick 메소드
	public void show_default_dialog(View v) {
		//클릭시 defaultDialog 를 띄워준다
		PostDeleteDialog.getInstance(this).showDefaultDialog();
	}
}