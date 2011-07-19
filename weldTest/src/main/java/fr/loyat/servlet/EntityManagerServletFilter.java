package fr.loyat.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class EntityManagerServletFilter  implements Filter{

	public void destroy() {
		System.out.println("EntityManagerServletFilter.destroy");
		
	}

	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("EntityManagerServletFilter.doFilter");
		chain.doFilter(req, rep);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("EntityManagerServletFilter.init ");
		
	}


}
