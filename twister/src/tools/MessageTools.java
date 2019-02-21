package tools;

import java.util.Date;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import db.DBStatic;
import db.Database;

public class MessageTools {
	
	public static boolean addMessage(int id, String from, String to, String text) {
		MongoCollection<Document> col = Database.getMongoCollection(DBStatic.MONGO_COL_MESSAGE);
		Document query = new Document();
		query.append("id_message", id);
		query.append("from_id_user", Integer.parseInt(from));
		query.append("to_id_user", Integer.parseInt(to));
		query.append("text", text);
		query.append("date", new Date());
		col.insertOne(query);
		return !query.isEmpty();
	}
	
}
