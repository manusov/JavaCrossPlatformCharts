// Description class for text label.
// This variant ( 2 of 2) for changeable texts, 
// see also DescriptLabelConst.java.

package charts.view;

public abstract class DescriptLabel 
{
public abstract String getName();          // get label string
public abstract void setName(String s);    // set label string
public abstract String getText();          // get string for tooltips output
public abstract void setText(String s);    // set string for tooltips output
}
