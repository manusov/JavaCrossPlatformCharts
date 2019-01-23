/*

Abstract class for combo box description template.
This is template for real combo boxes descriptions.

*/

package integrator.view;

import integrator.controller.RunInterface;

public abstract class DescriptCombo 
{
public abstract String[] getValues(); // get string for combo name
public abstract String getText();     // get string for tooltips output
public abstract int[] getKeys();      // get keyboard keys for click this combo
// action handler
public abstract void comboAction( RunInterface ri, int index );
}
