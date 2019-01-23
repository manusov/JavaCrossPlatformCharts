/*

Interconnect class for function, calculation module and
function drawing model.
This class can handling control messages for
select mathematics function visualization option.

*/

package charts.model;

import java.math.BigDecimal;

public class FunctionCore 
{
// control constants for function tabulation
private final static BigDecimal[] TAB_STEP = new BigDecimal[]
            {
            new BigDecimal("100.0")  ,
            new BigDecimal("10.0")   ,
            new BigDecimal("1.0")   ,
            new BigDecimal("0.1")  ,
            new BigDecimal("0.05")
            };
private final static int TAB_STEP_COUNT = TAB_STEP.length;
private final static int TAB_STEP_DEFAULT = 2;
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
// classes variables
private final ViewerState   viewerState;
private final FunctionCalc  functionCalc;
private final FunctionsList functionsList;
private Function selectedFunction = null;
    
public FunctionCore()
    {
    // functionState = new FunctionState();  // function state class
    viewerState   = new ViewerState();    // visualizer state class
    functionCalc  = new FunctionCalc();   // calculator assist method
    functionsList = new FunctionsList();  // descriptions of functions
    }
    
public enum CONTROLS { X_AXIS_UP   , X_AXIS_DOWN , Y_AXIS_LEFT , Y_AXIS_RIGHT , 
                       X_SCALE_INC , X_SCALE_DEC , Y_SCALE_INC , Y_SCALE_DEC  ,
                       TAB_INC , TAB_DEC , RESET };
    
// public FunctionState getFunctionState() { return functionState; }
public ViewerState getViewerState()     { return viewerState;   }
public FunctionCalc getFunctionCalc()   { return functionCalc;  }
public FunctionsList getFunctionsList() { return functionsList; }

private void init(Function f)
    {
    // parameters, required for calculation
    BigDecimal x1, x2, dx;
    // double[][] result;
    // set visual options
    // requires fix bug with clear button cross-change combo box
    // viewerState.setColorScheme(ViewerState.GCOLOR.BACKGROUND_BLACK);
    // set parameters by selected function description
    viewerState.setXmin( x1 = f.getXmin() );
    viewerState.setXmax( x2 = f.getXmax() );
    viewerState.setYmin( f.getYmin() );
    viewerState.setYmax( f.getYmax() );
    viewerState.setXstepSmall( f.getXstepSmall() );
    viewerState.setXstepBig( f.getXstepBig() );
    viewerState.setYstepSmall( f.getYstepSmall() );
    viewerState.setYstepBig( f.getYstepBig() );
    viewerState.setTabStep( dx = f.getTabStep() );
    viewerState.setNameX( f.getNameX() );
    viewerState.setNameY( f.getNameY() );
    // set scaling and positioning constants
    selectedTab = TAB_STEP_DEFAULT;
    selectedXrange = X_RANGE_DEFAULT;
    selectedYrange = Y_RANGE_DEFAULT;
    // calculate function and set result array
    // result = functionCalc.calculate(x1, x2, dx, f);
    functionCalc.calculate(x1, x2, dx, f);
    // functionState.setFunction(result);
    }

public void sendFunction(int i)
    {
    // get function description , selected by index
    Function f = functionsList.getFunction(i);
    selectedFunction = f;
    init(f);
    }

private BigDecimal tabIncLimit()
    {
    int n = selectedTab + 1;
    if ( ( selectedFunction == null ) || ( viewerState == null ) ||
         (  n >= TAB_STEP_COUNT ) )  return null;
    BigDecimal tabDefault = selectedFunction.getTabStep();
    BigDecimal tab = tabDefault.multiply( TAB_STEP [n] );
    return tab;
    }

private BigDecimal tabDecLimit()
    {
    int n = selectedTab - 1;
    if ( ( selectedFunction == null ) || ( viewerState == null ) ||
         ( n < 0 ) )  return null;
    BigDecimal tabDefault = selectedFunction.getTabStep();
    BigDecimal tab = tabDefault.multiply( TAB_STEP [n] );
    return tab;
    }

private BigDecimal[] xIncLimit()
    {
    int n = selectedXrange + 1;
    if ( ( selectedFunction == null ) || ( viewerState == null ) ||
         ( n >= X_RANGE_COUNT ) )  return null;
    BigDecimal defaultXmin = selectedFunction.getXmin();
    BigDecimal defaultXmax = selectedFunction.getXmax();
    BigDecimal xmin = defaultXmin.multiply( X_RANGE [n] );
    BigDecimal xmax = defaultXmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { xmin, xmax };
    }

private BigDecimal[] xDecLimit()
    {
    int n = selectedXrange - 1;
    if ( ( selectedFunction == null ) || ( viewerState == null ) ||
         ( n < 0 ) )  return null;
    BigDecimal defaultXmin = selectedFunction.getXmin();
    BigDecimal defaultXmax = selectedFunction.getXmax();
    BigDecimal xmin = defaultXmin.multiply( X_RANGE [n] );
    BigDecimal xmax = defaultXmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { xmin, xmax };
    }

private BigDecimal[] yIncLimit()
    {
    int n = selectedYrange + 1;
    if ( ( selectedFunction == null ) || ( viewerState == null ) ||
         ( n >= Y_RANGE_COUNT ) )  return null;
    BigDecimal defaultYmin = selectedFunction.getYmin();
    BigDecimal defaultYmax = selectedFunction.getYmax();
    BigDecimal ymin = defaultYmin.multiply( X_RANGE [n] );
    BigDecimal ymax = defaultYmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { ymin, ymax };
    }

private BigDecimal[] yDecLimit()
    {
    int n = selectedYrange - 1;
    if ( ( selectedFunction == null ) || ( viewerState == null ) ||
         ( n < 0 ) )  return null;
    BigDecimal defaultYmin = selectedFunction.getYmin();
    BigDecimal defaultYmax = selectedFunction.getYmax();
    BigDecimal ymin = defaultYmin.multiply( X_RANGE [n] );
    BigDecimal ymax = defaultYmax.multiply( X_RANGE [n] );
    return new BigDecimal[] { ymin, ymax };
    }

public void sendControl(CONTROLS control)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) ) return;
    
    switch(control)
        {
        case X_SCALE_INC:
            {
            BigDecimal[] bd = xIncLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedXrange++;
            viewerState.setXmin( bd[0] );
            viewerState.setXmax( bd[1] );
            break;
            }
        case X_SCALE_DEC:
            {
            BigDecimal[] bd = xDecLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedXrange--;
            viewerState.setXmin( bd[0] );
            viewerState.setXmax( bd[1] );
            break;
            }
        case Y_SCALE_INC:
            {
            BigDecimal[] bd = yIncLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedYrange++;
            viewerState.setYmin( bd[0] );
            viewerState.setYmax( bd[1] );
            break;
            }
        case Y_SCALE_DEC:
            {
            BigDecimal[] bd = yDecLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedYrange--;
            viewerState.setYmin( bd[0] );
            viewerState.setYmax( bd[1] );
            break;
            }
        case TAB_INC:
            {
            BigDecimal bd = tabIncLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedTab++;
            viewerState.setTabStep( bd );
            break;
            }
        case TAB_DEC:
            {
            BigDecimal bd = tabDecLimit();
            if ( bd == null ) break;  // redundant check if button gray
            selectedTab--;
            viewerState.setTabStep( bd );
            break;
            }
        case RESET:
            {
            Function f = selectedFunction;  // try use current function
            if ( f == null )
                {                      // if not assigned, use first function
                f = functionsList.getFunction(0);
                }
            init(f);  // set defaults
            break;
            }
        }
    // recalculate function with new parameters
    functionCalc.calculate( viewerState.getXmin() , viewerState.getXmax() ,
                            viewerState.getTabStep() , selectedFunction );
    }            
                
