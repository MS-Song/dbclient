package com.song7749.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.song7749.web.util.RequestWrapper;
import org.springframework.core.annotation.Order;

/**
 * SR DATA Center 의 run 인 경우 XSS 필터를 실행 한다. -- 그 외에는 동작 하지 않는다.
 */

@WebFilter(
		filterName = "XSSFilter",
		urlPatterns={"/srDataRequest/runNow"},
		description= "SR Data Center 의 실행 시에 XSS 필터가 되도록 처리함 "
)
@Order(2)
public class XSSFilter implements Filter {

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {}

		@Override
		public void destroy() {}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
		}
}