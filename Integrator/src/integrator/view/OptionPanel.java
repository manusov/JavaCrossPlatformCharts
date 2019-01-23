/*

Abstract class for option panel description.

*/

package integrator.view;

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

public abstract class OptionPanel extends JPanel
{
// method get list of control buttons
public abstract DescriptButton[] getButtonsList();
// method get list of labels for combo boxes
public abstract DescriptLabelConst[] getComboLabelsList();
// method get list of combo boxes
public abstract DescriptCombo[] getComboList();
// method get list of labels for text input fields
public abstract DescriptLabelConst[] getFieldsLabelsList();
// method get list of text input fields
public abstract DescriptField[] getFieldsList();
// methods returns action listeners
public abstract AbstractAction getKeyAction();
public abstract ActionListener getCombosListener();
public abstract ActionListener getFieldsListener();
public abstract ActionListener getButtonsListener();
// method for update view after changed parameters
public abstract void dataChanged();

// method for panel-specific layout
public abstract void layoutComponents
    ( JLabel[] cl, JComboBox[] c, JLabel[] fl, JTextField[] f, JButton[] b );
// method for setup components, make layout and add to panel
public void addComponents()
    {
    Font font = new Font( "Verdana", Font.PLAIN, 12 );
    // buttons
    int nb = 0;
    if ( getButtonsList() != null ) nb = getButtonsList().length;
    JButton[] b = new JButton[nb];
    // combo boxes
    int ncl = 0, nc = 0;
    if ( getComboLabelsList() != null ) ncl = getComboLabelsList().length;
    if ( getComboList() != null )       nc = getComboList().length;
    int nlc = Integer.max( ncl, nc );
    JLabel[] cl = new JLabel[ncl];
    JComboBox[] c = new JComboBox[nc];
    // keyboard listener for some "hot" keys, class for keys press handling
    AbstractAction action = getKeyAction();
    // combo listener
    ActionListener comboListener = getCombosListener();
    // labels and combo cycle #1, executed before layout manager
    for(int i=0; i<nlc; i++)
        {
        String stringId = "" + i;
        // build labels
        if ( ncl > 0 )
            {
            cl[i] = new JLabel( getComboLabelsList()[i].getName() );
            cl[i].setToolTipText ( getComboLabelsList()[i].getText() );
            }
        // build combos
        if ( nc > 0 )
            {
            c[i] = new JComboBox( getComboList()[i].getValues() );
            c[i].setToolTipText ( getComboList()[i].getText() );
            c[i].setActionCommand(stringId);
            // setup action handling
            c[i].addActionListener( comboListener );
            // setup keyboard handling, use previously created abstract action
            InputMap inputMap =
                c[i].getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
            ActionMap actionMap = c[i].getActionMap();
            int[] keys = getComboList()[i].getKeys();
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
        ncl--;  // decrement labels counter
        nc--;   // decrement combos counter
        }
    // text fields with associated labels
    int nfl = 0, nf = 0;
    if ( getFieldsLabelsList() != null ) nfl = getFieldsLabelsList().length;
    if ( getFieldsList() != null )       nf = getFieldsList().length;
    int nlf = Integer.max( nfl, nf );
    JLabel[] fl = new JLabel[nfl];
    JTextField[] f = new JTextField[nf];
    // text input fields listener
    ActionListener fieldsListener = getFieldsListener();
    // labels and text input fields cycle #1, executed before layout manager
    for(int i=0; i<nlf; i++)
        {
        String stringId = "" + i;
        // build labels
        if ( nfl > 0 )
            {
            fl[i] = new JLabel( getFieldsLabelsList()[i].getName() );
            fl[i].setToolTipText ( getFieldsLabelsList()[i].getText() );
            }
        // build text input fields
        if ( nf > 0 )
            {
            f[i] = new JTextField
                ( getFieldsList()[i].getValue(), getFieldsList()[i].getSize() );
            f[i].setToolTipText ( getFieldsList()[i].getText() );
            f[i].setActionCommand( stringId );
            // setup action handling
            f[i].addActionListener( fieldsListener );
            // setup keyboard handling, use previously created AbstractAction
            InputMap inputMap =
                f[i].getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
            ActionMap actionMap = f[i].getActionMap();
            int[] keys = getFieldsList()[i].getKeys();
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
        nfl--;  // decrement labels counter
        nf--;   // decrement text input fields counter
        }
    // buttons listener, this functionality can be extended, see KeyboardFabric
    ActionListener buttonsListener = getButtonsListener();
        // buttons cycle #1, prepare buttons executed before layout manager
    for( int i=0; i<nb; i++ )
        {
        // identifier string for button, executed at this iteration
        String stringId = "" + i;
        // create button, set name for button visual
        b[i] = new JButton( getButtonsList()[i].getName() );
        // set tooltip, visualized when mouse cursor located at button
        b[i].setToolTipText ( getButtonsList()[i].getText() );
        // set default or custom for button name string
        Font customFont = getButtonsList()[i].getCustomFont();
        if ( customFont != null ) 
            { b[i].setFont(customFont); }
        else
            { b[i].setFont( font ); }
        // set custom color for button name string
        Color customColor = getButtonsList()[i].getCustomColor();
        if ( customColor != null )
            { b[i].setForeground( customColor ); }
        // set identifier, used by handler 
        b[i].setActionCommand( stringId );
        // setup handler for this button
        b[i].addActionListener( buttonsListener );
        // setup keyboard key(s) for this button
        InputMap inputMap =
            b[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = b[i].getActionMap();
        // get array of keystrokes from button description
        int[] keys = getButtonsList()[i].getKeys();
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
    // panel-specific layout
    layoutComponents( cl, c, fl, f, b );
    // labels and combo cycle #2, add to panel executed after layout manager
    if ( getComboLabelsList() != null ) ncl = getComboLabelsList().length;
    if ( getComboList() != null )       nc = getComboList().length;
    for( int i=0; i<nlc; i++ )
        {
        if ( ncl > 0 )
            {
            add( cl[i] );
            }
        if ( nc > 0 )
            {
            add( c[i] );
            }
        ncl--;
        nc--;
        }
    // labels and fields cycle #2, add to panel executed after layout manager
    if ( getFieldsLabelsList() != null ) nfl = getFieldsLabelsList().length;
    if ( getFieldsList() != null )       nf = getFieldsList().length;
    for( int i=0; i<nlf; i++ )
        {
        if ( nfl > 0 )
            {
            add( fl[i] );
            }
        if ( nf > 0 )
            {
            add( f[i] );
            }
        nfl--;
        nf--;
        }
    // buttons cycle #2, add to panel executed after layout manager
    for( int i=0; i<nb; i++ )
        {
        add( b[i] );  // add button to panel
        }
    }

}
