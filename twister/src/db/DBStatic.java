package db;

public interface DBStatic {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	// port 8889 used on Mac OS
	public static final String DB_URL1 = "jdbc:mysql://localhost:8889/Twister_YE_ZHANG";
	// port 3306 used in PPTI
	public static final String DB_URL2 = "jdbc:mysql://localhost:3306/Twister_YE_ZHANG";

	public static final String USER = "root";
	
	public static final String PASS = "root";
	
	// public static final boolean mysql_pooling = false;
}
