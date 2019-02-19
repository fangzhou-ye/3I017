package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ServiceException;
import tools.UserTools;

public class AccountService {
	
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException, SQLException {
		if(login == null || password == null || nom == null || prenom == null) {
			return ServiceException.serviceRefused("wrong argument", -1);
		}
		if(UserTools.loginExists(login)) {
			return ServiceException.serviceRefused("user(login) already exists", 1);
		}
		if(UserTools.addNewUser(login, password, nom, prenom)) {
			JSONObject res = new JSONObject();
			res.put("result", "new user added successfully!");
			return res;
		}else {
			return ServiceException.serviceRefused("add user fails", 2);
		}
	}
	
}