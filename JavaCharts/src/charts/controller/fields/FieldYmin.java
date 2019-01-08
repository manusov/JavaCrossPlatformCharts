/*

Text field description class: input minimum Y value.
This text field used for select function draw Y minimum (down),
yet not supported.

*/

package charts.controller.fields;

import charts.controller.DescriptField;
import charts.model.FunctionCore;
import java.awt.event.KeyEvent;

public class FieldYmin extends DescriptField
{
private String value = "";  // " -1.0";
private final static String TEXT = "function Y=F(X) view down";
private final static int SIZE = 10;
private final static int[]  KEYS = { KeyEvent.VK_Y };
@Override public String getValue()       { return value;  }
@Override public void setValue(String s) { value = s;     }
@Override public int getSize()           { return SIZE;   }
@Override public String getText()        { return TEXT;   }
@Override public int[] getKeys()         { return KEYS;   }
@Override public void fieldAction(FunctionCore fc) { }
}
