/*

Class-controller for mathematics function draw scenario.

*/

package integrator.controller;

import integrator.model.MathDataSource;

public class MathRun extends Scenario
{
public MathRun()
    {
    dataModel = new MathDataSource();
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

