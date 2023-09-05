package com.jica.dangam;

import java.util.Date;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostProfile {
	private String title, contents, location, image1, image2, image3;
	private Date pdate, sdate, edate;
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference gsReference1 = storage.getReferenceFromUrl(image1);
	StorageReference gsReference2 = storage.getReferenceFromUrl(image2);
	StorageReference gsReference3 = storage.getReferenceFromUrl(image3);

	public PostProfile(String title, String contents, String location, String image1, String image2, String image3,
		Date pdate, Date sdate, Date edate) {
		this.title = title;
		this.contents = contents;
		this.location = location;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.pdate = pdate;
		this.sdate = sdate;
		this.edate = edate;
	}

	public PostProfile() {
	}

	//test용
	public PostProfile(String title, String contents) {
		this.title = title;
		this.contents = contents;
		this.location = "";
		this.image1 = "";
		this.image2 = "";
		this.image3 = "";
		this.pdate = new Date();
		this.sdate = new Date();
		this.edate = new Date();
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public String getLocation() {
		return location;
	}

	public String getImage1() {
		return image1;
	}

	public String getImage2() {
		return image2;
	}

	public String getImage3() {
		return image3;
	}

	public Date getPdate() {
		return pdate;
	}

	public Date getSdate() {
		return sdate;
	}

	public Date getEdate() {
		return edate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public void setImage3(String immge3) {
		this.image3 = immge3;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}
}