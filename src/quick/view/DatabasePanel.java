package quick.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import quick.controller.AppController;
import quick.model.Person;

public class DatabasePanel extends JPanel
{
    private AppController baseController;
    private SpringLayout baseLayout;
    private JTextField ageField;
    private JTextField nameField;
    private JTextField deathDateField;
    private JTextField birthDateField;
    private JButton createDatabaseButton;
    private JButton createPeopleTableButton;
    private JButton insertPersonIntoTableButton;
    private JButton updatePersonButton;
    private JButton selectButton;
    private JButton connectButton;
    private JTextArea resultsArea;
    private JScrollPane resultsPane;
    
    /**
     * This is the constructor for the DatabasePanel.
     * @param baseController This is the DatabaseController that is handling the heavy lifting.
     */
    public DatabasePanel(AppController baseController)
    {
	this.baseController = baseController;
	createDatabaseButton = new JButton("Make the database");
	createPeopleTableButton = new JButton("Click to create table people");
	insertPersonIntoTableButton = new JButton("Insert the supplied person into the database");
	updatePersonButton = new JButton("update a person");
	ageField = new JTextField("age", 5);
	nameField = new JTextField("person name", 20);
	deathDateField = new JTextField("death date", 15);
	resultsArea = new JTextArea(7, 35);
	resultsPane = new JScrollPane(resultsArea);
	selectButton  = new JButton("Select Data");
	connectButton = new JButton("Connect to an external Database");
	birthDateField = new JTextField("birth date", 15);
	
	baseLayout = new SpringLayout();
	setupPanel();
	setupLayout();
	setupListeners();
    }
    
    /**
     * This method sets up the panel.
     */
    private void setupPanel()
    {
	this.setLayout(baseLayout);
	this.add(createDatabaseButton);
	this.add(createPeopleTableButton);
	this.add(insertPersonIntoTableButton);
	this.add(ageField);
	this.add(nameField);
	this.add(deathDateField);
	this.add(updatePersonButton);
	this.add(resultsPane);
	this.add(selectButton);
	this.add(connectButton);
//	this.add(birthDateField);
	
	resultsArea.setWrapStyleWord(true);
	resultsArea.setLineWrap(true);
    }
    
    /**
     * This method clears all the fields so it doesn't get confusing.
     */
    private void clearFields()
    {
	deathDateField.setText("");
	ageField.setText("");
	nameField.setText("");
	resultsArea.setText("");
    }
    
    /**
     * This method reads a Vector<Person> and returns its values.
     * @param currentPeople This is the Vector<Person> that is being read.
     */
    private void readFromVector(Vector<Person> currentPeople)
    {
	clearFields();
	for(Person currentPerson: currentPeople)
	{
	    resultsArea.append(currentPerson.toString() + "\n");
	}
    }
    
