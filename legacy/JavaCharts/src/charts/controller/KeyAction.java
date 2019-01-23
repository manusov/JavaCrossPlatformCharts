/*

Action handler for map PC keyboard keys to viewer control keyboard buttons.
This functionality yet supported for buttons and combo boxes.

*/

package charts.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class KeyAction extends AbstractAction
{
@Override public void actionPerformed(ActionEvent event) 
    {
    Object b = event.getSource();
    // handling buttons events
    if ( b instanceof JButton )
        {
        ((JButton)(b)).doClick();
        }
    // handling combo events (note same id used)
    if ( b instanceof JComboBox )
        {
        ((JComboBox)(b)).showPopup();
        }
    
    // TODO: handling for text fields
    
    }    
}
