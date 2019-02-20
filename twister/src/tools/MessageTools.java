package tools;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MessageTools {

	public static void main(String[] args) {
		MongoClient mongo = MongoClients.create();
		MongoDatabase mDB= mongo.getDatabase("twister_mongo");
	}
	
}
