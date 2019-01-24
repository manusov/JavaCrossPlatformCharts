/*

Class-controller for mathematics function draw scenario.

*/

package integrator.controller;

import integrator.model.Function;
import integrator.model.MathDataSource;
import integrator.model.Statistics;
import java.math.BigDecimal;

public class MathRun implements RunInterface
{

private final MathDataSource mds;
    
public MathRun()
    {
    mds = new MathDataSource();
    selectedFunction = mds.getFunctionsList()[0];
    initParms();
    initFunction( selectedFunction );
    }

// method gets list of math. functions descriptions
@Override public Function[] getFunctionsList()
    {
    return mds.getFunctionsList();
    }

// method gets one math. function description from list, by index
@Override public Function getFunction( int i )
    {
    Function[] f = getFunctionsList();
    if ( i >= f.length ) i = 0;
    return f[i];
    }

// method gets current selected function description
@Override public Function getSelectedFunction()
    {
    return selectedFunction;
    }

// method gets function calculated data, as array of pairs X,Y
@Override public double[][] getFunctionArray()
    {
    return functionArray;
    }

// calculate statistics for function Y=F(X)
@Override public Statistics calculateStatistics()
{
    return mds.calculateStatistics( functionArray );
}

// default constants for functions visual mode options
private final BigDecimal X_MIN        = new BigDecimal("-1.0");
private final BigDecimal X_MAX        = new BigDecimal("1.0");
private final BigDecimal Y_MIN        = new BigDecimal("-1.0");
private final BigDecimal Y_MAX        = new BigDecimal("1.0");
private final BigDecimal X_STEP_SMALL = new BigDecimal("0.02");
private final BigDecimal X_STEP_BIG   = new BigDecimal("0.1");
private final BigDecimal Y_STEP_SMALL = new BigDecimal("0.02");
private final BigDecimal Y_STEP_BIG   = new BigDecimal("0.1");
private final BigDecimal TAB_STEP     = new BigDecimal("0.001");
private final String NAME_X = "X";
private final String NAME_Y = "Y";
private final Gcolor DEFAULT_COLOR = Gcolor.BACKGROUND_WHITE;

// variables for functions visual mode options
private Gcolor colorScheme;
private BigDecimal xMin, xMax, yMin, yMax,
                   xStepSmall, xStepBig, yStepSmall, yStepBig, 
                   tabStep;
private String     nameX, nameY;

// load default parameters
// see detail parameters comments at abstract Function.java class
private void initParms()
    {
    colorScheme = DEFAULT_COLOR;
    xMin        = X_MIN;
    xMax        = X_MAX;
    yMin        = Y_MIN;
    yMax        = Y_MAX;
    xStepSmall  = X_STEP_SMALL;
    xStepBig    = X_STEP_BIG;
    yStepSmall  = Y_STEP_SMALL;
    yStepBig    = Y_STEP_BIG;
    tabStep     = TAB_STEP;
    nameX       = NAME_X;
    nameY       = NAME_Y;
    }

// get variables methods
// see detail parameters comments at abstract Function.java class
@Override public Gcolor getColorScheme()      { return colorScheme; }
@Override public BigDecimal getXmin()         { return xMin;        }
@Override public BigDecimal getXmax()         { return xMax;        }
@Override public BigDecimal getYmin()         { return yMin;        }
@Override public BigDecimal getYmax()         { return yMax;        }
@Override public BigDecimal getXstepSmall()   { return xStepSmall;  }
@Override public BigDecimal getXstepBig()     { return xStepBig;    }
@Override public BigDecimal getYstepSmall()   { return yStepSmall;  }
@Override public BigDecimal getYstepBig()     { return yStepBig;    }
@Override public BigDecimal getTabStep()      { return tabStep;     }
@Override public String getNameX()            { return nameX;       }
@Override public String getNameY()            { return nameY;       }

// set variables methods
// see detail parameters comments at abstract Function.java class
@Override public void setColorScheme(Gcolor cs)     { colorScheme = cs; }
@Override public void setXmin(BigDecimal bd)        { xMin = bd;        }
@Override public void setXmax(BigDecimal bd)        { xMax = bd;        }
@Override public void setYmin(BigDecimal bd)        { yMin = bd;        }
@Override public void setYmax(BigDecimal bd)        { yMax = bd;        }
@Override public void setXstepSmall(BigDecimal bd)  { xStepSmall = bd;  }
@Override public void setXstepBig(BigDecimal bd)    { xStepBig = bd;    }
@Override public void setYstepSmall(BigDecimal bd)  { yStepSmall = bd;  }
@Override public void setYstepBig(BigDecimal bd)    { yStepBig = bd;    }
@Override public void setTabStep(BigDecimal bd)     { tabStep = bd;     }
@Override public void setNameX(String s)            { nameX = s;        }
@Override public void setNameY(String s)            { nameY = s;        }
// get default color scheme
@Override public Gcolor getDefaultColorScheme() { return DEFAULT_COLOR; }

// control constants for function tabulation
private final BigDecimal[] TAB_STEPS = new BigDecimal[]
            {
            new BigDecimal("100.0")  ,
            new BigDecimal("10.0")   ,
            new BigDecimal("1.0")   ,
            new BigDecimal("0.1")  ,
            new BigDecimal("0.05")
            };
private final int TAB_STEP_COUNT = TAB_STEPS.length;
private final int TAB_STEP_DEFAULT = 2;
// control constants for function X range
private final static BigDecimal[] X_RANGE = new BigDecimal[]
            {
            new BigDecimal("0.5")   ,
            new BigDecimal("1.0")   ,
            new BigDecimal("2.0")  ,
            };
private final static int X_RANGE_COUNT = X_RANGE.length;
private final static int X_RANGE_DEFAULT = 1;
// control constants for function Y range
private final static BigDecimal[] Y_RANGE = new BigDecimal[]
            {
            new BigDecimal("0.5")   ,
            new BigDecimal("1.0")   ,
            new BigDecimal("2.0")  ,
            };
private final static int Y_RANGE_COUNT = Y_RANGE.length;
private final static int Y_RANGE_DEFAULT = 1;

// control variables
private int selectedTab;
private int selectedXrange;
private int selectedYrange;
private Function selectedFunction = null;
private double[][] functionArray = null;

// assign and initialize mathematics function
@Override public void sendFunction( int i )
    {
    // get function description , selected by index
    Function f = getFunction( i );
    selectedFunction = f;
    initFunction( f );
    }

// send control from view settings panel
@Override public void sendControl( Controls control )
    {
    if ( selectedFunction == null )  return;
    switch(control)
        {
        case X_SCALE_INC:
            {
            BigDecimal[] bd = xIncLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedXrange++;
            setXmin( bd[0] );
            setXmax( bd[1] );
            break;
            }
        case X_SCALE_DEC:
            {
            BigDecimal[] bd = xDecLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedXrange--;
            setXmin( bd[0] );
            setXmax( bd[1] );
            break;
            }
        case Y_SCALE_INC:
            {
            BigDecimal[] bd = yIncLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedYrange++;
            setYmin( bd[0] );
            setYmax( bd[1] );
            break;
            }
        case Y_SCALE_DEC:
            {
            BigDecimal[] bd = yDecLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedYrange--;
            setYmin( bd[0] );
            setYmax( bd[1] );
            break;
            }
        case TAB_INC:
            {
            BigDecimal bd = tabIncLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedTab++;
            setTabStep( bd );
            break;
            }
        case TAB_DEC:
            {
            BigDecimal bd = tabDecLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedTab--;
            setTabStep( bd );
            break;
            }
        case RESET:
            {
            Function f = selectedFunction;  // try use current function
            if ( f == null )
                {                      // if not assigned, use first function
                f = getFunction(0);
                }
            initFunction( f );  // set defaults
            break;
            }
        }
    // recalculate function with new parameters
    functionArray = calculate
        ( getXmin(), getXmax(), getTabStep(), selectedFunction );
    }            

// method for set control buttons activity
@Override public boolean isTabIncable()  { return tabIncLimit() != null; }
@Override public boolean isTabDecable()  { return tabDecLimit() != null; }
@Override public boolean isXincable()    { return xIncLimit()   != null; }
@Override public boolean isXdecable()    { return xDecLimit()   != null; }
@Override public boolean isYincable()    { return yIncLimit()   != null; }
@Override public boolean isYdecable()    { return yDecLimit()   != null; }

private void initFunction( Function f )
    {
    // parameters, required for calculation
    BigDecimal x1, x2, dx;
    setXmin( x1 = f.getXmin() );
    setXmax( x2 = f.getXmax() );
    setYmin( f.getYmin() );
    setYmax( f.getYmax() );
    setXstepSmall( f.getXstepSmall() );
    setXstepBig( f.getXstepBig() );
    setYstepSmall( f.getYstepSmall() );
    setYstepBig( f.getYstepBig() );
    setTabStep( dx = f.getTabStep() );
    setNameX( f.getNameX() );
    setNameY( f.getNameY() );
    // set scaling and positioning constants
    selectedTab = TAB_STEP_DEFAULT;
    selectedXrange = X_RANGE_DEFAULT;
    selectedYrange = Y_RANGE_DEFAULT;
    // calculate function and set result array
    functionArray = calculate( x1, x2, dx, f );
    }

private BigDecimal tabIncLimit()
    {
    int n = selectedTab + 1;
    if ( ( selectedFunction == null ) || (  n >= TAB_STEP_COUNT ) )
       return null;
    BigDecimal tabDefault = selectedFunction.getTabStep();
    BigDecimal tab = tabDefault.multiply( TAB_STEPS[n] );
    return tab;
    }

private BigDecimal tabDecLimit()
    {
    int n = selectedTab - 1;
    if ( ( selectedFunction == null ) || ( n < 0 ) )  return null;
    BigDecimal tabDefault = selectedFunction.getTabStep();
    BigDecimal tab = tabDefault.multiply( TAB_STEPS[n] );
    return tab;
    }

private BigDecimal[] xIncLimit()
    {
    int n = selectedXrange + 1;
    if ( ( selectedFunction == null ) || ( n >= X_RANGE_COUNT ) )  return null;
    BigDecimal defaultXmin = selectedFunction.getXmin();
    BigDecimal defaultXmax = selectedFunction.getXmax();
    BigDecimal xmin = defaultXmin.multiply( X_RANGE [n] );
    BigDecimal xmax = defaultXmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { xmin, xmax };
    }

private BigDecimal[] xDecLimit()
    {
    int n = selectedXrange - 1;
    if ( ( selectedFunction == null ) || ( n < 0 ) )  return null;
    BigDecimal defaultXmin = selectedFunction.getXmin();
    BigDecimal defaultXmax = selectedFunction.getXmax();
    BigDecimal xmin = defaultXmin.multiply( X_RANGE [n] );
    BigDecimal xmax = defaultXmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { xmin, xmax };
    }

private BigDecimal[] yIncLimit()
    {
    int n = selectedYrange + 1;
    if ( ( selectedFunction == null ) || ( n >= Y_RANGE_COUNT ) )  return null;
    BigDecimal defaultYmin = selectedFunction.getYmin();
    BigDecimal defaultYmax = selectedFunction.getYmax();
    BigDecimal ymin = defaultYmin.multiply( X_RANGE [n] );
    BigDecimal ymax = defaultYmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { ymin, ymax };
    }

private BigDecimal[] yDecLimit()
    {
    int n = selectedYrange - 1;
    if ( ( selectedFunction == null ) || ( n < 0 ) )  return null;
    BigDecimal defaultYmin = selectedFunction.getYmin();
    BigDecimal defaultYmax = selectedFunction.getYmax();
    BigDecimal ymin = defaultYmin.multiply( X_RANGE [n] );
    BigDecimal ymax = defaultYmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { ymin, ymax };
    }

// calculate math function
private double[][] calculate
        ( BigDecimal bd1, BigDecimal bd2, BigDecimal bd3, Function f )
    {
    double[][] array;
    double x1 = bd1.doubleValue();  // convert input parameters, assist part
    double x2 = bd2.doubleValue();
    double dx = bd3.doubleValue();
    double m = ( x2 - x1 ) / dx;
    int n = (int)m + 1;               // n = number of tabulation steps
    array = f.function(x1, dx, n);    // call target method and return
    return array;
    }
}

