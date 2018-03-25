package goc.pojo;

import java.sql.Date;

public class Project {

	private Integer userid;
	private String text;//项目文档存放地址
	private String video;//项目视频存放地址
	private Date date;//提交日期
	private int state;//1--已上，2--我要实践
	private String title;//内容标题
	private String type;//内容类型
	@Override
	public String toString() {
		return "Project [userid=" + userid + ", text=" + text + ", video="
				+ video + ", date=" + date + ", state=" + state + ", title="
				+ title + ", type=" + type + "]";
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
