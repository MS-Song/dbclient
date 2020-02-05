package com.song7749.web.filter;

import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * Class Name : CacheFilter.java
 * Description : Web browser cache 를 컨트롤 한다.
* 사용에 대한 매우 주의가 필요하다
* TODO 운영 서버에 갈때는 꺼야 한다.
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 25.		song7749@gmail.com		NEW
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 25.
*/

@WebFilter(
        filterName = "cacheFilter",
        urlPatterns={"/static/**"},
        description= "static elements 가 캐시 되지 않도록 처리 한다. "
)
@Order(1)
public class CacheFilter implements Filter {

    @Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
        resp.setDateHeader("Last-Modified", new Date().getTime());
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");

        chain.doFilter(request, response);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
}