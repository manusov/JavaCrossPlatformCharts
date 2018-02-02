// Description for control buttons panel.
// JPanel class with additional function: store lists of JComponents.

package charts.controller;

import charts.controller.labels.LabelColorScheme;
import charts.controller.labels.LabelXmax;
import charts.controller.labels.LabelXmin;
import charts.controller.labels.LabelYmin;
import charts.controller.labels.LabelMathFunction;
import charts.controller.labels.LabelYmax;
import charts.controller.fields.FieldYmax;
import charts.controller.fields.FieldXmax;
import charts.controller.fields.FieldXmin;
import charts.controller.fields.FieldYmin;
import charts.controller.combo.ComboColorScheme;
import charts.controller.combo.ComboMathFunction;
import charts.controller.buttons.ButtonScaleDown;
import charts.controller.buttons.ButtonBaseDown;
import charts.controller.buttons.ButtonTabUp;
import charts.controller.buttons.ButtonScaleRight;
import charts.controller.buttons.ButtonTabDown;
import charts.controller.buttons.ButtonBaseLeft;
import charts.controller.buttons.ButtonBaseUp;
import charts.controller.buttons.ButtonBaseRight;
import charts.controller.buttons.ButtonReset;
import charts.controller.buttons.ButtonScaleLeft;
import charts.controller.buttons.ButtonScaleUp;
import javax.swing.JPanel;

public class KeyboardPanel extends JPanel
{
private final String[] flist;  // this for dynamicaly created list of functions
    
// constructor accepts dynamicaly created list of functions
public KeyboardPanel(String[] s)    
    {
    flist = s;
    }

// method get list of control buttons
public DescriptButton[] getButtonsList()
    {
    DescriptButton[] list = new DescriptButton[]
        {
        new ButtonBaseUp()     ,  // quad group 1
        new ButtonBaseDown()   ,
        new ButtonBaseLeft()   ,
        new ButtonBaseRight()  ,
        
        new ButtonScaleUp()    ,  // quad group 2
        new ButtonScaleDown()  ,
        new ButtonScaleLeft()  ,
        new ButtonScaleRight() ,
        
        new ButtonTabUp()      ,  // down group
        new ButtonTabDown()    ,
        new ButtonReset()
        };
    return list;
    }

// method get list of labels for combo boxes
public DescriptLabelConst[] getComboLabelsList()
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelColorScheme() ,
        new LabelMathFunction()
        };
    return list;
    }

// method get list of combo boxes
public DescriptCombo[] getComboList()
    {
    DescriptCombo[] list = new DescriptCombo[]
        {
        new ComboColorScheme() ,
        new ComboMathFunction(flist)
        };
    return list;    
    }

// method get list of labels for text input fields
public DescriptLabelConst[] getFieldsLabelsList()
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelXmin() ,
        new LabelXmax() ,
        new LabelYmin() ,
        new LabelYmax() 
        };
    return list;
    }

// method get list of text input fields
public DescriptField[] getFieldsList()
    {
    DescriptField[] list = new DescriptField[]
        {
        new FieldXmin() ,
        new FieldXmax() ,
        new FieldYmin() ,
        new FieldYmax()   
        };
    return list;
    }

}
