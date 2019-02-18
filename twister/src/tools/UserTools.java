package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import Database.Database;

public class UserTools {
	
	public static boolean loginExists(String login) throws SQLException {
		boolean retour;
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT * FROM User WHERE login = '%s';", login);
		Statement stm = conn.createStatement();
		stm.executeQuery(sql);
		ResultSet rs = stm.getResultSet();
		retour = rs.next();
		rs.close();
		stm.close();
		conn.close();
		return retour;
	}
	
	public static boolean addNewUser(String login, String password, String nom, String prenom) throws SQLException {
		boolean res;
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO User (login, password, nom, prenom) VALUES\n" + 
				"('%s', '%s', '%s', '%s');", login, password, nom, prenom);
		Statement stm = conn.createStatement();
		if(stm.executeUpdate(sql) == 1) {
			res = true;
		}else {
			res = false;
		}
		stm.close();
		conn.close();
		return res;
	}
	
	public static boolean checkPassword(String login, String password) {
		return true;
	}
	
}
