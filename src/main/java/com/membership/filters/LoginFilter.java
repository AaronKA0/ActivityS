package com.membership.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
		Object memAcc = session.getAttribute("memAcc");
		// 【檢查是否訪問登入頁面，避免無窮迴圈的重新導向】
		String requestURI = req.getRequestURI();
		if (memAcc == null && !requestURI.endsWith("/login")) {
			session.setAttribute("location", requestURI);
			res.sendRedirect(req.getContextPath() + "/membership/login");
			return;
		} else {
			chain.doFilter(request, response);
		}

	}

}
