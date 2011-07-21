package fr.loyat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import fr.loyat.domain.User;
import fr.loyat.interceptors.Transactional;

@Named("env")
@RequestScoped
public class Env implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8291917715085214158L;


	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Inject
	public Env(EntityManager entityManager){
		em = entityManager;
	}
	
	public Env(){
		em=null;
	}
	
	// @Inject ConnexionManager connexionManager;
	
	final EntityManager em;
	
	EntityManager getEntityManager(){
		return em;
	}
	
	public String getEnv() {
		System.out.println("VCAP_SERVICES");
		//logger.log(Level.SEVERE, "VCAP_SERVICES");
		// String env = System.getenv("VCAP_SERVICES");
		
		return "";
	}

	@Transactional
	public String  addUser(){
		User user = new User("JM", "De La Tour",
				new GregorianCalendar().get(Calendar.SECOND));
		getEntityManager().persist(user);
		//getEntityManager().getTransaction().begin();
		//getEntityManager().getTransaction().commit();
		users=null;
		return "";
	}
	
	@Transactional
	public String modifyUser(){
		User user = users.get(0);
		user.setFirstName(user.getFirstName()+ "X");
		//getEntityManager().getTransaction().begin();
		//getEntityManager().getTransaction().commit();
		return "";
	}
	
	List<User> users;
	
	@SuppressWarnings("unchecked")
	public List<User>getUsers() {
		if (users == null) {
			users = (List<User>) getEntityManager().createQuery("from User").getResultList();
		}
		return users;
	}

	@PostConstruct
	public void postConstruct(){
		System.out.println("postConstruct " + this);
	}
	



}
