// Label description class: select minimum Y.
// This label is name of field, located near associated text input field.

package charts.controller.labels;

import charts.controller.DescriptLabelConst;

public class LabelYmin extends DescriptLabelConst
{
private final static String NAME = "Ymin";
private final static String TEXT = "function Y=F(X) view down";
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
}
