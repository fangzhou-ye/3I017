package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.UserTools;

public class AccountService {
	
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException, SQLException {
		if(login == null || password == null || nom == null || prenom == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(UserTools.loginExists(login)) {
			return ServiceTools.serviceRefused("user(login) already exists", 1);
		}
		if(UserTools.addNewUser(login, password, nom, prenom)) {
			return ServiceTools.serviceAccepted("result", "new user created successfully");
		}else {
			return ServiceTools.serviceRefused("add user fails", 2);
		}
	}
	
}
