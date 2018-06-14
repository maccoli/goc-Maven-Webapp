package goc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import goc.pojo.Tests;
import goc.pojo.User;
import goc.pojo.Usertest;
import goc.service.TestsService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.java.javaURLContextFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/tests")
public class TestsController {
	
	@Resource
	private TestsService testsService;
	
/*	@ResponseBody
	@RequestMapping(value="/testBybelong", produces="text/json;charset=UTF-8")
	public void testBybelong(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);

		String belong = request.getParameter("belong");
		
		List<Tests> tests = new ArrayList<Tests>();
		tests = this.testsService.SelectByBelong(belong);
	    request.getSession().setAttribute("belong", belong);
	    
	    Gson gson = new Gson();

		response.getWriter().write(gson.toJson(tests));
	}*/
	
	/**
	 * 根据用户传过来的projectid返回测试题和解析
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/tsDetailed", produces="text/json;charset=UTF-8")
	public void tsDetailed(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
        
		String belong = request.getParameter("belong");
		
		//---提取正确答案
		List<Tests> tests = new ArrayList<Tests>();
		tests = this.testsService.SelectTests(belong);
		
		request.getSession().setAttribute("belong", belong);
		Gson gson = new Gson();
	
		response.getWriter().write(gson.toJson(tests));
	}
	
	
	//提交答案
	@ResponseBody
	@RequestMapping("/submitAns")
	public void submitAns(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		String belong = (String) request.getSession().getAttribute("belong");
	
		User user = (User) request.getSession().getAttribute("user");
		Integer userid = user.getUserid();
		Usertest usertest = new Usertest();
		
		String userans = request.getParameter("userans");
		String time = request.getParameter("time");
		
	    //添加用户答案信息
		usertest.setUserid(userid);
		usertest.setBelong(belong);
		usertest.setTime(time);
		usertest.setUserans(userans);
		usertest.setCorrectrate("70%");
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		usertest.setDate(date);
		this.testsService.SubmitAns(usertest);
				
		Usertest usertests = this.testsService.SelectUsertest(userid, belong);

		long times = Long.parseLong(usertests.getTime());
		
		Date date2 = new Date(times);
		
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		
		usertests.setTime(format.format(date2)); 
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(usertests));	
		
	}

}
