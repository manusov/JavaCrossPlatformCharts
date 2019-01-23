/*

Combo box description class: select function.
This combo box selects mathematics function for visualization.

*/

package charts.controller.combo;

import charts.controller.DescriptCombo;
import charts.model.FunctionCore;
import java.awt.event.KeyEvent;

public class ComboMathFunction extends DescriptCombo 
{
// this default list replaced by real math functions list, by class constructor
private static String[] valuesList = 
    { "test1" , "test2" };

// method for update dynamically created math. functions list
public ComboMathFunction(String[] s)
    {
    valuesList = s;
    }

private final static String TEXT    = "select function, M key";
private final static int[]  KEYS    = { KeyEvent.VK_M };
@Override public String[] getValues()  { return valuesList; }
@Override public String getText()      { return TEXT;   }
@Override public int[] getKeys()       { return KEYS;   }
@Override public void comboAction( FunctionCore fc, int index )
    {
    fc.sendFunction(index);
    }
}
