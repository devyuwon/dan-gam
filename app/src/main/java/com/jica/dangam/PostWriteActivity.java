package com.jica.dangam;

import java.io.File;
import java.util.ArrayList;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostWriteActivity extends AppCompatActivity {
	final int PICTURE_REQUEST_CODE = 100;
	String state;
	Button btnIlgam, btnIlgun;
	Button btnPlusGps;
	RadioGroup rg_post_state_modify;
	Button btnPostComplete;
	ImageView ivPostPicture;
	Button btnPostPicture;

	EditText title;
	EditText contents;
	Uri uri;
	ArrayList<Uri> uriList = new ArrayList<>();
	RecyclerView rvPostImage; // 이미지를 보여줄 리사이클러뷰
	ImageAdapter adapter;
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReference();

	String TAG = "postTest";
	int loadCount = 0;
	int count = 0;
	ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_write);

		btnIlgam = findViewById(R.id.btnIlgam);
		btnIlgun = findViewById(R.id.btnIlgun);

		btnPlusGps = findViewById(R.id.btnPlusGps);

		btnPostComplete = findViewById(R.id.btnPostComplete);

		ivPostPicture = findViewById(R.id.ivPostPicture);
		btnPostPicture = findViewById(R.id.btnPostPicture);

		rvPostImage = findViewById(R.id.rvPostImage);
		title = findViewById(R.id.etPostTitle);
		contents = findViewById(R.id.etPostContent);

		// 유형 선택시 색상 변경
		btnIlgam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btnIlgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.orange_secondary));
				btnIlgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
			}
		});

		btnIlgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btnIlgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.green_light));
				btnIlgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
			}
		});

		btnPostPicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
				intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, PICTURE_REQUEST_CODE);
				// btnPostPicture.setVisibility(view.INVISIBLE);
				rvPostImage.setVisibility(view.VISIBLE);
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
		btnPlusGps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PostWriteActivity.this, PostGpsActivity.class);
				startActivity(intent);
			}
		});

		//작성완료
		btnPostComplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				PostProfile post = new PostProfile(title.getText().toString(), contents.getText().toString());

				//업로드할 이미지 화일의 갯수 구하기
				count = uriList.size();
				//업로드된 화일의 갯수
				loadCount = 0;

				for(Uri uri : uriList){
					UploadTask uploadTask = storageRef.child(uri.getLastPathSegment()).putFile(uri);
					uploadTask.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception exception) {
							// Handle unsuccessful uploads
							loadCount++;
							if(loadCount == count){
								Log.d("TAG", "결과: 실패!  업로드할 갯수:" + count +" 현재완료된 갯수:" + loadCount);
								//대화상자를 보이지 않게한다.
								progressDialog.dismiss();
								//글제목과 이미지경로 저장
								//db.collection("post_gam").document(post.getTitle()).set(post);
								//액티비티 이동
								Intent intent = new Intent(getApplicationContext(), PostActivity.class);
								startActivity(intent);
							}
						}
					}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							loadCount++;
							//업로드된 화일의 uri를 구해온다.
							Log.d("TAG", taskSnapshot.getMetadata().getReference().toString());
							post.setImageUrl1(taskSnapshot.getMetadata().getReference().toString());
							if(loadCount == count){
								Log.d("TAG", "결과 성공!! 업로드할 갯수:" + count +" 현재완료된 갯수:" + loadCount);
								//대화상자를 보이지 않게한다.
								progressDialog.dismiss();
								//글제목과 이미지경로 저장
								db.collection("post_gam")
									.document(post.getTitle())
									.set(post)
									.addOnSuccessListener(new OnSuccessListener<Void>() {
										@Override
										public void onSuccess(Void unused) {
											Log.d("TAG", "post내용 업로드 성공!");
										}
									}).addOnFailureListener(new OnFailureListener() {
										@Override
										public void onFailure(@NonNull Exception e) {
											Log.d("TAG", "post내용 업로드 실패");
										}
									});

								//액티비티 이동
								Intent intent = new Intent(getApplicationContext(), PostActivity.class);
								startActivity(intent);
							}
						}
					});

				}
				//위의 업로드 기능이 완료된 이후에 아래의 기능이 작동해야 한다.
				progressDialog = new ProgressDialog(PostWriteActivity.this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); //진행율
				//                              ProgressDialog.STYLE_SPINNER      //빙글빙글
				progressDialog.setMessage("이미지를 업로드 하는 ..");
				progressDialog.show();


			}
		});

	}

	//사진 불러오기(최대 3장)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data == null) {
			Toast.makeText(getApplicationContext(), "첨부할 사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
			return; // 바로 여기에서 반환합니다.
		}

		if (data.getClipData() == null) {
			if (uriList.size() >= 3) {
				Toast.makeText(getApplicationContext(), "사진은 3장까지 첨부할 수 있습니다.", Toast.LENGTH_SHORT).show();
				return;
			}
			Log.e("Single Choice: ", String.valueOf(data.getData()));
			Uri imageUri = data.getData();
			uriList.add(imageUri);

		} else {
			ClipData clipData = data.getClipData();
			Log.e("clipData", String.valueOf(clipData.getItemCount()));

			if (clipData.getItemCount() + uriList.size() > 3) { // 이미 리스트에 있는 이미지 수를 고려하여 확인
				Toast.makeText(getApplicationContext(), "사진은 3장까지 첨부할 수 있습니다.", Toast.LENGTH_SHORT).show();
				return;
			} else {
				Log.e("PostWriteActivity", "Multiple Choice");

				for (int i = 0; i < clipData.getItemCount(); i++) {
					Uri imgUri = clipData.getItemAt(i).getUri();

					try {
						uriList.add(imgUri);
						if (uriList.size() >= 3) {
							btnPostPicture.setVisibility(View.GONE);
						}
					} catch (Exception e) {
						Log.e("PostWriteActivity", "Image Select Error", e);
					}
				}
			}
		}

		adapter = new ImageAdapter(uriList, getApplicationContext());
		rvPostImage.setAdapter(adapter);
		rvPostImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
	}
}
