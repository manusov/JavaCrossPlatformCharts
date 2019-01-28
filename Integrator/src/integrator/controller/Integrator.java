/*

Debug benchmark integrator. Main class.

ROADMAP.

--- January 2019 ---

1)+  Design packages and classes structure.
2)+  Write class About.java.
3)+  Write class Integrator.java.
4)+  Write part of class RootMenu.java, except "Report all", "Report this".
5)+  Write class Application.java.
6)+  Write class DataTree.java, include listener.
7)+  Write class TextLog.java.
8)+  Write class StatTable.java, include part of switch by dataKey.
9)+  Write class ViewSettings.java, include listeners call, without listeners operations. 
10)+ Extract parents or helpers from ViewSettings.java.
11)+ RunInterface, implement functions for math.
12)+ RunSettings.java, adjust for math mode only. Yet without listeners.
13)+ ViewSettings.java, adjust for math mode only, current functionality. Yet without listeners.
14)+ Draw panel, for math.
15)+ Function select checkbox.
16)+ Move "Function" from controller to model.
17)+ Drawing window corruption by tool tips.
     Prevent always and full redraw because time delays ?
     Listener for tooltip or improve draw window redrawability ?
18)+ All supported widgets, by mouse. Include mark unsupported as gray.
19)+ Log panel, for math. Design it place in the scenario.
20)+ Table panel, for math. Design it place in the scenario.
21)+ Save text root menu item, design text file format, math mode edition.
     Verify 0Dh, 0Ah, "\r\n" or "\n".
22)+ Save graphics root menu item.

--- January 2019 ---

1)+  Math mode basics.

--- February 2019 ---

2)   File mode basics.
     RunSettings.java, lines 121, 270 to temporary lock file options,
     RunSettings.java, line 827 is current work.
     FileDataSource.java, line 62.
     Assign key for file data interpretion.
     X, F(X), Array X[i], Y[i].
     Handling for NaN.
     Required adaptive mode, FileDataSource.java line 75. Or save-load include statistics.

3)   JVM benchmark mode basics, required axis shift.
4)   Native benchmark mode basics.
5)   Re-design draw display window, axis shifts buttons.
6)   Re-design draw display window, Xmin, Xmax, Ymin, Ymax fields.

---

1)    Math mode precision issues, use BigDecimal if required.
2)    Math mode infinity issues, use BigDecimal if required.
3)    Redesign draw window, selectable base. "Redraw all" issues.

---

1)   Print and save graphics options.
2)   Divider location and size make changeable.
3)   GUI window resize bugs.
4)   Draw bugs, yet set not resizable.
5)   Use TAB-selected active widgets instead hotkeys.
     All supported widgets, by keyboard, resolve conflicts with default keys:
     cursor control, PgUp, PgDn, Home, End,
     remember many widgets, for example native mode, 
     don't use hotkeys for it, use TAB focus ?
6)   Adaptive drawings instead find maximum size (repeated item).

*/

package integrator.controller;

import integrator.view.*;
import integrator.view.DataTree.DataKeys;

public class Integrator 
{

private static Application application;
private static DataTree dataTree;
private static RunSettings runSettings;
private static Display display;
private static ViewSettings viewSettings;
private static TextLog textLog;
private static StatTable statTable;
private static About about;
private static RootMenu rootMenu;

public static Application getApplication()    { return application;  };
public static DataTree getDataTree()          { return dataTree;     };
public static RunSettings getRunSettings()    { return runSettings;  };
public static Display getDisplay()            { return display;      };
public static ViewSettings getViewSettings()  { return viewSettings; };
public static TextLog getTextLog()            { return textLog;      };
public static StatTable getStatTable()        { return statTable;    };
public static About getAbout()                { return about;        };
public static RootMenu getRootMenu()          { return rootMenu;     };

public static void setApplication( Application x )    { application = x;  };
public static void setDataTree( DataTree x )          { dataTree = x;     };
public static void setRunSettings( RunSettings x )    { runSettings = x;  };
public static void setDisplay( Display x )            { display = x;      };
public static void setViewSettings( ViewSettings x )  { viewSettings = x; };
public static void setTextLog( TextLog x )            { textLog = x;      };
public static void setStatTable( StatTable x )        { statTable = x;    };
public static void setAbout( About x )                { about = x;        };
public static void setRootMenu( RootMenu x )          { rootMenu = x;     };

private static RunInterface runInterface;
private static RunInterface ri1, ri2, ri3, ri4;
public static RunInterface getRunInterface() { return runInterface; }
public static void assignRunInterface( DataKeys x )
    {
    switch ( x )
        {
        case NATIVE:
            runInterface = ri4;
            break;
        case JVM:
            runInterface = ri3;
            break;
        case FILE:
            runInterface = ri2;
            break;
        case MATH:
        default:
            runInterface = ri1;
            break;
        }
    }

// application entry point means start controller ( as part of MVC pattern )
public static void main( String[] args ) 
    {
    // start model(s) by associated controller(s)
    ri1 = new MathRun();
    ri2 = new FileRun();
    ri3 = new JvmRun();
    ri4 = new NativeRun();
    assignRunInterface( DataKeys.MATH );
    // start view
    dataTree = new DataTree();
    runSettings = new RunSettings();
    display = new Display();
    viewSettings = new ViewSettings();
    textLog = new TextLog();
    statTable = new StatTable();
    about = new About();
    rootMenu = new RootMenu();
    application = new Application();   // this must be last, references ready
    }
}
