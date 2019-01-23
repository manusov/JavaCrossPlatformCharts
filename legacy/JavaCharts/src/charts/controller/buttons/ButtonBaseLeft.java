/*

Button description class: move left, X-, shift Y-axis left.
This button scrolls view field left, yet not supported.

*/

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonBaseLeft extends DescriptButton
{
private final static String NAME = "X-";                 // "<html>&#8592";
private final static String TEXT = "move left";          // "move Y-axis left";
private final static int[]  KEYS = { KeyEvent.VK_LEFT };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.Y_AXIS_LEFT ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
