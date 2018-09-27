package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class MainClass {

	static final String driver   = "oracle.jdbc.driver.OracleDriver";
	static final String url      = "jdbc:oracle:thin:@host:1521:swl";
	static final String user     = "XXXX";
	static final String password = "XXXX";

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Connection connection = null;

		try {
			Class.forName(driver);

			//connection = getConnectType1();
			//connection = getConnectType2();
			connection = getConnectType3();

			//SQLにセミコロンを付けるとエラーになるので注意
			Statement statement = connection.createStatement();
			ResultSet rsS       = statement.executeQuery("SELECT * FROM EMPLOYEE");

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//connection by Connection
	public static Connection getConnectType1() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	//connection by datasource
	public static Connection getConnectType2() throws SQLException {
		OracleDataSource oda = new OracleDataSource();
		oda.setURL(url);
		oda.setUser(user);
		oda.setPassword(password);
		return oda.getConnection();
	}

	//connection by commons-dbcp
	public static Connection getConnectType3() throws Exception {
		Properties properties = new Properties();
		properties.setProperty("driverClassName", "oracle.jdbc.driver.OracleDriver");
		properties.setProperty("url",             "jdbc:oracle:thin:@host:1521:swl");
		properties.setProperty("username",        "XXXX");
		properties.setProperty("password",        "XXXX");
		properties.setProperty("initialSize",     "5");
		properties.setProperty("maxActive",       "10");
		properties.setProperty("maxIdle",         "15");
		properties.setProperty("maxWait",         "5000");
		properties.setProperty("validationQuery", "select count(*) from dual");

		DataSource bds = BasicDataSourceFactory.createDataSource(properties);
		return bds.getConnection();
	}
}
