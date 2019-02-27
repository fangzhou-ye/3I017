package tools;

import java.util.Date;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

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
		col.insertOne(query);
		return !query.isEmpty();
	}
	
}
