package com.jica.dangam.util;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageUploadData {
	private String url;
	public String fileName;
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReference();

	public ImageUploadData(String fileName) {
		this.fileName = fileName+".jpg";
	}
	StorageReference imageRef = storageRef.child(fileName);

}
