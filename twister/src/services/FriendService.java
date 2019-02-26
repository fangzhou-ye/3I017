package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.FollowTools;
import tools.UserTools;

public class FriendService {

	public static JSONObject addFriend(String login, String friend) throws JSONException, SQLException {
		if(login == null || friend == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(new String(login).equals(friend)) {
			return ServiceTools.serviceRefused("friend can not be yourself", 3);
		}
		if(!ConnectionTools.isConnected(login)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(FollowTools.isFollowing(login, friend)) {
			return ServiceTools.serviceRefused("already following", 5);
		}
		if(!UserTools.loginExists(friend)) {
			return ServiceTools.serviceRefused("friend not in database", 6);
		}
		if(FollowTools.follow(login, friend)) {
			return ServiceTools.serviceAccepted(login, "be friend with " + friend);
		}else {
			return ServiceTools.serviceRefused(login + "fail to add " + friend, 7);
		}
	}

	public static JSONObject removeFriend(String login, String friend) throws JSONException, SQLException {
		if(login == null || friend == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(new String(login).equals(friend)) {
			return ServiceTools.serviceRefused("can not follow yourself", 3);
		}
		if(!ConnectionTools.isConnected(login)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(!FollowTools.isFollowing(login, friend)) {
			return ServiceTools.serviceRefused(login + " is not following " + friend, 6);
		}
		JSONObject res = new JSONObject();
		if(FollowTools.unfollow(login, friend)) {
			res.put(login, "unfollow" + friend);
		}else {
			res.put(login, "fail to unfollow " + friend);
		}
		return res;
	}
	
}
