package cn.zmdx.locker.entity;

import java.sql.Timestamp;


public class WallPaper {
	
	private int id;
	private String p_name;
	private String p_desc;
	private String p_author;
	private String thumbURL;
	private String imageURL;
	private String imageNAME;
	private String imageEXT;
	private Timestamp publishDATE;
	private int data_sub;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_desc() {
		return p_desc;
	}
	public void setP_desc(String p_desc) {
		this.p_desc = p_desc;
	}
	public String getP_author() {
		return p_author;
	}
	public void setP_author(String p_author) {
		this.p_author = p_author;
	}
	public String getThumbURL() {
		return thumbURL;
	}
	public void setThumbURL(String thumbURL) {
		this.thumbURL = thumbURL;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getImageNAME() {
		return imageNAME;
	}
	public void setImageNAME(String imageNAME) {
		this.imageNAME = imageNAME;
	}
	public String getImageEXT() {
		return imageEXT;
	}
	public void setImageEXT(String imageEXT) {
		this.imageEXT = imageEXT;
	}
	public Timestamp getPublishDATE() {
		return publishDATE;
	}
	public void setPublishDATE(Timestamp publishDATE) {
		this.publishDATE = publishDATE;
	}
	public int getData_sub() {
		return data_sub;
	}
	public void setData_sub(int data_sub) {
		this.data_sub = data_sub;
	}
	
}
