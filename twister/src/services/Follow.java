package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.FollowTools;
import tools.MessageTools;
import tools.UserTools;

public class Follow {

	public static JSONObject follow(String email, String friend_email) throws JSONException, SQLException, ClassNotFoundException {
		if(email == null || friend_email == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(new String(email).equals(friend_email)) {
			return ServiceTools.serviceRefused("friend can not be yourself", 7);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 5);
		}
		if(FollowTools.isFollowing(email, friend_email)) {
			return ServiceTools.serviceRefused("already following", 8);
		}
		if(!UserTools.emailExists(friend_email)) {
			return ServiceTools.serviceRefused("friend not in database", 9);
		}
		if(FollowTools.follow(email, friend_email)) {
			return ServiceTools.serviceAccepted(email, "be friend with " + friend_email, 0);
		}else {
			return ServiceTools.serviceRefused(email + "fail to add " + friend_email, -1);
		}
	}

	public static JSONObject unfollow(String email, String friend_email) throws JSONException, SQLException, ClassNotFoundException {
		if(email == null || friend_email == null) {
			return ServiceTools.serviceRefused("wrong argument", 1);
		}
		if(new String(email).equals(friend_email)) {
			return ServiceTools.serviceRefused("can not unfollow yourself", 10);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 5);
		}
		if(!FollowTools.isFollowing(email, friend_email)) {
			return ServiceTools.serviceRefused(email + " is not following " + friend_email, 11);
		}
		if(FollowTools.unfollow(email, friend_email)) {
			return ServiceTools.serviceAccepted(email, friend_email, 0);
		}else {
			return ServiceTools.serviceRefused("fail to unfollow", -1);
		}
	}
	
	public static JSONObject listFollowed(String email) throws JSONException, ClassNotFoundException, SQLException {
		if(ConnectionTools.isConnected(email)) {
			return FollowTools.getAllFollowed(email);
		}else {
			return new JSONObject();
		}
	}
	
}
