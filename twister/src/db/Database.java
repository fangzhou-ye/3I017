package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Database {
	private DataSource dataSource;
	private static Database database = null;

	
	public Database(String jndiname) throws SQLException {
		try{
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/db");
		}catch(NamingException e){
			throw new SQLException(jndiname + " is missing in JDNI! : " + e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		
		if (DBStatic.mysql_pooling == true){
			Class.forName("com.mysql.jdbc.Driver");
			return(DriverManager.getConnection(DBStatic.DB_URL1, DBStatic.USER, DBStatic.PASS));
		}
		else{
			if (database == null){
				database = new Database("jdbc/db");
			}
			return database.getConnection();
		}
		
		/*
		Connection conn = null;
		try {
			Class.forName(DBStatic.JDBC_DRIVER);
			conn = DriverManager.getConnection(DBStatic.DB_URL1, DBStatic.USER, DBStatic.PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		*/
	}
	
	public static MongoCollection<Document> getMongoCollection(String collection) {
		MongoClient mongo = MongoClients.create();
		MongoDatabase db =  mongo.getDatabase(DBStatic.MONGO_DB);
		return db.getCollection(collection);
	}

}