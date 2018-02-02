// Action handler for root menu: application exit.
// This action listener (handler) connected to root menu item "Exit" 
// in the RootFrame constructor.

package charts.view.rootmenu;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class HandlerExit extends AbstractAction 
{
@Override public void actionPerformed(ActionEvent e) 
    {
    System.exit(0); 
    }  
}
