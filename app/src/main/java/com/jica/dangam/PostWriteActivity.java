package com.jica.dangam;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.ProgressDialog;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostWriteActivity extends AppCompatActivity {
	final int PICTURE_REQUEST_CODE = 100;
	boolean postKind = true;
	Button btnIlgam, btnIlgun;
	Button btnPlusGps;
	RadioGroup rg_post_state_modify;
	Button btnPostComplete;
	Button btnPostPicture;
	EditText title, contents;
	ArrayList<Uri> uriList = new ArrayList<>();
	RecyclerView rvPostImage; // 이미지를 보여줄 리사이클러뷰
	ImageAdapter adapter;
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReference();
	int completeCount = 0;
	int uploadCount = 0;
	int i =0;
	ProgressDialog progressDialog = null;

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
		title = findViewById(R.id.etPostTitle);
		contents = findViewById(R.id.etPostContent);

		adapter = new ImageAdapter(uriList, this); //getApplicationContext()
		rvPostImage.setAdapter(adapter);
		rvPostImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

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
				Intent intent = new Intent(PostWriteActivity.this, PostGpsActivity.class);
				startActivity(intent);
			}
		});

		//작성완료
		btnPostComplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), PostActivity.class);
				PostProfile post = new PostProfile(title.getText().toString(), contents.getText().toString());
				uploadCount = uriList.size();

				while (i<uriList.size()){
					UploadTask uploadTask = storageRef.child(post.getUid()+post.getPdate().toString()+i).putFile(uriList.get(i));
					uploadTask.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception exception) {
							// Handle unsuccessful uploads
							completeCount++;
							if(completeCount == uploadCount){
								Log.d("TAG", "결과: 실패!  업로드할 갯수:" + uploadCount +" 현재완료된 갯수:" + completeCount);
								//대화상자를 보이지 않게한다.
								progressDialog.dismiss();
							}
						}
					}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							completeCount++;
							//업로드된 화일의 uri를 구해온다.
							Log.d("TAG", taskSnapshot.getMetadata().getReference().toString());
							Log.d("TAG","현재 completeCount = "+completeCount);
							Log.d("TAG",storageRef.getDownloadUrl().toString());

							if (completeCount==1){
								post.setImageUrl1(taskSnapshot.getMetadata().getReference().toString());
							}else if (completeCount==2){
								post.setImageUrl2(taskSnapshot.getMetadata().getReference().toString());
							}else if (completeCount==3){
								post.setImageUrl3(taskSnapshot.getMetadata().getReference().toString());
							}
							if(completeCount == uploadCount){
								Log.d("TAG", "결과 성공!! 업로드할 갯수:" + uploadCount +" 현재완료된 갯수:" + completeCount);
								//대화상자를 보이지 않게한다.

								//글제목과 이미지경로 저장
								if(postKind){
									db.collection("post_gam")
										.document(post.getUid()+post.getPdate().toString())
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
								}else {
									db.collection("post_ggun")
										.document(post.getUid()+post.getPdate().toString())
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
								}
								progressDialog.dismiss();

								//액티비티 이동
								Intent intent = new Intent(getApplicationContext(), PostActivity.class);
								intent.putExtra("post",post); //인텐스에 객체 정보 저장
								startActivity(intent);
							}
						}
					});
					i++;
				}
				//위의 업로드 기능이 완료된 이후에 아래의 기능이 작동해야 한다.
				progressDialog = new ProgressDialog(PostWriteActivity.this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //진행율
				//                                    //빙글빙글
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
				//Toast.makeText(getApplicationContext(), "사진은 3장까지 첨부할 수 있습니다.", Toast.LENGTH_SHORT).show();
				onBtn_delete_no2Clicked(contents);
				return;
			} else {
				Log.e("PostWriteActivity", "Multiple Choice");

				for (int i = 0; i < clipData.getItemCount(); i++) {
					Uri imgUri = clipData.getItemAt(i).getUri();

					try {
						uriList.add(imgUri);
						//어탭터에서 사용하는 원본데이타를 추가했으므로
						//어탭터에서 원본데이타의 정보에 맞추어 새로 화면을 뿌려주라고 알려준다.
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
}