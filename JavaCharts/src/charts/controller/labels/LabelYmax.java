// Label description class: select maximum Y.
// This label is name of field, located near associated text input field.

package charts.controller.labels;

import charts.controller.DescriptLabelConst;

public class LabelYmax extends DescriptLabelConst
{
private final static String NAME = "Ymax";
private final static String TEXT = "function Y=F(X) view up";
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT;   }
}
