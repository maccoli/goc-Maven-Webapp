package goc.pojo;

import java.sql.Date;

public class Resource {

	private Integer userid;
	private String writer;
	private String content;//内容or简介？
	private Date date;
	private String type;
	private String text;
	@Override
	public String toString() {
		return "Resource [userid=" + userid + ", writer=" + writer
				+ ", content=" + content + ", date=" + date + ", type=" + type
				+ ", text=" + text + "]";
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
