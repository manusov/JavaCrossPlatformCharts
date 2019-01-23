/*

Fabric class for mathematics functions data source core.
Yet only one mode supported: DRAWS.SIMPLE.
TODO: 
Required 4 methods:
1) this SIMPLE method, data source = mathematics function calculation,
2) visual saved data, data source = text (typical) or binary file,
3) JVM real time benchmark, data source = executed JVM benchmark,
4) Native real time benchmark, data source = executed native application,
   this application writes text file (typical) or communicate by
   network sockets.

*/

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
