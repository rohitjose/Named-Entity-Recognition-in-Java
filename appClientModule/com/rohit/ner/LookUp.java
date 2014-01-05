/**
 * Lookup is a class that enables a lookup from the database for the strings passed
 * from a calling function. The implemented version utilizes a sqlite 3 database
 * and connects to the ner database included in the project folder.
 */
package com.rohit.ner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author Rohit
 * 
 */
public class LookUp {
	static Connection connection = null;// varible for the connection
	static Statement statement = null;// variable for the db statement

	public static HashMap<String, String> getLookup(String word) {
		HashMap<String, String> lookupWords = new HashMap<String, String>();

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:ner");//driver registration
			connection.setAutoCommit(false);//sets autocommit as false

			statement = connection.createStatement();//creates the statement
			String query = "select * from dictionary where name like '%" + word
					+ "%'";//builds the query
			ResultSet resultSet = statement.executeQuery(query);//retrieves the result
			//loop to build the lookup table from the phrases passed
			while (resultSet.next()) {
				String name = resultSet.getString("name");//gets the name from the query result
				String entity = resultSet.getString("entity");//gets the entity relation from the query result
				if (lookupWords.containsKey(name)) {
					lookupWords.put(name, entity + "," + lookupWords.get(name));//assigns the values to the lookup table
					//this is for a name that has multiple entity relations
				} else {
					lookupWords.put(name, entity);//assigns the values to the lookup table
				}

			}
			resultSet.close();//closes the result set
			statement.close();//closes the statement
			connection.close();//closes the connection
		} catch (Exception e) {
			//catch block for any exceptions
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return lookupWords;//returns the identified lookup dictionary to the calling function
	}

}
