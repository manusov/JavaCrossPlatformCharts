// Label description class: select minimum X.
// This label is name of field, located near associated text input field.

package charts.controller.labels;

import charts.controller.DescriptLabelConst;

public class LabelXmin extends DescriptLabelConst
{
private final static String NAME = "Xmin";
private final static String TEXT = "argument X start value";
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT;   }
}
