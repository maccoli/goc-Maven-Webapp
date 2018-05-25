package goc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import goc.pojo.Fcourse;
import goc.pojo.FcourseChangeA;
import goc.pojo.Fresource;
import goc.pojo.Project;
import goc.pojo.Projects;
import goc.pojo.Studycourse;
import goc.pojo.Studying;
import goc.pojo.Tcourse;
import goc.pojo.User;
import goc.service.CourseService;
import goc.service.ProjectService;
import goc.service.RouteService;
import goc.service.StudyingService;
import goc.util.RsetCutil;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/course")
public class CourseController {

	@Resource
	private CourseService courseService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private StudyingService studyingService;
	
	@Resource
	private RouteService routeService;
	
	@ResponseBody
	@RequestMapping(value="/showTc", produces = "text/json;charset=UTF-8")
	public void showTc(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		
		Integer userid = user.getUserid();
		
		//Integer userid = 111;
		
		int [] exc = new int[100];
		//查询用户专属路线
		String usercourseid = this.routeService.SelectAllCidByUserid(userid);
		
		String[] courseidstr = usercourseid.split(";");
		
		//专属路线的id
		List<Integer> courseidstrlist = new ArrayList<Integer>();
        for(int i = 0; i < courseidstr.length; i++){
        	courseidstrlist.add(Integer.parseInt(courseidstr[i]));
        }
		
        //路线中的课程
        for(int i =0; i < courseidstrlist.size(); i++){
        	if(courseidstrlist.get(i) != 0){
        	exc[courseidstrlist.get(i)-1] = 1;
        	}
        }
        
		Gson gson = new Gson();
		
		Map<String, Object> resultMap = new HashMap<>();
		
		
		List<Object> courselist = new ArrayList<Object>();
		
		List<Tcourse> tcourse = this.courseService.SelectAllTc();
		
		//存放多个tcourseid
		List<Integer> tcid = new ArrayList<Integer>();
		
		List<Integer> cid = new ArrayList<Integer>();
		
		//存放各个tcourseid下的各个courseid
		List<Integer> countstunus = new ArrayList<Integer>();
		

		//用于选择哪个天数返回
	/*	FcourseChangeA fcourseChangeA = new FcourseChangeA();
		List<FcourseChangeA> fcourseChangeAs = new ArrayList<FcourseChangeA>();*/
		
		Integer grade = user.getGrade();	
		
		Iterator<Tcourse> iterator = tcourse.iterator();
		while(iterator.hasNext()){
			Tcourse tcourse2 = (Tcourse) iterator.next();
			tcid.add(tcourse2.getTcourseid());
		    if(grade == 1){
		    	//添加tcourseid下的所有的courseid建议用时天数
		       countstunus.add(this.courseService.SelectCountAdate(tcourse2.getTcourseid())) ;
			}else if(grade == 2){
			   countstunus.add(this.courseService.SelectCountBdate(tcourse2.getTcourseid()));
			}
		    
		    List<Fcourse> fcourse = new ArrayList<Fcourse>();
		    
		    fcourse =  this.courseService.SelectFc(tcourse2.getTcourseid());
	        
		    
		    for(int i=fcourse.size()-1;i>=0; i--){
		    	if(exc[i] == 0){
		    		fcourse.remove(i);
		    	}
		    }

		    
		    courselist.add(tcourse2);
		    courselist.add(fcourse);
		}
		
		//接收tcourseid下的全部的课程
		List<Fcourse> fcourse = this.courseService.SelectAllFc(tcid);
    
		Iterator<Fcourse> iterator2 = fcourse.iterator();
		while(iterator2.hasNext()){
			Fcourse course =(Fcourse) iterator2.next();
			cid.add(course.getCourseid());
		}
		
		//接收已经学了的课程的天数
		List<Studycourse> studycourses = new ArrayList<Studycourse>();
		
		Iterator<Integer> iterator3 = cid.iterator();
		while(iterator3.hasNext()){
			Studycourse studycourse = this.courseService.SelectStudycourse(userid, iterator3.next());
			
			if(studycourse != null){
			studycourses.add(studycourse);
			}
		}
		resultMap.put("countstunus", countstunus);
		resultMap.put("studycourses", studycourses);
		resultMap.put("course", courselist);
		response.getWriter().write(gson.toJson(resultMap));
	}
	
	@ResponseBody
	@RequestMapping(value="/showFR", produces = "text/json;charset=UTF-8")
	public void showFR(HttpServletResponse response,HttpServletRequest request) throws IOException{
	/*	RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);*/
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");

		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8084");
		response.addHeader("Access-Control-Allow-Headers", "Origin,x-requested-with, Content-Type, Accept, X-Cookie");
        response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
		
		
        User user = (User) request.getSession().getAttribute("user");
		
		Integer userid = user.getUserid();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Map<String, Object> resultMap = new HashMap<>();
	    
		//解决跨域中文乱码问题
		String coursename = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8"); 
		
		
		Studying studying = this.studyingService.SelectForStuding(userid);
		
		Integer courseid = this.courseService.SelectFcid(coursename);
		
		Studycourse stucourse = this.courseService.SelectStudycourse(userid, courseid);
		//判断是否为当前学习的狀態
		if(stucourse != null){ //表示上传过作品了
		    resultMap.put("state", "2");
		    resultMap.put("datenum", stucourse.getDatenum());
		    
		}else if(studying.getStartdate() == null)
		{//未开始学习
			resultMap.put("state", "0");
		}else{//已经开学学习，但没学完
			resultMap.put("state", "1");
			
			java.sql.Date date = new java.sql.Date(new Date().getTime());
			
			studying.setLatelydate(date);
			
			this.studyingService.UpdateStuding(studying);
			
	    }
	    
		List<Fresource> fresources = this.courseService.SelectFresource(courseid);
		
		
		Fcourse fcourse = this.courseService.SelectFcBycid(courseid);
		
		Projects projects = this.projectService.SelectByProjectid(courseid);
		
		List<Project> proList = this.projectService.SelectById(courseid);

		resultMap.put("fcourse", fcourse);
		resultMap.put("content", fresources);
		resultMap.put("projects", projects);
		resultMap.put("proList", proList);
		
		response.getWriter().write(gson.toJson(resultMap));
	}
}
