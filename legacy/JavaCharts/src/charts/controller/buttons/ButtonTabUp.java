/*

Button description class: increase tabulation step.
This button increments number of function tabulation steps per interval,
increase function visualization quality.

*/

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.event.KeyEvent;

public class ButtonTabUp extends DescriptButton
{
private final static String NAME = "Steps +";
private final static String TEXT = "increase function tabulation quality";
private final static int[]  KEYS = { KeyEvent.VK_ADD };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.TAB_INC ); }
}
