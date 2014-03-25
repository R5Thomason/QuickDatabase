package quick.controller;

import java.util.ArrayList;

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

}
