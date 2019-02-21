package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ServiceException;
import tools.ConnectionTools;
import tools.MessageTools;
import tools.UserTools;

public class MessageService {
	
	private static int idMessage = 1;

	public static JSONObject addMessage(String from, String to, String text) throws JSONException, NumberFormatException, SQLException {
		if(from == null || to == null) {
			return ServiceException.serviceRefused("wrong argument", -1);
		}
		if(!ConnectionTools.isConnected(ConnectionTools.getLoginFromIdUser(Integer.parseInt(from)))) {
			return ServiceException.serviceRefused("user not connected", 4);
		}
		if(!UserTools.loginExists(ConnectionTools.getLoginFromIdUser(Integer.parseInt(to)))) {
			return ServiceException.serviceRefused("friend not in database", 6);
		}
		JSONObject res = new JSONObject();
		if(MessageTools.addMessage(idMessage++, from, to, text)) {
			res.put(from, "sent to " + to);
		}else {
			res.put(from, "fail to send to " + to);
		}
		return res;
	}
	
}
