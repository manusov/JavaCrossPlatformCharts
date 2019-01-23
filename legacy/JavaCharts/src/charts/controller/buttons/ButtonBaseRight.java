/*

Button description class: move right, X+, shift Y-axis right.
This button scrolls view field right, yet not supported.

*/

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonBaseRight extends DescriptButton
{
private final static String NAME = "X+";                // "<html>&#8594";
private final static String TEXT = "move right";        // "move Y-axis right";
private final static int[]  KEYS = { KeyEvent.VK_RIGHT };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.Y_AXIS_RIGHT ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
