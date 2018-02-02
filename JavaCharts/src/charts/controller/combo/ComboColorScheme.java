// Combo box description class: select color scheme.

package charts.controller.combo;

import charts.controller.DescriptCombo;
import charts.model.FunctionCore;
import charts.model.ViewerState;
import charts.model.ViewerState.GCOLOR;
import java.awt.event.KeyEvent;

public class ComboColorScheme extends DescriptCombo 
{
private final static String[] VALUES = { "Dark for screen" , 
                                         "White for print" };
private final static String TEXT     = "select color scheme for graph, C key";
private final static int[]  KEYS     = { KeyEvent.VK_C };
@Override public String[] getValues()  { return VALUES; }
@Override public String getText()      { return TEXT;   }
@Override public int[] getKeys()       { return KEYS;   }
@Override public void comboAction( FunctionCore fc, int index ) 
    {
    GCOLOR[] values = GCOLOR.values();
    int n = values.length;
    if ( index > n ) index = n;
    GCOLOR value = values[index];
    ViewerState vs = fc.getViewerState();
    if ( vs != null ) vs.setColorScheme(value);
    }
}
