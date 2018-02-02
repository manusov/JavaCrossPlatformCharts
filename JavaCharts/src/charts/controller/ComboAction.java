// Action listener (events handler) class for combo boxes click.
// This listener connected to combo boxes by builder 
// in the KeyboardFabric class.

package charts.controller;

import charts.model.FunctionCore;
import charts.view.drawdisplay.DisplayPanel;
import charts.view.drawstatus.StatusPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class ComboAction implements ActionListener 
{
private final DescriptCombo[] descript;  // combo boxes descriptors array
private final FunctionCore    function;  // communications with data model
private final DisplayPanel[]  displays;  // display for update information
private final StatusPanel[]   statuses;  // status panel for update information
private final JButton[]       buttons;   // buttons for set enabled/disabled
    
public ComboAction ( DescriptCombo[] dc , FunctionCore fc ,
                     DisplayPanel[] dp  , StatusPanel[] sp ,
                     JButton[] bt )
    {
    descript = dc;
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
    int selector, index = 0;
    if ( ( command != null ) && ( command.length() >= 1 ) )
        {
        try {
            selector = Integer.decode(command);
            }
        catch (Exception e)
            {
            selector = -1;    // force wrong combo ID if error
            }
        if ( (selector >= 0) & selector < descript.length ) 
            {
            Object o = event.getSource();
            if ( o instanceof JComboBox )
                {  // get selected item number
                index = ((JComboBox)o).getSelectedIndex();
                }
            // execute operation, associated with this combo box selection
            descript[selector].comboAction(function, index);
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
