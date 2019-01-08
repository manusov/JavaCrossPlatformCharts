/*

Fabric class for build control buttons panel.
Method accepts panel type (yet supported SIMPLE only), model (FunctionCore),
views (DisplayPanel, StatusPanel). Returns application panel.

*/

package charts.controller;

import charts.Charts.DRAWS;
import charts.model.Function;
import charts.model.FunctionCore;
import charts.model.FunctionsList;
import charts.view.drawdisplay.DisplayPanel;
import charts.view.drawstatus.StatusPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;

public class KeyboardFabric 
{

public static JPanel[] getKeyboardPanels
        ( DRAWS type , FunctionCore function ,
          DisplayPanel[] display , StatusPanel[] status )
    {
    JPanel[] keyboardPanels;
    KeyboardPanel kp;
    Font font;
    
    switch(type)
        {
        //
        // Reserved for add new advanced modes
        //
        case SIMPLE:
        default:
            {
            String[] s = { "n/a" };
            FunctionsList fl = function.getFunctionsList();
            if ( fl != null )
                {
                Function[] f = fl.getFunctionsList();
                if ( f != null )
                    {
                    int n = f.length;
                    s = new String[n];
                    for(int i=0; i<n; i++)
                        {
                        s[i] = f[i].getNameY();
                        }
                    }
                }
                
            kp = new KeyboardPanel(s);
            font = new Font("Verdana", Font.PLAIN, 12);
            break;
            }
        }

    // globally used buttons data
    DescriptButton[] buttonsList = kp.getButtonsList();
    int nb = buttonsList.length;
    JButton[] b = new JButton[nb];
    
    // combo boxes with associated labels
    DescriptLabelConst[] comboLabelsList = kp.getComboLabelsList();
    DescriptCombo[] comboList = kp.getComboList();
    int ncl = comboLabelsList.length;
    int nc = comboList.length;
    int nlc = Integer.max(ncl, nc);
    JLabel[] cl = new JLabel[ncl];
    JComboBox[] c = new JComboBox[nc];
    ActionListener comboListener = new ComboAction
        ( comboList, function, display, status, b );
    
    // labels and combo cycle #1, executed before layout manager
    for(int i=0; i<nlc; i++)
        {
        String stringId = "" + i;
        // build labels
        if ( ncl > 0 )
            {
            cl[i] = new JLabel( comboLabelsList[i].getName() );
            cl[i].setToolTipText ( comboLabelsList[i].getText() );
            }
        // build combos
        if ( nc > 0 )
            {
            c[i] = new JComboBox( comboList[i].getValues() );
            c[i].setToolTipText ( comboList[i].getText() );
            c[i].setActionCommand(stringId);
            // setup action handling
            c[i].addActionListener(comboListener);
            // setup keyboard handling
            AbstractAction action = new KeyAction();    // class for handling
            InputMap inputMap =
                c[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = c[i].getActionMap();
            int[] keys = comboList[i].getKeys();
            if ( keys != null )
                {
                for ( int k=0; k<keys.length; k++ )
                    {
                    // add key description to button input map, by stringId
                    inputMap.put( KeyStroke.getKeyStroke
                        ( keys[k], 0, true ) , stringId );
                    }
                    // add handler class to action map by stringId
                    actionMap.put( stringId, action );
                }
            }
        ncl--;
        nc--;
        }

    // text fields with associated labels
    DescriptLabelConst[] fieldsLabelsList = kp.getFieldsLabelsList();
    DescriptField[] fieldsList = kp.getFieldsList();
    int nfl = fieldsLabelsList.length;
    int nf = fieldsList.length;
    int nlf = Integer.max(nfl, nf);
    JLabel[] fl = new JLabel[nfl];
    JTextField[] f = new JTextField[nf];
    ActionListener fieldsListener = new FieldsAction
        ( fieldsList, function, display, status );
    
    // labels and combo cycle #1, executed before layout manager
    for(int i=0; i<nlf; i++)
        {
        String stringId = "" + i;
        // build labels
        if ( nfl > 0 )
            {
            fl[i] = new JLabel( fieldsLabelsList[i].getName() );
            fl[i].setToolTipText ( fieldsLabelsList[i].getText() );
            }
        // build combos
        if ( nf > 0 )
            {
            f[i] = new JTextField
                ( fieldsList[i].getValue(), fieldsList[i].getSize() );
            f[i].setToolTipText ( fieldsList[i].getText() );
            f[i].setActionCommand(stringId);
            // setup action handling
            f[i].addActionListener(fieldsListener);
            // setup keyboard handling
            AbstractAction action = new KeyAction();    // class for handling
            InputMap inputMap =
                f[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = f[i].getActionMap();
            int[] keys = fieldsList[i].getKeys();
            if ( keys != null )
                {
                for ( int k=0; k<keys.length; k++ )
                    {
                    // add key description to button input map, by stringId
                    inputMap.put( KeyStroke.getKeyStroke
                        ( keys[k], 0, true ) , stringId );
                    }
                    // add handler class to action map by stringId
                    actionMap.put( stringId, action );
                }
            }
        nfl--;
        nf--;
        }
    
    // buttons
    // DescriptButton[] buttonsList = kp.getButtonsList();
    // int nb = buttonsList.length;
    // JButton[] b = new JButton[nb];
    ActionListener buttonsListener = new ButtonsAction
        ( buttonsList, function, display, status, b );

    // buttons cycle #1, prepare buttons executed before layout manager
    for(int i=0; i<nb; i++)
        {
        // identifier string for button, executed at this iteration
        String stringId = "" + i;
        // create button, set name for button visual
        b[i] = new JButton( buttonsList[i].getName() );
        // set tooltip, visualized when mouse cursor located at button
        b[i].setToolTipText ( buttonsList[i].getText() );
        // set default or custom for button name string
        Font customFont = buttonsList[i].getCustomFont();
        if ( customFont != null ) 
            { b[i].setFont(customFont); }
        else
            { b[i].setFont(font); }
        // set custom color for button name string
        Color customColor = buttonsList[i].getCustomColor();
        if ( customColor != null )
            { b[i].setForeground(customColor); }
        // set identifier, used by handler 
        b[i].setActionCommand(stringId);
        // setup handler for this button
        b[i].addActionListener(buttonsListener);
        // setup keyboard key(s) for this button
        AbstractAction action = new KeyAction();    // class for handling
        InputMap inputMap =
            b[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = b[i].getActionMap();
        // get array of keystrokes from button description
        int[] keys = buttonsList[i].getKeys();
        if ( keys != null )
            {
            for ( int k=0; k<keys.length; k++ )
                {
                // add key description to button input map, by stringId
                inputMap.put( KeyStroke.getKeyStroke
                        ( keys[k], 0, true ) , stringId );
                }
                // add handler class to action map by stringId
                actionMap.put( stringId, action );
            }
        }
    
    // layout manager
    SpringLayout s = new SpringLayout();
    kp.setLayout(s);
    
    // layout for associated labels and combo boxes
    layoutSingleLeft ( s, kp, fl[0], 112 );
    layoutSingleRight( s, kp, f[0],  112 );
    layoutSingleLeft ( s, kp, fl[1], 142 );
    layoutSingleRight( s, kp, f[1],  142 );
    layoutSingleLeft ( s, kp, fl[2], 172 );
    layoutSingleRight( s, kp, f[2],  172 );
    layoutSingleLeft ( s, kp, fl[3], 202 );
    layoutSingleRight( s, kp, f[3],  202 );
    
    // layout for associated labels and combo boxes
    layoutSingle( s, kp, cl[0], 0 );
    layoutSingle( s, kp, c[0],  23 );
    layoutSingle( s, kp, cl[1], 46 );
    layoutSingle( s, kp, c[1],  69 );
    
    // layout for buttons
    layoutQuad( s, kp, b[0], b[1], b[2], b[3], 240 );
    layoutQuad( s, kp, b[4], b[5], b[6], b[7], 330 );
    layoutSingle( s, kp, b[8], 420 );
    layoutSingle( s, kp, b[9], 447 );
    layoutSingle( s, kp, b[10], 474 );

    // labels and combo cycle #2, add to panel executed after layout manager
    ncl = comboLabelsList.length;
    nc = comboList.length;
    for(int i=0; i<nlc; i++)
        {
        if ( ncl > 0 )
            {
            kp.add( cl[i] );
            }
        if ( nc > 0 )
            {
            kp.add( c[i] );
            }
        ncl--;
        nc--;
        }
    
    // labels and fields cycle #2, add to panel executed after layout manager
    nfl = fieldsLabelsList.length;
    nf = fieldsList.length;
    for(int i=0; i<nlf; i++)
        {
        if ( nfl > 0 )
            {
            kp.add( fl[i] );
            }
        if ( nf > 0 )
            {
            kp.add( f[i] );
            }
        nfl--;
        nf--;
        }
    
    // buttons cycle #2, add to panel executed after layout manager
    for(int i=0; i<nb; i++)
        {
        kp.add( b[i] );  // add button to panel
        }

    
// ************** THIS FRAGMENT FOR UNDER CONSTRUCTION STAGE *******************
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
    
/* 
    // TODO: BUG FIX AT FUNCTIONCORE.JAVA, BIG DECIMAL CUMULATIVE +/-   
    b[4].setEnabled(false);
    b[5].setEnabled(false);
    b[6].setEnabled(false);
    b[7].setEnabled(false);
*/

//  b[8].setEnabled(false);
//  b[9].setEnabled(false);
//  b[10].setEnabled(false);
// ************** END OF FRAGMENT FOR UNDER CONSTRUCTION STAGE *****************


    // prepare and return result panels array (yet one panel)
    keyboardPanels = new KeyboardPanel[1];
    keyboardPanels[0] = kp;
    return keyboardPanels;
    }        

// helpers methods for spring layout        

/*
private static void layoutQuad
        ( SpringLayout s, JPanel kp, 
          JButton b0, JButton b1, JButton b2, JButton b3, int v )
    {
    // button arrow up: example = move X-axis up
    s.putConstraint( SpringLayout.NORTH, b0,  v+4, SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , b0,   -1, SpringLayout.WEST , b3 );
    s.putConstraint( SpringLayout.WEST , b0,  -28, SpringLayout.WEST , b3 );
    s.putConstraint( SpringLayout.SOUTH, b0, v+30, SpringLayout.NORTH, kp );
    // button arrow down: example = move X-axis down
    s.putConstraint( SpringLayout.NORTH, b1,    1, SpringLayout.SOUTH, b0 );
    s.putConstraint( SpringLayout.EAST , b1,   -1, SpringLayout.WEST , b3 );
    s.putConstraint( SpringLayout.WEST , b1,  -28, SpringLayout.WEST , b3 );
    s.putConstraint( SpringLayout.SOUTH, b1,   27, SpringLayout.SOUTH, b0 );
    // button arrow left, example = move Y-axis left
    s.putConstraint( SpringLayout.NORTH, b2, v+4,  SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , b2,  -1,  SpringLayout.WEST , b0 );
    s.putConstraint( SpringLayout.WEST , b2, -28,  SpringLayout.WEST , b0 );
    s.putConstraint( SpringLayout.SOUTH, b2, v+57, SpringLayout.NORTH, kp );
    // button arrow right: example = move Y-axis right
    s.putConstraint( SpringLayout.NORTH, b3, v+4,  SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , b3,  -3,  SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , b3, -30,  SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, b3, v+57, SpringLayout.NORTH, kp );
    }
*/

private static void layoutQuad
        ( SpringLayout s, JPanel kp, 
          JButton b0, JButton b1, JButton b2, JButton b3, int v )
    {
    // button arrow up: example = move X-axis up
    s.putConstraint( SpringLayout.NORTH, b0, v+4 , SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , b0,  -3 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , b0, -126, SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, b0, v+30, SpringLayout.NORTH, kp );
    // button arrow left, example = move Y-axis left
    s.putConstraint( SpringLayout.NORTH, b2,   1 , SpringLayout.SOUTH, b0 );
    s.putConstraint( SpringLayout.EAST , b2, -61 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , b2, -126, SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, b2,  27 , SpringLayout.SOUTH, b0 );
    // button arrow right: example = move Y-axis right
    s.putConstraint( SpringLayout.NORTH, b3,   1 , SpringLayout.SOUTH, b0 );
    s.putConstraint( SpringLayout.WEST , b3,   1 , SpringLayout.EAST , b2 );
    s.putConstraint( SpringLayout.EAST , b3,  58 , SpringLayout.EAST , b2 );
    s.putConstraint( SpringLayout.SOUTH, b3,  27 , SpringLayout.SOUTH, b0 );
    // button arrow down: example = move X-axis down
    s.putConstraint( SpringLayout.NORTH, b1,   1 , SpringLayout.SOUTH, b2 );
    s.putConstraint( SpringLayout.EAST , b1,  -3 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , b1, -126, SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, b1,  30 , SpringLayout.SOUTH, b2 );    
    }

private static void layoutSingle
        ( SpringLayout s, JPanel kp, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , c,  -3 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , c, -126, SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, kp );
    }

private static void layoutSingleLeft
        ( SpringLayout s, JPanel kp, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , c, -73 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , c, -126, SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, kp );
    }

        
private static void layoutSingleRight
        ( SpringLayout s, JPanel kp, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, kp );
    s.putConstraint( SpringLayout.EAST , c,  -3 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.WEST , c, -89 , SpringLayout.EAST , kp );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, kp );
    }
        
}
