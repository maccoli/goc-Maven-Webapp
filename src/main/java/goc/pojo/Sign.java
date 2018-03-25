package goc.pojo;

public class Sign {

	private Integer userid;
	private String date;//签到日期
	private int times;//签到次数
	@Override
	public String toString() {
		return "Sign [userid=" + userid + ", date=" + date + ", times=" + times
				+ "]";
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	

}
