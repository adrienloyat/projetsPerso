package fr.loyat.interceptors;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transactional @Interceptor
public class TransactionalInterceptors {
	
	@Inject EntityManager em;

	
	@AroundInvoke
	public Object invocation(InvocationContext invocation) throws Exception {
		System.out.println(invocation + " " + invocation.getMethod());
		
		em.getTransaction().begin();
		try {
			return invocation.proceed();
		} finally {
			em.getTransaction().commit();
			System.out.println("commit");
		}
	}
	
}
