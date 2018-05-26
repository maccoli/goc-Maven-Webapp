package goc.pojo;

public class Fresource {

	private int contentid;
	private int courseid;
	private String content;//该课程下的内容/测试/任务等
	private String time;//建议学习时间
	private String contenturl;//内容链接
	private String book;//链接的话，则是图片，或者测试的话，则是具体内容
	private int browsings;//浏览量
	@Override
	public String toString() {
		return "Fresource [contentid=" + contentid + ", courseid=" + courseid
				+ ", content=" + content + ", time=" + time + ", contenturl="
				+ contenturl + ", book=" + book + ", browsings=" + browsings
				+ "]";
	}
	public int getContentid() {
		return contentid;
	}
	public void setContentid(int contentid) {
		this.contentid = contentid;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContenturl() {
		return contenturl;
	}
	public void setContenturl(String contenturl) {
		this.contenturl = contenturl;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public int getBrowsings() {
		return browsings;
	}
	public void setBrowsings(int browsings) {
		this.browsings = browsings;
	}
	
}
