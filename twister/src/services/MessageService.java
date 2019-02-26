package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.MessageTools;
import tools.UserTools;

public class MessageService {
	
	private static int idMessage = 1;

	public static JSONObject addMessage(String from, String to, String text) throws JSONException, NumberFormatException, SQLException {
		if(from == null || to == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(!ConnectionTools.isConnected(ConnectionTools.getLoginFromIdUser(Integer.parseInt(from)))) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(!UserTools.loginExists(ConnectionTools.getLoginFromIdUser(Integer.parseInt(to)))) {
			return ServiceTools.serviceRefused("friend not in database", 6);
		}
		if(MessageTools.addMessage(idMessage++, from, to, text)) {
			return ServiceTools.serviceAccepted(from + "->" + to, "message added");
		}else {
			return ServiceTools.serviceRefused(from + "fail to send to " + to, 7);
		}
	}
	
}
