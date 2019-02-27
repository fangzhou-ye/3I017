package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.FollowTools;
import tools.UserTools;

public class Follow {

	public static JSONObject follow(String email, String friend_email) throws JSONException, SQLException {
		if(email == null || friend_email == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(new String(email).equals(friend_email)) {
			return ServiceTools.serviceRefused("friend can not be yourself", 3);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(FollowTools.isFollowing(email, friend_email)) {
			return ServiceTools.serviceRefused("already following", 5);
		}
		if(!UserTools.emailExists(friend_email)) {
			return ServiceTools.serviceRefused("friend not in database", 6);
		}
		if(FollowTools.follow(email, friend_email)) {
			return ServiceTools.serviceAccepted(email, "be friend with " + friend_email);
		}else {
			return ServiceTools.serviceRefused(email + "fail to add " + friend_email, 7);
		}
	}

	public static JSONObject unfollow(String email, String friend_email) throws JSONException, SQLException {
		if(email == null || friend_email == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(new String(email).equals(friend_email)) {
			return ServiceTools.serviceRefused("can not follow yourself", 3);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(!FollowTools.isFollowing(email, friend_email)) {
			return ServiceTools.serviceRefused(email + " is not following " + friend_email, 6);
		}
		JSONObject res = new JSONObject();
		if(FollowTools.unfollow(email, friend_email)) {
			res.put(email, "unfollow " + friend_email);
		}else {
			res.put(email, "fail to unfollow " + friend_email);
		}
		return res;
	}
	
}
