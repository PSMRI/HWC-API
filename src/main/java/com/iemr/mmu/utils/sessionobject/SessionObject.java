/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.utils.sessionobject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.redis.RedisSessionException;
import com.iemr.mmu.utils.redis.RedisStorage;

@Component
public class SessionObject {

	// //
	// @Autowired(required = true)
	// // @Required
	// public void setConfigProperties(ConfigProperties configProperties)
	// {
	// // if (configProperties == null)
	// // {
	// // configProperties = new ConfigProperties();
	// // }
	// this.configProperties = configProperties;
	// }

	private RedisStorage objectStore;

	@Autowired
	// @Required
	public void setObjectStore(RedisStorage objectStore) {
		// if (objectStore == null)
		// {
		// objectStore = new RedisStorage();
		// }
		this.objectStore = objectStore;
	}

	public SessionObject() {
		// configProperties = new ConfigProperties();
		// objectStore = new RedisStorage();
		// if (objectStore == null)
		// {
		// objectStore = new RedisStorage();
		// }
		extendExpirationTime = ConfigProperties.getExtendExpiryTime();
		sessionExpiryTime = ConfigProperties.getSessionExpiryTime();
	}

	private boolean extendExpirationTime;// =
											// configProperties.getExtendExpiryTime();
	private int sessionExpiryTime;// = configProperties.getSessionExpiryTime();

	public String getSessionObject(String key) throws RedisSessionException {
		Boolean extendExpirationTime = ConfigProperties.getExtendExpiryTime();
		Integer sessionExpiryTime = ConfigProperties.getSessionExpiryTime();
		// RedisStorage objectStore = new RedisStorage();
		return objectStore.getObject(key, extendExpirationTime, sessionExpiryTime);
	}

	public String setSessionObject(String key, String value) throws RedisSessionException {
		Integer sessionExpiryTime = ConfigProperties.getSessionExpiryTime();
		return objectStore.setObject(key, value, sessionExpiryTime);
	}

	public String updateSessionObject(String key, String value) throws RedisSessionException {
		Boolean extendExpirationTime = ConfigProperties.getExtendExpiryTime();
		Integer sessionExpiryTime = ConfigProperties.getSessionExpiryTime();
		// RedisStorage objectStore = new RedisStorage();
		updateConcurrentSessionObject(key, value, extendExpirationTime, sessionExpiryTime);
		return objectStore.updateObject(key, value, extendExpirationTime, sessionExpiryTime);
		
	}
	private void updateConcurrentSessionObject(String key, String value, Boolean extendExpirationTime,
			Integer sessionExpiryTime) {
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(value);
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ.has("userName") && jsnOBJ.get("userName") != null) {
				objectStore.updateObject(jsnOBJ.get("userName").getAsString().trim().toLowerCase(), key,
						extendExpirationTime, sessionExpiryTime);
			}
		} catch (Exception e) {

		}
	}
	public void deleteSessionObject(String key) throws RedisSessionException {
		// RedisStorage objectStore = new RedisStorage();
		System.out.println(objectStore.deleteObject(key));
	}

	// public static void test(String[] args)
	// {
	// SessionObject obj = new SessionObject();
	// JSONObject testdata = new JSONObject();
	// try
	// {
	// System.out.println("Set Object " + obj.getSessionObject("test1234"));
	// System.out.println("Set Object " + obj.setSessionObject("test1234",
	// testdata.toString()));
	// System.out.println("Set Object " + obj.getSessionObject("test1234"));
	// testdata.put("userName", "test");
	// // testdata.put("validity", obj.sessionExpiryTime);
	// System.out.println("Set Object " + obj.updateSessionObject("test1234",
	// testdata.toString()));
	// System.out.println("Set Object " + obj.getSessionObject("test1234"));
	// obj.deleteSessionObject("test1234");
	// System.out.println("Set Object " + obj.getSessionObject("test1234"));
	// } catch (RedisSessionException | JSONException e)
	// {
	// e.printStackTrace();
	// }
	// }
}
