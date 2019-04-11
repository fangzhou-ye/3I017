package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class ConnectionTools {
	
	public static int getIdUserFromEmail(String email) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT id_user FROM User WHERE email = '%s';", email);
		System.out.println(sql);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		rs.next();
		int id = rs.getInt("id_user");
		closeAll(rs, stm, conn);
		return id;
	}
	
	public static String getEmailFromIdUser(int id_user) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT email FROM User WHERE id_user = %d;", id_user);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		rs.next();
		String login = rs.getString("email");
		closeAll(rs, stm, conn);
		return login;
	}
	
	public static String getUsernameFromEmail(String email) throws ClassNotFoundException, SQLException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT username FROM User WHERE email='%s';", email);
		System.out.println(sql);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		rs.next();
		String res = rs.getString("username");
		closeAll(rs, stm, conn);
		return res;
	}

	public static boolean insertConnection(String email) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO Connection (id_user) VALUES ('%d')", getIdUserFromEmail(email));
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean removeConnection(String email) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("DELETE FROM Connection WHERE id_user = %d;", getIdUserFromEmail(email));
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean isConnected(String email) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT * FROM User U, Connection C "
				+ "WHERE U.email = '%s' AND U.id_user = C.id_user;", email);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		boolean res = rs.next();
		closeAll(rs, stm, conn);
		return res;
	}
	
	public static void closeAll(ResultSet rs, Statement stm, Connection conn) throws SQLException {
		if(rs != null) {
			rs.close();
		}
		if(stm != null) {
			stm.close();
		}
		if(conn != null) {
			conn.close();
		}
	}
	
}
