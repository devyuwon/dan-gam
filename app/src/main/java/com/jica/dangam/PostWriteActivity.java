package com.jica.dangam;

// import com.bumptech.glide.Glide;
// import com.bumptech.glide.annotation.GlideModule;
// import com.bumptech.glide.request.target.CustomTarget;
// import com.bumptech.glide.request.transition.Transition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

public class PostWriteActivity extends AppCompatActivity {
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
		// Registers a photo picker activity launcher in multi-select mode.
		// In this example, the app lets the user select up to 5 media files.
		ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
			registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), uris -> {
				// Callback is invoked after the user selects media items or closes the
				// photo picker.
				if (!uris.isEmpty()) {
					Log.d("PhotoPicker", "Number of items selected: " + uris.size());
				} else {
					Log.d("PhotoPicker", "No media selected");
				}
			});
		/*
		btn_post_picture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pickMultipleMedia.launch(PickVisualMediaRequest());

			}
		});
		*/



/*
		//갤러리에서 이미지 불러오기 - 객체선언
		ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(),
			new ActivityResultCallback<ActivityResult>() {
				@Override
				public void onActivityResult(ActivityResult result) {
					if (result.getResultCode() == RESULT_OK && result.getData() != null) {

						uri = result.getData().getData();

						try {
							Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
							iv_post_picture.setImageBitmap(bitmap);

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}
		);

		//사진불러오기
		btn_post_picture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityResult.launch(intent);

			}
		});



*/


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

}
