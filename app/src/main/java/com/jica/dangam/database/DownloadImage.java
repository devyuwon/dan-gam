package com.jica.dangam.database;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DownloadImage {
	String imageURL;

	public DownloadImage() {
		this.imageURL = "gs://dangam-6489e.appspot.com/KakaoTalk_20230904_132634484.jpg";
	}

	public DownloadImage(String imageURL) {
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
}

