package com.wx.v1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.HttpTool;
import tool.ReadConfig;

/**
 * Servlet implementation class GetWX_Get_Token
 */
@WebServlet(description = "获取微信token", urlPatterns = { "/GetWX_Get_Token" })
public class GetWX_Get_Token extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		try {
			String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
			String appSecret = new ReadConfig().getProp("/conf.properties").getProperty("APP_SECRET");
			String str = HttpTool.GET(url, "grant_type=client_credential&appid=" + appID + "&secret=" + appSecret);

			System.out.println(url);
			out.print(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
