package goc.controller;

import java.io.IOException;

import goc.pojo.User;
import goc.service.UserService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping("/nabar")
public class NavigationbarController {

	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/getuser", produces = "text/json;charset=UTF-8")
	public void getuser(HttpServletResponse response,HttpServletRequest request) throws IOException{
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		User user = (User) request.getSession().getAttribute("user");
		
//		User user2 = this.userService.SelectByUserid(user.getUserid());
		Gson gson = new Gson();
		
		response.getWriter().write(gson.toJson(user));
		
	}

}
