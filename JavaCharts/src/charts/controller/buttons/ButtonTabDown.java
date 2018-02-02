// Button description class: decrease tabulation step.

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.event.KeyEvent;

public class ButtonTabDown extends DescriptButton
{
private final static String NAME = "Steps -";
private final static String TEXT = "decrease function tabulation quality";
private final static int[]  KEYS = { KeyEvent.VK_SUBTRACT };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.TAB_DEC ); }
}
