package goc.pojo;

import java.sql.Date;

public class Collection {

	private Integer userid;//收藏者ID
	private String writer;//内容作者
	private String type;//标签、类别
	private String title;//内容标题
	private Date date;//收藏的日期
	@Override
	public String toString() {
		return "Collection [userid=" + userid + ", writer=" + writer
				+ ", type=" + type + ", title=" + title + ", date=" + date
				+ "]";
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	                                                                                                                                                                                                            
}
