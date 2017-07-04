package cn.itcast.shop.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;
import cn.itcast.shop.service.impl.UserServiceImpl;

public class AutoLoginFilter implements Filter {

	public AutoLoginFilter() {
	}

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Cookie[] cookies = req.getCookies();
		String username = "";
		String password = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("cookie_username".equals(cookie.getName()))
					username = cookie.getValue();
				if ("cookie_password".equals(cookie.getName()))
					password = cookie.getValue();
			}
		}
		UserService userService = new UserServiceImpl();
		if (username.trim().length() > 0 && password.trim().length() > 0) {
			User user = userService.login(username, password);
			if (user != null) {
				req.getSession().setAttribute("user", user);
			}
		}
		chain.doFilter(request, response);
	}

}
