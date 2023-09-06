package com.jica.dangam;

import java.util.ArrayList;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostWriteActivity extends AppCompatActivity {
	final int PICTURE_REQUEST_CODE = 100;
	String state;
	Button btn_ilgam, btn_ilgun;
	Button btn_plus_gps;
	RadioGroup rg_post_state_modify;
	Button btn_post_complete;
	ImageView iv_post_picture;
	Button btn_post_picture;
	Uri uri;
	ArrayList<Uri> uriList = new ArrayList<>();
	RecyclerView rv_post_image; // 이미지를 보여줄 리사이클러뷰
	ImageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_write);

		btn_ilgam = findViewById(R.id.btn_ilgam);
		btn_ilgun = findViewById(R.id.btn_ilgun);

		btn_plus_gps = findViewById(R.id.btn_plus_gps);

		btn_post_complete = findViewById(R.id.btn_post_complete);

		iv_post_picture = findViewById(R.id.iv_post_picture);
		btn_post_picture = findViewById(R.id.btn_post_picture);

		rv_post_image = findViewById(R.id.rv_post_image);

		// 유형 선택시 색상 변경
		btn_ilgam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.orange_secondary));
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
			}
		});

		btn_ilgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.green_light));
				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
			}
		});

		btn_post_picture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
				intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, PICTURE_REQUEST_CODE);
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
				intent.putExtra("푸바오", "안녕하세요");
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data == null) {
			Toast.makeText(getApplicationContext(), "첨부할 사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
		} else {
			if (data.getClipData() == null) {
				Log.e("Single Choice: ", String.valueOf(data.getData()));
				Uri imageUri = data.getData();
				uriList.add(imageUri);

				adapter = new ImageAdapter(uriList, getApplicationContext());
				rv_post_image.setAdapter(adapter);
				rv_post_image.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
			} else {
				ClipData clipData = data.getClipData();
				Log.e("clipData", String.valueOf(clipData.getItemCount()));

				if (clipData.getItemCount() > 4) {
					Toast.makeText(getApplicationContext(), "사진은 3장까지 첨부할 수 있습니다.", Toast.LENGTH_SHORT).show();
				} else {
					Log.e("PostWriteActivity", "Multiple Choice");

					for (int i = 0; i < clipData.getItemCount(); i++) {
						Uri imgUri = clipData.getItemAt(i).getUri();

						try {
							uriList.add(imgUri);
						} catch (Exception e) {
							Log.e("PostWriteActivity", "Image Select Error", e);
						}
					}

					adapter = new ImageAdapter(uriList, getApplicationContext());
					rv_post_image.setAdapter(adapter);
					rv_post_image.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
				}
			}
		}
	}
}
