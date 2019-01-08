/*

Abstract class for text field description template.
This is template for real text fields descriptions.

*/

package charts.controller;

import charts.model.FunctionCore;

public abstract class DescriptField 
{
public abstract String getValue();         // get field string
public abstract void setValue(String s);   // set field string
public abstract int getSize();             // get maximum number of chars
public abstract String getText();          // get string for tooltips output
public abstract int[] getKeys();           // get keyboard keys for click
public abstract void fieldAction(FunctionCore fc);  // action handler
}
