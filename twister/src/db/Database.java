package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Database {
	private DataSource dataSource;
	private static Database database = null;

	
	public Database(String jndiname) throws SQLException {
		try{
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
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
	
	public static Connection getMySQLConnection() {
		/*
		if (DBStatic.mysql_pooling == false){
			Class.forName("com.mysql.jdbc.Driver");
			return(DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" + 
					DBStatic.mysql_db, DBStatic.mysql_username, DBStatic.mysql_password));
		}
		else{
			if (database == null){
				database = new Database("jdbc/db");
			}
			return (database.getConnection());
		}*/
		Connection conn = null;
		try {
			Class.forName(DBStatic.JDBC_DRIVER);
			conn = DriverManager.getConnection(DBStatic.DB_URL2, DBStatic.USER, DBStatic.PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}