/*

Text field description class: input minimum X value.
This text field used for select function draw X minimum (left),
yet not supported.

*/

package charts.controller.fields;

import charts.controller.DescriptField;
import charts.model.FunctionCore;
import java.awt.event.KeyEvent;

public class FieldXmin extends DescriptField
{
private String value = "";  // " -1.0";
private final static String TEXT = "argument X start value";
private final static int SIZE = 10;
private final static int[]  KEYS = { KeyEvent.VK_X };
@Override public String getValue()       { return value;  }
@Override public void setValue(String s) { value = s;     }
@Override public int getSize()           { return SIZE;   }
@Override public String getText()        { return TEXT;   }
@Override public int[] getKeys()         { return KEYS;   }
@Override public void fieldAction(FunctionCore fc) { }
}
