// Description and builder class for status panel.
// JPanel class with additional function: store lists of JComponents.

package charts.view.drawstatus;

import charts.view.labels.LabelText1;
import charts.view.labels.LabelText3;
import charts.view.labels.LabelText2;
import charts.model.FunctionCalc;
import charts.model.FunctionCore;
import charts.model.ViewerState;
import charts.view.DescriptLabel;
import charts.view.FloatPrintUtil;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel
{
private DescriptLabel[] descriptors = null;
private JLabel[] labels = null;
    
// get list of information labels
public DescriptLabel[] getLabelsList()
    {
    DescriptLabel[] list = new DescriptLabel[]
        {
        new LabelText1() ,
        new LabelText2() ,
        new LabelText3() ,
        };
    return list;
    }

// setup labels for dynamically update
public void setupLabels(DescriptLabel[] d, JLabel[] l )
    {
    descriptors = d;
    labels = l;
    }

// set function core for update view by data model changes
public void updateStatus(FunctionCore cc, int selector)
    {
    if ( ( descriptors != null ) && ( labels != null ) )
    {
    int n = descriptors.length;
    int m = labels.length;
    FunctionCalc fc = cc.getFunctionCalc();
    ViewerState vs = cc.getViewerState();
    // function name string: y=f(x)
    if ( n > 0 )
        {
        String s = "Function  " + cc.getViewerState().getNameY();
        descriptors[0].setName(s);
        }
    // X-axis parameters string, Xmin, Xmax, Xstep
    if ( n > 1 )
        {
        String s = "n/a";
        if ( ( fc != null ) && ( vs != null ) )
            {
            double[][] array = fc.getArray();
            double[] minMaxValid = fc.findMinMaxValid();
            if ( ( array != null ) && ( minMaxValid != null ) )
                {
                int arrayLength = array[0].length;
                double x1 = array[0][0];
                double x2 = array[0][arrayLength-1];
                double dx = vs.getTabStep().doubleValue();
                double xv1 = minMaxValid[0];
                double xv2 = minMaxValid[1];
                /*
                s = String.format
                    ( "x = [ %.3g ... %.3g ] , step = %.6f", x1, x2, dx );
                */
                String s1 = FloatPrintUtil.printCorrected(x1, true);
                String s2 = FloatPrintUtil.printCorrected(x2, true);
                String s3 = FloatPrintUtil.printCorrected(dx, true);
                String s4 = FloatPrintUtil.printCorrected(xv1, true);
                String s5 = FloatPrintUtil.printCorrected(xv2, true);
                s = "x = [ " + s1 + "  ,  " + s2 + " ] , step = " + s3 +
                    " , valid = [ " + s4 + " , " + s5 + " ] ";
                }
            }
        descriptors[1].setName(s);
        }
    // Y-axis parameters string, Ymin, Ymax, average, median
    if ( n > 2 )
        {
        String s = "n/a";
        if ( ( fc != null ) && ( vs != null ) )
            {
            double[] minMax = fc.findMinMax();
            if ( minMax != null )
                {
                double y1 = minMax[0];
                double y2 = minMax[1];
                double average = fc.findAverage();
                double median = fc.findMedian();
                /*
                s = String.format
                    ( "y = [ %.3g ... %.3g ] " +
                      " , average = %.6f , median = %.6f ", 
                      y1, y2, average, median );
                */
                String s1 = FloatPrintUtil.printCorrected(y1, true);
                String s2 = FloatPrintUtil.printCorrected(y2, true);
                String s3 = FloatPrintUtil.printCorrected(average, true);
                String s4 = FloatPrintUtil.printCorrected(median, true);
                s = "y = [ " + s1 + "  ,  " + s2 + 
                    " ] , average = " + s3 + " , median = " + s4;
                }
            }
        descriptors[2].setName(s);
        }
    // set text from labels descriptors to labels
    n = Integer.min(n, m);
    for (int i=0; i<n; i++)
        {
        labels[i].setText( descriptors[i].getName() );
        }
    }
        
        
        
/*   
        
    // DEBUG REQUIRED FOR THIS CODE SECTION    
        
    if ( ( descriptors != null ) && ( labels != null ) )
        {
        int n = descriptors.length;
        int m = labels.length;
        
        FunctionState fs = cc.getFunctionState();
        ViewerState vs = cc.getViewerState();
        if ( n > 0 )
            {
            String s = "Function  " + cc.getViewerState().getNameY();
            descriptors[0].setName(s);
            }
        if ( n > 1 )
            {
            String s = "n/a";
            if ( ( fs != null ) && ( vs != null ) )
                {
                double[][] function = fs.getFunction();
                if ( function != null )
                    {
                    int arrayLength = function[0].length;
                    double x1 = function[0][0];
                    double x2 = function[0][arrayLength-1];
                    double dx = vs.getTabStep().doubleValue();
                    s = String.format
                        ( "x = [ %.3g ... %.3g ] , step = %.3g", x1, x2, dx );
                    }
                
                }
            descriptors[1].setName(s);
            }
        if ( n > 2 )
            {
            String s = "n/a";
            if ( fs != null )
                {
                double[] y = fs.getMinMax();
                if (( y != null ) && ( y.length >= 2 ) )
                    {
                    double y1 = y[0];
                    double y2 = y[1];
                    double average = fs.calculateAverage();
                    double median = fs.calculateMedian();
                    s = String.format
                        ( "At selected interval, Y = [ %.3g ... %.3g ] , " +
                          "average = %.3g , median = %.3g" ,
                          y1, y2, average, median );
                    }
                }
            descriptors[2].setName(s);
            }
        
        n = Integer.min(n, m);
        for (int i=0; i<n; i++)
            {
            labels[i].setText( descriptors[i].getName() );
            }
        }
*/        
    }

    
}
