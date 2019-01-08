/*

Description class for changeable status label #1.
See detail comments about methods
in the abstract parent class DescriptLabel.java.

*/

package charts.view.labels;

import charts.view.DescriptLabel;

public class LabelText1 extends DescriptLabel
{
private String name = "n/a";
private String text = "Function description";
@Override public String getName()        { return name; }
@Override public void setName(String s)  { name = s;    }
@Override public String getText()        { return text; }
@Override public void setText(String s)  { text = s;    }
}
