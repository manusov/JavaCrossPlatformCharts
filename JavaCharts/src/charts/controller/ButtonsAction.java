/*

Action listener (events handler) class for control buttons press.
This listener connected to buttons by builder in the KeyboardFabric class.
When button action performed:
1) detect event,
2) execute operation, associated with this button,
3) refresh Display and Status components, 
4) set buttons enable/disable by result of operation.

*/

package charts.controller;

import charts.model.FunctionCore;
import charts.view.drawdisplay.DisplayPanel;
import charts.view.drawstatus.StatusPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ButtonsAction implements ActionListener
{
private final DescriptButton[] descript;  // buttons descriptors array
private final FunctionCore     function;  // communications with data model
private final DisplayPanel[]   displays;  // display for update information
private final StatusPanel[]    statuses;  // status panel for update inf.
private final JButton[]        buttons;   // buttons for set enabled/disabled
    
public ButtonsAction ( DescriptButton[] db , FunctionCore fc ,
                       DisplayPanel[] dp , StatusPanel[] sp ,
                       JButton[] bt )
    {
    descript = db;
    function = fc;
    displays = dp;
    statuses = sp;
    buttons  = bt;
    }
    
@Override public void actionPerformed(ActionEvent event)
    {
    // command = button ID as string
    String command = event.getActionCommand();
    // selector = button ID as integer number, converted from string
    int selector;
    if ( ( command != null ) && ( command.length() >= 1 ) )
        {
        try {
            selector = Integer.decode(command);
            }
        catch (Exception e)  // handling error when convert string to number
            {
            selector = -1;  // force wrong button ID if error
            }
        if ( (selector >= 0) & selector < descript.length ) 
            {
            // execute operation, associated with this button press
            descript[selector].buttonAction(function);
            for(int i=0; i<displays.length; i++)
                {  // update function graph at central display panel
                displays[i].updateDisplay(function, i);
                displays[i].repaint();
                }
            for(int i=0; i<statuses.length; i++)
                {  // update statistics at down status panel
                statuses[i].updateStatus(function, i);
                statuses[i].repaint();
                }
            // set buttons enabled or disabled by this action result
            buttons[4].setEnabled( function.isYincable() );
            buttons[5].setEnabled( function.isYdecable() );
            buttons[6].setEnabled( function.isXdecable() );
            buttons[7].setEnabled( function.isXincable() );
            buttons[8].setEnabled( function.isTabIncable() );
            buttons[9].setEnabled( function.isTabDecable() );
            }
        }
    }

}
