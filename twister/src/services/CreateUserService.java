package services;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ServiceException;
import tools.UserTools;

public class CreateUserService {
	
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException {
		// check if arguments null
		if(login == null || password == null || nom == null || prenom == null) {
			return ServiceException.serviceRefused("argument null", -1);
		}
		// check if login already exists in database (not duplicate)
		if(UserTools.loginExists(login)) {
			return ServiceException.serviceRefused("login already exists", 1);
		}
		if(UserTools.addNewUser(login, password, nom, prenom)) {
			JSONObject res = new JSONObject();
			res.put("retour", "OK");
			return res;
		}else {
			return ServiceException.serviceRefused("add user fails", 2);
		}
	}
	
}