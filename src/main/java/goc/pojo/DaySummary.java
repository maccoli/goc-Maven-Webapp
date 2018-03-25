package goc.pojo;

public class DaySummary {

	private Integer userid;
	private String daysummary;//每日小结
	private String date;//日期
	@Override
	public String toString() {
		return "DaySummary [userid=" + userid + ", daysummary=" + daysummary
				+ ", date=" + date + "]";
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
