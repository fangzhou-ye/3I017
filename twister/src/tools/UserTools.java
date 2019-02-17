package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import Database.Database;

public class UserTools {

	public static boolean userExists(String login) throws SQLException{
		boolean retour;
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT id FROM login WHERE login = " + login + ";";
		System.out.println(query);
		Statement instruction = conn.createStatement();
		instruction.executeQuery(query);
		ResultSet rs = instruction.getResultSet();
		retour = rs.next();
		rs.close();
		instruction.close();
		conn.close();
		return retour;
	}
	
	public static JSONObject listAllUsers() throws SQLException {
		Connection conn = Database.getMySQLConnection();
		JSONObject res = new JSONObject();
		String query = "SELECT * FROM User";
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(query);
		while(rs.next()) {
			JSONObject user = new JSONObject();
			int id_user = rs.getInt("id_user");
			String login = rs.getString("login");
			String pwd = rs.getString("password");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			try {
				user.put("id_user", id_user);
				user.put("login", login);
				user.put("password", pwd);
				user.put("nom", nom);
				user.put("prenom", prenom);
				res.put("user" + id_user, user);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs.close();
		stm.close();
		conn.close();
		return res;
	}
	
	public static boolean checkPassword(String login, String password) {
		return true;
	}
	
	public static boolean loginExists(String login) {
		return false;
	}
	
	public static boolean addNewUser(String login, String password, String nom, String prenom) {
		return true;
	}
	
}
