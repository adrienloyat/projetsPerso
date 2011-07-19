package fr.loyat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Transient;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.MysqlServiceInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Named
@SessionScoped
public class ConnexionManager implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5964064435242212653L;
	
	
	Logger logger = Logger.getLogger(this.getClass().getName());

	
	public String getMysqlPwd(final String json) {
		try {
			JSONObject jsonCredential = getMysqlCredentials(json);
			return (String) jsonCredential.get("password");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getMysqlUser(final String json) {
		try {
			JSONObject jsonCredential = getMysqlCredentials(json);
			return (String) jsonCredential.get("user");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	private JSONObject getMysqlCredentials(final String json)
			throws JSONException {
		JSONObject jo = new JSONObject(json);
		JSONArray jsonMySql = jo.getJSONArray("mysql-5.1");
		jsonMySql.toString(4);
		JSONObject jsonCredential = ((JSONObject) jsonMySql.get(0))
				.getJSONObject("credentials");
		return jsonCredential;
	}
	
	EntityManagerFactory emf=null;
	
	public EntityManagerFactory getEntityManagerFactory() {
		if (emf==null) {
			CloudEnvironment cloudEnvironment = new CloudEnvironment();
			MysqlServiceInfo mysqlInfo = null;
			try {
				mysqlInfo = cloudEnvironment.getServiceInfo("mysqlDb",
						MysqlServiceInfo.class);
			} catch (NullPointerException npe) {
				mysqlInfo = setServiceInfoWithoutCloud();
			}
			logger.log(Level.SEVERE, "MySQL info: url=" + mysqlInfo.getUrl()
					+ "| user = " + mysqlInfo.getUserName() + "| pwd = "
					+ mysqlInfo.getPassword());
	
			List<PersistenceProvider> l = PersistenceProviderResolverHolder
					.getPersistenceProviderResolver().getPersistenceProviders();
			logger.log(Level.SEVERE, "{0} persistence provider " + l.size(),
					l.size());
			for (PersistenceProvider p : l) {
				logger.log(Level.SEVERE, p.toString());
			}
	
			Map<String, String> emfProperties = new HashMap<String, String>();
			emfProperties.put("hibernate.connection.username",
					mysqlInfo.getUserName());
			emfProperties.put("hibernate.connection.password",
					mysqlInfo.getPassword());
			emfProperties.put("hibernate.connection.url", mysqlInfo.getUrl());
	
			emf =  Persistence
					.createEntityManagerFactory("weldTest", emfProperties);
		}
		return emf;
	}

	private static MysqlServiceInfo setServiceInfoWithoutCloud() {
		MysqlServiceInfo mysqlInfo;

		Map<String, Object> credentials = new HashMap<String, Object>();
		credentials.put("hostname", "localhost");
		credentials.put("port", 3306);
		credentials.put("password", "test");
		credentials.put("user", "test");
		credentials.put("name", "test");

		HashMap<String, Object> serviceInfo = new HashMap<String, Object>();
		serviceInfo.put("credentials", credentials);
		mysqlInfo = new MysqlServiceInfo(serviceInfo);
		return mysqlInfo;
	}

	private EntityManager em;

	public  EntityManager getEntityManager() {
		if (em == null || !em.isOpen()) {
			em = getEntityManagerFactory().createEntityManager();
			logger.log(Level.SEVERE, "Creation de l'entityManage " + em);
		}
		return em; 
	}

	
}
