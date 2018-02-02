// Button description class: increase vertical scale.

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonScaleUp extends DescriptButton
{
private final static String NAME = "scale Y+";               // "<html>&#8597";
private final static String TEXT = "increase vertical scale";
private final static int[]  KEYS = { KeyEvent.VK_PAGE_UP };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.Y_SCALE_INC ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
