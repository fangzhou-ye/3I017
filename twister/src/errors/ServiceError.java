package errors;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceError {

	public static JSONObject serviceRefused(String msg, int code) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		obj.put("code", code);
		return obj;
	}
	
}