public boolean isTabIncable()
    {
    return tabIncLimit() != null;
    }
    
public boolean isTabDecable()
    {
    return tabDecLimit() != null;
    }
        
public boolean isXincable()
    {
    return xIncLimit() != null;
    }
    
public boolean isXdecable()
    {
    return xDecLimit() != null;
    }

public boolean isYincable()
    {
    return yIncLimit() != null;
    }
    
public boolean isYdecable()
    {
    return yDecLimit() != null;
    }

}


/*

// OLD VARIANT WITH DIRECT VALUE MODIFICATION, WITHOUT PRE-DEFINED ARRAYS.

public class FunctionCore 
{
// control constants
private final static String TAB_INC_LIMIT = "0.1";
private final static String TAB_DEC_LIMIT = "100.0";
private final static String X_INC_LIMIT = "2.0";
private final static String X_DEC_LIMIT = "0.5";
private final static String Y_INC_LIMIT = "2.0";
private final static String Y_DEC_LIMIT = "0.5";
    
// private final FunctionState functionState;
private final ViewerState   viewerState;
private final FunctionCalc  functionCalc;
private final FunctionsList functionsList;
private Function selectedFunction = null;
    
public FunctionCore()
    {
    // functionState = new FunctionState();  // function state class
    viewerState   = new ViewerState();    // visualizer state class
    functionCalc  = new FunctionCalc();   // calculator assist method
    functionsList = new FunctionsList();  // descriptions of functions
    }
    
public enum CONTROLS { X_AXIS_UP   , X_AXIS_DOWN , Y_AXIS_LEFT , Y_AXIS_RIGHT , 
                       X_SCALE_INC , X_SCALE_DEC , Y_SCALE_INC , Y_SCALE_DEC  ,
                       TAB_INC , TAB_DEC , RESET };
    
// public FunctionState getFunctionState() { return functionState; }
public ViewerState getViewerState()     { return viewerState;   }
public FunctionCalc getFunctionCalc()   { return functionCalc;  }
public FunctionsList getFunctionsList() { return functionsList; }

private void init(Function f)
    {
    // parameters, required for calculation
    BigDecimal x1, x2, dx;
    // double[][] result;
    // set visual options
    // requires fix bug with clear button cross-change combo box
    // viewerState.setColorScheme(ViewerState.GCOLOR.BACKGROUND_BLACK);
    // set parameters by selected function description
    viewerState.setXmin( x1 = f.getXmin() );
    viewerState.setXmax( x2 = f.getXmax() );
    viewerState.setYmin( f.getYmin() );
    viewerState.setYmax( f.getYmax() );
    viewerState.setXstepSmall( f.getXstepSmall() );
    viewerState.setXstepBig( f.getXstepBig() );
    viewerState.setYstepSmall( f.getYstepSmall() );
    viewerState.setYstepBig( f.getYstepBig() );
    viewerState.setTabStep( dx = f.getTabStep() );
    viewerState.setNameX( f.getNameX() );
    viewerState.setNameY( f.getNameY() );
    // calculate function and set result array
    // result = functionCalc.calculate(x1, x2, dx, f);
    functionCalc.calculate(x1, x2, dx, f);
    // functionState.setFunction(result);
    }

public void sendFunction(int i)
    {
    // get function description , selected by index
    Function f = functionsList.getFunction(i);
    selectedFunction = f;
    init(f);
    }

private class LimitResult
{
final boolean b;
final BigDecimal bd1, bd2;
LimitResult( boolean x1, BigDecimal x2 )
    {
    b = x1;
    bd1 = x2;
    bd2 = null;
    }
LimitResult( boolean x1, BigDecimal x2, BigDecimal x3 )
    {
    b = x1;
    bd1 = x2;
    bd2 = x3;
    }
}

private LimitResult tabIncLimit(BigDecimal tab)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) )
       { return new LimitResult(false, tab); }
    // increase quality, small dx means increase number of steps
    tab = tab.multiply(new BigDecimal("0.1"));
    BigDecimal tabDefault = selectedFunction.getTabStep();
    // make minimum dx limit
    BigDecimal tabMin = tabDefault.multiply(new BigDecimal( TAB_INC_LIMIT ));
    boolean b = tab.compareTo(tabMin) >= 0;
    // TODO: MAKE OUTPUT VALUE WITHOUT CUMULATIVE PRECISION BIGDECIMAL
    // WITH SEPARATE MULTIPLIER
    return new LimitResult(b, tab);
    }

private LimitResult tabDecLimit(BigDecimal tab)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) )
       { return new LimitResult(false, tab); }
    // decrease quality, big dx means decrease number of steps
    tab = tab.multiply(new BigDecimal("10.0"));
    BigDecimal tabDefault = selectedFunction.getTabStep();
    // make maximum dx limit
    BigDecimal tabMax = tabDefault.multiply(new BigDecimal( TAB_DEC_LIMIT ));
    boolean b = tab.compareTo(tabMax) <= 0;
    // TODO: MAKE OUTPUT VALUE WITHOUT CUMULATIVE PRECISION BIGDECIMAL
    // WITH SEPARATE MULTIPLIER
    return new LimitResult(b, tab);
    }

private LimitResult xIncLimit(BigDecimal x1, BigDecimal x2)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) )
       { return new LimitResult(false, x1, x2); }
    BigDecimal minDefault = selectedFunction.getXmin();
    BigDecimal maxDefault = selectedFunction.getXmax();
    x1 = x1.multiply(new BigDecimal(2.0));
    x2 = x2.multiply(new BigDecimal(2.0));
    BigDecimal xMin = minDefault.multiply(new BigDecimal( X_INC_LIMIT ));
    BigDecimal xMax = maxDefault.multiply(new BigDecimal( X_INC_LIMIT ));
    boolean b1 = x1.compareTo(xMin) >= 0;
    boolean b2 = x2.compareTo(xMax) <= 0;
    boolean b = b1 & b2;
    // TODO: MAKE OUTPUT VALUE WITHOUT CUMULATIVE PRECISION BIGDECIMAL
    // WITH SEPARATE MULTIPLIER
    return new LimitResult(b, x1, x2);
    }

private LimitResult xDecLimit(BigDecimal x1, BigDecimal x2)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) )
       { return new LimitResult(false, x1, x2); }
    BigDecimal minDefault = selectedFunction.getXmin();
    BigDecimal maxDefault = selectedFunction.getXmax();
    x1 = x1.multiply(new BigDecimal(0.5));
    x2 = x2.multiply(new BigDecimal(0.5));
    BigDecimal xMin = minDefault.multiply(new BigDecimal( X_DEC_LIMIT ));
    BigDecimal xMax = maxDefault.multiply(new BigDecimal( X_DEC_LIMIT ));
    boolean b1 = x1.compareTo(xMin) <= 0;
    boolean b2 = x2.compareTo(xMax) >= 0;
    boolean b = b1 & b2;
    // TODO: MAKE OUTPUT VALUE WITHOUT CUMULATIVE PRECISION BIGDECIMAL
    // WITH SEPARATE MULTIPLIER
    return new LimitResult(b, x1, x2);
    }

private LimitResult yIncLimit(BigDecimal y1, BigDecimal y2)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) )
       { return new LimitResult(false, y1, y2); }
    BigDecimal minDefault = selectedFunction.getYmin();
    BigDecimal maxDefault = selectedFunction.getYmax();
    y1 = y1.multiply(new BigDecimal(2.0));
    y2 = y2.multiply(new BigDecimal(2.0));
    BigDecimal yMin = minDefault.multiply(new BigDecimal( Y_INC_LIMIT ));
    BigDecimal yMax = maxDefault.multiply(new BigDecimal( Y_INC_LIMIT ));
    boolean b1 = y1.compareTo(yMin) >= 0;
    boolean b2 = y2.compareTo(yMax) <= 0;
    boolean b = b1 & b2;
    // TODO: MAKE OUTPUT VALUE WITHOUT CUMULATIVE PRECISION BIGDECIMAL
    // WITH SEPARATE MULTIPLIER
    return new LimitResult(b, y1, y2);
    }

private LimitResult yDecLimit(BigDecimal y1, BigDecimal y2)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) )
       { return new LimitResult(false, y1, y2); }
    BigDecimal minDefault = selectedFunction.getYmin();
    BigDecimal maxDefault = selectedFunction.getYmax();
    y1 = y1.multiply(new BigDecimal(0.5));
    y2 = y2.multiply(new BigDecimal(0.5));
    BigDecimal yMin = minDefault.multiply(new BigDecimal( Y_DEC_LIMIT ));
    BigDecimal yMax = maxDefault.multiply(new BigDecimal( Y_DEC_LIMIT ));
    boolean b1 = y1.compareTo(yMin) <= 0;
    boolean b2 = y2.compareTo(yMax) >= 0;
    boolean b = b1 & b2;
    // TODO: MAKE OUTPUT VALUE WITHOUT CUMULATIVE PRECISION BIGDECIMAL
    // WITH SEPARATE MULTIPLIER
    return new LimitResult(b, y1, y2);
    }


public void sendControl(CONTROLS control)
    {
    if ( ( selectedFunction == null ) || ( viewerState == null ) ) return;
    
    BigDecimal tab = viewerState.getTabStep();
    BigDecimal x1  = viewerState.getXmin();
    BigDecimal x2  = viewerState.getXmax();
    BigDecimal y1  = viewerState.getYmin();
    BigDecimal y2  = viewerState.getYmax();
    
    switch(control)
        {
        case X_SCALE_INC:
            {
            LimitResult lr = xIncLimit(x1, x2);
            if ( ! lr.b ) break;  // redundant check if button gray
            viewerState.setXmin( lr.bd1 );
            viewerState.setXmax( lr.bd2 );
            break;
            }
        case X_SCALE_DEC:
            {
            LimitResult lr = xDecLimit(x1, x2);
            if ( ! lr.b ) break;  // redundant check if button gray
            viewerState.setXmin( lr.bd1 );
            viewerState.setXmax( lr.bd2 );
            break;
            }
        case Y_SCALE_INC:
            {
            LimitResult lr = yIncLimit(y1, y2);
            if ( ! lr.b ) break;  // redundant check if button gray
            viewerState.setYmin( lr.bd1 );
            viewerState.setYmax( lr.bd2 );
            break;
            }
        case Y_SCALE_DEC:
            {
            LimitResult lr = yDecLimit(y1, y2);
            if ( ! lr.b ) break;  // redundant check if button gray
            viewerState.setYmin( lr.bd1 );
            viewerState.setYmax( lr.bd2 );
            break;
            }
        case TAB_INC:
            {
            LimitResult lr = tabIncLimit(tab);
            if ( ! lr.b ) break;  // redundant check if button gray
            viewerState.setTabStep( lr.bd1 );
            break;
            }
        case TAB_DEC:
            {
            LimitResult lr = tabDecLimit(tab);
            if ( ! lr.b ) break;  // redundant check if button gray
            viewerState.setTabStep( lr.bd1 );
            break;
            }
        case RESET:
            {
            Function f = selectedFunction;
            if ( f == null )
                {
                f = functionsList.getFunction(0);
                }
            init(f);
            break;
            }
        }
    // recalculate function with new parameters
    functionCalc.calculate( viewerState.getXmin() , viewerState.getXmax() ,
                            viewerState.getTabStep() , selectedFunction );
    }            
                
public boolean isTabIncable()
    {
    BigDecimal tab = viewerState.getTabStep();
    return tabIncLimit(tab).b;
    }
    
public boolean isTabDecable()
    {
    BigDecimal tab = viewerState.getTabStep();
    return tabDecLimit(tab).b;
    }
        
public boolean isXincable()
    {
    BigDecimal x1 = viewerState.getXmin();
    BigDecimal x2 = viewerState.getXmax();
    return xIncLimit(x1, x2).b;
    }
    
public boolean isXdecable()
    {
    BigDecimal x1 = viewerState.getXmin();
    BigDecimal x2 = viewerState.getXmax();
    return xDecLimit(x1, x2).b;
    }

public boolean isYincable()
    {
    BigDecimal y1 = viewerState.getYmin();
    BigDecimal y2 = viewerState.getYmax();
    return yIncLimit(y1, y2).b;
    }
    
public boolean isYdecable()
    {
    BigDecimal y1 = viewerState.getYmin();
    BigDecimal y2 = viewerState.getYmax();
    return yDecLimit(y1, y2).b;
    }

}


*/