/*

Label description class: select mathematics function.
This label is name of math. function selection combo box,
this label located near associated combo box.

*/

package charts.controller.labels;

import charts.controller.DescriptLabelConst;

public class LabelMathFunction extends DescriptLabelConst
{
private final static String NAME = "Math function";
private final static String TEXT = "select function, M key";
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
}
