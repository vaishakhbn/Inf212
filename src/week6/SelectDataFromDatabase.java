package week6;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class SelectDataFromDatabase {
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		ResultSet rs;
		Statement statement;
		Connection connection = createConnection();
	    displayTableContents(connection);
	}

	private static void displayTableContents(Connection connection)
			throws SQLException {
		ResultSet rs;
		Statement statement;
		statement = connection.createStatement();
	    PreparedStatement preparedStatement = null;
	    statement = connection.createStatement();
	    preparedStatement = connection.prepareStatement("SELECT value, COUNT(*) as C FROM words GROUP BY value ORDER BY C DESC LIMIT 25");
	    preparedStatement.setMaxRows(25);
		rs = preparedStatement.executeQuery();
		while ( rs.next() ) 
		{
			System.out.println(rs.getString(1)+" - "+rs.getString(2));
		}
	}

	private static Connection createConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;
		connection = DriverManager.getConnection("jdbc:sqlite:tf.db");
	    connection.setAutoCommit(false);
		return connection;
	}

}
