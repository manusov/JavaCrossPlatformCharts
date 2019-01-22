/*

View settings window (right up) class:
1) Builder.
2) Components lists.
3) Events listeners.
4) Window logic.

*/

package integrator.view;

import integrator.controller.Integrator;
import integrator.controller.RunInterface;
import integrator.controller.RunInterface.CONTROLS;
import integrator.controller.RunInterface.GCOLOR;
import integrator.view.DataTree.DataKeys;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ViewSettings
{
private final int X = 107, Y = 510;
private final JPanel panel;
private DataKeys dataKey;
public JPanel getPanel() { return panel; }
public void setDataKey( DataKeys x ) { dataKey = x; }

public ViewSettings()
    {
    panel = new ViewSettingsPanel();
    panel.setPreferredSize( new Dimension( X, Y ) );
    ( (OptionPanel)panel ).addComponents();
    }
}

// class for panel

class ViewSettingsPanel extends OptionPanel
{
@Override public DescriptButton[] getButtonsList() 
    {
    DescriptButton[] list = new DescriptButton[]
        {  // quad group 1
        new ButtonBaseUp()     ,
        new ButtonBaseDown()   ,
        new ButtonBaseLeft()   ,
        new ButtonBaseRight()  ,
        // quad group 2
        new ButtonScaleUp()    ,
        new ButtonScaleDown()  ,
        new ButtonScaleLeft()  ,
        new ButtonScaleRight() ,
        // down group
        new ButtonTabUp()      ,
        new ButtonTabDown()    ,
        new ButtonReset()
        };
    return list;
    }

@Override public DescriptLabelConst[] getComboLabelsList() 
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelColorScheme() ,
        };
    return list;
    }

@Override public DescriptCombo[] getComboList() 
    {
    DescriptCombo[] list = new DescriptCombo[]
        {
        new ComboColorScheme() ,
        };
    return list;    
    }

@Override public DescriptLabelConst[] getFieldsLabelsList() 
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelXmin() ,
        new LabelXmax() ,
        new LabelYmin() ,
        new LabelYmax() 
        };
    return list;
    }

@Override public DescriptField[] getFieldsList()
    {
    DescriptField[] list = new DescriptField[]
        {
        new FieldXmin() ,
        new FieldXmax() ,
        new FieldYmin() ,
        new FieldYmax()   
        };
    return list;
    }

private JButton[] buttons;  // this required for listener

@Override public void layoutComponents
    ( JLabel[] cl, JComboBox[] c, JLabel[] fl, JTextField[] f, JButton[] b )
    {
    // store for listener
    buttons = b;
    // layout manager
    SpringLayout s = new SpringLayout();
    JPanel panel = this;
    panel.setLayout( s );
    // layout for associated labels and combo boxes
    layoutSingle( s, panel, cl[0], 0 );
    layoutSingle( s, panel, c[0],  23 );
    // layout for associated labels and text input fields
    layoutSingleLeft ( s, panel, fl[0], 56 );
    layoutSingleRight( s, panel, f[0],  56 );
    layoutSingleLeft ( s, panel, fl[1], 85 );
    layoutSingleRight( s, panel, f[1],  85 );
    layoutSingleLeft ( s, panel, fl[2], 114 );
    layoutSingleRight( s, panel, f[2],  114 );
    layoutSingleLeft ( s, panel, fl[3], 143 );
    layoutSingleRight( s, panel, f[3],  143 );
    // layout for buttons
    layoutQuad  ( s, panel, b[0], b[1], b[2], b[3], 177 );
    layoutQuad  ( s, panel, b[4], b[5], b[6], b[7], 267 );
    layoutSingle( s, panel, b[8], 357 );
    layoutSingle( s, panel, b[9], 384 );
    layoutSingle( s, panel, b[10], 411 );
    
    // THIS BECAUSE UNDER CONSTRUCTION
    // disable elements under construction
    fl[0].setEnabled(false);
    fl[1].setEnabled(false);
    fl[2].setEnabled(false);
    fl[3].setEnabled(false);
    f[0].setEnabled(false);
    f[1].setEnabled(false);
    f[2].setEnabled(false);
    f[3].setEnabled(false);
    b[0].setEnabled(false);
    b[1].setEnabled(false);
    b[2].setEnabled(false);
    b[3].setEnabled(false);
    // THIS BECAUSE UNDER CONSTRUCTION

    }

