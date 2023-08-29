package com.jica.dangam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PostProfile {

	public String title, contents, location, image1, image2, image3;
	public Date pdate, sdate, edate;

	public PostProfile() {
	}

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

	public PostProfile(String title, String contents) {
		this.title = title;
		this.contents = contents;
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

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
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
	@Exclude
	public Map<String,Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("title", title);
		result.put("contents", contents);
		result.put("location", location);
		result.put("image1", image1);
		result.put("image2", image2);
		result.put("image3", image3);
		result.put("pdate", pdate);
		result.put("sdate", sdate);
		result.put("edate", edate);
		FieldValue.serverTimestamp();
		return result;
	}
}

