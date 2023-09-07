package com.jica.dangam.util;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetImageData {
	String imageURL;

	public GetImageData() {
		this.imageURL = "gs://dangam-6489e.appspot.com/KakaoTalk_20230904_132634484.jpg";
	}

	public GetImageData(String imageURL) {
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