// helpers methods for components location by SpringLayout
private static void layoutSingle
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.EAST , c,  -2 , SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.WEST , c, -105, SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleLeft
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.EAST , c, -63 , SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.WEST , c, -100, SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleRight
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.EAST , c,  -2 , SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.WEST , c, -62 , SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutQuad
        ( SpringLayout s, JPanel p, 
          JButton b0, JButton b1, JButton b2, JButton b3, int v )
    {
    // button arrow up: example = move X-axis up
    s.putConstraint( SpringLayout.NORTH, b0, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.EAST , b0,  -3 , SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.WEST , b0, -105, SpringLayout.EAST , p );
    s.putConstraint( SpringLayout.SOUTH, b0, v+30, SpringLayout.NORTH, p );
    // button arrow left, example = move Y-axis left
    s.putConstraint( SpringLayout.NORTH, b2,   1 , SpringLayout.SOUTH, b0 );
    s.putConstraint( SpringLayout.WEST , b2,   0 , SpringLayout.WEST , b0 );
    s.putConstraint( SpringLayout.EAST , b2,  50 , SpringLayout.WEST , b0 );
    s.putConstraint( SpringLayout.SOUTH, b2,  27 , SpringLayout.SOUTH, b0 );
    // button arrow right: example = move Y-axis right
    s.putConstraint( SpringLayout.NORTH, b3,   1 , SpringLayout.SOUTH, b0 );
    s.putConstraint( SpringLayout.WEST , b3, -51 , SpringLayout.EAST , b0  );
    s.putConstraint( SpringLayout.EAST , b3,   0 , SpringLayout.EAST , b0 );
    s.putConstraint( SpringLayout.SOUTH, b3,  27 , SpringLayout.SOUTH, b0 );
    // button arrow down: example = move X-axis down
    s.putConstraint( SpringLayout.NORTH, b1,   1 , SpringLayout.SOUTH, b2 );
    s.putConstraint( SpringLayout.EAST , b1,  -3 , SpringLayout.EAST , p  );
    s.putConstraint( SpringLayout.WEST , b1, -105, SpringLayout.EAST , p  );
    s.putConstraint( SpringLayout.SOUTH, b1,  30 , SpringLayout.SOUTH, b2 );    
    }

@Override public AbstractAction getKeyAction()
    {
    return new AbstractAction()
        { @Override public void actionPerformed( ActionEvent e ) 
            { 
            
            }
        }; 
    }

@Override public ActionListener getCombosListener()
    {
    return ( ActionEvent e ) -> 
        {
        // command = button ID as string
        String command = e.getActionCommand();
        // selector = button ID as integer number, converted from string
        int selector, index = 0;
        if ( ( command != null ) && ( command.length() >= 1 ) )
            {
            try {
                selector = Integer.decode( command );
                }
            catch ( Exception ex )  // if error when convert string to number
                {
                selector = -1;    // force wrong combo ID if error
                }
            Object o = e.getSource();
            if ( o instanceof JComboBox )
                {  // get selected item number
                index = ( (JComboBox)o ).getSelectedIndex();
                }
            RunInterface ri = Integrator.getRunInterface();
            if ( (selector >= 0) && ( selector < getComboList().length ) &&
                 ( index >= 0 ) && ( ri != null ) )
                {
                // make action
                if ( selector < getComboList().length )
                    {
                    getComboList()[selector].comboAction( ri, index );
                    }
                // refresh GUI = f ( action results )
                JPanel p = Integrator.getDisplay().getPanel();
                p.repaint();
                }
            }
        };
    }

@Override public ActionListener getFieldsListener()
    {
    return ( ActionEvent e ) -> 
        {
        // ... under construction ...
        };
    }

@Override public ActionListener getButtonsListener()
    {
    return ( ActionEvent e ) -> 
        {
        // command = button ID as string
        String command = e.getActionCommand();
        // selector = button ID as integer number, converted from string
        int selector;
        if ( ( command != null ) && ( command.length() >= 1 ) )
            {
            try {
                selector = Integer.decode(command);
                }
            catch (Exception ex)  // if error when convert string to number
                {
                selector = -1;  // force wrong button ID if error
                }
            if ( (selector >= 0) & selector < getButtonsList().length ) 
                {
                // execute operation, associated with this button press
                RunInterface ri = Integrator.getRunInterface();
                getButtonsList()[selector].buttonAction( ri );
                // set buttons enabled or disabled by this action result
                buttons[4].setEnabled( ri.isYincable() );
                buttons[5].setEnabled( ri.isYdecable() );
                buttons[6].setEnabled( ri.isXdecable() );
                buttons[7].setEnabled( ri.isXincable() );
                buttons[8].setEnabled( ri.isTabIncable() );
                buttons[9].setEnabled( ri.isTabDecable() );
                // Revisual function draw window after this action
                JPanel p = Integrator.getDisplay().getPanel();
                p.repaint();
                p = Integrator.getViewSettings().getPanel();
                p.repaint();
                }
            }
        };
    }

}

// classes for buttons, labels, text fields, combo boxes

