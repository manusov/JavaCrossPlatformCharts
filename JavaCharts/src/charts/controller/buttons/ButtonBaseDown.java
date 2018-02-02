// Button description class: move down, Y-, shift X-axis down.

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonBaseDown extends DescriptButton
{
private final static String NAME = "Y-";                 // "<html>&#8595";
private final static String TEXT = "move down";          // "move X-axis down";
private final static int[]  KEYS = { KeyEvent.VK_DOWN };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.X_AXIS_DOWN ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
