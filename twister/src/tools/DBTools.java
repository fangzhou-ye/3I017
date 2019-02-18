package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTools {

	public static String insertConnection(String login, boolean bool) {
		return "connected";
	}
	
	public static void closeAll(ResultSet rs, Statement stm, Connection conn) throws SQLException {
		if(rs != null) {
			rs.close();
		}
		if(stm != null) {
			stm.close();
		}
		if(conn != null) {
			conn.close();
		}
	}
	
}
