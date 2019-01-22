package integrator.view;

import integrator.controller.Integrator;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class DataTree 
{
private final int X = 220, Y = 190;
private final JPanel panel;
public JPanel getPanel() { return panel; }

private final String ROOT = "system";
private final String[] DATA = 
    new String[] { "data sources" };
private final String[] DATA_SOURCES =
    new String[] { "mathematics",
                   "data from file",
                   "jvm performance", 
                   "native performance" };

public enum DataKeys { MATH, FILE, JVM, NATIVE };
private final DataKeys[] dataKeys = 
                 { DataKeys.MATH,
                   DataKeys.FILE,
                   DataKeys.JVM,
                   DataKeys.NATIVE };

public DataTree()
    {
    // create and setup panel
    panel = new DataTreePanel();
    panel.setPreferredSize( new Dimension( X, Y ) );
    panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS) );
    Box vertical = Box.createVerticalBox();
    // create data model and tree
    DefaultMutableTreeNode root = new DefaultMutableTreeNode( ROOT );
    DefaultMutableTreeNode data = new DefaultMutableTreeNode( DATA[0] );
    root.add( data );
    int n = DATA_SOURCES.length;
    for ( int i=0; i<n; i++ )
        { data.add( new DefaultMutableTreeNode( DATA_SOURCES[i], false ) ); }
    DefaultTreeModel model = new DefaultTreeModel( root, true );
    JTree tree = new JTree( model );
    // add component selection listener, it handles tree events
    tree.getSelectionModel().setSelectionMode
        ( TreeSelectionModel.SINGLE_TREE_SELECTION );
    // Listener for tree events
    tree.addTreeSelectionListener( new TreeRootListener() );
    // locate at panel
    JScrollPane scroll = new JScrollPane( tree );
    tree.setEditable( false );
    vertical.add( scroll );
    panel.add( vertical );
    }

private class DataTreePanel extends JPanel
    {
    // ... reserved for extensions ...
    }

private class TreeRootListener implements TreeSelectionListener
    {
    @Override public void valueChanged( TreeSelectionEvent event )
        {
        Object tree = event.getSource(); 
        if ( tree instanceof JTree )
            {
            TreePath[] paths = ( (JTree)tree ).getSelectionPaths();
            if ( ( paths != null ) && ( paths.length > 0 ) )
                {
                TreePath path = paths[0];
                if ( path != null )
                    {
                    Object dmtn = path.getLastPathComponent();
                    if ( dmtn instanceof DefaultMutableTreeNode )
                        {
                        Object uobj = 
                            ( ( DefaultMutableTreeNode )dmtn ).getUserObject();
                        int n = DATA_SOURCES.length;
                        for( int i=0; i<n; i++ )
                            {
                            if ( ( uobj instanceof String )  &&
                                 ( i < DATA_SOURCES.length ) &&
                                 ( uobj.equals( DATA_SOURCES[i] ) ) )
                                {
                                DataKeys dk = dataKeys[i];
                                // assign static model selection
                                
                                // DEBUG,
                                // BETTER MAKE THIS BY "RUN" BUTTON
                                // Integrator.assignRunInterface( dk );
                                
                                // update GUI objects, because data mode changed
                                Integrator.getRunSettings().setDataKey( dk );
                                Integrator.getDisplay().setDataKey( dk );
                                Integrator.getViewSettings().setDataKey( dk );
                                Integrator.getTextLog().setDataKey( dk );
                                Integrator.getStatTable().setDataKey( dk );
                                // update run settings panel
                                JPanel rsPanel = 
                                    Integrator.getRunSettings().getPanel();
                                JSplitPane split = 
                                    Integrator.getApplication().getLeftSplit();
                                int location = split.getDividerLocation();
                                split.setBottomComponent( rsPanel );
                                split.setDividerLocation( location );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

