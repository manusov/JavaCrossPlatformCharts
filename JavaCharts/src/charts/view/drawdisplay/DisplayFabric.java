// Fabric class for build drawings display panel.
// Yet only one mode supported: DRAWS.SIMPLE.

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
