package goc.controller;

import goc.pojo.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Controller
@RequestMapping("/test")
public class TestWebServiceController {

	@ResponseBody
	@RequestMapping(value="/tee", produces = "text/json;charset=UTF-8")
	public void tee(HttpServletResponse response,HttpServletRequest request) throws IOException{
	/*	response.setHeader("Access-Control-Allow-Origin", "*");
	    
		response.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type, Accept,X-Cookie");
        response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");*/

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		
		User user = new User();
		user.setUserid(111);
		user.setAge(20);
		user.setUsername("kkk");
		
        System.out.println("测试成功");
        Gson gson = new Gson();
        
   //     List<User> user2 = new ArrayList<User>();

        
         response.getWriter().write( gson.toJson(user));
      /*  String gg = gson.toJson(user);
        System.out.println(gson.toJson(user));
        response.getWriter().write(gg);*/
	/*	JSONObject jsonObject = new JSONObject();
		jsonObject.put("test", user);
		response.getWriter().write(user.toString());*/
	}
}
