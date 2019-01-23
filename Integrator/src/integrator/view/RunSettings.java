/*

Run settings window (left down) class:
1) Builder.
2) Components lists.
3) Events listeners.
4) Window logic.

*/

package integrator.view;

import integrator.model.Function;
import integrator.controller.Integrator;
import integrator.controller.RunInterface;
import integrator.controller.RunInterface.Controls;
import integrator.view.DataTree.DataKeys;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class RunSettings
{
private final int X = 220, Y = 450;
private JPanel panel;
private final JPanel[] p = new JPanel[4];
private DataKeys dataKey;
public JPanel getPanel() { return panel; }

public RunSettings()
    {
    p[0] = new RunSettingsPanelMath();
    p[1] = new RunSettingsPanelFile();
    p[2] = new RunSettingsPanelJvm();
    p[3] = new RunSettingsPanelNative();
    for( int i=0; i<4; i++ )
        {
        p[i].setPreferredSize( new Dimension( X, Y ) );
        ( (OptionPanel)p[i] ).addComponents();
        }

    panel = p[0];
    }

public void setDataKey( DataKeys x )
    {
    dataKey = x;
    switch( dataKey )
        {
        case NATIVE:
            panel = p[3];
            break;
        case JVM:
            panel = p[2];
            break;
        case FILE:
            panel = p[1];
            break;
        case MATH:
        default:
            panel = p[0];
            break;
        }
    }
}

// classes for panels, 4 run modes supported

class RunSettingsPanelMath extends OptionPanel
{
@Override public DescriptButton[] getButtonsList()
    {
    DescriptButton[] list = new DescriptButton[]
        {
        new ButtonMathRun()
        };
    return list;
    }
@Override public DescriptLabelConst[] getComboLabelsList()
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelMathFunction()
        };
    return list;
    }
@Override public DescriptCombo[] getComboList()
    {
    DescriptCombo[] list = new DescriptCombo[]
        {
        new ComboMathFunction()
        };
    return list;    
    }
@Override public DescriptLabelConst[] getFieldsLabelsList() { return null; }
@Override public DescriptField[] getFieldsList()            { return null; }
@Override public void dataChanged() { }
@Override public void layoutComponents
    ( JLabel[] cl, JComboBox[] c, JLabel[] fl, JTextField[] f, JButton[] b )
    {
    // layout manager
    SpringLayout s = new SpringLayout();
    JPanel panel = this;
    panel.setLayout( s );
    // layout for associated labels and combo boxes
    layoutSingleBig( s, panel, cl[0], 0 );
    layoutSingleBig( s, panel, c[0],  23 );
    // layout for buttons
    layoutSingleSmall( s, panel, b[0], 55 );
    // THIS BECAUSE UNDER CONSTRUCTION
    b[0].setEnabled( false );
    // THIS BECAUSE UNDER CONSTRUCTION
    }
private static void layoutSingleBig
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 125 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleSmall
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 105 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
@Override public AbstractAction getKeyAction()
    {
    return new AbstractAction()
        { @Override public void actionPerformed( ActionEvent e ) 
            { 
            }
        }; 
    }
@Override public ActionListener getCombosListener()
    {
    return ( ActionEvent e ) -> 
        {
        // command = button ID as string
        String command = e.getActionCommand();
        // selector = button ID as integer number, converted from string
        int selector, index = 0;
        if ( ( command != null ) && ( command.length() >= 1 ) )
            {
            try {
                selector = Integer.decode( command );
                }
            catch ( Exception ex )  // if error when convert string to number
                {
                selector = -1;    // force wrong combo ID if error
                }
            Object o = e.getSource();
            if ( o instanceof JComboBox )
                {  // get selected item number
                index = ( (JComboBox)o ).getSelectedIndex();
                }
            RunInterface ri = Integrator.getRunInterface();
            if ( (selector >= 0) && ( selector < getComboList().length ) &&
                 ( index >= 0 ) && ( ri != null ) )
                {
                // make action
                if ( selector < getComboList().length )
                    {
                    getComboList()[selector].comboAction( ri, index );
                    }
                // refresh GUI = f ( action results )
                // refresh function Y=F(X) draw window
                JPanel p = Integrator.getDisplay().getPanel();
                p.repaint();
                // refresh view settings buttons state: gray or active
                p = Integrator.getViewSettings().getPanel();
                if ( p instanceof OptionPanel )
                    {
                    ( (OptionPanel)p ).dataChanged();
                    p.repaint();
                    }
                // refresh text log, center down left window
                p = Integrator.getTextLog().getPanel();
                if ( p instanceof LogPanel )
                    {
                    ( (LogPanel)p ).dataChanged();
                    p.repaint();
                    }
                // refresh statistics table, center down right window
                p = Integrator.getStatTable().getPanel();
                if ( p instanceof LogPanel )
                    {
                    ( (LogPanel)p ).dataChanged();
                    p.repaint();
                    }
                }
            }
        };
    }
