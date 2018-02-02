// Label description class: select maximum X.
// This label is name of field, located near associated text input field.

package charts.controller.labels;

import charts.controller.DescriptLabelConst;

public class LabelXmax extends DescriptLabelConst
{
private final static String NAME = "Xmax";
private final static String TEXT = "argument X end value";
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
}
