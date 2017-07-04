package cn.itcast.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckCodeImgServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String check = (String) session.getAttribute("checkcode_session");
		String checkCode = request.getParameter("checkCode");
		boolean isTrue = false;
		System.out.println(check);
		System.out.println(checkCode);
		if (check.equals(checkCode)) {
			isTrue = true;
			System.out.println(isTrue);
		}
		response.getWriter().write("{\"isTrue\":" + isTrue + "}");
	}

}