@Override public ActionListener getFieldsListener() { return null; }
@Override public ActionListener getButtonsListener()
    {
    return ( ActionEvent e ) -> 
        {
        };
    }
}

class RunSettingsPanelFile extends OptionPanel
{
@Override public DescriptButton[] getButtonsList()
    {
    DescriptButton[] list = new DescriptButton[]
        {
        new ButtonFileLoad() ,
        new ButtonFileRun()
        };
    return list;
    }
@Override public DescriptLabelConst[] getComboLabelsList()  { return null; }
@Override public DescriptCombo[] getComboList()             { return null; }
@Override public DescriptLabelConst[] getFieldsLabelsList() { return null; }
@Override public DescriptField[] getFieldsList()            { return null; }
@Override public void dataChanged() { }
@Override public void layoutComponents
    ( JLabel[] cl, JComboBox[] c, JLabel[] fl, JTextField[] f, JButton[] b )
    {
    // layout manager
    SpringLayout s = new SpringLayout();
    JPanel panel = this;
    panel.setLayout( s );
    // layout for buttons
    layoutSingleSmall( s, panel, b[0],  3 );
    layoutSingleSmall( s, panel, b[1], 33 );
    // BECAUSE UNDER CONSTRUCTION
    b[0].setEnabled(false);
    b[1].setEnabled(false);
    // BECAUSE UNDER CONSTRUCTION
    }
private static void layoutSingleSmall
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 105 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
@Override public AbstractAction getKeyAction()
    {
    return new AbstractAction()
        { @Override public void actionPerformed( ActionEvent e ) 
            { 
            }
        }; 
    }
@Override public ActionListener getCombosListener() { return null; }
@Override public ActionListener getFieldsListener() { return null; }
@Override public ActionListener getButtonsListener()
    {
    return ( ActionEvent e ) -> 
        {
        };
    }
}

class RunSettingsPanelJvm extends OptionPanel
{
    @Override public DescriptButton[] getButtonsList()
    {
    DescriptButton[] list = new DescriptButton[]
        {
        new ButtonJvmRun()
        };
    return list;
    }
@Override public DescriptLabelConst[] getComboLabelsList()
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelJvmArray(),
        new LabelJvmThreads(),
        new LabelJvmRepeats(),
        new LabelJvmOperation(),
        new LabelJvmOperand()
        };
    return list;
    }
@Override public DescriptCombo[] getComboList()
    {
    DescriptCombo[] list = new DescriptCombo[]
        {
        new ComboJvmArray(),
        new ComboJvmThreads(),
        new ComboJvmRepeats(),
        new ComboJvmOperation(),
        new ComboJvmOperand()
        };
    return list;    
    }
@Override public DescriptLabelConst[] getFieldsLabelsList() { return null; }
@Override public DescriptField[] getFieldsList()            { return null; }
@Override public void dataChanged() { }
@Override public void layoutComponents
    ( JLabel[] cl, JComboBox[] c, JLabel[] fl, JTextField[] f, JButton[] b )
    {
    // layout manager
    SpringLayout s = new SpringLayout();
    JPanel panel = this;
    panel.setLayout( s );
    // layout for labels
    int ncl = getComboLabelsList().length;
    int shift = 2;
    for( int i=0; i<ncl; i++ )
        {
        layoutSingleLabels( s, panel, cl[i], shift );
        shift += 29;
        // BECAUSE UNDER CONSTRUCTION
        cl[i].setEnabled(false);
        // BECAUSE UNDER CONSTRUCTION
        }
    int nc = getComboList().length;
    shift = 2;
    for( int i=0; i<nc; i++ )
        {
        layoutSingleCombos( s, panel, c[i], shift );
        shift += 29;
        // BECAUSE UNDER CONSTRUCTION
        c[i].setEnabled(false);
        // BECAUSE UNDER CONSTRUCTION
        }
    int nb = getButtonsList().length;
    shift += 16;
    for( int i=0; i<nb; i++ )
        {
        layoutSingleButtons( s, panel, b[i], shift );
        shift += 32;
        // BECAUSE UNDER CONSTRUCTION
        b[i].setEnabled(false);
        // BECAUSE UNDER CONSTRUCTION
        }
    }
