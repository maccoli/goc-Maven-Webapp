package goc.pojo;

import java.sql.Date;

public class WeekSummary {

	private Integer userid;
	private String weeksummary;//每周小结
	private Date date;//日期
	@Override
	public String toString() {
		return "WeekSummary [userid=" + userid + ", weeksummary=" + weeksummary
				+ ", date=" + date + "]";
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getWeeksummary() {
		return weeksummary;
	}
	public void setWeeksummary(String weeksummary) {
		this.weeksummary = weeksummary;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
}
