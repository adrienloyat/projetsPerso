package fr.loyat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import fr.loyat.domain.User;

@Named("env")
@RequestScoped
public class Env {

	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Inject
	ConnexionManager connexionManager;
	
	
	EntityManager getEntityManager(){
		return connexionManager.getEntityManager();
	}
	
	public String getEnv() {
		System.out.println("VCAP_SERVICES");
		//logger.log(Level.SEVERE, "VCAP_SERVICES");
		// String env = System.getenv("VCAP_SERVICES");
		
		return "";
	}

	public String  addUser(){
		User user = new User("JM", "De La Tour",
				new GregorianCalendar().get(Calendar.SECOND));
		getEntityManager().persist(user);
		getEntityManager().getTransaction().begin();
		getEntityManager().getTransaction().commit();
		users=null;
		return "";
	}
	
	public String modifyUser(){
		User user = users.get(0);
		user.setFirstName(user.getFirstName()+ "X");
		getEntityManager().getTransaction().begin();
		getEntityManager().getTransaction().commit();
		return "";
	}
	
	List<User> users;
	
	@SuppressWarnings("unchecked")
	public List<User>getUsers() {
		if (users == null) {
			System.out.println("lecture users");
			users = (List<User>) getEntityManager().createQuery("from User").getResultList();
		}
		return users;
	}

	
	



}
