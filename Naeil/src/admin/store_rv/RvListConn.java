package admin.store_rv;

import java.sql.Connection;
import java.sql.DriverManager;

public class RvListConn {

	private Connection con;

	public Connection getConnection() {
		return con;
	}

	public RvListConn() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

	}

}
