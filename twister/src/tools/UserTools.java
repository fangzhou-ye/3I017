package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
