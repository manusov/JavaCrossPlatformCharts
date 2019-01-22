/*

Text log window class:
1) Builder.
2) Components lists.
3) Events listeners.
4) Window logic.

*/

package integrator.view;

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
    JTextArea text = new JTextArea();
    text.append( "Log window..." );
    text.setEditable( false );
    vertical.add( new JScrollPane( text ) );
    panel.add( vertical );
    }

private class TextLogPanel extends JPanel
    {
    // ... reserved for extensions ...
    }
    
}

