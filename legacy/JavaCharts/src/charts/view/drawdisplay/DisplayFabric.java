/*

Fabric class for build drawings display panel.
Yet only one mode supported: DRAWS.SIMPLE,
for this mode, data source = calculate mathematics functions,
alternative variants is: data file, real-time JVM benchmarks,
real-time native benchmarks.

*/

package charts.view.drawdisplay;

import charts.Charts.DRAWS;

public class DisplayFabric 
{

public static DisplayPanel[] getDisplayPanels(DRAWS type)
    {
    DisplayPanel[] displayPanels;
    DisplayPanel dp;
    
    switch(type)
        {
        //
        // Reserved for add new advanced modes
        //
        case SIMPLE:
        default:
            { 
            dp = new DisplayPanel(); 
            break;
            }
        }
    
    displayPanels = new DisplayPanel[1];  // array with one element
    displayPanels[0] = dp;                // set element
    return displayPanels;                 // required return array
    }    
    
}
