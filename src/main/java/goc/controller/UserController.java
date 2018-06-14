package goc.controller;

import goc.pojo.User;
import goc.service.UserService;
import goc.util.MD5Util;
import goc.util.RsetCutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.naming.java.javaURLContextFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping(value="/user")
public class UserController {

	@Resource
	private UserService userService;

	@ResponseBody
	@RequestMapping(value="/register", produces = "text/json;charset=UTF-8")
	public void register(HttpServletResponse response,HttpServletRequest request) throws IOException{
		//调用数据方法
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = new User();
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String verifycode = request.getParameter("verifycode");

		String svc =(String) request.getSession().getAttribute("sessionverify");

		Gson gson = new Gson();
		
		if(svc.equals(verifycode)){
			try {
				password = MD5Util.encrypt(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			user.setPhone(phone);
			user.setPassword(password);
			user.setUsername("user_"+phone.substring(4, 8));
			user.setHead("hp1.png");
			user.setCover("cover1_1.png");
			User user2 = this.userService.SelectByPhone(phone);
			
			
			if(user2 != null){
				response.getWriter().write(gson.toJson("该手机号已经注册,请重新输入"));
				//jsonObject.put("notice", "该手机号已经注册,请重新输入");
			}else{
				   java.sql.Date date = new java.sql.Date(new Date().getTime());
				   user.setDate(date);
		           this.userService.InsertUser(user);
		           response.getWriter().write(gson.toJson("注册成功"));
			}
		}else {
			
			 response.getWriter().write(gson.toJson("验证码错误"));
		}
				
	}
	
	@ResponseBody
	@RequestMapping(value="/login",produces="text/json;charset=UTF-8")
	public void login(HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
	
		
		User user = new User();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String verifycode = request.getParameter("verifycode");
		String password2 = "";
		String svc =(String) request.getSession().getAttribute("sessionverify");  
		
		Gson gson = new Gson();

		Map<String, Object> resultMap = new HashMap<>();
		
	    user = this.userService.SelectByPhone(phone);
		if(svc.equals(verifycode)){
			try {
				password2 = MD5Util.encrypt(password);// MD5加密
			} catch (NoSuchAlgorithmException e) {
		
				e.printStackTrace();
			}
			if(password2.equals(user.getPassword())){
				session.setAttribute("user", user);
				
				String notice = "登陆成功";
			//	resultMap.put("notice", notice);
				
				if(user.getDirection()==null||user.getDirection()=="")
				{
					String notice2 = "未选择方向";
				
					resultMap.put("notice", notice);
					resultMap.put("direction", notice2);

					response.getWriter().write(gson.toJson(resultMap));
				//jsonObject.put("direction", "未选择方向");
				}else{
					String notice2 = "已选择方向";
					resultMap.put("notice", notice);
					resultMap.put("direction", notice2);
					response.getWriter().write(gson.toJson(resultMap));

				}
			}else{ 
				String notice = "密码错误";
				resultMap.put("notice", notice);
				response.getWriter().write(gson.toJson(resultMap));
			}
		}
		else{
			String notice = "验证码错误";
			resultMap.put("notice", notice);
			response.getWriter().write(gson.toJson(resultMap));
		}

	}
	
	@ResponseBody
	@RequestMapping(value="changePW")
	public String changePW(HttpServletResponse response,HttpServletRequest request){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		String password = request.getParameter("password");
		User user = (User) request.getSession().getAttribute("user");
	//	Integer userid  = user.getUserid();

		String password2 = "";
		try {
			password2 = MD5Util.encrypt(password);// MD5加密
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setPassword(password2);
		this.userService.UpdateByUserid(user);
		return "更改密码成功";
	}
	
	@ResponseBody
	@RequestMapping(value="/exitUser")
	public String exitUser(HttpServletResponse response,HttpServletRequest request,HttpSession session){
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		session.removeAttribute("user");
		
		session.removeAttribute("belong");
		System.out.println("退出成功！");
		
		return "退出成功!";
	}
}
