package com.jica.dangam;

public class PostProfile {

	private int postNumber;
	private String title;
	private String contents;
	private String location;

	//구현 예정
	//private String image;

	public PostProfile(){
		this.postNumber=0;
		this.title="No Title";
		this.contents="No Contents";
		this.location="missing";
	}

	//test용 title, contents만 있는 생성자
	public PostProfile(String title, String contents){
		this.title=title;
		this.contents=contents;
		this.postNumber=0;
		this.location="";
	}

	public PostProfile(int postNumber, String title, String contents, String location){
		this.postNumber=postNumber;
		this.title=title;
		this.contents=contents;
		this.location=location;
		//this.image=image;
	}
	//getter
	public int getPostNumber(){
		return postNumber;
	}
	public String getTitle(){
		return title;
	}
	public String getContents(){
		return contents;
	}
	public String getLocation(){
		return location;
	}

	//구현 예정
	//public String getImage(){
	//	return image;
	//}

	//setter

	public void setPostNumber(int postNumber){
		this.postNumber=postNumber;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void  setContents(String contents){
		this.contents=contents;
	}
	public void setLocation(String location){
		this.location=location;
	}

	//구현 예정
	//public void setImage(String image){
	//	this.image=image;
	//}
}
