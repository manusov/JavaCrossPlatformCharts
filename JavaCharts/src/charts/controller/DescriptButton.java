// Abstract class for button description template.

package charts.controller;

import charts.model.FunctionCore;
import java.awt.Color;
import java.awt.Font;

public abstract class DescriptButton 
{
// constants for GUI customization
protected final static Color CLR_CUSTOM_COLOR = Color.RED;

protected final static Font  CLR_CUSTOM_FONT  = 
            new Font("Verdana", Font.PLAIN, 13);

protected final static Font  SMALL_CUSTOM_FONT  = 
             new Font("Verdana", Font.PLAIN, 11);

// public abstract methods
public abstract String getName();  // get string for button name
public abstract String getText();  // get string for tooltips output
public abstract int[] getKeys();   // get keyboard keys for press this button
// public optional supported methods
public Color getCustomColor() { return null; }  // get custom color for button
public Font getCustomFont()   { return null; }  // get custom font for button
// public abstract method: operation when press buttons
public abstract void buttonAction(FunctionCore fc);
}
