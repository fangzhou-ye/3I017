package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceTools {

	public static JSONObject serviceRefused(String msg, int code) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		obj.put("code", code);
		return obj;
	}
	
	public static JSONObject serviceAccepted(String res, String msg, int code) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("code", code);
		obj.put(res, msg);
		return obj;
	}
	
}
