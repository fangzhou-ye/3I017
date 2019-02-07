package services;

import org.json.JSONException;
import org.json.JSONObject;

import errors.ServiceError;
import tools.ConnectionTools;
import tools.UserTools;

public class LoginService {

	public static JSONObject login(String login, String password) throws JSONException {
		// check if arguments null
		if(login == null || password == null) {
			return ServiceError.serviceRefused("wrong argument", 1);
		}
		// check if user exists
		if(!UserTools.userExists(login)) {
			return ServiceError.serviceRefused("unknown user", 10);
		}
		// check if password correct
		if(!UserTools.checkPassword(login, password)) {
			return ServiceError.serviceRefused("wrong password", 100);
		}
		JSONObject res = new JSONObject();
		String key = ConnectionTools.insertConnection(login, false);
		res.put("key", key);
		return res;
	}
	
}
