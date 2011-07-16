package fr.loyat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.MysqlServiceInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.loyat.domain.User;

@Named("env")
public class Env {

	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Inject
	ConnexionManager connexionManager;
	
	EntityManager getEntityManager(){
		return connexionManager.getEntityManager();
	}
	
	public String getEnv() {
		System.out.println("VCAP_SERVICES");
		logger.log(Level.SEVERE, "VCAP_SERVICES");
		// String env = System.getenv("VCAP_SERVICES");
		User user = new User("JM", "De La Tour",
				new GregorianCalendar().get(Calendar.SECOND));
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(user);
		getEntityManager().getTransaction().commit();
		return "";
	}

	public List<User>getUsers() {
		@SuppressWarnings("unchecked")
		List<User> resultList = (List<User>) connexionManager.getEntityManager().createQuery("from User").getResultList();
		return resultList;
	}





}
