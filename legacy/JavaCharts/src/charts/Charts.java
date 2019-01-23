/*

Java Function Graphs utility.
(C)2018 IC Book Labs.
(C)2018 Manusov I.V.

Main class with application entry point.

Problems:
1) Drawing function, all controls and labels.
2) Change function screen, function names by HTML.
3) Tangent bug, heigh of lines, limit Y for X^2, X^3.
4) Average, median, other comments, see FunctionState.java.
5) Code comments.
6) Make some variables private.
7) Make adjustable drawings.
8) Remove unused methods and data.
9) Add new functions to list, see JavaCalc.
10) See KeyboardFabric.java, line 293, temporary disabling.
11) See StatusFabric.java, line 56, temporary disabling.

TODO (maraphon mode after existed valid version v0.01):
1) + Detail comments.
2) + Test under win32, win64, linux32, linux64. First verification.
3) + Extend functions list up to JavaCalc + y=x. Interrogate SQRT bug.
4) Average, median, other comments, see FunctionState.java.
    See and debug: StatusFabric.java, StatusPanel.java.
    Bug with y = 1/x detect min, max.
    Bug with y = tg(x) infinity not reached.
    Bug with -0.0 average y=x.
    Maximize steps, minimize number of iterations. Precision check.
    Make method for find median without ordering, or fast ordering
    increase tab max.
    Bug if ARRAY_SIZE % BLOCK_SIZE = 0. Exception, empty array.
5) Make function names as HTML, both for JComboBox and Display, example X^2.
6) Support infinity, NaN, example tangent bug.
7) Optimizing text location at display.
8) Make some variables private.
9) Remove or comment-lock unused code and data.
10) Enable buttons, see KeyboardFabric.java, line 293, temporary disabling.
     Make button gray, when parameter limit reached.
     Update combo boxes values when modified by buttons, for example CLEAR.
11) Enable text fields, see StatusFabric.java, line 56, temporary disabling,
    optimizing functions ranges, for example sin = [-pi ... pi ].
12) Auto scaling and correct default scaling customization by function.
13) Total verification.

v0.07. BUG FIXED: graphics window corrupted when work main menu.
       Under verify. RootFrame.java. Marker "v0.07".

v0.08. Improve comments. Required adaptation this application for
       benchmarks drawings: 4 modes = { math, file, java, native }.
       Fix some IDE warnings.

Required redesign and functional upgrade for use this application for
benchmarks drawings and some applications integrator.

*/

package charts;

import charts.controller.KeyboardFabric;
import charts.controller.KeyboardPanel;
import charts.model.CoreFabric;
import charts.model.FunctionCore;
import charts.view.about.About;
import charts.view.drawdisplay.DisplayFabric;
import charts.view.drawdisplay.DisplayPanel;
import charts.view.drawstatus.StatusFabric;
import charts.view.drawstatus.StatusPanel;
import charts.view.rootmenu.RootFrame;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Charts 
{

public enum DRAWS { SIMPLE , RESERVED_1 , RESERVED_2 };
private static final DRAWS[] DRAWS_LIST = { DRAWS.SIMPLE };

private static FunctionCore[]   functionCores;   // funct. and viewers MODEL
private static DisplayPanel[][] displayPanels;   // VIEW funct. graph (center)
private static StatusPanel[][]  statusPanels;    // VIEW status panel (down)
private static JPanel[][]       keyboardPanels;  // CONTROLLER keys (right)
private static RootFrame        rootMenu;        // Application GUI frame
    
public static void main(String[] args) 
    {
    int n = DRAWS_LIST.length;
    functionCores  = new FunctionCore[n];
    displayPanels  = new DisplayPanel[n][];
    statusPanels   = new StatusPanel[n][];
    keyboardPanels = new KeyboardPanel[n][];
    
    // cycle for all modes, yet single mode only (n=1), reserved for extensions
    for(int i=0; i<n; i++)
        {
        // Initializing MVC.MODEL
        functionCores[i] = CoreFabric.getFunctionCore ( DRAWS_LIST[i] );
        // Initializing MVC.VIEW
        displayPanels[i] = DisplayFabric.getDisplayPanels ( DRAWS_LIST[i] );
        statusPanels[i]  = StatusFabric.getStatusPanels   ( DRAWS_LIST[i] );
        // Initializing MVC.CONTROLLER
        keyboardPanels[i] = KeyboardFabric.getKeyboardPanels ( DRAWS_LIST[i] ,
                functionCores[i], displayPanels[i], statusPanels[i] );
        // Synchronize MODEL and VIEW for display panels
        for (int j=0; j<displayPanels[i].length; j++)    
                {
                displayPanels[i][j].updateDisplay( functionCores[i], j );
                }
        // Synchronize MODEL and VIEW for status strings panels
        for (int j=0; j<statusPanels[i].length; j++)    
                {
                statusPanels[i][j].updateStatus( functionCores[i], j );
                }
        }

    // Start GUI
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
    rootMenu = new RootFrame( About.getShortName() , 
                              displayPanels , statusPanels , keyboardPanels );
    rootMenu.startApplication();
    }
    
}
