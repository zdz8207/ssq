package com.ssq.util;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetData extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public GetData() {
		super();
	}

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		if (action == null || action.length() == 0) {
			return;
		}
		Method actionMethod;
		try {
			actionMethod = getClass().getMethod(action,
					new Class[] { HttpServletRequest.class });
		} catch (NoSuchMethodException e) {
			throw new ServletException("Unknown action: " + action);
		}
		// 根据请求调用相应的方法
		try {
			actionMethod.invoke(this, new Object[] { request });
		} catch (IllegalAccessException e) {
			throw new ServletException(e);
		} catch (InvocationTargetException e) {
			throw new ServletException(e.getTargetException());
		}
		System.out.println(result);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(result);
		pw.flush();
		pw.close();
	}
	
	public void getPostData(HttpServletRequest request){
		String action = request.getParameter("action");
		String message = request.getParameter("message");
		try {
			message = URLDecoder.decode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.setResult("{success:true,action:'"+ action +"',message:'"+ message +"'}");
	}
	
	public void getTestData(HttpServletRequest request){
		String action = request.getParameter("action");
		this.setResult("{success:true,action:'"+ action +"'}");
	}
}
