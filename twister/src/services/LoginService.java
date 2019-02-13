package services;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ServiceException;
import tools.ConnectionTools;
import tools.UserTools;

public class LoginService {

	public static JSONObject login(String login, String password) throws JSONException {
		// check if arguments null
		if(login == null || password == null) {
			return ServiceException.serviceRefused("wrong argument", 1);
		}
		// check if user exists
		if(!UserTools.userExists(login)) {
			return ServiceException.serviceRefused("unknown user", 10);
		}
		// check if password correct
		if(!UserTools.checkPassword(login, password)) {
			return ServiceException.serviceRefused("wrong password", 100);
		}
		JSONObject res = new JSONObject();
		String key = ConnectionTools.insertConnection(login, false);
		res.put("key", key);
		return res;
	}
	
}
