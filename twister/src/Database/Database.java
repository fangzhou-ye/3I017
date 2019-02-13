package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
// mysql -u root -p pwd root
public class Database implements DBStatic{
	
	private DataSource dataSource;
	public static Database database;
	
	public Database(String jndiname) throws SQLException {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
		} catch (NamingException e) {
			// Handle error that it’s not configured in JNDI.
			throw new SQLException(jndiname + " is missing in JNDI! : " + e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException {
		// ???
		database = new Database("jdbc/db");
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException {
		if(DBStatic.mysql_pooling == false) {
			return DriverManager.getConnection("jdbc:mysql//" + DBStatic.mysql_host + "/" + DBStatic.mysql_db, 
												DBStatic.mysql_username,
												DBStatic.mysql_password);
		}else {
			if(database == null) {
				database = new Database("jdbc/db");
			}
			return database.getConnection();
		}
	}
}
