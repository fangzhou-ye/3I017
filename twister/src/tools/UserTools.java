package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.sql.Date;

import db.Database;

public class UserTools {
	
	public static boolean emailExists(String email) throws SQLException, ClassNotFoundException {
		boolean retour;
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT * FROM User WHERE email = '%s';", email);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		retour = rs.next();
		ConnectionTools.closeAll(rs, stm, conn);
		return retour;
	}
	
	public static boolean signUp(String email, String user, String password, String nom, String prenom) throws SQLException, ClassNotFoundException {
		boolean res;
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO User (email, username, password, nom, prenom, join_date) VALUES\n" + 
				"('%s', '%s', '%s', '%s', '%s', '%s');", email, user, password, nom, prenom, new Date(Calendar.getInstance().getTimeInMillis()));
		Statement stm = conn.createStatement();
		res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean checkPassword(String email, String password) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT password FROM User WHERE email = '%s';", email);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		rs.next();
		String correct_password = rs.getString("password");
		ConnectionTools.closeAll(rs, stm, conn);
		return new String(password).equals(correct_password);
	}
	
}
