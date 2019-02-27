package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.UserTools;

public class AccountService {
	
	public static JSONObject signUp(String email, String username, String password, String nom, String prenom) throws JSONException, SQLException {
		if(username == null || password == null || nom == null || prenom == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(UserTools.emailExists(email)) {
			return ServiceTools.serviceRefused("user(email) already exists", 1);
		}
		if(UserTools.signUp(email, username, password, nom, prenom)) {
			return ServiceTools.serviceAccepted(email, "created successfully");
		}else {
			return ServiceTools.serviceRefused("add user fails", 2);
		}
	}
	
}
