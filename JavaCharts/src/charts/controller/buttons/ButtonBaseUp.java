// Button description class: move down, Y+, shift X-axis up.

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonBaseUp extends DescriptButton
{
private final static String NAME = "base Y+";              // "<html>&#8593";
private final static String TEXT = "move up";              // "move X-axis up";
private final static int[]  KEYS = { KeyEvent.VK_UP };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.X_AXIS_UP ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
