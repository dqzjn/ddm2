package cn.zmdx.locker.entity;

import java.sql.Timestamp;


public class Data_table {
	
	private int id;
	private String content;
	private int top;
	private int step;
	private Timestamp collect_time;
	private Timestamp release_time;
	private String collect_website;
	private String title;
	private String data_type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Timestamp getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(Timestamp collect_time) {
		this.collect_time = collect_time;
	}
	public Timestamp getRelease_time() {
		return release_time;
	}
	public void setRelease_time(Timestamp release_time) {
		this.release_time = release_time;
	}

	public String getCollect_website() {
		return collect_website;
	}
	public void setCollect_website(String collect_website) {
		this.collect_website = collect_website;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	
	
}
