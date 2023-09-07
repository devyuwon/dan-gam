package com.jica.dangam.list;

import java.util.Date;

public class ListModel {
	private String title, contents, location, imageUrl1, imageUrl2, imageUrl3;
	private Date pdate, sdate, edate;
	private Byte image1, image2, image3;

	public ListModel(String title, String contents, String location, String imageUrl1, String imageUrl2,
		String imageUrl3,
		Date pdate, Date sdate, Date edate) {
		this.title = title;
		this.contents = contents;
		this.location = location;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.pdate = pdate;
		this.sdate = sdate;
		this.edate = edate;
	}

	public ListModel() {
	}

	//test용
	public ListModel(String title, String contents) {
		this.title = title;
		this.contents = contents;
		this.location = "";
		this.imageUrl1 = "";
		this.imageUrl2 = "";
		this.imageUrl3 = "";
		this.pdate = new Date();
		this.sdate = new Date();
		this.edate = new Date();
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

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public Byte getImage1() {
		return image1;
	}

	public void setImage1(Byte image1) {
		this.image1 = image1;
	}

	public Byte getImage2() {
		return image2;
	}

	public void setImage2(Byte image2) {
		this.image2 = image2;
	}

	public Byte getImage3() {
		return image3;
	}

	public void setImage3(Byte image3) {
		this.image3 = image3;
	}
}
