package goc.controller;

import java.io.IOException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import goc.pojo.DaySummary;
import goc.pojo.Fcourse;
import goc.pojo.Resources;
import goc.pojo.Sign;
import goc.pojo.Studying;
import goc.pojo.Tcourse;
import goc.pojo.User;
import goc.service.CourseService;
import goc.service.ResourcesService;
import goc.service.RouteService;
import goc.service.SignService;
import goc.service.StudyingService;
import goc.service.SummaryService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/home")
public class HomePageController {

	@Resource
	private SignService signService;
	
	@Resource
	private SummaryService summaryService;
	
	@Resource
	private ResourcesService resourcesService;
	
	@Resource
	private StudyingService studyingService;
	
	@Resource
	private CourseService courseService;
	
	@Resource
	private RouteService routeService;
	
	@ResponseBody
	@RequestMapping(value="/addsign")
	public String addsign(HttpServletResponse response,HttpServletRequest request){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		Integer userid = user.getUserid();
		
		Sign sign = new Sign();
		sign.setUserid(userid);
		
		Sign sign2 = this.signService.SelectByUserid(userid);

		java.sql.Date today = new java.sql.Date(new Date().getTime());
		//判断是否为第一次签到
		if(sign2 == null)
		{
			java.sql.Date today2 = new java.sql.Date(new Date().getTime());
	       String date = "1";
	       int times = 1;
		   sign.setStartdate(today);
		   sign.setLatelydate(today2);
		   sign.setDate(date);
		   sign.setTimes(times);
		   System.out.println("sign:"+sign);
		   
		   this.signService.InsertSign(sign);
		}else
		{
			Long datenums;

			String date2 = null;

			Date Startdate = sign2.getStartdate();
			Date Latelydate = sign2.getLatelydate();

			String changes = ";0";
			//获取相差的天数
			datenums = (today.getTime()-Latelydate.getTime())/(24*60*60*1000);
			if(datenums == 2){
				changes = ";0";
				date2 = sign2.getDate()+changes;
			}else if(datenums>2)
	        {
	        	for(int i = 0;i< datenums-2;i++){
	        	    changes = changes+";0";
	        	}
	        	date2 = sign2.getDate()+changes;
	        }else{
	        	date2 = sign2.getDate();
	        }
	        
			java.sql.Date Startdate2 = new java.sql.Date(Startdate.getTime());
			
			java.sql.Date Latelydate2 = new java.sql.Date(new Date().getTime());
			
			sign.setStartdate(Startdate2);
			sign.setLatelydate(Latelydate2);
			String date = date2+";1";
			sign.setDate(date);
			sign.setTimes(sign2.getTimes()+1);

			this.signService.UpdateSignByUserid(sign);
			
		}
		
		return "签到成功";
	}
	
	
	//显示首页
	@ResponseBody
	@RequestMapping(value="/gethome", produces = "text/json;charset=UTF-8")
	public void gethome(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		User user = (User) request.getSession().getAttribute("user");
		Integer userid = user.getUserid();
		//Integer userid = 111;
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		Map<String, Object> resultMap = new HashMap<>();
		
		Sign sign = this.signService.SelectByUserid(userid);

		
		if(sign == null){
			resultMap.put("notice", "还未开始签到");
			
			List<DaySummary> daysums = new ArrayList<DaySummary>();
			
			daysums.add(null);
			daysums.add(null);
			daysums.add(null);
			daysums.add(null);
			daysums.add(null);
			
			List<java.sql.Date> datenums = new ArrayList<java.sql.Date>();
			java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
			
			Date aDate,bDate,cDate,dDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			//前一天
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			aDate = calendar.getTime();
			java.sql.Date adate = new java.sql.Date(aDate.getTime());
			
			//前2天
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			bDate = calendar.getTime();
			java.sql.Date bdate = new java.sql.Date(bDate.getTime());
			
			//前三天
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			cDate = calendar.getTime();
			java.sql.Date cdate = new java.sql.Date(cDate.getTime());
			
			//前四天
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			dDate = calendar.getTime();
			
			java.sql.Date ddate = new java.sql.Date(dDate.getTime());

			datenums.add(date);
			datenums.add(adate);
			datenums.add(bdate);
			datenums.add(cdate);
			datenums.add(ddate);
			
			List<String> das = new ArrayList<>();
			das.add("0");
			das.add("0");
			das.add("0");
			das.add("0");
			das.add("0");
			
			
			resultMap.put("daysums", daysums);
			resultMap.put("datenums", datenums);
			
			resultMap.put("das", das);
			
		}else{

		//java.sql.Date date= (java.sql.Date) new Date();--这个是错误的，util是sql的父类，父类不能向子类转化
		//下面是util转化为sql的特殊方法
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		
		Date aDate,bDate,cDate,dDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		aDate = calendar.getTime();
		java.sql.Date adate = new java.sql.Date(aDate.getTime());
		
		DaySummary aDaySummary = new DaySummary();
		aDaySummary.setUserid(userid);
		aDaySummary.setDate(adate);
		
		DaySummary daySummarya = this.summaryService.selectByIdAndDate(aDaySummary);
		
		//前二天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		bDate = calendar.getTime();
		java.sql.Date bdate = new java.sql.Date(bDate.getTime());
		
		DaySummary bDaySummary = new DaySummary();
		bDaySummary.setUserid(userid);
		
		
		
		bDaySummary.setDate(bdate);
		
		DaySummary daySummaryb = this.summaryService.selectByIdAndDate(bDaySummary);
		
		//前三天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		cDate = calendar.getTime();
		
		java.sql.Date cdate = new java.sql.Date(cDate.getTime());
		
		DaySummary cDaySummary = new DaySummary();
		cDaySummary.setUserid(userid);
		cDaySummary.setDate(cdate);
		
		DaySummary daySummaryc = this.summaryService.selectByIdAndDate(cDaySummary);
		
		
		//前四天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		dDate = calendar.getTime();
		
		java.sql.Date ddate = new java.sql.Date(dDate.getTime());
		
		DaySummary dDaySummary = new DaySummary();
		dDaySummary.setUserid(userid);
		dDaySummary.setDate(ddate);
		
		DaySummary daySummaryd = this.summaryService.selectByIdAndDate(dDaySummary);

		//当天
		DaySummary edaySummary = new DaySummary();
		edaySummary.setUserid(userid);
		edaySummary.setDate(date);
		DaySummary daySummarye = this.summaryService.selectByIdAndDate(edaySummary);
        
		//近5天 打个包，返回小结给前端
		List<DaySummary> daysums = new ArrayList<DaySummary>();
		daysums.add(daySummarye);
		daysums.add(daySummarya);
		daysums.add(daySummaryb);
		daysums.add(daySummaryc);
		daysums.add(daySummaryd);
		

		//近5天日期合集
		List<java.sql.Date> datenums = new ArrayList<java.sql.Date>();
		
		datenums.add(date);
		datenums.add(adate);
		datenums.add(bdate);
		datenums.add(cdate);
		datenums.add(ddate);
		
		
		
		resultMap.put("daysums", daysums);
		resultMap.put("datenums", datenums);
		
		//下面是签到
		List<String> das = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		String le = sdf.format(sign.getLatelydate());
		
		String de = sdf.format(date);
		
		Date Latelydate = sign.getLatelydate();
		Date today = date;
		Long datenumss;
		String date2 = null;
		String changs = ";0";
		//判断是否今天签到
		if(le.equals(de)){
			resultMap.put("isSignIn", "true");

			String[] dates = sign.getDate().split(";");
			
			for(int i=dates.length-5;i<dates.length;i++){
				das.add(dates[i]);		
			}
			
		}else
		{
			resultMap.put("isSignIn", "false");

			String[] dates;
			datenumss = (today.getTime()-Latelydate.getTime())/(24*60*60*1000);
			if(datenumss>1){
				for(int i =0; i < datenumss-1;i++ ){
					changs = changs+";0";
				}
				date2 = sign.getDate()+changs;
				dates = date2.split(";");
			}else if(datenumss == 1)
	        {
	        	date2 = sign.getDate()+";0";
            	
            	dates = date2.split(";");	
	        }else{
	        dates = sign.getDate().split(";");
	        }
			if(dates.length<5){
				for(int i = 0; i<dates.length;i++){
					das.add(dates[i]);
		    }
			}else{
			for(int i=dates.length-5;i<dates.length;i++){
				das.add(dates[i]);
			}
		//	das.add("0");
		    }

			/*sign.setDate(sign.getDate()+changs);
			this.signService.UpdateSignByUserid(sign);*/
		}
		resultMap.put("das", das);
		}
		//正在学习
		Studying studying = this.studyingService.SelectForStuding(userid);
		
		//如果还没开始学习
        if(studying == null){
        	resultMap.put("notice", "还未开始学习");
        	
        //	resultMap.put("ccompleted", 0);
        	
        	String allcourseid = this.routeService.SelectAllCidByUserid(userid);
        	String[] firstid = allcourseid.split(";");

        	Fcourse course = this.courseService.SelectFcBycid(Integer.parseInt(firstid[0]));
        	
        	Tcourse tcourse =  this.courseService.SelectTcouseNameById(course.getTcourseid());
        	
        	Studying studying2 = new Studying();
        	
        	studying2.setCoursename(course.getCoursename());
        	studying2.setTcoursename(tcourse.getTcoursename());
        	studying2.setUserid(userid);
        	if(user.getGrade() == 1){
        	studying2.setStudydate(course.getAstudydate());
        	}else{
        		studying2.setStudydate(course.getBstudydate());
        	}
        	
        	this.studyingService.InsertStuding(studying2);

        	 resultMap.put("studying", studying2);
        	 
        	 resultMap.put("lcompleted", 0);
        	 
        	 Integer countstunus = 0;
     		if(user.getGrade() == 1){
     			countstunus = this.courseService.SelectCountAdate(course.getTcourseid());
     		}else if(user.getGrade() == 2){
     			countstunus = this.courseService.SelectCountBdate(course.getTcourseid());
     		}
        	 
     		System.out.println("csss"+countstunus);
     		resultMap.put("ccompleted", 0);
     		resultMap.put("ltimeLength", countstunus);
        
     	//已经开始学习了~~~~
        }else{
        resultMap.put("studying", studying);
      
        
        Integer grade = user.getGrade();
        
        Tcourse tcourse = this.courseService.SelectTcid(studying.getTcoursename());
             
        //统计下对应的学习等级下，统计该tcourse的建议学习天数        
        Integer countstunus = 0;
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
		
        }
		
		//热门资源
		List<Resources> hotrs = new ArrayList<Resources>();
		
		hotrs = this.resourcesService.SelectHot();

		resultMap.put("hotrs", hotrs);
		
		response.getWriter().write(gson.toJson(resultMap));

		
	}
}
