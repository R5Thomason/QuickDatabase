package quick.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton createDatabaseButton;
    private JButton createPeopleTableButton;
    private JButton insertPersonIntoTableButton;
    private JButton updatePersonButton;
    private JTextArea testArea;
    private JScrollPane scroller;
    
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
	
	baseLayout = new SpringLayout();
	setupPanel();
	setupLayout();
	setupListeners();
    }
    
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
    }
    
    private void setupLayout()
    {
	baseLayout.putConstraint(SpringLayout.NORTH, ageField, 18, SpringLayout.SOUTH, deathDateField);
	baseLayout.putConstraint(SpringLayout.WEST, ageField, 0, SpringLayout.WEST, createDatabaseButton);
	baseLayout.putConstraint(SpringLayout.WEST, deathDateField, 0, SpringLayout.WEST, createDatabaseButton);
	baseLayout.putConstraint(SpringLayout.NORTH, createDatabaseButton, 28, SpringLayout.NORTH, this);
	baseLayout.putConstraint(SpringLayout.WEST, insertPersonIntoTableButton, 88, SpringLayout.WEST, this);
	baseLayout.putConstraint(SpringLayout.SOUTH, insertPersonIntoTableButton, -31, SpringLayout.SOUTH, this);
	baseLayout.putConstraint(SpringLayout.EAST, createDatabaseButton, -303, SpringLayout.EAST, this);
	baseLayout.putConstraint(SpringLayout.NORTH, createPeopleTableButton, 0, SpringLayout.NORTH, createDatabaseButton);
	baseLayout.putConstraint(SpringLayout.WEST, createPeopleTableButton, 51, SpringLayout.EAST, createDatabaseButton);
	baseLayout.putConstraint(SpringLayout.NORTH, deathDateField, 138, SpringLayout.NORTH, this);
	baseLayout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.WEST, createDatabaseButton);
	baseLayout.putConstraint(SpringLayout.SOUTH, nameField, -25, SpringLayout.NORTH, deathDateField);
	baseLayout.putConstraint(SpringLayout.NORTH, updatePersonButton, -1, SpringLayout.NORTH, ageField);
	baseLayout.putConstraint(SpringLayout.EAST, updatePersonButton, -81, SpringLayout.EAST, this);
    }
    
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
//		baseController.getMyDataController().connectToExternalServer();
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
	    }
	});
	
	updatePersonButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent click)
	    {
		baseController.getMyDataController().updatePersonName(nameField.getText(), deathDateField.getText());
	    }
	});
    }
    
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
