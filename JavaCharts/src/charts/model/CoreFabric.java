// Fabric class for calculator core.
// Yet only one mode supported: DRAWS.SIMPLE.

package charts.model;

import charts.Charts.DRAWS;

public class CoreFabric 
{

public static FunctionCore getFunctionCore(DRAWS type)
    {
    FunctionCore functionCore;
    switch(type)
        {
        //
        // Reserved for add new advanced modes
        //
        case SIMPLE:
        default:
            { 
            functionCore = new FunctionCore();
            functionCore.getViewerState().init();
            functionCore.sendFunction(0);
            break;
            }
        }
    return functionCore;
    }    
    
}
