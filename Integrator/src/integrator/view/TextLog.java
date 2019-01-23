/*

Text log window class:
1) Builder.
2) Components lists.
3) Events listeners.
4) Window logic.

*/

package integrator.view;

import integrator.controller.Integrator;
import integrator.controller.RunInterface;
import integrator.model.Function;
import integrator.view.DataTree.DataKeys;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextLog
{
private final int X = 400, Y = 130;
private final JPanel panel;
private final JTextArea text;
private DataKeys dataKey;
public JPanel getPanel() { return panel; }
public void setDataKey( DataKeys x ) { dataKey = x; }

public TextLog()
    {
    panel = new TextLogPanel();
    panel.setPreferredSize( new Dimension( X, Y ) );
    panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
    panel.setPreferredSize( new Dimension( X, Y ) );
    Box vertical = Box.createVerticalBox();
    text = new JTextArea();
    text.append( "starting..." );
    text.setEditable( false );
    vertical.add( new JScrollPane( text ) );
    panel.add( vertical );
    ( (LogPanel)panel ).dataChanged();
    }

private class TextLogPanel extends LogPanel
    {
    @Override public void dataChanged()
        {
        RunInterface ri = Integrator.getRunInterface();
        String s;
        if ( ri != null )
            {
            Function f = ri.getSelectedFunction();
            if( f != null )
                {
                s = f.getNameY();
                s = "select function: " + s;
                }
            else
                {
                s = "function not selected.";
                }
            text.append( "\n" + s );
            text.setCaretPosition( text.getDocument().getLength() );
            }
        }
    }
}

