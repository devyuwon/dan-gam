package com.jica.dangam;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DownLoadImage {
	String imageURL1;
	String imageURL2;
	String imageURL3;

	public DownLoadImage() {
		this.imageURL1="gs://dangam-6489e.appspot.com/KakaoTalk_20230904_132634484.jpg";
		this.imageURL2="";
		this.imageURL3="";
	}

	public DownLoadImage(String imageURL1) {
		this.imageURL1 = imageURL1;
	}

	public DownLoadImage(String imageURL1, String imageURL2) {
		this.imageURL1 = imageURL1;
		this.imageURL2 = imageURL2;
	}

	public DownLoadImage(String imageURL1, String imageURL2, String imageURL3) {
		this.imageURL1 = imageURL1;
		this.imageURL2 = imageURL2;
		this.imageURL3 = imageURL3;
	}

	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");


}
