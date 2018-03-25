package goc.controller;

import goc.pojo.User;
import goc.service.UserService;
import goc.util.MD5Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/register",produces="text/json;charset=UTF-8")
	public void register(HttpServletResponse response,HttpServletRequest request) throws IOException{
		User user = new User();
		String password = "";
		String phone = "";
		String username = "";
		String verifycode = "0";
		String svc =(String) request.getSession().getAttribute("sessionverify");  
		
		StringBuffer requestBody;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		try {
			BufferedReader reader = request.getReader();
			String input = null;
			requestBody = new StringBuffer();
			while((input = reader.readLine())!=null){
				requestBody.append(input);
				JSONObject jsonObject = new JSONObject();
				phone = jsonObject.get("phone").toString();
				password = jsonObject.get("password").toString();
				verifycode = jsonObject.get("verifycode").toString();
			}
		}catch(JSONException e1){
			
			e1.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject();
		if(svc.equals(verifycode)){
			try {
				password = MD5Util.encrypt(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setUsername(username);
			user.setPhone(phone);
			user.setPassword(password);
			user.setUsername("user_"+phone.substring(0, 4));
			this.userService.InsertUser(user);
			jsonObject.put("notcie", "ok");
		}
		else {
			jsonObject.put("notice", "yzmcw");
		}
		
		response.getWriter().write(jsonObject.toString());
		
	}
	
	@ResponseBody
	@RequestMapping(value="/login",produces="text/json;charset=UTF-8")
	public void login(HttpServletResponse response,HttpServletRequest request) throws IOException{
		User user = new User();
		String phone = "";
		String password = "";
		String verifycode = "0";
		String password2 = "";
		String svc =(String) request.getSession().getAttribute("sessionverify");  
		
		StringBuffer requestBody;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		try {
			BufferedReader reader = request.getReader();
			String input = null;
			requestBody = new StringBuffer();
			while((input = reader.readLine())!=null){
				requestBody.append(input);
				JSONObject jsonObject = new JSONObject();
				phone = jsonObject.get("phone").toString();
				password = jsonObject.get("password").toString();
				verifycode = jsonObject.get("verifycode").toString();
			}
		}catch(JSONException e1){
	
			e1.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject();

	    user = this.userService.SelectByPhone(phone);
		if(svc.equals(verifycode)){
			try {
				password2 = MD5Util.encrypt(password);// MD5加密
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(password2.equals(user.getPassword())){
				request.getSession().setAttribute("user", user);
				jsonObject.put("notice", "ok");
			}
			else{ 
				jsonObject.put("notice", "mmcw");	
			}
		}
		else{
			jsonObject.put("notice", "yzmcw");
		}
		response.getWriter().write(jsonObject.toString());
	}
	
	@ResponseBody
	@RequestMapping(value="changePW",produces="text/json;charset=UTF-8")
	public String changePW(HttpServletResponse response,HttpServletRequest request){
		String password = "";
		User user = (User) request.getSession().getAttribute("user");
		int userid  = user.getUserid();
		
		StringBuffer requestBody;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		try {
			BufferedReader reader = request.getReader();
			String input = null;
			requestBody = new StringBuffer();
			while((input = reader.readLine())!=null){
				requestBody.append(input);
				JSONObject jsonObject = new JSONObject();
				password = jsonObject.get("password").toString();
			}
		}catch(JSONException e1){
	
			e1.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		user.setPassword(password);
		this.userService.UpdateByUserid(user);
		return "更改密码成功";
	}
}
