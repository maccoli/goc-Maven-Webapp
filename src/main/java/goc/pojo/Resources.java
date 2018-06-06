package goc.pojo;

import java.sql.Date;

import javax.ws.rs.HEAD;

public class Resources {

	private String writer;
	private String head;//作者头像
	private String title;//标题
	private Date date;
	private String type;//分类--企业--学习经验--资源
	private String text;//资源地址
	private String category;//类别--字符串
	private int browsings;//浏览量
	private int fabulous;//点赞数
	


	@Override
	public String toString() {
		return "Resources [writer=" + writer + ", head=" + head + ", title="
				+ title + ", date=" + date + ", type=" + type + ", text="
				+ text + ", category=" + category + ", browsings=" + browsings
				+ ", fabulous=" + fabulous + "]";
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
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


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getBrowsings() {
		return browsings;
	}


	public void setBrowsings(int browsings) {
		this.browsings = browsings;
	}


	public int getFabulous() {
		return fabulous;
	}


	public void setFabulous(int fabulous) {
		this.fabulous = fabulous;
	}


	public String getHead() {
		return head;
	}


	public void setHead(String head) {
		this.head = head;
	}

	
}
