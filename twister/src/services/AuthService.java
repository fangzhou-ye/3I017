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
			return ServiceTools.serviceRefused("already connected", 11);
		}
		if(!UserTools.emailExists(email)) {
			return ServiceTools.serviceRefused("unknown user", 10);
		}
		if(!UserTools.checkPassword(email, password)) {
			return ServiceTools.serviceRefused("wrong password", 100);
		}
		if(ConnectionTools.insertConnection(email)) {
			return ServiceTools.serviceAccepted(email, "connected");
		}else {
			return ServiceTools.serviceRefused(email + ": connection fail", 1000);
		}
	}
	
	public static JSONObject signOut(String email) throws JSONException, SQLException, ClassNotFoundException {
		if(email == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(ConnectionTools.removeConnection(email)) {
			return ServiceTools.serviceAccepted(email, "sign out");
		}else {
			return ServiceTools.serviceRefused("sign out fails", 10000);
		}
	}
	
}
