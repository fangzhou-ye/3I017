package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.UserTools;

public class AuthService {

	public static JSONObject login(String login, String password) throws JSONException, SQLException {
		if(login == null || password == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(!UserTools.loginExists(login)) {
			return ServiceTools.serviceRefused("unknown user", 10);
		}
		if(!UserTools.checkPassword(login, password)) {
			return ServiceTools.serviceRefused("wrong password", 100);
		}
		if(ConnectionTools.insertConnection(login)) {
			return ServiceTools.serviceAccepted(login, "connected");
		}else {
			return ServiceTools.serviceRefused(login + ": connection fail", 1000);
		}
	}
	
	public static JSONObject logout(String login) throws JSONException, SQLException {
		if(login == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(!ConnectionTools.isConnected(login)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(ConnectionTools.removeConnection(login)) {
			return ServiceTools.serviceAccepted(login, "sign out");
		}else {
			return ServiceTools.serviceRefused("sign out fails", 10000);
		}
	}
	
}
