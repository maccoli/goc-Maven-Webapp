package goc.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import goc.pojo.Route;
import goc.pojo.User;
import goc.service.CourseService;
import goc.service.RouteService;
import goc.service.UserService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/route")
public class RouteController {

	@Resource
	private RouteService routeService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CourseService courseService;
	
	@ResponseBody
	@RequestMapping(value="/addRoute")
	public String addRoute(HttpServletResponse response,HttpServletRequest request){
		//调用数据方法
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		
		System.out.println("ROUUSEER:"+user);
		
		Route route = new Route();
		route.setUserid(user.getUserid());
		
		//方向id
		Integer id = null;
		
		//删除标签用的数组
		int labels[] = new int[100];
		
		Gson gson = new Gson();
		Map<String, Object> resultMap = new HashMap<>();

		//前端、后台、移动 (选择学的类别)
		String firstStep2 = request.getParameter("firstStep");
		//已学参数
		String secondStep2 = request.getParameter("secondStep");
		//想学参数
		String thirdStep2 = request.getParameter("thirdStep");
	
		
		String[] firstStep = firstStep2.split(";");
		
		String[] secondStep = secondStep2.split(";");
		
		String[] thirdStep = thirdStep2.split(";");

		route.setDirect(firstStep2);
		route.setQuestiontwo(secondStep2);
		route.setQuestionthree(thirdStep2);;

	
		//通过选择的direct判断是什么方向
		if(firstStep2.equals("前端;移动前端")){
			id = 1001;
		}else if(firstStep2.equals("前端;web前端")){
			id = 1002;
		}else if(firstStep2.equals("前端;全栈前端")){
			id = 1003;
		}else{
			return "暂未添加该方向课程";
		}
		
		//获得该方向的所有课程id
		Route route2 = this.routeService.SelectById(id);
		
		String allcourseid = route2.getAllcourseid();
		
		String[] allcourse = allcourseid.split(";");
		
		//通过选择第二问题的答案，判断为什么等级的学员，然后通过通过等级选择对应的建议学习天数
		if(secondStep[0].equals("轻松")){
			user.setGrade(2);

		}else if(secondStep[0].equals("不困难")){
			user.setGrade(1);
		}else if(secondStep[0].equals("较困难")){
			user.setGrade(1);
		}
		
		user.setDirection(firstStep[1]);
		
		this.userService.UpdateByUserid(user);

		
		//通过选择的第三个问题，判别是否删除对应的标签课程
		//1.是否删除含有企业标签的课程
		if(thirdStep[0].equals("感兴趣，简单了解一下")){
			String label = "企业";
			List<Integer> courseids = this.courseService.SelectBylabel(label);
			for(int j = 0; j < courseids.size(); j++){
				labels[courseids.get(j)-1] = 1;
			}
			
			for(int k = 0; k < allcourse.length; k++){
				if(labels[k] == 1){
					allcourse[k] = "0";
				}
			}
			
		}
		
		//2.是否删除含有主流技术标签的课程
		if(thirdStep[1].equals("主流技术")){
			String label = "其他";
			List<Integer> courseids = this.courseService.SelectBylabel(label);
			
			for(int j = 0; j < courseids.size(); j++){
				labels[courseids.get(j)-1] = 1;
			}
			
			for(int k = 0; k < allcourse.length; k++){
				if(labels[k] == 1){
					allcourse[k] = "0";
				}
			}
			
		}
		//修改对应的用户专属课程
		String allcourseid2 = "0";
		for(int y = 0; y < allcourse.length; y++){
			String exc = ";"+allcourse[y];
			allcourseid2 = allcourseid2+exc;
		}

		route.setAllcourseid(allcourseid2.substring(2, allcourseid2.length()));
		
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		
		route.setDate(date);

		Route route3 = this.routeService.SelectByUserid(user.getUserid());

		
		if(route3 == null){
		   this.routeService.InsertRoute(route);
		}else{
			this.routeService.UpdateRouteByUserid(route);
		}
		
		return "添加路线成功";
	}

	@ResponseBody
	@RequestMapping(value="/showRoute", produces = "text/json;charset=UTF-8")
	public void showRoute(HttpServletResponse response,HttpServletRequest request) throws IOException{
		//调用数据方法
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		
		Integer userid = user.getUserid();
		
		Route route = this.routeService.SelectByUserid(userid);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		response.getWriter().write(gson.toJson(route));
	}


}
