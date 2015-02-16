package week6;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrTokenizer;

public class InsertIntoDB {
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException
	  {
	    Connection connection = null;
	    try
	    {
	      connection = createConnection();
	      createTables(connection);
	    }
	    catch(SQLException e)
	    {
	      System.err.println(e.getMessage());
	    }
	    try
	    {
	    	PreparedStatement preparedStatement = null;
	    	Statement stmt = null;
	    	insertFileNameIntoDocumentTable(args, connection);
	    }
	    catch(SQLException e)
	    {
	    	System.err.println(e.getMessage());
	    }
	    
	    List<String> wordList = getTokens();
		removeStopWordsFromTokens(wordList);
		insertTokensIntoWordsTable(args, connection, wordList);
	  }

	private static void insertTokensIntoWordsTable(String[] args,
			Connection connection, List<String> wordList) throws SQLException {
		PreparedStatement prep = connection.prepareStatement("SELECT id from documents WHERE name=?");
		prep.setString(1,args[0]);
		prep.setMaxRows(1); 
		ResultSet rs = prep.executeQuery();
		int docId = rs.getInt("id");
		prep =  connection.prepareStatement("SELECT MAX(id) FROM words");
		prep.setMaxRows(1); 
		rs = prep.executeQuery();
		int id = rs.getRow();
		for(String w : wordList)
		{
			prep = connection.prepareStatement("INSERT INTO words VALUES (?, ?, ?)");
			prep.setInt(1,id);
			prep.setInt(2,docId);
			prep.setString(3,w);
			id+=1;
			prep.execute();
		}
		connection.commit();
		connection.close();
	}

	private static void insertFileNameIntoDocumentTable(String[] args,
			Connection connection) throws SQLException {
		Statement stmt;
		stmt = connection.createStatement();
		PreparedStatement prep = connection.prepareStatement("INSERT INTO documents (name) VALUES (?)");
		prep.setString(1, args[0]);
		prep.execute();
		connection.commit();
	}

	private static void removeStopWordsFromTokens(List<String> wordList)
			throws IOException {
		wordList.removeAll(Arrays.asList(new StrTokenizer(FileUtils.readFileToString(new File("stop_words.txt")).replaceAll("[^a-zA-Z]+"," ")).getTokenArray()));
	}

	private static List<String> getTokens() throws IOException {
		List<String> wordList = new ArrayList<String>(Arrays.asList(new StrTokenizer(FileUtils.readFileToString(new File("pride-and-prejudice.txt")).toLowerCase().replaceAll("[^a-zA-Z]+"," ")).getTokenArray()));
		return wordList;
	}

	private static void createTables(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
	      statement.executeUpdate("drop table if exists documents");
	      statement.executeUpdate("CREATE TABLE documents (id INTEGER PRIMARY KEY AUTOINCREMENT, name)");
	      statement.executeUpdate("CREATE TABLE words (id, doc_id, value)");
	}

	private static Connection createConnection() throws ClassNotFoundException,
			SQLException {
		Connection connection;
		Class.forName("org.sqlite.JDBC");
	      connection = DriverManager.getConnection("jdbc:sqlite:tf.db");
	      connection.setAutoCommit(false);
		return connection;
	}
}
