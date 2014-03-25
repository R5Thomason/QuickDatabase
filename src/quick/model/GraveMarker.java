package quick.model;

import java.util.ArrayList;

public class GraveMarker
{
    private ArrayList<Person> gravePersonList;
    private boolean isReadable;
    //type of marker - String
    //picture -
    //location -
    //people<> - 
    //information -
    //isReadable -
    //constructor -
    //get/set
    
    public GraveMarker()
    {
	
    }
    
    public String toString()
    {
	String graveInfo = "";
	
	for(Person current : gravePersonList)
	{
	    graveInfo += current + " is buried here.\n";
	}
	
	if(isReadable)
	{
	    graveInfo += "This grave is readable";
	}
	else
	{
	    graveInfo += "This grave is NOT readable";
	}
	
	return graveInfo;
	
    }

}
