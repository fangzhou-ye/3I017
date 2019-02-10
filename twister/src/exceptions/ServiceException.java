package exceptions;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceException {

	public static JSONObject serviceRefused(String msg, int code) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		obj.put("code", code);
		return obj;
	}
	
}
