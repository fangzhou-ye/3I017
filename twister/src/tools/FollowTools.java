package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import db.Database;

public class FollowTools {
	
	public static boolean isFollowing(String login, String followed) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		int id_follower = ConnectionTools.getIdUserFromLogin(login);
		int id_followed = ConnectionTools.getIdUserFromLogin(followed);
		String sql = String.format("SELECT * FROM Follow WHERE id_follower = %d "
								 + "AND id_followed = %d;", id_follower, id_followed);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		boolean res = rs.next();
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
	public static boolean follow(String login, String followed) throws SQLException {
		int id_follower = ConnectionTools.getIdUserFromLogin(login);
		int id_followed = ConnectionTools.getIdUserFromLogin(followed);
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO Follow (id_follower, id_followed) "
								 + "VALUES (%d, %d);", id_follower, id_followed);
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean unfollow(String login, String followed) throws SQLException {
		int id_follower = ConnectionTools.getIdUserFromLogin(login);
		int id_followed = ConnectionTools.getIdUserFromLogin(followed);
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"DELETE FROM Follow\n" + 
				"WHERE id_follower = %d AND id_followed = %d;", id_follower, id_followed);
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static JSONObject getAllFollowers(String login) throws SQLException, JSONException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"SELECT *\n" + 
				"FROM User\n" + 
				"WHERE id_user in\n" + 
				"(SELECT id_follower\n" + 
				"FROM User u, Follow f\n" + 
				"WHERE u.login = '%s' \n" + 
				"AND f.id_followed = u.id_user);", login);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		JSONObject res = new JSONObject();
		int cpt = 1;
		while(rs.next()) {
			res.put("follower " + cpt++, rs.getString("login"));
		}
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
	public static JSONObject getAllFollowed(String login) throws SQLException, JSONException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"SELECT *\n" + 
				"FROM User\n" + 
				"WHERE id_user in\n" + 
				"(SELECT id_followed\n" + 
				"FROM User u, Follow f\n" + 
				"WHERE u.login = '%s' \n" + 
				"AND f.id_follower = u.id_user);", login);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		JSONObject res = new JSONObject();
		int cpt = 1;
		while(rs.next()) {
			res.put("followed " + cpt++, rs.getString("login"));
		}
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
}
