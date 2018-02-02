// Button description class: decrease horizontal scale.

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonScaleLeft extends DescriptButton
{
private final static String NAME = "X-";                    // "<html>&#8596";
private final static String TEXT = "decrease horizontal scale";
private final static int[]  KEYS = { KeyEvent.VK_HOME };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.X_SCALE_DEC ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
