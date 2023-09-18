package com.jica.dangam.post;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jica.dangam.R;
import com.jica.dangam.main.MainActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

public class PostModifyActivity extends AppCompatActivity {

	Button btn_post_back, btn_ilgam, btn_ilgun, btn_plus_gps, btn_post_complete;
	EditText etPostModifyTitle, etPostContent, etReward;
	private FirebaseFirestore db;
	boolean postKind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_modify);

		db = FirebaseFirestore.getInstance();

		Intent intent = getIntent();
		PostModel post = (PostModel)intent.getSerializableExtra("post");

		//UI객체찾기
		btn_post_back = findViewById(R.id.btnPostBack);
		btn_ilgam = findViewById(R.id.btnIlgam);
		btn_ilgun = findViewById(R.id.btnIlgun);
		btn_plus_gps = findViewById(R.id.btnPlusGps);
		btn_post_complete = findViewById(R.id.btnPostComplete);
		etPostModifyTitle = findViewById(R.id.etPostModifyTitle);
		etPostContent = findViewById(R.id.etPostContent);
		etReward = findViewById(R.id.etReward);

		//글정보 뿌려주기
		String modifyTitle = intent.getStringExtra("title");
		String modifyContents = intent.getStringExtra("contents");
		String modifyReward = intent.getStringExtra("reward");
		String modifyId = intent.getStringExtra("id");
		String posttype = intent.getStringExtra("posttype");

		etPostModifyTitle.setText(modifyTitle);    //제목
		etPostContent.setText(modifyContents);    //내용
		etReward.setText(modifyReward);    //수행비

		if (posttype.equals("post_ggun")) {
			btn_ilgam.setText("일꾼");
			btn_ilgam.setBackgroundTintList(
				AppCompatResources.getColorStateList(getApplicationContext(), R.color.green_light));
		}

		//뒤로가기 버튼
		btn_post_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		//유형 선택 - 일감
		btn_ilgam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.orange_secondary));
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
				postKind = true;
			}
		});

		//유형 선택 - 일꾼
		btn_ilgun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				btn_ilgam.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.grey_10));
				btn_ilgun.setBackgroundTintList(
					AppCompatResources.getColorStateList(getApplicationContext(), R.color.green_light));
				postKind = false;
			}
		});

		//위치추가 버튼
		btn_plus_gps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PostModifyActivity.this, PostGpsActivity.class);
				startActivity(intent);
			}
		});

		//작성완료
		btn_post_complete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				modifyPostgam(modifyId, posttype);

				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	public void modifyPostgam(String modifyId, String Collection) {
		String subject = etPostModifyTitle.getText().toString();
		String content = etPostContent.getText().toString();
		String reward = etReward.getText().toString();
		String documentId = modifyId;

		if (subject.isEmpty()) {
			waring_title(etPostContent);    // 제목 미입력 안내토스트
			return;
		}
		if (content.isEmpty()) {
			waring_contents(etPostContent);        //내용 미입력 안내토스트
			return;
		}
		Map<String, Object> updatePost = new HashMap<>();
		updatePost.put("title", subject);
		updatePost.put("contents", content);
		updatePost.put("reward", reward);
		//

		db.collection(Collection)
			.document(documentId)
			.update(updatePost)
			.addOnSuccessListener(new OnSuccessListener<Void>() {
				@Override
				public void onSuccess(Void unused) {
					etPostModifyTitle.setText("");
					etPostContent.setText("");
					etReward.setText("");
					modify_completed(etPostContent);
				}
			})
			.addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception e) {
				}
			});
	}

	//커스텀 토스트 - 수정완료 안내
	public void waring_title(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getApplicationContext());
		text11.setText("제목을 입력하세요");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	//커스텀 토스트 - 수정완료 안내
	public void waring_contents(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getApplicationContext());
		text11.setText("내용을 입력하세요.");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	//커스텀 토스트 - 수정완료 안내
	public void modify_completed(View view) {
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(
			R.layout.toast_layout,
			(ViewGroup)findViewById(R.id.toast_layout));

		TextView text11 = layout.findViewById(R.id.tvToast);
		Toast toast = new Toast(getApplicationContext());
		text11.setText("수정이엘 완료되었습니다.");
		text11.setTextSize(15);
		text11.setTextColor(Color.WHITE);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
}
