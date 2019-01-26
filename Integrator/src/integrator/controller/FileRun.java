/*

Class-controller for drawings file data scenario.
UNDER CONSTRUCTION.

*/

package integrator.controller;

import integrator.model.FileDataSource;

public class FileRun extends Scenario
{
public FileRun()
    {
    dataModel = new FileDataSource();
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
