package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import db.Database;

public class FollowTools {
	
	public static boolean isFollowing(String email, String followed) throws SQLException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		int id_follower = ConnectionTools.getIdUserFromEmail(email);
		int id_followed = ConnectionTools.getIdUserFromEmail(followed);
		String sql = String.format("SELECT * FROM Follow WHERE id_follower = %d "
								 + "AND id_followed = %d;", id_follower, id_followed);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		boolean res = rs.next();
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
	public static boolean follow(String email, String followed) throws SQLException, ClassNotFoundException {
		int id_follower = ConnectionTools.getIdUserFromEmail(email);
		int id_followed = ConnectionTools.getIdUserFromEmail(followed);
		Connection conn = Database.getMySQLConnection();
		String sql = String.format("INSERT INTO Follow (id_follower, id_followed) "
								 + "VALUES (%d, %d);", id_follower, id_followed);
		System.out.println(sql);
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static boolean unfollow(String email, String followed) throws SQLException, ClassNotFoundException {
		int id_follower = ConnectionTools.getIdUserFromEmail(email);
		int id_followed = ConnectionTools.getIdUserFromEmail(followed);
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"DELETE FROM Follow\n" + 
				"WHERE id_follower = %d AND id_followed = %d;", id_follower, id_followed);
		Statement stm = conn.createStatement();
		boolean res = (stm.executeUpdate(sql) == 1);
		ConnectionTools.closeAll(null, stm, conn);
		return res;
	}
	
	public static JSONObject getAllFollowers(String email) throws SQLException, JSONException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"SELECT *\n" + 
				"FROM User\n" + 
				"WHERE id_user in\n" + 
				"(SELECT id_follower\n" + 
				"FROM User u, Follow f\n" + 
				"WHERE u.email = '%s' \n" + 
				"AND f.id_followed = u.id_user);", email);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		JSONObject res = new JSONObject();
		int cpt = 1;
		while(rs.next()) {
			res.put("follower " + cpt++, rs.getString("email"));
		}
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
	public static JSONObject getAllFollowed(String email) throws SQLException, JSONException, ClassNotFoundException {
		Connection conn = Database.getMySQLConnection();
		String sql = String.format(
				"SELECT *\n" + 
				"FROM User\n" + 
				"WHERE id_user in\n" + 
				"(SELECT id_followed\n" + 
				"FROM User u, Follow f\n" + 
				"WHERE u.email = '%s' \n" + 
				"AND f.id_follower = u.id_user);", email);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		JSONObject res = new JSONObject();
		int cpt = 1;
		while(rs.next()) {
			res.put("followed " + cpt++, rs.getString("email"));
		}
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
}
