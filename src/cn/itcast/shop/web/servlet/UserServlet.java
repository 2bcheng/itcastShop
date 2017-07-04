package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;
import cn.itcast.shop.service.impl.UserServiceImpl;
import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.MD5Utils;
import cn.itcast.shop.utils.MailUtils;

public class UserServlet extends BaseServlet {

	// 用户登录
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 每次登录清空保存user的session
		HttpSession session = request.getSession();
		session.setAttribute("usr", null);

		// 获取用户名
		String username = request.getParameter("username");
		// 获取密码
		String pwd = request.getParameter("password");
		// 密码加密
		String password = MD5Utils.md5(pwd);
		UserService userService = new UserServiceImpl();
		User user = null;
		// 登录,获取当前对象
		user = userService.login(username, password);
		String autoLogin = null;
		if (user != null) {
			// 获取是否自动登录
			autoLogin = request.getParameter("autoLogin");
			if ("autoLogin".equals(autoLogin)) {
				// 创建cookie 存储账号和密码;
				Cookie cookie_username = new Cookie("cookie_username", username);
				Cookie cookie_password = new Cookie("cookie_password", password);
				cookie_password.setMaxAge(60 * 60);
				cookie_username.setMaxAge(60 * 60);
				response.addCookie(cookie_password);
				response.addCookie(cookie_username);
			}
			// session存储当前登录用户

			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {
			request.setAttribute("msg", "用户名或者密码不正确");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}
	}

	// 用户注册
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置字符集
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// 获取页面传递过来的所有数据

		Map<String, String[]> properties = request.getParameterMap();
		// 创建user对象
		User user = new User();
		try {
			// 接收过的数据,封装到实体类
			// BeanUtils.populate(user, properties);

			BeanUtils.copyProperties(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		// 设置uuid
		user.setUid(CommonUtils.getUUID());
		// 设置电话号码
		user.setTelephone(null);
		// 是否激活
		user.setState(0);
		// md5方式加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		// 设置激活码
		user.setCode(CommonUtils.getUUID());
		UserService userService = new UserServiceImpl();
		// 将user传递给service层
		boolean isRegisterStatus = false;
		if (user != null) {
			// 注册
			isRegisterStatus = userService.register(user);
		}

		if (isRegisterStatus) {

			try {
				MailUtils.sendMail(user.getEmail(), user.getUsername(),
						user.getCode());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// 注册失败,不处理
		}

		response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");

	}

	// 检验用户名
	public void checkUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		if (username.length() > 0) {
			UserService userService = new UserServiceImpl();
			try {
				//没返回给浏览器客户端
				userService.checkUser(username);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
		}

	}

	// 激活用户
	public void active(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("username");
		String code = request.getParameter("validateCode");
		// 不判断重复激活,以及用户其他状态
		UserService userService = new UserServiceImpl();
		if (userName.length() > 0 && code.length() > 0) {
			try {
				if (userService.active(userName, code)) {
					// 激活成功,重定向页面
					response.sendRedirect(request.getContextPath()
							+ "/activeSuccess.jsp");
				} else {
					// 暂不对激活失败进行处理
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 退出登录
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		// 移除session中的用户
		session.removeAttribute("user");
		// 移除浏览器cookie
		Cookie cookie_username = new Cookie("cookie_username", "");
		Cookie cookie_password = new Cookie("cookie_password", "");
		cookie_password.setMaxAge(0);
		cookie_username.setMaxAge(0);
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

}