private static void layoutSingleLabels
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 105 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleCombos
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c, 72  , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 217 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleButtons
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 115 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
@Override public AbstractAction getKeyAction()
    {
    return new AbstractAction()
        { @Override public void actionPerformed( ActionEvent e ) 
            { 
            }
        }; 
    }
@Override public ActionListener getCombosListener()
    {
    return ( ActionEvent e ) -> 
        {
        };
    }
@Override public ActionListener getFieldsListener() { return null; }
@Override public ActionListener getButtonsListener()
    {
    return ( ActionEvent e ) -> 
        {
        };
    }
}

class RunSettingsPanelNative extends OptionPanel
{
    @Override public DescriptButton[] getButtonsList()
    {
    DescriptButton[] list = new DescriptButton[]
        {
        new ButtonNativeRun()
        };
    return list;
    }
@Override public DescriptLabelConst[] getComboLabelsList()
    {
    DescriptLabelConst[] list = new DescriptLabelConst[]
        {
        new LabelNativeApplication(),
        new LabelNativeAction(),
        new LabelNativeTest(),
        new LabelNativeMemory(),
        new LabelNativeAsm(),
        new LabelNativeThreads(),
        new LabelNativeHt(),
        new LabelNativeNuma(),
        new LabelNativePage(),
        new LabelNativeRepeats(),
        new LabelNativeAdaptive(),
        new LabelNativeStart(),
        new LabelNativeEnd(),
        new LabelNativeStep()
        };
    return list;
    }
@Override public DescriptCombo[] getComboList()
    {
    DescriptCombo[] list = new DescriptCombo[]
        {
        new ComboNativeApplication(),
        new ComboNativeAction(),
        new ComboNativeTest(),
        new ComboNativeMemory(),
        new ComboNativeAsm(),
        new ComboNativeThreads(),
        new ComboNativeHt(),
        new ComboNativeNuma(),
        new ComboNativePage(),
        new ComboNativeRepeats(),
        new ComboNativeAdaptive(),
        new ComboNativeStart(),
        new ComboNativeEnd(),
        new ComboNativeStep()
        };
    return list;    
    }
@Override public DescriptLabelConst[] getFieldsLabelsList() { return null; }
@Override public DescriptField[] getFieldsList()            { return null; }
@Override public void dataChanged() { }
@Override public void layoutComponents
    ( JLabel[] cl, JComboBox[] c, JLabel[] fl, JTextField[] f, JButton[] b )
    {
    // layout manager
    SpringLayout s = new SpringLayout();
    JPanel panel = this;
    panel.setLayout( s );
        // layout for labels
    int ncl = getComboLabelsList().length;
    int shift = 2;
    for( int i=0; i<ncl; i++ )
        {
        layoutSingleLabels( s, panel, cl[i], shift );
        shift += 29;
        // BECAUSE UNDER CONSTRUCTION
        cl[i].setEnabled(false);
        // BECAUSE UNDER CONSTRUCTION
        }
    int nc = getComboList().length;
    shift = 2;
    for( int i=0; i<nc; i++ )
        {
        layoutSingleCombos( s, panel, c[i], shift );
        shift += 29;
        // BECAUSE UNDER CONSTRUCTION
        c[i].setEnabled(false);
        // BECAUSE UNDER CONSTRUCTION
        }
    int nb = getButtonsList().length;
    shift += 9;
    for( int i=0; i<nb; i++ )
        {
        layoutSingleButtons( s, panel, b[i], shift );
        shift += 32;
        // BECAUSE UNDER CONSTRUCTION
        b[i].setEnabled(false);
        // BECAUSE UNDER CONSTRUCTION
        }
    }
private static void layoutSingleLabels
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 105 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleCombos
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c, 72  , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 217 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
private static void layoutSingleButtons
        ( SpringLayout s, JPanel p, JComponent c, int v )
    {
    s.putConstraint( SpringLayout.NORTH, c, v+4 , SpringLayout.NORTH, p );
    s.putConstraint( SpringLayout.WEST , c,   3 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.EAST , c, 115 , SpringLayout.WEST , p );
    s.putConstraint( SpringLayout.SOUTH, c, v+30, SpringLayout.NORTH, p );
    }
@Override public AbstractAction getKeyAction()
    {
    return new AbstractAction()
        { @Override public void actionPerformed( ActionEvent e ) 
            { 
            }
        }; 
    }
@Override public ActionListener getCombosListener()
    {
    return ( ActionEvent e ) -> 
        {
        };
    }
@Override public ActionListener getFieldsListener() { return null; }
@Override public ActionListener getButtonsListener()
    {
    return ( ActionEvent e ) -> 
        {
        };
    }
}


