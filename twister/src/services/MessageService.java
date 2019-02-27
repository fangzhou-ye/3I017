package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import tools.ServiceTools;
import tools.ConnectionTools;
import tools.MessageTools;

public class MessageService {

	public static JSONObject postMessage(String idMessage, String email, String content) throws JSONException, SQLException, ClassNotFoundException {
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
	
	public static List<Document> searchMessage(String email, String query) throws SQLException, ClassNotFoundException{
		if(query != "") {
			// ToDo search by key word
			return new ArrayList<Document>();
		}
		if(ConnectionTools.isConnected(email)) {
			return MessageTools.getAllMessage(email);
		}else {
			return new ArrayList<Document>();
		}
	}
	
}
