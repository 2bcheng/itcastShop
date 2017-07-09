package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		try {

			String methodname = request.getParameter("method");
			Class clazz = this.getClass();
			System.out.println(methodname);
			Method method = clazz.getMethod(methodname,
					HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
}