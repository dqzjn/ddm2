package cn.zmdx.locker.entity;

import java.sql.Timestamp;

public class Data_img_table {

	private int id;
	private String title;
	private String url;
	private String imgUrl;
	private Timestamp release_time;
	private Timestamp collect_time;
	private int top;
	private int step;
	private String collect_website;
	private String data_type;
	private String type;
	private int data_sub;
	private int userid;
	private int views;
	private int data_view;
	private String stick;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Timestamp getRelease_time() {
		return release_time;
	}

	public void setRelease_time(Timestamp release_time) {
		this.release_time = release_time;
	}

	public Timestamp getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Timestamp collect_time) {
		this.collect_time = collect_time;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getCollect_website() {
		return collect_website;
	}

	public void setCollect_website(String collect_website) {
		this.collect_website = collect_website;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public int getData_sub() {
		return data_sub;
	}

	public void setData_sub(int data_sub) {
		this.data_sub = data_sub;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getData_view() {
		return data_view;
	}

	public void setData_view(int data_view) {
		this.data_view = data_view;
	}

	public String getStick() {
		return stick;
	}

	public void setStick(String stick) {
		this.stick = stick;
	}

}
