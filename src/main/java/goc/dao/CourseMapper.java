package goc.dao;

import goc.pojo.Fcourse;
import goc.pojo.Fresource;
import goc.pojo.Studycourse;
import goc.pojo.Tcourse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CourseMapper {

	List<Tcourse> SelectAllTc();
	
	Tcourse SelectTcid(String tcoursename);
	
	List<Fcourse> SelectFc(Integer tcourseid);
	
	Integer SelectFcid(String coursename);
	
	Fcourse SelectFcBycid(Integer courseid);
	
	List<Fcourse> SelectAllFc(List<Integer> tcourseid);
	
	List<Fresource> SelectFresource(Integer courseid);
	
	Studycourse SelectStudycourse(@Param("userid") Integer userid,@Param("courseid") Integer courseid);

	
	Integer SelectCountDatenums(@Param("tcourseid") Integer tcourseid,@Param("userid") Integer userid);
	
	Integer SelectCountAdate(Integer tcourseid);
	
	Integer SelectCountBdate(Integer tcourseid);
	
	List<Integer> SelectBylabel(String label);
	
	List<Fcourse> SelectFcByCourseid(List<Integer> courseid);
	
	Tcourse SelectTcouseNameById(Integer tcourseid);
	
	void InsertStudyCourse(Studycourse studycourse);

}
