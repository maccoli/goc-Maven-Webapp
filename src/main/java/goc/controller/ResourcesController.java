package goc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import goc.pojo.Resources;
import goc.service.ResourcesService;
import goc.util.RsetCutil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.java.javaURLContextFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/resources")
public class ResourcesController {

	@Resource
	private ResourcesService resourcesService;
	
	@ResponseBody
	@RequestMapping("/showAllre")
	public void showAllre(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//调用数据方法
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);

		List<Resources> resources=this.resourcesService.SelectAll();

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		response.getWriter().write(gson.toJson(resources));
	}
	
	@ResponseBody
	@RequestMapping("/showByType")
	public void showByType(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//调用数据方法
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		String type = java.net.URLDecoder.decode(request.getParameter("type"), "UTF-8"); 
		
		List<Resources> resources = this.resourcesService.SelectByType(type);
		
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		response.getWriter().write(gson.toJson(resources));
		
	}
	
	@ResponseBody
	@RequestMapping("/showByTitle")
	public void showByTitle(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//调用数据方法
		RsetCutil rsl = new RsetCutil();
		rsl.setCE(response, request);
		
		String title = java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8"); 
		
		Resources resources = this.resourcesService.SelectByTitle(title);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		response.getWriter().write(gson.toJson(resources));

	}
}
