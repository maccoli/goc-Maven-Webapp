package goc.pojo;

import java.sql.Date;

public class DaySummary {

	private Integer userid;
	private int score;//评分
	private String daysummary;//每日小结
	private Date date;//日期
	@Override
	public String toString() {
		return "DaySummary [userid=" + userid + ", score=" + score
				+ ", daysummary=" + daysummary + ", date=" + date + "]";
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getDaysummary() {
		return daysummary;
	}
	public void setDaysummary(String daysummary) {
		this.daysummary = daysummary;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	
	
}
