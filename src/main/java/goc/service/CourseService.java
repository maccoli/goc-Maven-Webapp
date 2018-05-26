package goc.service;

import goc.pojo.Fcourse;
import goc.pojo.Fresource;
import goc.pojo.Studycourse;
import goc.pojo.Tcourse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CourseService {

	/**
	 * 
	 * @return
	 */
    public List<Tcourse> SelectAllTc();
	
    
    /**
     * 
     * @param tcoursename
     * @return
     */
    public Tcourse SelectTcid(String tcoursename);
    
    
    /**
     * 
     * @param tcourseid
     * @return
     */
	public List<Fcourse> SelectFc(Integer tcourseid);
	
	/**
	 * 
	 * @param coursename
	 * @return
	 */
    public Integer SelectFcid(String coursename);
	
    /**
     * 
     * @param courseid
     * @return
     */
    public Fcourse SelectFcBycid(Integer courseid);
    
    /**
	 * 
	 * @param list tcourseid
	 * @return
	 */
	public List<Fcourse> SelectAllFc(List<Integer> tcourseid);
	
	
	/**
	 * 
	 * @param courseid
	 * @return
	 */
	public List<Fresource> SelectFresource(Integer courseid);
	
	/**
	 * 
	 * @param userid
	 * @param courseid
	 * @return
	 */
	public Studycourse SelectStudycourse(@Param("userid") Integer userid,@Param("courseid") Integer courseid);

	/**
	 * 
	 * @param tcourseid
	 * @return
	 */
	public Integer SelectCountDatenums(@Param("tcourseid") Integer tcourseid,@Param("userid") Integer userid);

	/**
     * 
     * @param tcourseid
     * @return
     */
    public Integer SelectCountAdate(Integer tcourseid);
	
    /**
     * 
     * @param tcourseid
     * @return
     */
	public Integer SelectCountBdate(Integer tcourseid);


	public List<Integer> SelectBylabel(String label);
	
	public List<Fcourse> SelectFcByCourseid(List<Integer> courseid);
	
	public Tcourse SelectTcouseNameById(Integer tcourseid);
	
	public void InsertStudyCourse(Studycourse studycourse);
}
