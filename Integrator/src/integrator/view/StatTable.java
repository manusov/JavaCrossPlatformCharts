/*

Statistics table window class:
1) Builder.
2) Components lists.
3) Events listeners.
4) Window logic.

*/

package integrator.view;

import integrator.controller.Integrator;
import integrator.controller.RunInterface;
import integrator.model.Statistics;
import integrator.view.DataTree.DataKeys;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class StatTable
{
private final int X = 250, Y = 130;
private final JPanel panel;
private final JTable table;
private final DescriptTable model;
private DataKeys dataKey;

public JPanel getPanel()             { return panel; }
public AbstractTableModel getModel() { return model; }
public void setDataKey( DataKeys x ) { dataKey = x;  }

public StatTable()
    {
    panel = new StatTablePanel();
    panel.setPreferredSize( new Dimension( X, Y ) );
    panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
    Box vertical = Box.createVerticalBox();
    model = new ModelMath();   // TODO: make case for 4 modes
    table = new JTable( model );
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment( JLabel.CENTER );
    for( int i=0; i< model.getColumnCount(); i++ )
        {
        table.getColumnModel().getColumn( i ).setCellRenderer( renderer );
        }
    optimizeColumnsWidths();
    vertical.add( new JScrollPane( table ) );
    panel.add( vertical );
    ( (LogPanel)panel ).dataChanged();
    }

private void optimizeColumnsWidths()
    {
    TableModel tm = table.getModel();
    int n = tm.getColumnCount();
    int m = tm.getRowCount();
    int k = 0;
    double[] max = new double[n];
    // cycle for find maximum column string length in the table up
    for ( int i=0; i<n; i++ ) { max[i] = tm.getColumnName(i).length(); }
    // cycle for find maximum column string length in the table content part
    for ( int i=0; i<m; i++ )
        {
        for ( int j=0; j<n; j++ )
            {
            k = ((String)(tm.getValueAt(i,j))).length();
            if ( max[j] < k ) { max[j] = k; }
            }
        }
    double scale = 0.0;
    for ( int i=0; i<n; i++ ) { scale = scale + max[i]; }
    scale = X / scale;
    // table for set required preferred width of column
    for ( int i=0; i<n; i++ ) 
        { 
        TableColumn tc = table.getColumnModel().getColumn(i);
        tc.setPreferredWidth( (int)( max[i] * scale ) );
        }
    }

private class StatTablePanel extends LogPanel
    {
    @Override public void dataChanged()
        {
        RunInterface ri = Integrator.getRunInterface();
        if ( ri != null )
            {
             // row=5, column=1, step
            table.setValueAt( ri.getTabStep().toPlainString(), 5, 1 );
            // other data from statistics entry
            Statistics st = ri.calculateStatistics();
            table.setValueAt( st.xAverage.toPlainString()  , 0, 1 );
            table.setValueAt( st.xMedian.toPlainString()   , 0, 2 );
            table.setValueAt( st.xMin.toPlainString()      , 0, 3 );
            table.setValueAt( st.xMax.toPlainString()      , 0, 4 );
            
            table.setValueAt( st.yAverage.toPlainString()  , 1, 1 );
            table.setValueAt( st.yMedian.toPlainString()   , 1, 2 );
            table.setValueAt( st.yMin.toPlainString()      , 1, 3 );
            table.setValueAt( st.yMax.toPlainString()      , 1, 4 );
            
            table.setValueAt( st.dAverage.toPlainString()  , 2, 1 );
            table.setValueAt( st.dMedian.toPlainString()   , 2, 2 );
            table.setValueAt( st.dMin.toPlainString()      , 2, 3 );
            table.setValueAt( st.dMax.toPlainString()      , 2, 4 );
            
            table.setValueAt( st.myAverage.toPlainString() , 3, 1 );
            table.setValueAt( st.myMedian.toPlainString()  , 3, 2 );
            table.setValueAt( st.myMin.toPlainString()     , 3, 3 );
            table.setValueAt( st.myMax.toPlainString()     , 3, 4 );
            
            table.setValueAt( st.mdAverage.toPlainString() , 4, 1 );
            table.setValueAt( st.mdMedian.toPlainString()  , 4, 2 );
            table.setValueAt( st.mdMin.toPlainString()     , 4, 3 );
            table.setValueAt( st.mdMax.toPlainString()     , 4, 4 );
            }
        }
    }

private abstract class DescriptTable extends AbstractTableModel
    {
    abstract String[] getNames();
    abstract String[][] getValues();
    abstract void setValues( String[][] s );
    // table model this application-specific public methods
    public String[] getColumnsNames()          { return getNames();  }
    public String[][] getRowsValues()          { return getValues(); }
    public void setRowsValues( String[][] s )  { setValues( s );     }
    // table model standard required public methods
    @Override public int getColumnCount()    { return getNames().length;  }
    @Override public int getRowCount()       { return getValues().length; }
    @Override public String getColumnName( int column )
        {
        if ( column < getNames().length )   
            return getNames()[column];
        else
            return "?";
        }
    @Override public String getValueAt( int row, int column )
        { 
        if ( ( row < getValues().length ) && ( column < getNames().length ) )
            return " " + getValues()[row][column];
        else
            return "";
        }
    @Override public void setValueAt( Object obj, int row, int column )
        {
        if ( ( obj instanceof String ) &&
             ( row < getValues().length ) && ( column < getNames().length ) )
            {
            getValues()[row][column] = (String)obj;
            }
        }
    @Override public boolean isCellEditable( int row, int column )
        { return false; }
    }

private class ModelMath extends DescriptTable
    {
    private String[][] values =
        { 
            { "X"       , "-" , "-" , "-" , "-" } ,
            { "Y"       , "-" , "-" , "-" , "-" } ,
            { "dY/dX"   , "-" , "-" , "-" , "-" } ,
            { "|Y|"     , "-" , "-" , "-" , "-" } ,
            { "|dY/dX|" , "-" , "-" , "-" , "-" } ,
            { "step X"  , "-" , ""  , ""  , ""  } ,
            
        };
    @Override String[] getNames() 
        { 
        return new String[] 
            { "Parameter" , "Average" , "Median" , "Minimum" , "Maximum" };
        }
    @Override String[][] getValues()         { return values; }
    @Override void setValues( String[][] s ) { values = s;    }
    }

private class ModelCalc extends DescriptTable
    {
    private String[][] values =
        { 
            { "Median, MT"   , "-" , "-" , "-" } ,
            { "ST"           , "-" , "-" , "-" } ,
            { "Ratio"        , "-" , "-" , "-" } ,
            { "Average, MT"  , "-" , "-" , "-" } ,
            { "ST"           , "-" , "-" , "-" } ,
            { "Ratio"        , "-" , "-" , "-" }
        };
    @Override String[] getNames() 
        { 
        return new String[] 
        { "Value, MOPS" , "Actual" , "Minimum" , "Maximum" };
        }
    @Override String[][] getValues()         { return values; }
    @Override void setValues( String[][] s ) { values = s;    }
    }

private class ModelBandwidth extends DescriptTable
    {
    private String[][] values =
        { 
            { "Median, MT"   , "-" , "-" , "-" } ,
            { "Average, MT"  , "-" , "-" , "-" } 
        };
    @Override String[] getNames() 
        { 
        return new String[] 
        { "Bandwidth, MBPS" , "Actual" , "Minimum" , "Maximum" };
        }
    @Override String[][] getValues()         { return values; }
    @Override void setValues( String[][] s ) { values = s;    }
    }

private class ModelLatency extends DescriptTable
    {
    private String[][] values =
        { 
            { "Median, MT"   , "-" , "-" , "-" } ,
            { "Average, MT"  , "-" , "-" , "-" } 
        };
    @Override String[] getNames() 
        { 
        return new String[] 
        { "Latency, ns" , "Actual" , "Minimum" , "Maximum" };
        }
    @Override String[][] getValues()         { return values; }
    @Override void setValues( String[][] s ) { values = s;    }
    }

}

