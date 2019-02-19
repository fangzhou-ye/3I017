package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class ConnectionTools {
	
	public static int getIdUserFromLogin(String login) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT id_user FROM User WHERE login = '%s';", login);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		rs.next();
		int id = rs.getInt("id_user");
		closeAll(rs, stm, conn);
		return id;
	}
	
	public static String getLoginFromIdUser(int id_user) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT login FROM User WHERE id_user = %d;", id_user);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		rs.next();
		String login = rs.getString("login");
		closeAll(rs, stm, conn);
		return login;
	}

	public static boolean insertConnection(String login) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO Connection (id_user) VALUES ('%d')", getIdUserFromLogin(login));
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean removeConnection(String login) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("DELETE FROM Connection WHERE id_user = %d;", getIdUserFromLogin(login));
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean isConnected(String login) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("SELECT * FROM User U, Connection C "
				+ "WHERE U.login = '%s' AND U.id_user = C.id_user;", login);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		boolean res = rs.next();
		closeAll(rs, stm, conn);
		return res;
	}
	
	public static void closeAll(ResultSet rs, Statement stm, Connection conn) throws SQLException {
		if(rs != null)
			rs.close();
		if(stm != null)
			stm.close();
		if(conn != null)
			conn.close();
	}
	
}
