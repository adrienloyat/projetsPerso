package fr.loyat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
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

@Named("env")
@ApplicationScoped
public class Env {

	public Env() {
	}


	public String getEnv(){
		System.out.println("VCAP_SERVICES");
        logger.log(Level.SEVERE,"VCAP_SERVICES");
		String env = System.getenv("VCAP_SERVICES");
		getEntityManager();
		return env;
	}
	
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
		JSONObject jsonCredential = ((JSONObject) jsonMySql.get(0)).getJSONObject("credentials");
		return jsonCredential;
	}
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	public EntityManagerFactory getEntityManagerFactory(){
		CloudEnvironment cloudEnvironment = new CloudEnvironment();
		MysqlServiceInfo mysqlInfo=null;
		try {
			mysqlInfo = cloudEnvironment.getServiceInfo("mysqlDb", MysqlServiceInfo.class);
		}catch (NullPointerException npe) {
			mysqlInfo = setServiceInfoWithoutCloud();
		}
        logger.log(Level.SEVERE,"MySQL info: url=" + mysqlInfo.getUrl() + "| user = " +mysqlInfo.getUserName()+ "| pwd = " + mysqlInfo.getPassword() );
        
        List<PersistenceProvider> l = PersistenceProviderResolverHolder
		.getPersistenceProviderResolver()
		.getPersistenceProviders();
        logger.log(Level.SEVERE, "{0} persistence provider " + l.size(),l.size());
        for(PersistenceProvider p :l) {
        	logger.log(Level.SEVERE, p.toString());
        }
        
        Map<String, String> emfProperties = new HashMap<String, String>();
        emfProperties.put("hibernate.connection.username", mysqlInfo.getUserName());
        emfProperties.put("hibernate.connection.password", mysqlInfo.getPassword());
        emfProperties.put("hibernate.connection.url", mysqlInfo.getUrl());

        return Persistence.createEntityManagerFactory("weldTest", emfProperties);
	}


	private MysqlServiceInfo setServiceInfoWithoutCloud() {
		MysqlServiceInfo mysqlInfo;
		
		Map<String,Object> credentials = new HashMap<String, Object>();
		credentials.put("hostname", null);
		credentials.put("port", 3333);
		credentials.put("password", null);
		
		HashMap<String, Object> serviceInfo = new HashMap<String, Object>();
		serviceInfo.put("credentials", credentials);
		mysqlInfo = new MysqlServiceInfo(serviceInfo);
		return mysqlInfo;
	}
	
	public EntityManager getEntityManager(){
		return getEntityManagerFactory().createEntityManager();
	}
	
}
