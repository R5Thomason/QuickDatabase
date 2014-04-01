package quick.view;

import javax.swing.JFrame;

import quick.controller.AppController;

public class DatabaseFrame extends JFrame
{
    
    private AppController baseController;
    
    private DatabasePanel myDatabasePanel;
    
    public DatabaseFrame(AppController baseController)
    {
	this.baseController = baseController;
	myDatabasePanel = new DatabasePanel(baseController);
	
	setupFrame();
    }
    
    public void setupFrame()
    {
	this.setContentPane(myDatabasePanel);
	this.setSize(650,400);
	this.setVisible(true);
    }

}
