package com.jica.dangam.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jica.dangam.R;
import com.jica.dangam.main.MainActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostCreateActivity extends AppCompatActivity {
	final int PICTURE_REQUEST_CODE = 100;
	boolean postKind = true;
	Button btnIlgam, btnIlgun, btnPlusGps, btnPostComplete, btnPostPicture, btnPostBack;
	EditText etReward, title, contents;
	ArrayList<Uri> uriList = new ArrayList<>();
	RecyclerView rvPostImage;
	PostImageAdapter adapter;
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	String documentUid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_write);

		//UI객체 찾기
		btnIlgam = findViewById(R.id.btnIlgam);
		btnIlgun = findViewById(R.id.btnIlgun);
		btnPlusGps = findViewById(R.id.btnPlusGps);
		btnPostComplete = findViewById(R.id.btnPostComplete);
		btnPostPicture = findViewById(R.id.btnPostPicture);
		rvPostImage = findViewById(R.id.rvPostImage);
		title = findViewById(R.id.etPostModifyTitle);
		contents = findViewById(R.id.etPostContent);
		etReward = findViewById(R.id.etReward);
		btnPostBack = findViewById(R.id.btnPostBack);

		// 유형 선택 - 일감
		btnIlgam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btnIlgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.orange_secondary));
				btnIlgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
				postKind = true;
			}
		});
		// 유형 선택 - 일꾼
		btnIlgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btnIlgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.green_light));
				btnIlgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
				postKind = false;
			}
		});

		//사진첨부
		adapter = new PostImageAdapter(uriList, this);
		rvPostImage.setAdapter(adapter);
		rvPostImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
		btnPostPicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
				intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, PICTURE_REQUEST_CODE);
				rvPostImage.setVisibility(view.VISIBLE);
			}
		});

		//모집 희망 장소
		btnPlusGps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PostCreateActivity.this, PostGpsActivity.class);
				startActivity(intent);
			}
		});

		//작성완료
		btnPostComplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String titleText = title.getText().toString();
				String contentText = contents.getText().toString();
				String rewardText = etReward.getText().toString();

				if (titleText.isEmpty()) {
					warning_notitle(contents);    //제목 미입력시 안내 토스트
					return;
				}

				if (contentText.isEmpty()) {
					warning_nocontents(contents);    //제목 미입력시 안내 토스트
					return;
				}
				//post정보를 담을 post 모델 생성
				PostModel post = new PostModel();

				//post모델에 정보 담기
				post.setTitle(title.getText().toString());
				post.setContents(contents.getText().toString());
				Date now = new Date();
				post.setPdate(now);

				FirebaseAuth mAuth = FirebaseAuth.getInstance();
				if (mAuth.getCurrentUser() != null) {
					post.setUid(mAuth.getCurrentUser().getUid());
				}

				if (rewardText.isEmpty()) {
					post.setReward("상의 후 결정");
				} else {
					post.setReward(etReward.getText().toString());
				}
				post.setDeleted(false);

				documentUid = post.getUid() + now.getTime();
				getImgUri(post, documentUid, 0);

				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.putExtra("posttype", post.getPosttype());
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
	}

	//이미지 storage에 저장하기
	private void getImgUri(PostModel post, String documentUid, Integer i) {
		if (uriList.size() == 0) {
			postdatas(post);
		} else {
			StorageReference storageReference = FirebaseStorage.getInstance().getReference();
			StorageReference uploadRef = storageReference.child(documentUid + "/" + i);
			UploadTask uploadTask = uploadRef.putFile(uriList.get(i));
			uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
				@Override
				public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
					if (!task.isSuccessful()) {
						throw task.getException();
					}
					return uploadRef.getDownloadUrl();
				}
			}).addOnCompleteListener(new OnCompleteListener<Uri>() {
				@Override
				public void onComplete(@NonNull Task<Uri> task) {
					if (task.isSuccessful()) {
						Uri uri = task.getResult();
						if (i == 0) {
							post.setImageUrl1(task.getResult().toString());
						} else if (i == 1) {
							post.setImageUrl2(task.getResult().toString());
						} else if (i == 2) {
							post.setImageUrl3(task.getResult().toString());
						}
						if (i + 1 < uriList.size()) {
							getImgUri(post, documentUid, i + 1);
						} else {
							postdatas(post);
						}
					}
				}
			});
		}
	}

	//일감 일꾼 유형분리
	private void postdatas(PostModel post) {
		if (postKind) {
			DocumentReference addedDocRef = db.collection("post_gam").document();
			Map<String, Object> data = new HashMap<>();
			data.put("id", addedDocRef.getId());
			data.put("posttype", "post_gam");
			addedDocRef.set(post);
			addedDocRef.update(data);
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.putExtra("post", post);
			startActivity(intent);
			finish();
		} else {
			DocumentReference addedDocRef = db.collection("post_ggun").document();
			Map<String, Object> data = new HashMap<>();
			data.put("id", addedDocRef.getId());
			data.put("posttype", "post_ggun");
			addedDocRef.set(post);
			addedDocRef.update(data);
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.putExtra("post", post);
			startActivity(intent);
			finish();
		}
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
				onBtn_delete_no2Clicked(contents);
				return;
			}
			
			Uri imageUri = data.getData();
			uriList.add(imageUri);

		} else {
			ClipData clipData = data.getClipData();
			Log.e("clipData", String.valueOf(clipData.getItemCount()));

			if (clipData.getItemCount() + uriList.size() > 3) {
				onBtn_delete_no2Clicked(contents);
			} else {
				Log.e("PostWriteActivity", "Multiple Choice");

				for (int i = 0; i < clipData.getItemCount(); i++) {
					Uri imgUri = clipData.getItemAt(i).getUri();

					try {
						uriList.add(imgUri);
						//어탭터에서 사용하는 원본데이타를 추가했으므로 어탭터에서 원본데이타의 정보에 맞추어 새로 화면을 구성하라게함
						adapter.notifyDataSetChanged();
						Log.d("TAG", "현재 사진 갯수 : " + uriList.size());
						if (uriList.size() >= 3) {
							btnPostPicture.setVisibility(View.GONE);
						} else {
							btnPostPicture.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						Log.e("PostWriteActivity", "Image Select Error", e);
					}
				}
			}
		}

	}

	//커스텀 토스트 - 사진 갯수 제한 안내
	public void onBtn_delete_no2Clicked(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getApplicationContext());
		text11.setText("사진은 3장까지 첨부할 수 있습니다.");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	//커스텀 토스트 - 제목 미입력시 안내
	public void warning_notitle(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getApplicationContext());
		text11.setText("제목을 입력해주세요.");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	//커스텀 토스트 - 내용 미입력시 안내
	public void warning_nocontents(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getApplicationContext());
		text11.setText("내용을 입력해주세요.");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
}
