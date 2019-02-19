package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ServiceException;
import tools.ConnectionTools;
import tools.FriendTools;
import tools.UserTools;

public class FriendService {

	public static JSONObject addFriend(String login, String friend) throws JSONException, SQLException {
		if(login == null || friend == null) {
			return ServiceException.serviceRefused("wrong argument", -1);
		}
		if(new String(login).equals(friend)) {
			return ServiceException.serviceRefused("friend can not be yourself", 3);
		}
		if(!ConnectionTools.isConnected(login)) {
			return ServiceException.serviceRefused("user not connected", 4);
		}
		if(FriendTools.isFriend(login, friend)) {
			return ServiceException.serviceRefused("already friends", 5);
		}
		if(!UserTools.loginExists(friend)) {
			return ServiceException.serviceRefused("friend not in database", 6);
		}
		JSONObject res = new JSONObject();
		if(FriendTools.addFriend(login, friend)) {
			res.put(login, "be friend with " + friend);
		}else {
			res.put(login, "fail to add " + friend);
		}
		return res;
	}

	public static JSONObject removeFriend(String login, String friend) throws JSONException, SQLException {
		if(login == null || friend == null) {
			return ServiceException.serviceRefused("wrong argument", -1);
		}
		if(new String(login).equals(friend)) {
			return ServiceException.serviceRefused("friend can not be yourself", 3);
		}
		if(!ConnectionTools.isConnected(login)) {
			return ServiceException.serviceRefused("user not connected", 4);
		}
		if(!FriendTools.isFriend(login, friend)) {
			return ServiceException.serviceRefused(login + " not friend with " + friend, 6);
		}
		JSONObject res = new JSONObject();
		if(FriendTools.removeFriend(login, friend)) {
			res.put(login, "remove friend" + friend);
		}else {
			res.put(login, "fail to remove " + friend);
		}
		return res;
	}
	
}
