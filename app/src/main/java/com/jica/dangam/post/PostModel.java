package com.jica.dangam.post;

import java.io.Serializable;
import java.util.Date;

import com.jica.dangam.list.ListModel;

public class PostModel implements Serializable {
	private String title, contents, location, imageUrl1, imageUrl2, imageUrl3, uid, id, reward;
	private Date pdate;
	private boolean state, deleted;

	public PostModel(String uid, String title, String contents, String location, String imageUrl1, String imageUrl2,
		String imageUrl3, String id, String reward,
		Date pdate, boolean state, boolean deleted) {
		this.uid = uid;
		this.title = title;
		this.contents = contents;
		this.location = location;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.pdate = pdate;
		this.state = state;
		this.id = id;
		this.reward = reward;
		this.deleted = deleted;
	}

	public PostModel(ListModel listModel) {
		this.uid = listModel.getUid();
		this.title = listModel.getTitle();
		this.contents = listModel.getContents();
		this.imageUrl1 = listModel.getImageUrl1();
		this.imageUrl2 = listModel.getImageUrl2();
		this.imageUrl3 = listModel.getImageUrl3();
		this.pdate = listModel.getPdate();
		this.state = listModel.getState();
		this.id = listModel.getId();
		this.reward = listModel.getReward();
		this.deleted = listModel.isDeleted();
	}

	public PostModel() {
	}

	//test용
	public PostModel(String title, String contents) {
		this.title = title;
		this.contents = contents;
		this.location = "";
		this.imageUrl1 = "";
		this.imageUrl2 = "";
		this.imageUrl3 = "";
		this.pdate = new Date();
		this.uid = "";
		this.state = true;
		this.id = "";
		this.reward = "";
		this.deleted = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getImageUrl3() {
		return imageUrl3;
	}

	public void setImageUrl3(String immge3) {
		this.imageUrl3 = immge3;
	}

	public Date getPdate() {
		return pdate;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
