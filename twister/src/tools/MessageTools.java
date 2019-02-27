package tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

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
	
	public static List<Document> getAllMessage(String email) {
		List<Document> res = new ArrayList<Document>();
		MongoCollection<Document> col = Database.getMongoCollection(DBStatic.MONGO_COL_MESSAGE);
		Document query = new Document();
		query.append("email", email);
		MongoCursor<Document> cursor = col.find(query).iterator();
		while(cursor.hasNext()) {
			Document obj = cursor.next();
			res.add(obj);
		}
		return res;
	}
	
}
