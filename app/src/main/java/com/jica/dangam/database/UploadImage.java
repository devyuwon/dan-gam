package com.jica.dangam.database;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadImage {
	private String url;
	public String fileName;
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReference();

	public UploadImage(String fileName) {
		this.fileName = fileName+".jpg";
	}
	StorageReference imageRef = storageRef.child(fileName);

}
