package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.CommentTools;
import tools.ConnectionTools;
import tools.ServiceTools;

public class CommentService {

	public static JSONObject addComment(String idMessage, String idComment, String email, String text) throws JSONException, SQLException, ClassNotFoundException {
		if(email == null || text == null) {
			return ServiceTools.serviceRefused("wrong argument", -1);
		}
		if(!ConnectionTools.isConnected(email)) {
			return ServiceTools.serviceRefused("user not connected", 4);
		}
		if(CommentTools.addComment(Integer.parseInt(idMessage), Integer.parseInt(idComment), email, text)) {
			return ServiceTools.serviceAccepted(email, "comment posted");
		}else {
			return ServiceTools.serviceRefused(email + " add comment failed", 8);
		}
	}

}
