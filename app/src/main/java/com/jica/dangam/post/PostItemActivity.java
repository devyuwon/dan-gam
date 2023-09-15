package com.jica.dangam.post;

import com.jica.dangam.R;

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

public class PostItemActivity extends AppCompatActivity {

	Button btn_post_modify;
	ImageButton btn_post_delete;
	Button btn_post_menu;
	RadioGroup rg_post_state_modify;
	Button rg_post_close;
	TextView tv_post_title;
	TextView tv_post_content;

	private ViewPager2 sliderViewPager;
	private LinearLayout layoutIndicator;
	private String[] images = new String[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_item);

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
				Intent intent = new Intent(PostItemActivity.this, PostModifyActivity.class);
				startActivity(intent);
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

	//activity_post.xml 에서 show_dialog_btn 의 onclick 메소드
	public void show_default_dialog(View v) {
		//클릭시 defaultDialog 를 띄워준다
		PostDeleteDialog.getInstance(this).showDefaultDialog();
	}
}
