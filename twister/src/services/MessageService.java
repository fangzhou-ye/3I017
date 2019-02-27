package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.MessageTools;

public class MessageService {

	public static JSONObject postMessage(String idMessage, String email, String content) throws JSONException, SQLException {
		if(email == null || content == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(MessageTools.postMessage(Integer.parseInt(idMessage), email, content)) {
			return ServiceTools.serviceAccepted(email, "message posted");
		}else {
			return ServiceTools.serviceRefused(email + " post failed", 7);
		}
	}
	
}
