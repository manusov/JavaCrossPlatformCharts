/*

Application frame with root menu,
see calculator sources for dynamical mode changes from root menu.
TODO:
Store root menu items as control data class, not part of executable code.

*/

package charts.view.rootmenu;

import charts.view.guibuilders.GuiSimple;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class RootFrame extends JFrame
{
private final JMenuBar menuBar;
private final JPanel[][] displays, status, controls;
private final GuiSimple guiSimple;

// Frame constructor with root menu and 3 panels
public RootFrame( String name, JPanel[][] x1 , JPanel[][] x2 , JPanel[][] x3 )
    {
    super(name);
    displays = x1;  // central panel = display with math. function draw
    status   = x2;  // down panel = math. function statistics
    controls = x3;  // right panel = with control buttons and other components
    guiSimple = new GuiSimple();
    menuBar = new JMenuBar();
    // File menu
    JMenu file = new JMenu("File");
    HandlerExit handlerExit = new HandlerExit();
    handlerExit.putValue(NAME, "Exit");
    JMenuItem exit = new JMenuItem(handlerExit);
    file.add(exit);       // add "Exit" item to "File" menu level
    menuBar.add(file);    // add "File" item to menu bar
    // Help menu
    JMenu help = new JMenu("Help");
    HandlerAbout handlerAbout = new HandlerAbout(this);
    handlerAbout.putValue(NAME, "About");
    JMenuItem about = new JMenuItem(handlerAbout);
    help.add(about);    // add "About" item to "Help" menu level
    menuBar.add(help);  // add "Help" item to menu bar
    // This required to prevent charts corruption when root menu works (v0.07)
    DrawRefresh dr = new DrawRefresh();
    file.addMenuListener(dr);
    help.addMenuListener(dr);
    }

// This required to prevent charts corruption when root menu works (v0.07)
private class DrawRefresh implements MenuListener
    {
    @Override public void menuSelected(MenuEvent e)
        {
        // refresh not required for SELECT action: area re-drawed by menu
        }
    @Override public void menuDeselected(MenuEvent e)
        {
        // refresh required for deselect action
        refresh();
        }
    @Override public void menuCanceled(MenuEvent e) 
        {
        // refresh not required for CANCELLED action: are re-drawed by DESELECT
        }

    private void refresh()
        {
        int n = displays.length;
        for(int i=0; i<n; i++)
            {
            int m = displays[i].length;
            for(int j=0; j<m; j++)
                {
                displays[i][j].repaint();
                }
            }
        }
    }


// Start GUI application
public void startApplication()
    {
    setJMenuBar(menuBar);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    // yet only first elements used
    guiSimple.setGui( this, displays[0], status[0], controls[0] );
    setLocationRelativeTo(null);
    setResizable(false);
    setVisible(true);
    }
    
}
