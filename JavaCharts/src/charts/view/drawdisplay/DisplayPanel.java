/*

Description and builder class for drawings display panel.
JPanel class with additional function: store lists of JComponents.
Override paint() method for function graph functionality.
This class visualizes function Y=F(X) and additional information:
function name, Y,Y axis, axis text labels and other view elements.

*/

package charts.view.drawdisplay;

import charts.model.FunctionCalc;
import charts.model.FunctionCore;
import charts.model.ViewerState;
import charts.model.ViewerState.GCOLOR;
import charts.view.FloatPrintUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.BigDecimal;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel 
{

private FunctionCore fcore = null;  // communication with model
private ViewerState vs = null;      // communication with viewer state (vs)
// private FunctionState fs = null;    // communication with function state (fs)
private FunctionCalc fc = null;     // communication with function calculated
    
@Override public void paint(Graphics g)
    {
    super.paint(g);
    
    // initializing default parameters
    
    Color backgroundColor = Color.BLACK;
    Color axisColor       = Color.WHITE;
    Color gridColor       = Color.DARK_GRAY;
    Color textColor       = Color.GREEN;
    Font  axisFont = new Font ( "Verdana", Font.PLAIN, 10 );
    Font  textFont = new Font ( "Verdana", Font.BOLD + Font.ITALIC, 14 );
    
    BigDecimal xMin = new BigDecimal("-1.0");
    BigDecimal xMax = new BigDecimal("1.0");
    BigDecimal xStepSmall = new BigDecimal("0.02");
    BigDecimal xStepBig = new BigDecimal("0.1");
    BigDecimal yMin = new BigDecimal("-1.0");
    BigDecimal yMax = new BigDecimal("1.0");
    BigDecimal yStepSmall = new BigDecimal("0.02");
    BigDecimal yStepBig = new BigDecimal("0.1");
    
    // get parameters from model = function core, this overrides local defaults
    
    if ( fcore != null )
        {
        vs = fcore.getViewerState();
        if ( vs != null )
            {
            GCOLOR cs = vs.getColorScheme();
                {
                if ( cs != null )
                    {
                    switch(cs)
                        {
                        case BACKGROUND_BLACK:
                            {
                            backgroundColor = Color.BLACK;
                            axisColor       = Color.WHITE;
                            gridColor       = Color.DARK_GRAY;
                            textColor       = Color.GREEN;
                            break;
                            }
                        case BACKGROUND_WHITE:
                            {
                            backgroundColor = Color.WHITE;
                            axisColor       = Color.BLACK;
                            gridColor       = Color.LIGHT_GRAY;
                            textColor       = Color.BLUE;
                            break;
                            }
                        }
                        
                    }
                }
            xMin = vs.getXmin();
            xMax = vs.getXmax();
            xStepSmall = vs.getXstepSmall();
            xStepBig = vs.getXstepBig();
            yMin = vs.getYmin();
            yMax = vs.getYmax();
            yStepSmall = vs.getYstepSmall();
            yStepBig = vs.getYstepBig();
            }
        }
    
    // drawings
    
    // locate area
    Rectangle r = g.getClipBounds();
    // fill area with background color, set font
    g.setColor(backgroundColor);
    g.setFont(axisFont);
    g.fillRect( r.x , r.y ,  r.width , r.height );
    
    // draw X-axis
    
    int x1 = r.x + 2;
    int y1 = r.y + r.height / 2;
    int x2 = r.x + r.width - 3;
    int y2 = y1;
    
    int pixelX1, pixelX2, pixelY1, pixelY2;
    int pixelY0 = y1;
    
    g.setColor(axisColor);
    g.drawLine(x1, y1, x2, y2);
    // draw X-axis graduation, use minimal integer multiply of small steps
    int smallStepsCount =  // number of small steps 
            ( ( xMax.subtract(xMin) ).divideToIntegralValue(xStepSmall) ).
            intValue();
    int bigStepsCount =  // number of big steps
            ( ( xMax.subtract(xMin) ).divideToIntegralValue(xStepBig) ).
            intValue();
    int pixelCount = x2 - x1;
    int pixelExtra = pixelCount % smallStepsCount;
    int pixelOffset = pixelExtra / 2;
    pixelCount -= pixelExtra;
    int smallPixelStep = pixelCount / smallStepsCount;
    int bigPixelStep = pixelCount / bigStepsCount;
    int x3 = x1 + pixelOffset;
    int y3 = y1 - 1;
    int x4 = x3;
    int y4 = y1 + 1;
    
    pixelX1 = x3;
    for(int i=0; i<=smallStepsCount; i++)
        {
        g.drawLine(x3, y3, x3, y4);
        x3 += smallPixelStep;
        }
    pixelX2 = x3 - smallPixelStep;
    
    y3 = y1 - 4;
    y4 = y1 + 4;
    BigDecimal value = xMin;
    BigDecimal step = xStepBig;
    for(int i=0; i<=bigStepsCount; i++)
        {
        g.drawLine(x4, y3, x4, y4);       // draw X-axis graduation
        //
        // String s = "" + value;            // draw numbers for X-axis graduation
        String s = FloatPrintUtil.printCorrectedBigDecimal(value, true);
        //
        if ( ( value.compareTo(BigDecimal.ZERO)) != 0 )
            {
            Color tempColor = g.getColor();
            g.setColor(gridColor);
            g.drawLine(x4, r.y + 2, x4, y3);  // draw vertical grid lines
            g.drawLine(x4, y4, x4, r.y + r.height - 2);
            g.setColor(tempColor);
            g.drawString(s, x4-3, y4+11);
            }
        x4 += bigPixelStep;
        value = value.add(step);
        }
    // draw X-axis left arrow
    x3 = x1;
    y3 = y1;
    x4 = x1 + 7;
    y4 = y1 - 3;
    g.drawLine(x3, y3, x4, y4);
    y4 = y1 + 3;
    g.drawLine(x3, y3, x4, y4);
    // draw X-axis right arrow
    x3 = x2 - 7;
    y3 = y1 - 3;
    x4 = x2;
    y4 = y2;
    g.drawLine(x3, y3, x4, y4);
    y3 = y1 + 3;
    g.drawLine(x3, y3, x4, y4);
    
    // draw Y-axis
    
    x1 = r.x + r.width / 2;
    y1 = r.y + 2;
    x2 = x1;
    y2 = r.y + r.height - 3;
    g.drawLine(x1, y1, x2, y2);
    // draw Y-axis graduation, use minimal integer multiply of small steps
    smallStepsCount =  // number of small steps 
        ( ( yMax.subtract(yMin) ).divideToIntegralValue(yStepSmall) ).
            intValue();
    bigStepsCount =  // number of big steps
            ( ( yMax.subtract(yMin) ).divideToIntegralValue(yStepBig) ).
            intValue();
    pixelCount = y2 - y1;
    pixelExtra = pixelCount % smallStepsCount;
    pixelOffset = pixelExtra / 2;
    pixelCount -= pixelExtra;
    smallPixelStep = pixelCount / smallStepsCount;
    bigPixelStep = pixelCount / bigStepsCount;
    x3 = x1 - 1;
    y3 = y2 - pixelOffset;
    x4 = x1 + 1;
    y4 = y3;
    
    pixelY1 = y3;
    for(int i=0; i<=smallStepsCount; i++)
        {
        g.drawLine(x3, y3, x4, y3);
        y3 -= smallPixelStep;
        }
    pixelY2 = y3 + smallPixelStep;
    
    x3 = x1 - 4;
    x4 = x1 + 4;
    value = yMin;
    step = yStepBig;
    for(int i=0; i<=bigStepsCount; i++)
        {
        g.drawLine(x3, y4, x4, y4);     // draw Y-axis graduation
        //
        // String s = "" + value;          // draw numbers for Y-axis graduation
        String s = FloatPrintUtil.printCorrectedBigDecimal(value, true);
        //
        if ( ( value.compareTo(BigDecimal.ZERO)) != 0 )
            {
            Color tempColor = g.getColor();
            g.setColor(gridColor);
            g.drawLine(r.x + 2, y4, x3, y4);  // draw horizontal grid lines
            g.drawLine(x4 + 4, y4, r.x + r.width - 2, y4);
            g.setColor(tempColor);
            g.drawString(s, x4+2, y4+3);
            }
        else
            {
            g.drawString("0", x4-1, y4+12);
            }
        y4 -= bigPixelStep;
        value = value.add(step);
        }
    // draw Y-axis up arrow
    x3 = x1;
    y3 = y1;
    x4 = x1 - 3;
    y4 = y1 + 7;
    g.drawLine(x3, y3, x4, y4);
    x4 = x1 + 3;
    g.drawLine(x3, y3, x4, y4);
    // draw Y-axis down arrow
    x3 = x2 - 3;
    y3 = y2 - 7;
    x4 = x2;
    y4 = y2;
    g.drawLine(x3, y3, x4, y4);
    x3 = x2 + 3;
    g.drawLine(x3, y3, x4, y4);
    
    // draw name of X axis, draw name of Y axis
    
    g.setColor(textColor);
    g.setFont(textFont);
    if ( vs != null )
        {
        String s = vs.getNameX();            // get name of X-axis
        if ( s != null )
            {
            x1 = r.x + r.width - 24;
            y1 = r.y + r.height / 2 - 10;
            g.drawString(s, x1, y1);         // draw name of X-axis
            }
        s = vs.getNameY();                   // get name of Y-axis
        if ( s != null )
            {
            x1 = r.x + r.width / 2 + 28;
            y1 = r.y + 16;
            g.drawString(s, x1, y1);         // draw name of Y-axis
            }
        }
    
    // draw function Y=f(X)
    
    int pixelDx = pixelX2 - pixelX1;
    int pixelDy = pixelY1 - pixelY2;
    double valueDx = xMax.doubleValue() - xMin.doubleValue();
    double valueDy = yMax.doubleValue() - yMin.doubleValue();
    double unitX = valueDx / pixelDx;
    double unitY = valueDy / pixelDy;
    double valueX; // = xMin.doubleValue();
    
    // fs = fcore.getFunctionState();
    // double[][] data = fs.getFunction();    // array of pairs (x,y)
    fc = fcore.getFunctionCalc();
    double[][] data = fc.getArray();          // array of pairs (x,y)
    //

    if ( fc != null )
        {
        int count = data[0].length;
        int count1 = data[1].length;
        if ( count == count1 )
            {
            int j = 0;
            for(int i=0; i<pixelDx; i++)
                {
                // this variant with non-additive approximation error,
                // don't use addition per iteration
                valueX = xMin.doubleValue() + unitX * (i - 1); 
                if ( j >= count ) break;
                double ymin = data[1][j];
                double ymax = ymin;
                while ( j < count )  // limited by array size
                    {
                    // check is X in this pixel interval
                    if ( ( data[0][j] ) > ( valueX + unitX ) ) break;
                    // find min, max elements with X in this pixel interval
                    if ( ymin > data[1][j] ) ymin = data[1][j];
                    if ( ymax < data[1][j] ) ymax = data[1][j];
                    j++;
                    }
                // draw line or single pixel by ymin, ymax
                x1 = pixelX1 + i;
                x2 = x1;
                if ( Double.isFinite(ymin) && Double.isFinite(ymax) )
                    {
                    y1 = pixelY0 - (int) Math.round( ymin / unitY );
                    y2 = pixelY0 - (int) Math.round( ymax / unitY );
                    // draw
                    if ( ( x1 >= pixelX1 ) && ( x2 <= pixelX2 ) &&
                         ( y1 <= pixelY1 ) && ( y2 >= pixelY2 )  )
                        {
                        g.drawLine(x1, y1, x2, y2);  // draw
                        }
                    }
                // THIS CAUSE ADDITIVE APPROXIMATION ERROR
                // valueX += unitX;  // addend equal X-pixel interval
                // THIS NON ADDITIVE
                // valueX = xMin.doubleValue() + unitX * i; 
                // System.out.println( " i = " + i + " , valueX = " + valueX + 
                //                    " , ymin = " + ymin + " , ymax = " + ymax );
                //
                }
            }
        }
    }

// set function core for communications with data model
public void updateDisplay(FunctionCore fc, int selector)
    {
    fcore = fc;
    }
    
}
