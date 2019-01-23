/*

Action listener (event handler) for text fields.
This listener connected to text fields by builder in the
KeyboardFabric class.
When text field action performed:
1) detect event,
2) execute operation, associated with this text field,
3) refresh Display and Status components, 

*/

package charts.controller;

import charts.model.FunctionCore;
import charts.view.drawdisplay.DisplayPanel;
import charts.view.drawstatus.StatusPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldsAction implements ActionListener 
{
private final DescriptField[] descript;  // text fields descriptors array
private final FunctionCore    function;  // communications with data model
private final DisplayPanel[]  displays;  // display for update information
private final StatusPanel[]   statuses;  // status panel for update information
    
public FieldsAction ( DescriptField[] df , FunctionCore fc ,
                      DisplayPanel[] dp  , StatusPanel[] sp )
    {
    descript = df;
    function = fc;
    displays = dp;
    statuses = sp;
    }

@Override public void actionPerformed(ActionEvent event)
    {
    // command = button ID as string
    String command = event.getActionCommand();
    // selector = button ID as integer number, converted from string
    int selector;
    if ( ( command != null ) && ( command.length() >= 2 ) )
        {
        try {
            selector = Integer.decode(command);
            }
        catch (Exception e)   // handling error when convert string to number
            {
            selector = -1;    // force wrong field ID if error
            }
        if ( (selector >= 0) & selector < descript.length ) 
            {
            // execute operation, associated with this text field input
            descript[selector].fieldAction(function);
            for(int i=0; i<displays.length; i++)
                {  // update function graph at central display panel
                displays[i].updateDisplay(function, i);
                // REQUIRED // displays[i].repaint();
                }
            for(int i=0; i<statuses.length; i++)
                {  // update statistics at down status panel
                statuses[i].updateStatus(function, i);
                // REQUIRED // statuses[i].repaint();
                }
            }
        }
    }

}
