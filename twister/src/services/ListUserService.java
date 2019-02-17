package services;

import java.sql.SQLException;

import org.json.JSONObject;

import tools.UserTools;

public class ListUserService {

	public static JSONObject listUsers() throws SQLException {
		return UserTools.listAllUsers();
	}
	
}
