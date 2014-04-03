package quick.controller;

import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;

import quick.model.Person;

public class DatabaseController
{
    /**
     *  This is the String that pulls up the location of the database.
     */
    private String connectionString;
    
    /**
     * This is the connection.
     */
    private Connection databaseConnection;
    
    /**
     * This is the controller that we connect to that starts the app
     */
    private AppController baseController;
    
    /**
     * This is the constructor for the DatabaseController object.
     */
    public DatabaseController(AppController baseController)
    {
	connectionString = "jdbc:mysql://localhost/pmdatabase?user=root";
	this.baseController = baseController;
	
	checkDriver();
	setupConnection();
    }
    
    public void setConnectionString(String connectionString)
    {
	this.connectionString = connectionString;
    }
    
    public String getConnectionString()
    {
	return connectionString;
    }
    
    /*
     * Checks that the driver for the MySQL is loaded properly. If not it stops working.
     */
    private void checkDriver()
    {
	try
	{
	    Class.forName("com.mysql.jdbc.Driver");
	}
	catch (Exception currentException)
	{
	    System.err.println("Unable to load the driver");
	    System.err.println("Check that the ConnectorJ .jar file is loaded as an external JAR in the build path");
	    System.err.println("The original .jar should be in the ~/MySQL/ folder");
	    System.err.println(1);
	}
    }
    
    /**
     * Builds a Java connectionString for a MySQL database on a private server of Cody's
     * @param serverPath This is the path to the server we want to go to
     * @param database This is the database to be connected to 
     * @param userName This is specific place we want to connect to
     * @param password This is the extra data that is needed for the database
     */
    public void buildConnectionString(String serverPath, String database, String userName, String password)
    {
	connectionString = "jdbc:mysql://" + serverPath + "/" + database + "?user=" + userName + "&password=" + password;
    }
    
    /**
     * Sets up the connection with the database
     */
    public void setupConnection()
    {
	try
	{
	    databaseConnection = DriverManager.getConnection(connectionString);
	}
	catch (SQLException currentException)
	{
	    displaySQLErrors(currentException);
	}
    }
    
    /**
     * Resets the connection with the database in order to reconnect
     */
    private void resetDatabaseConnection()
    {
	closeConnection();
	setupConnection();
    }
    
    /**
     * Closes the connection with the database in order to reset the connection
     */
    private void closeConnection()
    {
	try
	{
	    databaseConnection.close();
	}
	catch(SQLException currentException)
	{
	    displaySQLErrors(currentException);
	}
    }
    
    public void createDatabase()
    {
	resetDatabaseConnection();
	try
	{
	    Statement createDatabaseStatement = databaseConnection.createStatement();
	    
	    int result = createDatabaseStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS `graveyard`");
	}
	catch (SQLException currentException)
	{
	    displaySQLErrors(currentException);
	}
    }
    
    /**
     * Builds a database at the site that the connectionString leads it to
     */
    public void createDatabase(String databaseName)
    {
	try
	{
	    Statement createDatabaseStatement = databaseConnection.createStatement();
	    
	    int result = createDatabaseStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS `"   + databaseName + "`");
	}
	catch (SQLException currentException)
	{
	    displaySQLErrors(currentException);
	}
    }
    
    /**
     * Deletes the database that this project deals with
     */
    public void deleteDatabase()
    {
	try
	{
	    Statement deleteDatabaseStatement = databaseConnection.createStatement();
	    
	    int result = deleteDatabaseStatement.executeUpdate("DROP DATABASE graveyardpm");
	}
	catch (SQLException currentException)
	{
	    displaySQLErrors(currentException);
	}
    }
    
