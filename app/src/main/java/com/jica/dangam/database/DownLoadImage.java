package com.jica.dangam.database;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class DownLoadImage {
	String imageURL;

	public DownLoadImage() {
		this.imageURL = "gs://dangam-6489e.appspot.com/KakaoTalk_20230904_132634484.jpg";
	}

	public DownLoadImage(String imageURL) {
		this.imageURL = imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference gsReference = storage.getReferenceFromUrl(imageURL);

	public StorageReference getReference(){
		return this.gsReference;
	}

	public void setImageOnView(Context context, ImageView view){
		Glide.with(context).load(gsReference).into(view);
	}
}

