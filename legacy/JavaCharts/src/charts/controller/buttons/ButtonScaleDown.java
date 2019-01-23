/*

Button description class: decrease vertical scale.
This buttons decrements ratio: value per vertical interval.

*/

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonScaleDown extends DescriptButton
{
private final static String NAME = "Y-";                     // "<html>&#8597";
private final static String TEXT = "decrease vertical scale";
private final static int[]  KEYS = { KeyEvent.VK_PAGE_DOWN };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.Y_SCALE_DEC ); }
// optional custom font for this button
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}
