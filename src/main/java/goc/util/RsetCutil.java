package goc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RsetCutil {

	public void setCE(HttpServletResponse response,HttpServletRequest request){
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");

		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8084");
		response.addHeader("Access-Control-Allow-Headers", "Origin,x-requested-with, Content-Type, Accept, X-Cookie");
        response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");

	}
}
