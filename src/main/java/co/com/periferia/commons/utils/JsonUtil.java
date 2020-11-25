package co.com.periferia.commons.utils;

import com.google.gson.Gson;

public class JsonUtil {
	
	private static Gson gson = new Gson();
	
	private JsonUtil() {}
	
	@SuppressWarnings("unchecked")
	public static Object json2Object(String json, @SuppressWarnings("rawtypes") Class classType) {
		return gson.fromJson(json, classType);
		
	}
	
	public static String object2Json(Object obj) {
		return gson.toJson(obj);
		
	}
	
}	
