package tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import db.DBStatic;
import db.Database;

public class MessageTools {
	
	public static boolean postMessage(int id, String email, String content) {
		MongoCollection<Document> col = Database.getMongoCollection(DBStatic.MONGO_COL_MESSAGE);
		Document query = new Document();
		query.append("id_message", id);
		query.append("email", email);
		query.append("content", content);
		query.append("date", new Date());
		List<Document> comments = new ArrayList<Document>();
		query.append("comments", comments);
		col.insertOne(query);
		return !query.isEmpty();
	}
	
	public static List<String> getFollowedMessages(String email) throws ClassNotFoundException, JSONException, SQLException{
		JSONArray follows = (JSONArray) FollowTools.getAllFollowed(email).get("friends");
		List<String> res = new ArrayList<String>();
		for(int i=0; i<follows.length(); i++) {
			res.add(follows.getJSONObject(i).getString("email"));
		}
		return res;
	}
	
	public static JSONObject getMyMessages(String email) throws JSONException {
		JSONObject res = new JSONObject();
		JSONArray arr = new JSONArray();
		MongoCollection<Document> col = Database.getMongoCollection(DBStatic.MONGO_COL_MESSAGE);
		Document query = new Document();
		query.append("email", email);
		MongoCursor<Document> cursor = col.find(query).iterator();
		while(cursor.hasNext()) {
			Document msg = cursor.next();
			arr.put(msg.getString("content"));
		}
		res.put("email", email);
		res.put("nbMessages", arr.length());
		res.put("messages", arr);
		return res;
	}
	
	public static JSONObject getAllMessages(String email) throws JSONException, ClassNotFoundException, SQLException {
		List<String> emails = getFollowedMessages(email);
		emails.add(email);
		JSONObject res = new JSONObject();
		JSONArray arr = new JSONArray();
		String my_nb_msgs = "";
		for(String mail : emails) {
			JSONObject msg = new JSONObject();
			JSONObject infos = getMyMessages(mail);
			msg.put("f_email", mail);
			msg.put("f_username", ConnectionTools.getUsernameFromEmail(mail));
			msg.put("f_content", infos.get("messages"));
			msg.put("f_nbMessages", infos.getString("nbMessages"));
			arr.put(msg);
			if(mail == email) {
				my_nb_msgs = infos.getString("nbMessages");
			}
		}
		res.put("nbMessages", my_nb_msgs);
		res.put("messages", arr);
		res.put("email", email);
		return res;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, JSONException, SQLException {
		System.out.println(getAllMessages("fangzhou.ye@yahoo.com"));
	}
	
}