    /**
     * This method sets up the positions of everything in the panel.
     */
    private void setupLayout()
    {

	baseLayout.putConstraint(SpringLayout.SOUTH, connectButton, -17, SpringLayout.NORTH, createPeopleTableButton);
	baseLayout.putConstraint(SpringLayout.EAST, connectButton, 0, SpringLayout.EAST, insertPersonIntoTableButton);
	baseLayout.putConstraint(SpringLayout.NORTH, selectButton, 27, SpringLayout.SOUTH, updatePersonButton);
	baseLayout.putConstraint(SpringLayout.SOUTH, createPeopleTableButton, -24, SpringLayout.NORTH, updatePersonButton);
	baseLayout.putConstraint(SpringLayout.EAST, createDatabaseButton, -68, SpringLayout.EAST, this);
	baseLayout.putConstraint(SpringLayout.NORTH, updatePersonButton, 2, SpringLayout.NORTH, resultsPane);
	baseLayout.putConstraint(SpringLayout.EAST, updatePersonButton, 0, SpringLayout.EAST, createDatabaseButton);
	baseLayout.putConstraint(SpringLayout.EAST, createPeopleTableButton, -41, SpringLayout.EAST, this);
	baseLayout.putConstraint(SpringLayout.NORTH, createDatabaseButton, 10, SpringLayout.NORTH, this);
	baseLayout.putConstraint(SpringLayout.WEST, selectButton, 68, SpringLayout.EAST, resultsPane);
	

	baseLayout.putConstraint(SpringLayout.NORTH, ageField, 20, SpringLayout.SOUTH, deathDateField);
	
		baseLayout.putConstraint(SpringLayout.NORTH, resultsPane, 10, SpringLayout.SOUTH, ageField);
		baseLayout.putConstraint(SpringLayout.WEST, resultsPane, 10, SpringLayout.WEST, this);
	baseLayout.putConstraint(SpringLayout.NORTH, deathDateField, 20, SpringLayout.SOUTH, nameField);
	baseLayout.putConstraint(SpringLayout.NORTH, insertPersonIntoTableButton, 6, SpringLayout.SOUTH, resultsPane);
	baseLayout.putConstraint(SpringLayout.EAST, insertPersonIntoTableButton, -34, SpringLayout.EAST, this);
	baseLayout.putConstraint(SpringLayout.NORTH, nameField, 29, SpringLayout.NORTH, this);
	baseLayout.putConstraint(SpringLayout.WEST, ageField, 0, SpringLayout.WEST, nameField);
	baseLayout.putConstraint(SpringLayout.WEST, deathDateField, 22, SpringLayout.WEST, this);
	baseLayout.putConstraint(SpringLayout.WEST, nameField, 22, SpringLayout.WEST, this);
    }
    
    /**
     * This methods sets up the buttons so that they behave properly.
     */
    private void setupListeners()
    {
	createDatabaseButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent click)
	    {
		
		baseController.getMyDataController().createDatabase();
	    }
	});
	
	createPeopleTableButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent click)
	    {
		baseController.getMyDataController().createPeopleTable("graveyardpm");
	    }
	});
	
	insertPersonIntoTableButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent click)
	    {
		Person currentPerson = new Person();
		currentPerson.setName(nameField.getText());
		currentPerson.setDeathDate(deathDateField.getText());
		if(checkParseInteger(ageField.getText()))
		{
		    currentPerson.setAge(Integer.parseInt(ageField.getText()));
		}
		
		baseController.getGraveyardPersons().add(currentPerson);
		baseController.getMyDataController().insertPersonIntoTable(currentPerson);
		clearFields();
		
	    }
	});
	
	selectButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent click)
	    {

		Vector<Person> temp = baseController.getMyDataController().selectDataFromTable("people");
		readFromVector(temp);
	    }
	});
	
	updatePersonButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent click)
	    {
		baseController.getMyDataController().updatePersonName(nameField.getText(), deathDateField.getText());
	    }
	});
	
	connectButton.addActionListener(new ActionListener()
	{
	   public void actionPerformed(ActionEvent click)
	   {

		baseController.getMyDataController().connectToExternalServer();
	   }
	});
    }
    
    /**
     * This creates a person from the values that it gives to you.
     * @return This is the person created.
     */
    private Person createPersonFromValues()
    {
	Person tempDeadPerson = null;
	int age = 0;
	
	if(checkParseInteger(ageField.getText()))
	{
	    age = Integer.parseInt(ageField.getText());
	}
	
	tempDeadPerson = new Person(nameField.getText(), deathDateField.getText());
	tempDeadPerson.setAge(age);
	
	return tempDeadPerson;
    }
    
    /**
     * This parses the age field to make sure that it is an integer.
     * @param current This is the current string.
     * @return This tells whether the string is an integer or not.
     */
    private boolean checkParseInteger(String current)
    {
	boolean isParseable = false;
	
	try
	{
	    Integer.parseInt(current);
	    isParseable = true;
	}
	catch(NumberFormatException currentError)
	{
	    JOptionPane.showMessageDialog(this, "Try typing in an integer for the age");
	}
	
	return isParseable;
    }
}
