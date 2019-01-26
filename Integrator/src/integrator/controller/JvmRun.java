/*

Class-controller for JVM benchmarks scenario.
UNDER CONSTRUCTION.

*/

package integrator.controller;

import integrator.model.JvmDataSource;

public class JvmRun extends Scenario
{
public JvmRun()
    {
    dataModel = new JvmDataSource();
    if ( dataModel != null )
        {
        functionDefaults = dataModel.getUnifiedDefaults();
        if ( ( functionDefaults != null ) && 
             ( dataModel.getFunctionsList() != null ) )
            {
            selectedFunction = dataModel.getFunctionsList()[0];
            initParms();
            if ( selectedFunction != null )
                {
                initFunction( selectedFunction );
                }
            }
        }
    }
}
