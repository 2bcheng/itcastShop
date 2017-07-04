package cn.itcast.shop.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class CharFilter
 */
public class CharFilter implements Filter {
	public CharFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//被增强的对象
		HttpServletRequest req = (HttpServletRequest) request;
		//增强对象
		CharRequest charReuest = new CharRequest(req);
		charReuest.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println(1);
		chain.doFilter(charReuest, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

class CharRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	public CharRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	// 对getParaameter增强
	@Override
	public String getParameter(String name) {
		String parameter = request.getParameter(name);// 乱码
		try {
			parameter = new String(parameter.getBytes("iso8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return parameter;
	}
}