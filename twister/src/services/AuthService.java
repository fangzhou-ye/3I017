package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.UserTools;

public class AuthService {

	public static JSONObject signIn(String email, String password) throws JSONException, SQLException, ClassNotFoundException {
		if(email == null || password == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("already connected", 2);
		}
		if(!UserTools.emailExists(email)) {
			return ServiceTools.serviceRefused("unknown user", 3);
		}
		if(!UserTools.checkPassword(email, password)) {
			return ServiceTools.serviceRefused("wrong password", 4);
		}
		if(ConnectionTools.insertConnection(email)) {
			//get username, listFollowers.... => JSONObject
			String username = ConnectionTools.getUsernameFromEmail(email);
			JSONObject res = new JSONObject();
			res.put("code", 0);
			res.put("message", "login success");
			res.put("email", email);
			res.put("username", username);
			return res;
		}else {
			return ServiceTools.serviceRefused(email + ": connection fail", -1);
		}
	}
	
	public static JSONObject signOut(String email) throws JSONException, SQLException, ClassNotFoundException {
		if(email == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 5);
		}
		if(ConnectionTools.removeConnection(email)) {
			return ServiceTools.serviceAccepted(email, "sign out", 0);
		}else {
			return ServiceTools.serviceRefused("sign out fails", -1);
		}
	}
	
}
