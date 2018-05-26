package goc.pojo;

public class FcourseChangeA {
	private int courseid;
	private int level;//课程等级
	private Integer tcourseid;//大于courseid级别
	private String coursename;//课程名称
	private String label;//标签
	private String klabel;//知识点标签
	private int astudydate;//
    private int state;//已经学了


	@Override
	public String toString() {
		return "FcourseChangeA [courseid=" + courseid + ", level=" + level
				+ ", tcourseid=" + tcourseid + ", coursename=" + coursename
				+ ", label=" + label + ", klabel=" + klabel + ", astudydate="
				+ astudydate + ", state=" + state + "]";
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getKlabel() {
		return klabel;
	}
	public void setKlabel(String klabel) {
		this.klabel = klabel;
	}
	public int getAstudydate() {
		return astudydate;
	}
	public void setAstudydate(int astudydate) {
		this.astudydate = astudydate;
	}
	public Integer getTcourseid() {
		return tcourseid;
	}
	public void setTcourseid(Integer tcourseid) {
		this.tcourseid = tcourseid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
