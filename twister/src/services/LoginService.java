package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ServiceException;
import tools.ConnectionTools;
import tools.UserTools;

public class LoginService {

	public static JSONObject login(String login, String password) throws JSONException, SQLException {
		if(login == null || password == null) {
			return ServiceException.serviceRefused("wrong argument", 1);
		}
		if(!UserTools.loginExists(login)) {
			return ServiceException.serviceRefused("unknown user", 10);
		}
		if(!UserTools.checkPassword(login, password)) {
			return ServiceException.serviceRefused("wrong password", 100);
		}
		JSONObject res = new JSONObject();
		if(ConnectionTools.insertConnection(login)) {
			res.put(login, "connected");
		}else {
			res.put(login, "connection failed");
		}
		return res;
	}
	
}
