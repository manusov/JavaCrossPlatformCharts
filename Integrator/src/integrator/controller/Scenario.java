/*

Parent class-controller for all draw scenarios.

*/

package integrator.controller;

import integrator.model.DataModel;
import integrator.model.Function;
import integrator.model.FunctionDefaults;
import integrator.model.Statistics;
import java.math.BigDecimal;

public class Scenario implements RunInterface
{
DataModel dataModel = null;
FunctionDefaults functionDefaults = null;
Function selectedFunction = null;

// variables for functions visual mode options
private Gcolor colorScheme;
private BigDecimal xMin, xMax, yMin, yMax,
                   xStepSmall, xStepBig, yStepSmall, yStepBig, 
                   tabStep;
private String     nameX, nameY;

// control variables
private int selectedTab;
private int selectedXrange;
private int selectedYrange;
private double[][] functionArray = null;

// method gets list of math. functions descriptions
@Override public Function[] getFunctionsList()
    {
    return dataModel.getFunctionsList();
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
    return dataModel.calculateStatistics( functionArray );
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
@Override public Gcolor getDefaultColorScheme() 
    { 
    return functionDefaults.getDefaultColor();  // DEFAULT_COLOR; 
    }

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

// at this point bound between public interface and non-public internal methods,
// include parent methods, used by childs

// load default parameters
// see detail parameters comments at abstract Function.java class
final void initParms()
    {
    colorScheme = functionDefaults.getDefaultColor();        // DEFAULT_COLOR;
    xMin        = functionDefaults.getDefaultXmin();         // X_MIN;
    xMax        = functionDefaults.getDefaultXmax();         // X_MAX;
    yMin        = functionDefaults.getDefaultYmin();         // Y_MIN;
    yMax        = functionDefaults.getDefaultYmax();         // Y_MAX;
    xStepSmall  = functionDefaults.getDefaultXstepSmall();   // X_STEP_SMALL;
    xStepBig    = functionDefaults.getDefaultXstepBig();     // X_STEP_BIG;
    yStepSmall  = functionDefaults.getDefaultYstepSmall();   // Y_STEP_SMALL;
    yStepBig    = functionDefaults.getDefaultYstepBig();     // Y_STEP_BIG;
    tabStep     = functionDefaults.getDefaultTabStep();      // TAB_STEP;
    nameX       = functionDefaults.getDefaultNameX();        // NAME_X;
    nameY       = functionDefaults.getDefaultNameY();        // NAME_Y;
    }

// setup mathematics function Y=F(X)
final void initFunction( Function f )
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
    selectedTab = functionDefaults.getDefaultTabStepsDefault();
    selectedXrange = functionDefaults.getDefaultXrangeDefault();
    selectedYrange = functionDefaults.getDefaultYrangeDefault();
    // calculate function and set result array
    functionArray = calculate( x1, x2, dx, f );
    }

// at this point starts class private methods part

private BigDecimal tabIncLimit()
    {
    int n = selectedTab + 1;
    if ( ( selectedFunction == null ) || 
         (  n >= functionDefaults.getDefaultTabStepsCount() ) )
       return null;
    BigDecimal tabDefault = selectedFunction.getTabStep();
    BigDecimal tab = tabDefault.multiply
        ( functionDefaults.getDefaultTabSteps()[n] );
    return tab;
    }

private BigDecimal tabDecLimit()
    {
    int n = selectedTab - 1;
    if ( ( selectedFunction == null ) || ( n < 0 ) )  return null;
    BigDecimal tabDefault = selectedFunction.getTabStep();
    BigDecimal tab = tabDefault.multiply
        ( functionDefaults.getDefaultTabSteps()[n] );
    return tab;
    }

private BigDecimal[] xIncLimit()
    {
    int n = selectedXrange + 1;
    if ( ( selectedFunction == null ) || 
         ( n >= functionDefaults.getDefaultXrangeCount() ) )  return null;
    BigDecimal defaultXmin = selectedFunction.getXmin();
    BigDecimal defaultXmax = selectedFunction.getXmax();
    BigDecimal xmin = defaultXmin.multiply
        ( functionDefaults.getDefaultXrange()[n] );
    BigDecimal xmax = defaultXmax.multiply
        ( functionDefaults.getDefaultXrange()[n] );
    return new BigDecimal[] { xmin, xmax };
    }

private BigDecimal[] xDecLimit()
    {
    int n = selectedXrange - 1;
    if ( ( selectedFunction == null ) || ( n < 0 ) )  return null;
    BigDecimal defaultXmin = selectedFunction.getXmin();
    BigDecimal defaultXmax = selectedFunction.getXmax();
    BigDecimal xmin = defaultXmin.multiply
        ( functionDefaults.getDefaultXrange()[n] );
    BigDecimal xmax = defaultXmax.multiply
        ( functionDefaults.getDefaultXrange()[n] );
    return new BigDecimal[] { xmin, xmax };
    }

private BigDecimal[] yIncLimit()
    {
    int n = selectedYrange + 1;
    if ( ( selectedFunction == null ) || 
         ( n >= functionDefaults.getDefaultYrangeCount() ) )  return null;
    BigDecimal defaultYmin = selectedFunction.getYmin();
    BigDecimal defaultYmax = selectedFunction.getYmax();
    BigDecimal ymin = defaultYmin.multiply
        ( functionDefaults.getDefaultYrange()[n] );
    BigDecimal ymax = defaultYmax.multiply
        ( functionDefaults.getDefaultYrange()[n] );
    return new BigDecimal[] { ymin, ymax };
    }

private BigDecimal[] yDecLimit()
    {
    int n = selectedYrange - 1;
    if ( ( selectedFunction == null ) || ( n < 0 ) )  return null;
    BigDecimal defaultYmin = selectedFunction.getYmin();
    BigDecimal defaultYmax = selectedFunction.getYmax();
    BigDecimal ymin = defaultYmin.multiply
        ( functionDefaults.getDefaultYrange()[n] );
    BigDecimal ymax = defaultYmax.multiply
        ( functionDefaults.getDefaultYrange()[n] );
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