    /**
     * Creates a table with a name inside a certain database 
     * @param database This is the database the table will go to 
     * @param tableName This is the name of the table
     */
    public void createTable(String database, String tableName)
    {
	try
	{
	    Statement createDatabaseTableStatement = databaseConnection.createStatement();
	    
	    String createTable = "CREATE TABLE `" + database + "`.`" + tableName + "`" +
		    		"(" + 
		    		"`grave_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," + 
		    		"`grave_name` VARCHAR( 20 ) NOT NULL" + 
		    		")" + 
		    		"ENGINE = INNODB;";
	    
	    int result = createDatabaseTableStatement.executeUpdate(createTable);
	}
	catch (SQLException currentException)
	{
	    displaySQLErrors(currentException);
	}
    }
    
    /**
     * Creates a people table on the supplied database using Person data members. Will not override an existing table.
     * Calls the displaySQLErrors method if there are SQL problems.
     * @param database The supplied database.
     */
    public void createPeopleTable(String database)
    {
	try
	{
	    Statement createPersonTableStatement = databaseConnection.createStatement();
	    String createPersonTable = "CREATE TABLE IF NOT EXISTS `" + database + "`.`people`" +
		    			"(" + 
		    				"`person_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
		    				"`person_name` VARCHAR (50)," +
		    				"`death_date` VARCHAR (30)," +
		    				"`birth_data` VARCHAR (30)," +
		    				"`is_married` TINYINT (1)," +
		    				"`has_children` TINYINT (1)," +
		    				"`age` INT" +
		    			")" +
		    			"ENGINE = INNODB;";
	    
	    int result = createPersonTableStatement.executeUpdate(createPersonTable);
	}
	catch (SQLException currentSQLError)
	{
	    displaySQLErrors(currentSQLError);
	}
    }
    
    /**
     * This puts a person into the table that was created, parsing fields as needed. Converts the boolean values to 1/0 for SQL support.
     *  If there are errors it will call the diplaySQLErrors method.
     * @param currentPerson This is the person that is currently being added
     */
    public void insertPersonIntoTable(Person currentPerson)
    {
	try
	{
	    
	    Statement insertPersonStatement = databaseConnection.createStatement();
	    
	    int databaseIsMarried, databaseHasChildren;
	    
	    if(currentPerson.isHasChildren())
	    {
		databaseHasChildren = 1;
	    }
	    else
	    {
		databaseHasChildren = 0;
	    }
	    
	    if(currentPerson.isMarried())
	    {
		databaseIsMarried = 1;
	    }
	    else
	    {
		databaseIsMarried = 0;
	    }
	    
	    String insertPersonString = "`INSERT INTO `graveyardpm`.`people` " + 
		    			"(`person_name`, `death_date`, `birth_date`, `is_married`, `has_children`, `age`) " + 
		    			"VALUES " + 
		    			"('"+ currentPerson.getName() +"' , '"+ currentPerson.getDeathDate() +"' , '"+ currentPerson.getBirthDate() +
		    			"' , '"+ databaseIsMarried +"' , '"+ databaseHasChildren +"' , '"+ currentPerson.getAge() +
		    			"'" + ");";
	    
	    int result = insertPersonStatement.executeUpdate(insertPersonString);
	    
	    insertPersonStatement.close();
	    
	    JOptionPane.showMessageDialog(baseController.getMyAppFrame(),  "Successfully inserted " + result + " rows into the table.");
	}
	catch(SQLException currentSQLError)
	{
	    displaySQLErrors(currentSQLError);
	}
    }
    
    /**
     * This displays any errors that occur in the project using a JOptionPane
     * @param current This is the current exception being displayed
     */
    public void displaySQLErrors(SQLException current)
    {
	JOptionPane.showMessageDialog(baseController.getMyAppFrame(), "SQL Message is: " + current.getMessage());
	JOptionPane.showMessageDialog(baseController.getMyAppFrame(), "SQL State is: " + current.getSQLState());
	JOptionPane.showMessageDialog(baseController.getMyAppFrame(), "Java error code is: " + current.getErrorCode());
    }
    
    /**
     * Method to change a person's name to something different
     * @param oldName This is the original name of the person
     * @param newName This is the new name to be changed to
     */
    public void updatePersonName(String oldName, String newName)
    