class ButtonBaseUp extends DescriptButton 
{
@Override public String getName() { return "base Y+"; }
@Override public String getText() { return "move up"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_UP }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.X_AXIS_UP ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonBaseDown extends DescriptButton
{
@Override public String getName() { return "Y-"; }
@Override public String getText() { return "move down"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_DOWN }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.X_AXIS_DOWN ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonBaseLeft extends DescriptButton
{
@Override public String getName() { return "X-"; }
@Override public String getText() { return "move left"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_LEFT }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.Y_AXIS_LEFT ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonBaseRight extends DescriptButton
{
@Override public String getName() { return "X+"; }
@Override public String getText() { return "move right"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_LEFT }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.Y_AXIS_RIGHT ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonScaleUp extends DescriptButton
{
@Override public String getName() { return "scale Y+"; }
@Override public String getText() { return "increase vertical scale"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_PAGE_UP }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.Y_SCALE_INC ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonScaleDown extends DescriptButton
{
@Override public String getName() { return "Y-"; }
@Override public String getText() { return "decrease vertical scale"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_PAGE_DOWN }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.Y_SCALE_DEC ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonScaleLeft extends DescriptButton
{
@Override public String getName() { return "X-"; }
@Override public String getText() { return "decrease horizontal scale"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_HOME }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.X_SCALE_DEC ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonScaleRight extends DescriptButton
{
@Override public String getName() { return "X+"; }
@Override public String getText() { return "increase horizontal scale"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_END }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.X_SCALE_INC ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonTabUp extends DescriptButton
{
@Override public String getName() { return "Steps +"; }
@Override public String getText() { return "increase function quality"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_ADD }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.TAB_INC ); }
}

class ButtonTabDown extends DescriptButton
{
@Override public String getName() { return "Steps -"; }
@Override public String getText() { return "decrease function quality"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_SUBTRACT }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.TAB_DEC ); }
}

class ButtonReset extends DescriptButton
{
@Override public String getName() { return "Reset"; }
@Override public String getText() { return "restore default parameters"; }
@Override public int[] getKeys()  
    { return new int[] { KeyEvent.VK_ESCAPE }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( CONTROLS.RESET ); }
@Override public Color getCustomColor() { return CLR_CUSTOM_COLOR; }
@Override public Font getCustomFont()   { return CLR_CUSTOM_FONT; }
}

class LabelColorScheme extends DescriptLabelConst
{
@Override public String getName() { return "Color"; }
@Override public String getText() { return "select background color, C key"; }
}

class ComboColorScheme extends DescriptCombo 
{
@Override public String[] getValues()
    { return new String[] { "Black" , "White" }; }
@Override public String getText()
    { return "select background color, C key"; }
@Override public int[] getKeys()
    { return new int[] { KeyEvent.VK_C }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    GCOLOR[] values = GCOLOR.values();
    int n = values.length;
    if ( index > n ) index = n;
    GCOLOR value = values[index];
    ri.setColorScheme(value);
    }
}

class LabelXmin extends DescriptLabelConst
{
@Override public String getName() { return "Xmin"; }
@Override public String getText() { return "argument X start value";  }
}

class LabelXmax extends DescriptLabelConst
{
@Override public String getName() { return "Xmax"; }
@Override public String getText() { return "argument X end value"; }
}

class LabelYmin extends DescriptLabelConst
{
@Override public String getName() { return "Ymin"; }
@Override public String getText() { return "function Y=F(X) view down"; }
}

class LabelYmax extends DescriptLabelConst
{
@Override public String getName() { return "Ymax"; }
@Override public String getText() { return "function Y=F(X) view up"; }
}

class FieldXmin extends DescriptField
{
private String value = "";
@Override public String getValue()       { return value;  }
@Override public void setValue(String s) { value = s;     }
@Override public int getSize()           { return 10;     }
@Override public String getText()        { return "argument X start value"; }
@Override public int[] getKeys()      { return new int[] { KeyEvent.VK_X }; }
@Override public void fieldAction( RunInterface ri ) { }
}

class FieldXmax extends DescriptField
{
private String value = "";
@Override public String getValue()       { return value;  }
@Override public void setValue(String s) { value = s;     }
@Override public int getSize()           { return 10;     }
@Override public String getText()        { return "argument X end value"; }
@Override public int[] getKeys()      { return new int[] { KeyEvent.VK_Z }; }
@Override public void fieldAction( RunInterface ri ) { }
}

class FieldYmin extends DescriptField
{
private String value = "";
@Override public String getValue()       { return value;  }
@Override public void setValue(String s) { value = s;     }
@Override public int getSize()           { return 10;     }
@Override public String getText()     { return "function Y=F(X) view down"; }
@Override public int[] getKeys()      { return new int[] { KeyEvent.VK_Y }; }
@Override public void fieldAction( RunInterface ri ) { }
}

class FieldYmax extends DescriptField
{
private String value = "";
@Override public String getValue()       { return value;  }
@Override public void setValue(String s) { value = s;     }
@Override public int getSize()           { return 10;     }
@Override public String getText()     { return "function Y=F(X) view up";   }
@Override public int[] getKeys()      { return new int[] { KeyEvent.VK_T }; }
@Override public void fieldAction( RunInterface ri ) { }
}
