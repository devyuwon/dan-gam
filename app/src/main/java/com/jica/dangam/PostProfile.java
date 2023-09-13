package com.jica.dangam;

import java.io.Serializable;
import java.util.Date;

public class PostProfile implements Serializable {
	private String title, contents, location, imageUrl1, imageUrl2, imageUrl3, uid;
	private Date pdate;
	private boolean state;

	public PostProfile(String uid, String title, String contents, String location, String imageUrl1, String imageUrl2,
		String imageUrl3,
		Date pdate, boolean state) {
		this.uid = uid;
		this.title = title;
		this.contents = contents;
		this.location = location;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.pdate = pdate;
		this.state = state;
	}

	public PostProfile() {
	}

	//test용
	public PostProfile(String title, String contents) {
		this.title = title;
		this.contents = contents;
		this.location = "";
		this.imageUrl1 = "";
		this.imageUrl2 = "";
		this.imageUrl3 = "";
		this.pdate = new Date();
		this.uid = "00000000";
		this.state = true;
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

	public String getImageUrl1() {
		return imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public String getImageUrl3() {
		return imageUrl3;
	}

	public Date getPdate() {
		return pdate;
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

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public void setImageUrl3(String immge3) {
		this.imageUrl3 = immge3;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}