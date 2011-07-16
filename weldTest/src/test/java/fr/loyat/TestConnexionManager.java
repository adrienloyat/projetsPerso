package fr.loyat;

import static  org.testng.Assert.*;

import java.util.ResourceBundle;

import org.json.JSONException;
import org.testng.annotations.Test;

public class TestConnexionManager {
	
	
	private static final String EXPECTED_PASSWORD = "pJohOuRLfV8W6";
	private static final String EXPECTED_USER = "unEyG6xXYiJYu";
	
	public void testGetMysqlPwd() throws JSONException {
		String password="";
		
		String json = getInfos();
		ConnexionManager env = new ConnexionManager();
		password = env.getMysqlPwd(json);
		
		assertEquals(password, EXPECTED_PASSWORD);
	}
	
	private String getInfos() {
		ResourceBundle myResources =
		      ResourceBundle.getBundle("test");
		String json = myResources.getString("json");
		return json;
	}

	@Test
	public void testGetMysqlUser()
	{
		String json = getInfos();
		ConnexionManager env = new ConnexionManager();
		String user  = env.getMysqlUser(json);
		
		assertEquals(user, EXPECTED_USER);
	
	}


}
