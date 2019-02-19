package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class FriendTools {
	
	public static boolean isFriend(String login, String friend) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		int id_login = ConnectionTools.getIdUserFromLogin(login);
		int id_friend = ConnectionTools.getIdUserFromLogin(friend);
		String sql = String.format(
				"SELECT *\n" + 
				"FROM Friendship\n" + 
				"WHERE id_user1 = %d AND id_user2 = %d\n" + 
				"UNION\n" + 
				"SELECT *\n" + 
				"FROM Friendship\n" + 
				"WHERE id_user1 = %d AND id_user2 = %d;", id_login, id_friend, id_friend, id_login);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		boolean res = rs.next();
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
	public static boolean addFriend(String login, String friend) throws SQLException {
		int id_login = ConnectionTools.getIdUserFromLogin(login);
		int id_friend = ConnectionTools.getIdUserFromLogin(friend);
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO Friendship (id_user1, id_user2) VALUES (%d, %d);", id_login, id_friend);
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean removeFriend(String login, String friend) throws SQLException {
		int id_login = ConnectionTools.getIdUserFromLogin(login);
		int id_friend = ConnectionTools.getIdUserFromLogin(friend);
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"DELETE FROM Friendship\n" + 
				"WHERE id_user1 = %d AND id_user2 = %d;\n" + 
				"DELETE FROM Friendship\n" + 
				"WHERE id_user1 = %d AND id_user2 = %d;", id_login, id_friend, id_friend, id_login);
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static void listFriends(String login) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		int id_login = ConnectionTools.getIdUserFromLogin(login);
		String sql = String.format(
				"SELECT id_user1 AS id_user\n" + 
				"FROM Friendship\n" + 
				"where id_user2 = %d\n" + 
				"UNION\n" + 
				"SELECT id_user2\n" + 
				"FROM Friendship\n" + 
				"where id_user1 = %d;", id_login, id_login);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()) {
			System.out.println(ConnectionTools.getLoginFromIdUser(rs.getInt("id_user")));
		}
		ConnectionTools.closeAll(rs, stm, conn);
	}
	
}
