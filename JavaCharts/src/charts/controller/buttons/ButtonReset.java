// Button description class: clear button, set defaults.

package charts.controller.buttons;

import charts.controller.DescriptButton;
import charts.model.FunctionCore;
import charts.model.FunctionCore.CONTROLS;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ButtonReset extends DescriptButton
{
private final static String NAME = "Reset";
private final static String TEXT = "restore default function parameters";
private final static int[]  KEYS = { KeyEvent.VK_ESCAPE };
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
@Override public int[] getKeys()  { return KEYS; }
@Override public void buttonAction(FunctionCore fc)
        { fc.sendControl( CONTROLS.RESET ); }
// optional custom color and font for this button
@Override public Color getCustomColor() { return CLR_CUSTOM_COLOR; }
@Override public Font getCustomFont()   { return CLR_CUSTOM_FONT; }
}
