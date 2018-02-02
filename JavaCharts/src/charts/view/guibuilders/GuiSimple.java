// GUI builder class.

package charts.view.guibuilders;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class GuiSimple 
{

// Method get preferred GUI frame size.
public Dimension getGuiSize() 
    {
    return new Dimension(780, 580); 
    }

// Method for build GUI in the frame, connect 3 panels.
//
// frame    = common GUI frame for application
// display  = array of display panels (central, with math. function graphs),
//            yet only one panel = display[0]
// status  = array of status panels (down, with math. function statistics),
//            yet only one panel = status[0]
// keyboard = array of control elements panels (right, with buttons and other
//            control elements), yet only one panel = keyboard[0]

public void setGui( JFrame frame, 
                    JPanel[] display, JPanel[] status, JPanel[] keyboard )
    {
    
/*
// add SpringLayout panels with BorderLayout frame has bad result
    Container container = frame.getContentPane();
    container.removeAll();
    container.setLayout(new BorderLayout());
    container.add( status[0]   , BorderLayout.SOUTH  );  // down status strings
    container.add( keyboard[0] , BorderLayout.EAST   );  // right buttons
    container.add( display[0]  , BorderLayout.CENTER );  // center display
    frame.setSize(getGuiSize());
*/
    
    Container c = frame.getContentPane();
    c.removeAll();
    SpringLayout s = new SpringLayout();
    c.setLayout(s);
    
    // down status panel with information strings
    
    s.putConstraint
        ( SpringLayout.SOUTH , status[0] ,  -2 , SpringLayout.SOUTH, c );
    s.putConstraint
        ( SpringLayout.NORTH , status[0] , -60 , SpringLayout.SOUTH, c );
    s.putConstraint
        ( SpringLayout.WEST  , status[0] ,  12 , SpringLayout.WEST,  c );
    s.putConstraint
        ( SpringLayout.EAST  , status[0] , -150 , SpringLayout.EAST,  c );
    c.add(status[0]);
    
    // right control panel with buttons
    
    s.putConstraint
        ( SpringLayout.NORTH , keyboard[0] ,   2 , SpringLayout.NORTH, c );
    s.putConstraint
        ( SpringLayout.SOUTH , keyboard[0] ,  -2 , SpringLayout.SOUTH, c );
    s.putConstraint
        ( SpringLayout.EAST  , keyboard[0] ,  -2 , SpringLayout.EAST,  c );
    s.putConstraint
        ( SpringLayout.WEST  , keyboard[0] , -135 , SpringLayout.EAST,  c );
    c.add(keyboard[0]);
    
    // central display panel with drawings y=f(x)
    
    s.putConstraint
        ( SpringLayout.NORTH , display[0] ,  7  , SpringLayout.NORTH, c );
    s.putConstraint
        ( SpringLayout.WEST  , display[0] ,  5  , SpringLayout.WEST, c );
    s.putConstraint
        ( SpringLayout.SOUTH , display[0] , -62 , SpringLayout.SOUTH, c );
    s.putConstraint
        ( SpringLayout.EAST  , display[0] , -140  , SpringLayout.EAST, c );
    c.add(display[0]);
        
    frame.setSize(getGuiSize());
    
    }
    
}
