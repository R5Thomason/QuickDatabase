package quick.controller;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import quick.model.GraveMarker;
import quick.model.Person;
import quick.view.DatabaseFrame;

public class AppController
{
    
    private DatabaseController myDataController;
    private DatabaseFrame myAppFrame;
    private ArrayList<GraveMarker> graveMarkerList;
    private ArrayList<Person> graveyardPersons;
    
    public AppController()
    {
	myDataController = new DatabaseController(this);
	
	graveyardPersons = new ArrayList<Person>();
	graveMarkerList = new ArrayList<GraveMarker>();
    }
    
    public DatabaseFrame getMyAppFrame()
    {
        return myAppFrame;
    }

    public ArrayList<GraveMarker> getGraveMarkerList()
    {
        return graveMarkerList;
    }

    public ArrayList<Person> getGraveyardPersons()
    {
        return graveyardPersons;
    }
    
    public DatabaseController getMyDataController()
    {
	return myDataController;
    }
    
    public void start()
    {
	myAppFrame = new DatabaseFrame(this);
    }
    
    public void addDeadPerson(Person currentDeadPerson)
    {
	graveyardPersons.add(currentDeadPerson);
    }

    
    /**
     * Createds a TableModel with the information from the graveyard database and the people table.
     * Designed for use with a JTable in the DataPanel
     * @return A TableModel composed of the header and data from the people table.
     */
    public TableModel createTableModel()
    {
	Vector<String> headers = myDataController.getTableHeaders("graveyard", "people");
	Vector<Person> base = myDataController.selectDataFromTable("people");
	Vector<Vector<String>> tableBase = parsePersonInformation(base);
	TableModel sampleModel = new DefaultTableModel(tableBase, headers);
	
	return sampleModel;
    }
    
    private Vector<Vector <String>> parsePersonInformation(Vector<Person> peopleData)
    {
	Vector<Vector<String>> parsedData = new Vector<Vector<String>>();
	int currentRowCount = 1;
	for(Person current: peopleData)
	{
	    Vector<String> currentRow = new Vector<String>();
	    
	    currentRow.add(Integer.toString(currentRowCount));
	    currentRow.add(current.getName());
	    currentRow.add(current.getBirthDate());
	    currentRow.add(current.getDeathDate());
	    currentRow.add(Boolean.toString(current.isMarried()));
	    currentRow.add(Boolean.toString(current.isHasChildren()));
	    currentRow.add(Integer.toString(current.getAge()));
	    
	    parsedData.add(currentRow);
	    currentRowCount++;
	}
	
	return parsedData;
    }
}