// classes for buttons, labels, text fields, combo boxes, 4 run modes supported
// 1 of 4 = mathematics functions mode

class ButtonMathRun extends DescriptButton
{
@Override public String getName() { return "Run"; }
@Override public String getText() { return "run function revisual"; }  // , ENTER key"; }
@Override public int[] getKeys()  
    { return null; }  // new int[] { KeyEvent.VK_ENTER }; }
@Override public void buttonAction( RunInterface ri )
    { ri.sendControl( Controls.RUN ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class LabelMathFunction extends DescriptLabelConst
{
@Override public String getName() { return "Function Y=F(X)"; }
@Override public String getText() { return "select function Y=F(X)"; }  // , M key"; }
}

class ComboMathFunction extends DescriptCombo 
{
private String[] valuesList;
public ComboMathFunction()
    {
    valuesList = new String[] { "?" };
    Function[] f = Integrator.getRunInterface().getFunctionsList();
    if ( f != null )
        {
        int n = f.length;
        if ( n > 0 )
            {
            valuesList = new String[n];
            for( int i=0; i<n; i++ )
                {
                valuesList[i] = f[i].getNameY();
                }
            }
        }
    }
@Override public String[] getValues()  { return valuesList; }
@Override public String getText()      { return "select function"; }  // , M key"; }
@Override public int[] getKeys()       { return null; }  // new int[] { KeyEvent.VK_M }; }
@Override public void comboAction( RunInterface ri, int index )
    { 
    ri.sendFunction( index );
    ri.sendControl( Controls.RESET );
    }
}

// classes for buttons, labels, text fields, combo boxes, 4 run modes supported
// 2 of 4 = visual data from text file

class ButtonFileLoad extends DescriptButton
{
@Override public String getName() { return "Load"; }
@Override public String getText() { return "load text data file"; } // , L key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_ENTER }; }
@Override public void buttonAction( RunInterface ri )
        { ri.sendControl( Controls.RUN ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class ButtonFileRun extends DescriptButton
{
@Override public String getName() { return "Run"; }
@Override public String getText() { return "show function"; }  // , ENTER key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_ENTER }; }
@Override public void buttonAction( RunInterface ri )
        { ri.sendControl( Controls.RUN ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

// classes for buttons, labels, text fields, combo boxes, 4 run modes supported
// 3 of 4 = JVM mathematics operations benchmarks

class ButtonJvmRun extends DescriptButton
{
@Override public String getName() { return "Run"; }
@Override public String getText() { return "run JVM benchmarks"; }  // , ENTER key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_ENTER }; }
@Override public void buttonAction( RunInterface ri )
        { ri.sendControl( Controls.RUN ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class LabelJvmArray extends DescriptLabelConst
{
@Override public String getName() { return "Array"; }
@Override public String getText() 
    { return "select array size for benchmarks"; }  // , A key"; }
}

class LabelJvmThreads extends DescriptLabelConst
{
@Override public String getName() { return "Threads"; }
@Override public String getText() 
    { return "number of execution threads"; }  //, T key"; }
}

class LabelJvmRepeats extends DescriptLabelConst
{
@Override public String getName() { return "Repeats"; }
@Override public String getText() 
    { return "number of measurement repeats"; }  //, R key"; }
}

class LabelJvmOperation extends DescriptLabelConst
{
@Override public String getName() { return "Operation"; }
@Override public String getText() 
    { return "mathematics operation select"; }  //, O key"; }
}

class LabelJvmOperand extends DescriptLabelConst
{
@Override public String getName() { return "Operand"; }
@Override public String getText() 
    { return "floating-point operand width"; }  //, P key"; }
}

class ComboJvmArray extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "select array size for benchmarks"; }  //, A key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_A  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboJvmThreads extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "number of threads for benchmarks"; }  //, T key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_T  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboJvmRepeats extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "number of measurement repeats"; }  //, R key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_R  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboJvmOperation extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "mathematics operation select"; }  //, O key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_O  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboJvmOperand extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "floating point operand width select"; }  //, P key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_P  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

// classes for buttons, labels, text fields, combo boxes, 4 run modes supported
// 4 of 4 = native operations benchmarks

class ButtonNativeRun extends DescriptButton
{
@Override public String getName() { return "Run"; }
@Override public String getText() { return "run native benchmarks"; }  //, ENTER key"; }
@Override public int[] getKeys()  { return null; }  // new int[] { KeyEvent.VK_ENTER }; }
@Override public void buttonAction( RunInterface ri )
        { ri.sendControl( Controls.RUN ); }
@Override public Font getCustomFont()  { return SMALL_CUSTOM_FONT; }
}

class LabelNativeApplication extends DescriptLabelConst
{
@Override public String getName() { return "Application"; }
@Override public String getText() 
    { return "native application 32 or 64 bit, A key"; }
}

class LabelNativeAction extends DescriptLabelConst
{
@Override public String getName() { return "Action"; }
@Override public String getText() 
    { return "select help, sysinfo or benchmarks"; }  //, T key"; }
}

class LabelNativeTest extends DescriptLabelConst
{
@Override public String getName() { return "Test object"; }
@Override public String getText() 
    { return "select memory or storage test"; }  //, O key"; }
}

class LabelNativeMemory extends DescriptLabelConst
{
@Override public String getName() { return "Memory"; }
@Override public String getText() 
    { return "select memory. L1-L4, DRAM"; }  //, M key"; }
}

class LabelNativeAsm extends DescriptLabelConst
{
@Override public String getName() { return "Assembler"; }
@Override public String getText() 
    { return "select CPU instruction set"; }  //, S key"; }
}

class LabelNativeThreads extends DescriptLabelConst
{
@Override public String getName() { return "Threads"; }
@Override public String getText() 
    { return "set number of threads for benchmark"; }  //, R key"; }
}

class LabelNativeHt extends DescriptLabelConst
{
@Override public String getName() { return "HT"; }
@Override public String getText() 
    { return "set hyper-threading option"; }  //, H key"; }
}

class LabelNativeNuma extends DescriptLabelConst
{
@Override public String getName() { return "NUMA"; }
@Override public String getText() 
    { return "set NUMA optimization option"; }  //, N key"; }
}

class LabelNativePage extends DescriptLabelConst
{
@Override public String getName() { return "Page"; }
@Override public String getText() 
    { return "set paging option, normal or large"; }  //, P key"; }
}

class LabelNativeRepeats extends DescriptLabelConst
{
@Override public String getName() { return "Repeats"; }
@Override public String getText() 
    { return "set benchmarks measurement repeats"; }  //, E key"; }
}

class LabelNativeAdaptive extends DescriptLabelConst
{
@Override public String getName() { return "Adaptive"; }
@Override public String getText() 
    { return "set adaptive measurement repeats"; }  //, D key"; }
}

class LabelNativeStart extends DescriptLabelConst
{
@Override public String getName() { return "Block start"; }
@Override public String getText() 
    { return "set start block size"; }  //, B key"; }
}

class LabelNativeEnd extends DescriptLabelConst
{
@Override public String getName() { return "Block end"; }
@Override public String getText() 
    { return "set end block size"; }  //, L key"; }
}

class LabelNativeStep extends DescriptLabelConst
{
@Override public String getName() { return "Block step"; }
@Override public String getText() 
    { return "set block size step"; }  //, K key"; }
}

class ComboNativeApplication extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "native application 32 or 64 bit"; }  //, A key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_A  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeAction extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "select help, sysinfo or benchmarks"; }  //, T key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_T  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeTest extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "select memory or storage test"; }  //, O key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_O  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeMemory extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "select memory. L1-L4, DRAM"; }  //, M key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_M  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeAsm extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "select CPU instruction set"; }  //, S key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_S  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeThreads extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set number of threads for benchmark"; }  //, R key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_R  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeHt extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set hyper-threading option"; }  //, H key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_H  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeNuma extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set NUMA optimization option"; }  //, N key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_N  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativePage extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set paging option, normal or large"; }  //, P key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_P  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeRepeats extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set benchmarks measurement repeats"; }  //, E key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_E  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeAdaptive extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set adaptive measurement repeats"; }  //, D key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_D  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeStart extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set start block size"; }  //, B key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_B  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeEnd extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set end block size"; }  //, L key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_L  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}

class ComboNativeStep extends DescriptCombo
{
@Override public String[] getValues()  { return new String[] { "" }; }
@Override public String getText()      
    { return "set block size step"; }  //, K key";   }
@Override public int[] getKeys() { return null; }  // new int[] { KeyEvent.VK_K  }; }
@Override public void comboAction( RunInterface ri, int index ) 
    {
    // ... under construction ...
    }
}