    {
	try
	{
	    Statement updateStatement = databaseConnection.createStatement();
	    
	    String updateString = "UPDATE `graveyardpm`.`people`" +
		    		"SET `person_name` = '" + newName + "'" + 
		    		"WHERE `person_name` = '" + oldName + "'";
	    
	    int result = updateStatement.executeUpdate(updateString);
	    
	    JOptionPane.showMessageDialog(baseController.getMyAppFrame(), "Sucessfully updated " + result + " row(s)");
	    
	    updateStatement.close();
	}
	catch(SQLException currentSQLError)
	{
	    displaySQLErrors(currentSQLError);
	}
    }
    
    /**
     * Connects to Cody's private server
     */
    public void connectToExternalServer()
    {
	buildConnectionString("10.228.6.204", "graveyard", "ctec", "student");
	setupConnection();
	createDatabase("ryan");
    }
    
    /**
     * Creates a Vector<String> from the header information stored in the ResultSetMetaData. Used to create the TableModel information for a JTable.
     * @param database The database that is being queried.
     * @param table The table inside the database
     * @return The Vector<String> that is being put into the JTable.
     */
    public Vector<String> getTableHeaders(String database, String table)
    {
	Vector<String> tableHeaders = new Vector<String>();
	String query = "Select * from `" + database + "`.`" + table + "`;";
	try
	{
	    Statement headerStatement = databaseConnection.createStatement();
	    ResultSet results = headerStatement.executeQuery(query);
	    ResultSetMetaData resultsData = results.getMetaData();
	    
	    for(int column = 1; column<= resultsData.getColumnCount(); column++)
	    {
		tableHeaders.add(resultsData.getColumnName(column));
	    }
	    
	    results.close();
	    headerStatement.close();
	}
	catch(SQLException currentSQLError)
	{
	    displaySQLErrors(currentSQLError);
	}
	
	return tableHeaders;
    }
    
    /**
     * Creates a Vector<Person> from the SELECT statement built in the method on the supplied tableName.
     * Places the contents of the ResultSet in to a Person and then inserts said person into the Vector<Person>.
     * @param tableName The specified table from the database. In this version it should be 'people'.
     * @return The list of Person objects from the people table.
     */
    public Vector<Person> selectDataFromTable(String tableName)
    {
	Vector<Person> personVector = new Vector<Person>();
	ResultSet seeDeadPeopleResults;
	String selectQuery = "SELECT person_age, " +
			"person_name, " +
			"person_has_children, " +
			"person_is_married, " +
			"person_birth_date, " +
			"person_death_date FROM " + 
			tableName + ";";
	
	try
	{
	    PreparedStatement selectStatement = databaseConnection.prepareStatement(selectQuery);
	    seeDeadPeopleResults = selectStatement.executeQuery();
	    
	    while(seeDeadPeopleResults.next())
	    {
		Person tempPerson = new Person();
		
		int tempAge = seeDeadPeopleResults.getInt(1);
		String tempName = seeDeadPeopleResults.getString(2);
		boolean tempkids = seeDeadPeopleResults.getBoolean(3);
		boolean tempMarried = seeDeadPeopleResults.getBoolean(4);
		String tempBirth = seeDeadPeopleResults.getString(5);
		String tempDeath = seeDeadPeopleResults.getString(6);
		
		tempPerson.setAge(tempAge);
		tempPerson.setBirthDate(tempBirth);
		tempPerson.setDeathDate(tempDeath);
		tempPerson.setHasChildren(tempkids);
		tempPerson.setMarried(tempMarried);
		tempPerson.setName(tempName);
		
		personVector.add(tempPerson);
	    }
	    
	    seeDeadPeopleResults.close();
	    selectStatement.close();
	}
	catch(SQLException currentSQLError)
	{
	    displaySQLErrors(currentSQLError);
	}
	
	return personVector;
    }
}
