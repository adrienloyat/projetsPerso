package fr.loyat.servlet;



import javax.servlet.ServletRequestEvent;

import org.jboss.weld.servlet.api.helpers.AbstractServletListener;



public class EntityManagerServlet  extends AbstractServletListener{

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		super.requestDestroyed(sre);
		System.out.println("requestDestroyed");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		super.requestInitialized(sre);
		System.out.println("requestInitialized");
	}


	

}
