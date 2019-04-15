package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.json.JSONArray;
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
		JSONArray arr = new JSONArray();
		while(rs.next()) {
			arr.put(rs.getString("email"));
		}
		res.put("nbMessages", MessageTools.getMyMessages(email).getString("nbMessages"));
		res.put("nbFollowers", arr.length());
		res.put("email", email);
		res.put("username", ConnectionTools.getUsernameFromEmail(email));
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
		JSONArray arr = new JSONArray();
		while(rs.next()) {
			arr.put(getAllFollowers(rs.getString("email")));
		}
		res.put("nbFriends", arr.length());
		res.put("username", ConnectionTools.getUsernameFromEmail(email));
		res.put("friends", arr);
		ConnectionTools.closeAll(rs, stm, conn);
		return res;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException {
		System.out.println(getAllFollowed("fangzhou.ye@yahoo.com"));
	}
	
}
