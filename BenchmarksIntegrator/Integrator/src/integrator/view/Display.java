package integrator.view;

import integrator.controller.Integrator;
import integrator.controller.RunInterface;
import integrator.controller.RunInterface.GCOLOR;
import integrator.view.DataTree.DataKeys;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.Locale;
import javax.swing.JPanel;

public class Display
{
private final int X = 613, Y = 510;
private final JPanel panel;
private DataKeys dataKey;
public JPanel getPanel() { return panel; }
public void setDataKey( DataKeys x ) { dataKey = x; }

public Display()
    {
    panel = new DisplayPanel();
    panel.setPreferredSize( new Dimension( X, Y ) );
    }

}

class DisplayPanel extends JPanel
{
    
@Override public void paint( Graphics g )
    {
    super.paint( g );
    
    // initializing default colors and fonts parameters
    Color backgroundColor = Color.BLACK;
    Color axisColor       = Color.WHITE;
    Color gridColor       = Color.DARK_GRAY;
    Color textColor       = Color.GREEN;
    Font  axisFont = new Font ( "Verdana", Font.PLAIN, 10 );
    Font  textFont = new Font ( "Verdana", Font.BOLD + Font.ITALIC, 14 );
    
    // initializing default X/Y axis parameters
    BigDecimal xMin = new BigDecimal("-1.0");
    BigDecimal xMax = new BigDecimal("1.0");
    BigDecimal xStepSmall = new BigDecimal("0.02");
    BigDecimal xStepBig = new BigDecimal("0.1");
    BigDecimal yMin = new BigDecimal("-1.0");
    BigDecimal yMax = new BigDecimal("1.0");
    BigDecimal yStepSmall = new BigDecimal("0.02");
    BigDecimal yStepBig = new BigDecimal("0.1");
    
    // get data model
    RunInterface ri = Integrator.getRunInterface();
    
    // override defaults from scenario
    if ( ri != null )
        {
        GCOLOR colorScheme = ri.getColorScheme();
        if ( colorScheme != null )
            {
            switch( colorScheme )
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
            xMin = ri.getXmin();
            xMax = ri.getXmax();
            xStepSmall = ri.getXstepSmall();
            xStepBig = ri.getXstepBig();
            yMin = ri.getYmin();
            yMax = ri.getYmax();
            yStepSmall = ri.getYstepSmall();
            yStepBig = ri.getYstepBig();
            }
        }
    
    // start drawings, fill background
    Rectangle r = g.getClipBounds();   // locate area
    g.setColor( backgroundColor );     // get background color for draw area
    g.setFont( axisFont );
    g.fillRect( r.x , r.y ,  r.width , r.height );

    // coordinates for draw X-axis
    int x1 = r.x + 2;
    int y1 = r.y + r.height / 2;
    int x2 = r.x + r.width - 3;
    int y2 = y1;

    // this variables required later, for Y=F(X) function draw
    int pixelX1, pixelX2, pixelY1, pixelY2;
    int pixelY0 = y1;
    
    // draw X-axis, main line
    g.setColor( axisColor );
    g.drawLine( x1, y1, x2, y2 );

    // prepare parameters for draw X-axis graduation,
    // use minimal integer multiply of small steps
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

    // draw X-axis small graduation
    pixelX1 = x3;
    for(int i=0; i<=smallStepsCount; i++)
        {
        g.drawLine(x3, y3, x3, y4);
        x3 += smallPixelStep;
        }
    pixelX2 = x3 - smallPixelStep;

        // draw X-axis big graduation, plus vertical grid and X digital labels
    y3 = y1 - 4;
    y4 = y1 + 4;
    BigDecimal value = xMin;
    BigDecimal step = xStepBig;
    for(int i=0; i<=bigStepsCount; i++)
        {
        g.drawLine(x4, y3, x4, y4);       // draw X-axis graduation
        String s = printCorrectedBigDecimal(value, true);
        if ( ( value.compareTo(BigDecimal.ZERO)) != 0 )
            {
            Color tempColor = g.getColor();
            g.setColor(gridColor);
            g.drawLine(x4, r.y + 2, x4, y3);  // draw vertical grid lines
            g.drawLine(x4, y4, x4, r.y + r.height - 2);
            g.setColor(tempColor);
            g.drawString(s, x4-3, y4+11);  // draw digital labels
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

    // prepare parameters for draw Y-axis
    x1 = r.x + r.width / 2;
    y1 = r.y + 2;
    x2 = x1;
    y2 = r.y + r.height - 3;
    
    // draw Y-axis
    g.drawLine(x1, y1, x2, y2);

    // prepare parameters for draw Y-axis graduation,
    // use minimal integer multiply of small steps
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
    
    // draw Y-axis small graduation
    pixelY1 = y3;
    for(int i=0; i<=smallStepsCount; i++)
        {
        g.drawLine(x3, y3, x4, y3);
        y3 -= smallPixelStep;
        }
    pixelY2 = y3 + smallPixelStep;

    // draw Y-axis big graduation, plus grid and Y digital labels
    x3 = x1 - 4;
    x4 = x1 + 4;
    value = yMin;
    step = yStepBig;
    for(int i=0; i<=bigStepsCount; i++)
        {
        g.drawLine(x3, y4, x4, y4);     // draw Y-axis graduation
        String s = printCorrectedBigDecimal(value, true);
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
    g.setColor( textColor );
    g.setFont( textFont );
    if ( ri != null )
        {
        String s = ri.getNameX();              // get name of X-axis
        if ( s != null )
            {
            x1 = r.x + r.width - 24;
            y1 = r.y + r.height / 2 - 10;
            g.drawString( s, x1, y1 );         // draw name of X-axis
            }
        s = ri.getNameY();                     // get name of Y-axis
        if ( s != null )
            {
            x1 = r.x + r.width / 2 + 28;
            y1 = r.y + 16;
            g.drawString( s, x1, y1 );       // draw name of Y-axis
            }
        }

    

    // prepare parameters for draw function Y=f(X)
    int pixelDx = pixelX2 - pixelX1;
    int pixelDy = pixelY1 - pixelY2;
    double valueDx = xMax.doubleValue() - xMin.doubleValue();
    double valueDy = yMax.doubleValue() - yMin.doubleValue();
    double unitX = valueDx / pixelDx;
    double unitY = valueDy / pixelDy;
    double valueX; // = xMin.doubleValue();
    if ( ri != null )
        {
        double[][] data = ri.getFunctionArray();
        int count = 0, count1 = 0;
        if ( ( data != null ) && ( data[0] != null ) && ( data[1] != null ) )
            {
            count = data[0].length;
            count1 = data[1].length;
            }
        if ( ( count > 0 ) && ( count == count1 ) )
            {
            int j = 0;
            // draw cycle
            for( int i=0; i < pixelDx; i++ )
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
                }
            }
        }
    }

private String printCorrectedBigDecimal(BigDecimal bdX, boolean result)
    {
    if ( bdX == null ) return "ERROR";
    String s = bdX.toString();          // convert value to string
    if (result)
        {
        s = rejectRightZeroes(s);       // reject right non-signed zeroes
        s = rejectRoundDigits(s);       // reject rounding errors
        if ( s.equals("-0.0") ) s = "0.0";    // prevent -0.0
        }
    return s;
    }
private String printCorrected(double x, boolean result)
    {
    String s = printDouble(x);       // convert value to string
    if (result && Double.isFinite(x) )
        {
        s = rejectRightZeroes(s);             // reject right non-signed zeroes
        s = rejectRoundDigits(s);             // reject rounding errors
        if ( s.equals("-0.0") ) s = "0.0";    // prevent -0.0
        }
    return s;
    }
// maximum number of enabled "0" digits after ".", otherwise round
private final static int THRESHOLD_0 = 6; // 10;
// maximum number of enabled "9" digits after ".", otherwise round
private final static int THRESHOLD_9 = 6; // 10;
// defines floating-point number format
private static String printDouble(double x)
    {
    // return String.format( Locale.US, "%.16f", x);
    return String.format( Locale.US, "%.10f", x);
    }
private static String rejectRightZeroes(String s)
    {
    int position = s.length() - 1;
    boolean point = s.contains(".");
    if ( point && ( position > 0 ) )
        {
        while( position > 0 )
            {
            char c = s.charAt(position);
            // prevent zero absent for example "1."
            if ( c == '.' )
                {
                position++;
                s = s + "0";
                break; 
                }
            // prevent extra zero for example "1.00"
            if ( c != '0' ) { break; }
            position--;
            }
            s = s.substring( 0, position+1 );
        }
    return s;
    }
private static String rejectRoundDigits(String s)
    {
    StringBuilder sb = new StringBuilder(s);
    StringBuilder sb0 = new StringBuilder("");
    StringBuilder sb9 = new StringBuilder("");
    int size = sb.length();
    int base = sb.indexOf(".");
    int detected0 = -1;
    int detected9 = -1;
    // check string sufficient valid for conversion
    if ( ( size > 0 )&( base > 0 )&( size > base ) )
        {
        int count = size - base - 1;
        // prepare patterns for detect digits sequences with "0" and "9"
        for(int i=0; i<count; i++) { sb0.append("0"); sb9.append("9"); }
        
        // detect digits sequences with "0" and "9"
        for(int i=0; i<count; i++)
            {
            detected0 = sb.indexOf(sb0.toString(), base);
            if ( detected0 > 0 ) break;
            detected9 = sb.indexOf(sb9.toString(), base);
            if ( detected9 > 0 ) break;
            sb0.deleteCharAt(sb0.length()-1);
            sb9.deleteCharAt(sb9.length()-1);
            }
        // reject rounding error zeroes, for example convert 1.000...005 to 1.0
        if ( ( detected0 > 0 )&( sb9.length() > THRESHOLD_0 ) )
            {
            sb.delete(detected0, size);
            if ( sb.charAt(detected0-1) == '.' )
                {
                sb.append('0');
                }
            }
        // rejects rounding error nines, for example convert 1.399...984 to 1.4
        if ( ( detected9 > 0 )&( sb9.length() > THRESHOLD_9 ) )
            {
            sb.delete(detected9, size);
            if ( sb.charAt(detected9-1) == '.' )
                {
                sb.append('9');
                detected9++;
                }
            for(int i=detected9-1; i>=0; i--)
                {
                char c = sb.charAt(i);
                if ( ( c >= '0') & ( c  < '9') )
                    {
                    sb.setCharAt( i, (char)(c+1) );
                    break;
                    }
                else if ( c == '9' )
                    {
                    sb.setCharAt( i, '0' );   // convert 8.9(9) to 9.0
                    if ( i == 0 )
                        {
                        sb.insert( 0, "1" );  // convert 9.9(9) to 10.0
                        }
                    else if ( ( i == 1 ) &
                              ((sb.charAt(0) == '+')|(sb.charAt(0) == '-')) )
                        {
                        sb.insert(1, "1");    // convert +9.9(9) to +10.0
                        }                     //         -9.9(9) to -10.0
                    }
                }
            }
        }
    // return modified string
    return sb.toString();
    }

}

