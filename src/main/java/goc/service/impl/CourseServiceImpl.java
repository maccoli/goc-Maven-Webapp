package goc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import goc.dao.CourseMapper;
import goc.pojo.Fcourse;
import goc.pojo.Fresource;
import goc.pojo.Studycourse;
import goc.pojo.Tcourse;
import goc.service.CourseService;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

	@Resource
	private CourseMapper courseMapper;
	
	@Override
	public List<Tcourse> SelectAllTc() {
		
		return this.courseMapper.SelectAllTc();
	}
	
	@Override
	public Tcourse SelectTcid(String tcoursename) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectTcid(tcoursename);
	}

	@Override
	public List<Fcourse> SelectFc(Integer tcourseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectFc(tcourseid);
	}

	@Override
	public List<Fcourse> SelectAllFc(List<Integer> tcourseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectAllFc(tcourseid);
	}

	
	@Override
	public List<Fresource> SelectFresource(Integer courseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectFresource(courseid);
	}

	@Override
	public Studycourse SelectStudycourse(Integer userid, Integer courseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectStudycourse(userid, courseid);
	}

	@Override
	public Integer SelectCountAdate(Integer tcourseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectCountAdate(tcourseid);
	}

	@Override
	public Integer SelectCountBdate(Integer tcourseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectCountBdate(tcourseid);
	}

	@Override
	public Integer SelectCountDatenums(Integer tcourseid, Integer userid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectCountDatenums(tcourseid,userid);
	}

	@Override
	public Integer SelectFcid(String coursename) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectFcid(coursename);
	}

	@Override
	public Fcourse SelectFcBycid(Integer courseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectFcBycid(courseid);
	}

	@Override
	public List<Integer> SelectBylabel(String label) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectBylabel(label);
	}

	@Override
	public List<Fcourse> SelectFcByCourseid(List<Integer> courseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectFcByCourseid(courseid);
	}

	@Override
	public Tcourse SelectTcouseNameById(Integer tcourseid) {
		// TODO Auto-generated method stub
		return this.courseMapper.SelectTcouseNameById(tcourseid);
	}

	@Override
	public void InsertStudyCourse(Studycourse studycourse) {
		// TODO Auto-generated method stub
		this.courseMapper.InsertStudyCourse(studycourse);
	}

	

	
}
