/*

Class-controller for native application benchmarks scenario.
UNDER CONSTRUCTION.

*/

package integrator.controller;

import integrator.model.NativeDataSource;

public class NativeRun extends Scenario
{
public NativeRun()
    {
    dataModel = new NativeDataSource();
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
