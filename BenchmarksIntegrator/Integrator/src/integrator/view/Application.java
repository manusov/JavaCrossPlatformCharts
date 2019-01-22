package integrator.view;

import integrator.controller.Integrator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Application 
{
// private final int X = 984, Y = 728;
private final JFrame frame;
private final JSplitPane upSplit;
private final JSplitPane downSplit;
private final JSplitPane centerSplit;
private final JSplitPane leftSplit;
private final JSplitPane allSplit;

public JFrame getFrame()           { return frame;       }
public JSplitPane getUpSplit()     { return upSplit;     };
public JSplitPane getDownSplit()   { return downSplit;   };
public JSplitPane getCenterSplit() { return centerSplit; };
public JSplitPane getLeftSplit()   { return leftSplit;   };
public JSplitPane getAllSplit()    { return allSplit;    };

public Application()
    {
    // Create application frame
    JFrame.setDefaultLookAndFeelDecorated( true );
    JDialog.setDefaultLookAndFeelDecorated( true );
    frame = new JFrame( Integrator.getAbout().getLongName() );
    frame.setJMenuBar( Integrator.getRootMenu().getMenuBar() );
    // Set event handling
    frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
    frame.addWindowListener( new ChildsExitListener() );
    // Start build panels with sub-panels,
    // get 4 sub-panels of center (right big) panel
    JPanel display = Integrator.getDisplay().getPanel();
    JPanel viewSettings = Integrator.getViewSettings().getPanel();
    JPanel textLog = Integrator.getTextLog().getPanel();
    JPanel statTable = Integrator.getStatTable().getPanel();
    // Panels: left = draw Y=F(X) and right = view options
    upSplit = new JSplitPane
        ( JSplitPane.HORIZONTAL_SPLIT, display, viewSettings );
    upSplit.setDividerSize( 4 );
    // Panels: left = text log window, right = statistics table
    downSplit = new JSplitPane
        ( JSplitPane.HORIZONTAL_SPLIT, textLog, statTable );
    downSplit.setDividerSize( 4 );
    // Panels groups: up = draw + options, down = log + statistics
    centerSplit = new JSplitPane
        ( JSplitPane.VERTICAL_SPLIT, upSplit, downSplit );
    centerSplit.setDividerSize( 4 );
    // get 2 sub-panels of left panel
    JPanel dataTree = Integrator.getDataTree().getPanel();
    JPanel runSettings = Integrator.getRunSettings().getPanel();
    // create left sub-panel of panel
    leftSplit = new JSplitPane
        ( JSplitPane.VERTICAL_SPLIT, dataTree, runSettings );
    leftSplit.setDividerSize( 4 );
    // create all panel
    allSplit = new JSplitPane
        ( JSplitPane.HORIZONTAL_SPLIT, leftSplit, centerSplit );
    allSplit.setDividerSize( 4 );
    // add complex split panel to frame
    frame.add( allSplit );
    // Adjust GUI window size
    frame.pack();
    // frame.setSize( X, Y );
    // Visual
    
    // BECAUSE DEBUG, YET SET NOT RESIZABLE
    frame.setResizable( false );
    upSplit.setEnabled( false );
    downSplit.setEnabled( false );
    centerSplit.setEnabled( false );
    leftSplit.setEnabled( false );
    allSplit.setEnabled( false );
    // BECAUSE DEBUG
    
    frame.setLocationRelativeTo( null );
    frame.setVisible( true );
    }

private class ChildsExitListener extends WindowAdapter
    {
    @Override public void windowClosing( WindowEvent event )    
        {
        Integrator.getRootMenu().stopAllChilds();
        }
    @Override public void windowClosed( WindowEvent event )    
        {
        Integrator.getRootMenu().stopAllChilds();
        }
    }

}
