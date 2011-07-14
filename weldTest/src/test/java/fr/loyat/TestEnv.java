package fr.loyat;

import static  org.testng.Assert.*;

import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.testng.annotations.Test;

public class TestEnv {
	
	
	private static final String EXPECTED_PASSWORD = "pJohOuRLfV8W6";
	private static final String EXPECTED_USER = "unEyG6xXYiJYu";
	
	public void testGetMysqlPwd() throws JSONException {
		String password="";
		
		String json = getInfos();
		Env env = new Env();
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
		Env env = new Env();
		String user  = env.getMysqlUser(json);
		
		assertEquals(user, EXPECTED_USER);
	
	}

	@Test
	public void testGetEntityManagerFactory()
	{
	
	}

}
