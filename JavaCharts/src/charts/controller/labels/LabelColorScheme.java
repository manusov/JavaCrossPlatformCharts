/*

Label description class: select color scheme.
This label is name of color selection combo box, 
this label located near associated combo box.

*/

package charts.controller.labels;

import charts.controller.DescriptLabelConst;

public class LabelColorScheme extends DescriptLabelConst
{
private final static String NAME = "Color scheme";
private final static String TEXT = "select color scheme for graph, C key";
@Override public String getName() { return NAME; }
@Override public String getText() { return TEXT; }
}
