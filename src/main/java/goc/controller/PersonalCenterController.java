package goc.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import goc.pojo.DaySummary;
import goc.pojo.Studying;
import goc.pojo.Tcourse;
import goc.pojo.User;
import goc.service.CourseService;
import goc.service.StudyingService;
import goc.service.SummaryService;
import goc.service.UserService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/center")
public class PersonalCenterController {

	@Resource
	private SummaryService summaryService;
	
	@Resource
	private StudyingService studyingService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CourseService courseService;
	//个人中心进去第一个页面
	@ResponseBody
	@RequestMapping(value="/firstPage")
	public void firstPage(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");

	    Integer userid = user.getUserid();
	//	Integer userid = 111;
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		Map<String, Object> resultMap = new HashMap<>();
		
		//每日小结
        java.sql.Date date= new java.sql.Date(new Date().getTime());
		
	    DaySummary day2 = new DaySummary();
	    day2.setUserid(userid);
	    day2.setDate(date);
		DaySummary daySummary = this.summaryService.selectByIdAndDate(day2);

		
		//正在学习ing
	    Studying studying = this.studyingService.SelectForStuding(userid);
		
        if(studying == null){
        	resultMap.put("notice", "还未开始学习");
        }else{
        	
        	resultMap.put("studying", studying);
        }
        
        //----届时要修改的成实际数据！！！
        Integer grade = user.getGrade();
        
        Tcourse tcourse = this.courseService.SelectTcid(studying.getTcoursename());
        
        //统计下对应的学习等级下，统计该tcourse的建议学习天数        
        int countstunus = 0;
		if(grade == 1){
			countstunus = this.courseService.SelectCountAdate(tcourse.getTcourseid());
		}else if(grade == 2){
			countstunus = this.courseService.SelectCountBdate(tcourse.getTcourseid());
		}
        
        
        //统计已经学的课程天数
        Integer countstueddate = this.courseService.SelectCountDatenums(tcourse.getTcourseid(),userid);

        //获得当前学习课程的已学天数
        Date startDate = studying.getStartdate();
        
        Date latelyDate = studying.getLatelydate();
        
        Long slnums = null;
        
        if(startDate == null || latelyDate == null){

        	resultMap.put("notice", "还未开始学习");
        	resultMap.put("ccompleted", 0);
        }else{
        	 slnums = (latelyDate.getTime()-startDate.getTime())/(24*60*60*1000);
        	resultMap.put("ccompleted", slnums);
        }
        
        //统计
        if(countstueddate == null || countstueddate == 0)
        {
        	resultMap.put("lcompleted", 0);
        }else {
		resultMap.put("lcompleted", countstueddate);
        }
		resultMap.put("ltimeLength", countstunus);
		
		resultMap.put("user", user);
		
		resultMap.put("daySummary", daySummary);
		
		
		gson.toJson(resultMap);
		
		response.getWriter().write(gson.toJson(resultMap));

		}
		
	
	@ResponseBody
	@RequestMapping(value="/seldaysum", produces="text/json;charset=UTF-8")
	public void seldaysum(HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		User user = (User) request.getSession().getAttribute("user");
	    Integer userid = user.getUserid();
		//Integer userid = 111;

		String date = request.getParameter("date");
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date2 = null;
		try {
			date2 =  sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		java.sql.Date date3= new java.sql.Date(date2.getTime());
		
	    DaySummary day2 = new DaySummary();
	    day2.setUserid(userid);
	    day2.setDate(date3);
	
	  //每日小结--选择当天的日期
	  	DaySummary daySummary = this.summaryService.selectByIdAndDate(day2);

	  	Gson goson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    
	  	goson.toJson(daySummary);
	  	
	  	response.getWriter().write(goson.toJson(daySummary));
	}
	

	//修改用户信息
	@ResponseBody
	@RequestMapping(value="/changeUser")
	public String changeUser(HttpServletResponse response,HttpServletRequest request){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		User user = (User) request.getSession().getAttribute("user");
	    Integer userid = user.getUserid();
	//	Integer userid = 111;
		
		String username = request.getParameter("name");
		String head = request.getParameter("head");
		String contact = request.getParameter("contact");
		String introduce = request.getParameter("introduce");
		String sex = request.getParameter("sex");
		Integer age = Integer.parseInt(request.getParameter("age"));
		
		user.setUsername(username);
		user.setHead(head);
		user.setContact(contact);
		user.setIntroduce(introduce);
		user.setSex(sex);
		user.setAge(age);
        user.setUserid(userid);
        
        this.userService.UpdateByUserid(user);
        
		return "修改信息成功！";
		
	}
	
	//修改封面
	@ResponseBody
	@RequestMapping(value="/changeCover")
	public String changeCover(HttpServletResponse response,HttpServletRequest request){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");

		
		Integer userid = user.getUserid();
		
		String path = "";
		String cover = "";
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
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
						path = request.getServletContext().getRealPath("/static/images") + "/"
								+ String.valueOf(date3.getTime()) + "."
								+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
						
						file.transferTo(new File(path));
						
						cover = String.valueOf(date3.getTime()) + "."
								+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
						
					} catch (IllegalStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return "上传文件失败";
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return "上传文件失败";
					}
			}
				
	
			}
		}

		user.setCover(cover);
		
		this.userService.UpdateByUserid(user);
		
		return "上传封面成功";
	}
	
	
	//添加每日小结
	@ResponseBody
	@RequestMapping(value="/addDaySummary")
	public String addDSummary(HttpServletResponse response,HttpServletRequest request){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		User user = (User) request.getSession().getAttribute("user");
	    Integer userid = user.getUserid();
	//	Integer userid = 111;
		String daysummary = request.getParameter("daysummary");
		String score = request.getParameter("score");
	
		//获取当天的日期
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		
		DaySummary daySummary2 = new DaySummary();
		daySummary2.setUserid(userid);
		daySummary2.setScore(Integer.parseInt(score));
		daySummary2.setDaysummary(daysummary);
		daySummary2.setDate(date);
		
		this.summaryService.InsertDaySummary(daySummary2);
		
		
		return "200";
	}
	
	
	
}
