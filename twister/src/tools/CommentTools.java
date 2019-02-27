package tools;

import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import db.DBStatic;
import db.Database;

public class CommentTools {

	public static boolean addComment(int idMessage, int idComment, String friend, String text) {
		MongoCollection<Document> col = Database.getMongoCollection(DBStatic.MONGO_COL_MESSAGE);
		Document message = new Document("id_message", 1);
		// create a new comment
		Document comment = new Document();
		comment.append("idComment", idComment);
		comment.append("by", friend);
		comment.append("text", text);
		comment.append("time", new Date());
		Document update = new Document("comments", comment);
		
		Document updateQuery = new Document("$push", update);
		col.updateMany(message, updateQuery);
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(addComment(1, 2, "user2@yahoo.com", "fuck"));
	}
	
}
