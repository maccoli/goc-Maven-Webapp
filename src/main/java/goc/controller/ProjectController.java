package goc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import goc.pojo.Fcourse;
import goc.pojo.Project;
import goc.pojo.Projects;
import goc.pojo.Studycourse;
import goc.pojo.Studying;
import goc.pojo.Tcourse;
import goc.pojo.User;
import goc.service.CourseService;
import goc.service.ProjectService;
import goc.service.StudyingService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.java.javaURLContextFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.fabric.xmlrpc.base.Array;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Resource
    private ProjectService projectService;
	
	@Resource
	private CourseService courseService;
	
	@Resource
	private StudyingService studyingService;
	
	//从路线那获得项目进入项目简介
	/*@ResponseBody
	@RequestMapping(value="/projects", produces="text/json;charset=UTF-8")
	public void projects(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		//User user = (User) request.getSession().getAttribute("user");
		User user = new User();
		user.setUserid(111);
		
		String projectid = request.getParameter("id");

		Projects projects = new Projects();
		Project project = new Project();
		//获得项目简介
		projects = this.projectService.SelectByProjectid(Integer.parseInt(projectid));
		
		//获取状态
		project = this.projectService.SelectByUseridAndProjectID(user.getUserid(), projects.getProjectid());
		
		int state = 0 ;
		if(project == null){
		    state = 0;
		}else{
			state = project.getState();
		}
		Gson gson = new Gson();
		
		System.out.println("st"+state);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("projects", projects);
		resultMap.put("state", state);
		
		response.getWriter().write(gson.toJson(resultMap));
		
	}*/
	
/*	//查询自己已经做了/已接项目列表
	@ResponseBody
	@RequestMapping(value="/projectByUser", produces="text/json;charset=UTF-8")
	public void projectByUser(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		
		List<Project> project = new ArrayList<Project>();
		
		project = this.projectService.SelectProjectByUserid(user.getUserid());
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(project));
		
	}*/
	
	//查询自己已经做了的项目详细内容
/*	@ResponseBody
	@RequestMapping(value="/projectById", produces="text/json;charset=UTF-8")
	public void projectById(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		
		String projectid = request.getParameter("id");
		
		Project project = new Project();

		Integer projectid2 = Integer.parseInt(projectid);
		project = this.projectService.SelectByUseridAndProjectID(user.getUserid(), projectid2);
		
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(project));
	}*/
	
	//接取新的项目
	@ResponseBody
	@RequestMapping(value="/insertProject")
	public String insertProject(HttpServletResponse response,HttpServletRequest request){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		User user = (User) request.getSession().getAttribute("user");

		Integer userid = user.getUserid();	
	
		Studying studying = this.studyingService.SelectForStuding(userid);

		//开始学习按钮
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		studying.setStartdate(date);
		studying.setLatelydate(date);
		
		this.studyingService.UpdateStuding(studying);
		
		return "添加项目成功，开始实践吧!";
	}
	
	//上传已接项目
	@ResponseBody
	@RequestMapping(value="/addProject", produces="text/json;charset=UTF-8")
	public void addProject(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		User user = (User) request.getSession().getAttribute("user");
			
		Project project = new Project();
		project.setUserid(user.getUserid());
		project.setUsername(user.getUsername());
		project.setHead(user.getHead());
		project.setIntroduce(user.getIntroduce());
		
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		project.setDate(date);
		
		String path;
		String text2 = "";//图片集
		String video = "";//上传的视频
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {
			project.setType(request.getParameter("fileType"));
			project.setIntroduction(request.getParameter("textDescribe"));
			project.setProjectid(Integer.parseInt(request.getParameter("projectid")));
			project.setGithubsrc(request.getParameter("githubSrc"));

			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
						Date date3 = new Date();
						try{
						if (file.getName().substring(0, 5).equals("video")) {
						//	cover = file.getOriginalFilename();
							path = request.getServletContext().getRealPath("/static/videos") + "/"
									+ String.valueOf(date3.getTime()) + "."
									+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
							
							file.transferTo(new File(path));
							
							video = String.valueOf(date3.getTime()) + "."
									+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
						} else if (file.getName().substring(0, 5).equals("image")) {
							path = request.getServletContext().getRealPath("/static/images") + "/"
									+ String.valueOf(date3.getTime()) + "."
									+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
							
							file.transferTo(new File(path));
							
							text2 = text2 + ";" + String.valueOf(date3.getTime()) + "."
									+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
							;
						} 
						} catch (IllegalStateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						//	return "上传文件失败";
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
					//		return "上传文件失败";
						}
					}
				if(video.equals("")){

				}else
				{
					project.setVideo(video);
				}
				
				if(text2.equals("")){
				}else{
					String text = text2.substring(1, text2.length());
					project.setText(text);
				}
				}
			}
		

		//插入新的上傳項目
	    this.projectService.InsertProject(project);
	    //返回已经上传了的项目集
	    List<Project> proList = this.projectService.SelectById(project.getProjectid());
	    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    
	    
	    //已经完成项目，添加到已经完成课程表里
	    Studying stued = this.studyingService.SelectForStuding(user.getUserid());
	  
	    
	    Tcourse tcourse = this.courseService.SelectTcid(stued.getTcoursename());
	    
	    Studycourse studycourse = new Studycourse();
	    
	    studycourse.setUserid(user.getUserid());
	    studycourse.setTcourseid(tcourse.getTcourseid());
	    studycourse.setCourseid(project.getProjectid());
	    studycourse.setStartdate(stued.getStartdate());
	    studycourse.setEnddate(date);
	    
        Date startDate = studycourse.getStartdate();
        
        Date latelyDate = studycourse.getEnddate();
        
        Long slnums = (latelyDate.getTime()-startDate.getTime())/(24*60*60*1000);
        
        int num = Integer.parseInt(String.valueOf(slnums));
        
        if(num == 0){
        studycourse.setDatenum(num+1);
        }
        
        this.courseService.InsertStudyCourse(studycourse);
	    
	    //已经完成项目,更改正在学习表
	    Fcourse fcourse = this.courseService.SelectFcBycid(project.getProjectid()+1);
	    
	    
	    Tcourse tcoursename = this.courseService.SelectTcouseNameById(fcourse.getTcourseid());
	    
	    Studying studying = new Studying();
	    
	    studying.setUserid(user.getUserid());
	    studying.setTcoursename(tcoursename.getTcoursename());
	    studying.setCoursename(fcourse.getCoursename());
	    studying.setStudydate(fcourse.getAstudydate());

	    this.studyingService.UpdateStuding(studying);
	    
	    Map<String, Object> resultMap = new HashMap<>();
	    
	    resultMap.put("proList", proList);
	    resultMap.put("datenum", num);
	    
		response.getWriter().write(gson.toJson(resultMap));

	}
}
