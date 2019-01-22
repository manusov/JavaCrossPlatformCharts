package integrator.controller;

import java.math.BigDecimal;

public class MathRun implements RunInterface
{

public MathRun()
    {
    selectedFunction = FUNCTIONS_LIST[0];
    initParms();
    initFunction( selectedFunction );
    }

// list of supported functions
private final Function[] FUNCTIONS_LIST =
        {
        new X()         ,
        new XPM1()      ,
        new XP2()       ,
        new XP3()       ,
        new SQRTX()     ,
        new EXPX()      ,
        new XP10()      ,
        new LNX()       ,
        new LGX()       ,
        new SINX()      ,
        new COSX()      ,
        new TGX()       ,
        new CTGX()      ,
        new SECX()      ,
        new COSECX()    ,
        new SHX()       ,
        new CHX()       ,
        new THX()       ,
        new CTHX()      ,
        new SECHX()     ,
        new COSECHX()   ,
        new ARCSINX()   ,
        new ARCCOSX()   ,
        new ARCTGX()    ,
        new ARCCTGX()   ,
        new ARCSECX()   ,
        new ARCCOSECX() ,
        new ARSHX()     ,
        new ARCHX()     ,
        new ARTHX()     ,
        new ARCTHX()    ,
        new ARSCHX()    ,
        new ARCSCHX()   ,
        new RAND()      ,
        };

// method get list of math. functions descriptions
@Override public Function[] getFunctionsList()
    {
    return FUNCTIONS_LIST;
    }

// method get one math. function description from list, by index
@Override public Function getFunction( int i )
    {
    Function[] f = getFunctionsList();
    if ( i >= f.length ) i = 0;
    return f[i];
    }

// method gets function calculated data, as pairs X,Y
@Override public double[][] getFunctionArray()
    {
    return functionArray;
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
private final GCOLOR DEFAULT_COLOR = GCOLOR.BACKGROUND_BLACK;

// variables for functions visual mode options
private GCOLOR colorScheme;
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
@Override public GCOLOR getColorScheme()      { return colorScheme; }
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
@Override public void setColorScheme(GCOLOR cs)     { colorScheme = cs; }
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
@Override public GCOLOR getDefaultColorScheme() { return DEFAULT_COLOR; }

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
@Override public void sendControl( CONTROLS control )
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
    // int n = (int)m;                // n = number of tabulation steps
    int n = (int)m + 1;               // n = number of tabulation steps
    array = f.function(x1, dx, n);    // call target method and return
    return array;
    }
}

// Functions descriptions as internal classes, peer extends possible,
// see detail parameters comments at abstract Function.java class

class X extends Function  // Function description class: y=x
{ 
@Override public BigDecimal getXmin()       { return new BigDecimal("-4.0");  }
@Override public BigDecimal getXmax()       { return new BigDecimal("4.0");   }
@Override public BigDecimal getYmin()       { return new BigDecimal("-4.0");  }
@Override public BigDecimal getYmax()       { return new BigDecimal("4.0");   }
@Override public BigDecimal getXstepSmall() { return new BigDecimal("0.2");   }
@Override public BigDecimal getXstepBig()   { return new BigDecimal("1.0");   }
@Override public BigDecimal getYstepSmall() { return new BigDecimal("0.2");   }
@Override public BigDecimal getYstepBig()   { return new BigDecimal("1.0");   }
@Override public BigDecimal getTabStep()    { return new BigDecimal("0.001"); }
@Override public String getNameX()          { return "x";                     }
@Override public String getNameY()          { return "y = x";                 }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = x;
        }
    return y;    
    }
}

class XPM1 extends X  // Function description class: y=1/x
{
@Override public BigDecimal getXmin()       { return new BigDecimal("-3.0"); }
@Override public BigDecimal getXmax()       { return new BigDecimal("3.0");  }
@Override public BigDecimal getYmin()       { return new BigDecimal("-2.0"); }
@Override public BigDecimal getYmax()       { return new BigDecimal("2.0");  }
@Override public BigDecimal getXstepSmall() { return new BigDecimal("0.1");  }
@Override public BigDecimal getXstepBig()   { return new BigDecimal("1.0");  }
@Override public BigDecimal getYstepSmall() { return new BigDecimal("0.1");  }
@Override public BigDecimal getYstepBig()   { return new BigDecimal("0.5");  }
// @Override public BigDecimal getTabStep()  { return new BigDecimal("0.001"); }
@Override public String getNameY()          { return "y = 1/x";              }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / x;
        }
    return y;    
    }
}

class XP2 extends X  // Function description class: y=x^2
{
@Override public String getNameY() // { return "<html>y = x<sup>2"; }
                                   { return "x = x^2"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = x*x;
        }
    return y;    
    }
}

class XP3 extends X  // Function description class: y=x^3
{
@Override public String getNameY() // { return "<html>y = x<sup>3"; }
                                   { return "y = x^3"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = x*x*x;
        }
    return y;    
    }
}

class SQRTX extends X  // Function description class: y=sqrt(x)
{
@Override public BigDecimal getXmin()       { return new BigDecimal("-4.0"); }
@Override public BigDecimal getXmax()       { return new BigDecimal("4.0");  }
@Override public BigDecimal getYmin()       { return new BigDecimal("-4.0"); }
@Override public BigDecimal getYmax()       { return new BigDecimal("4.0");  }
@Override public BigDecimal getXstepSmall() { return new BigDecimal("0.1");  }
@Override public BigDecimal getYstepSmall() { return new BigDecimal("0.1");  }
// @Override public BigDecimal getTabStep() { return new BigDecimal("0.001"); }
@Override public String getNameY() // { return "<html>y = x<sup>3"; }
                                       { return "y = sqrt(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.sqrt(x);
        }
    return y;    
    }
}

class EXPX extends X  // Function description class: y=e^x
{
@Override public String getNameY() // { return "<html>y = x<sup>2"; }
                                   { return "y = exp(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.exp(x);
        }
    return y;    
    }
}

class XP10 extends X  // Function description class: y=e^x
{
@Override public String getNameY() // { return "<html>y = x<sup>2"; }
                                   { return "y = 10^x"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.pow(10, x);
        }
    return y;    
    }
}

class LNX extends X  // Function description class: y=ln(x)
{
@Override public BigDecimal getYmin()       { return new BigDecimal("-1.0"); }
@Override public BigDecimal getYmax()       { return new BigDecimal("1.0");  }
@Override public BigDecimal getYstepSmall() { return new BigDecimal("0.02"); }
@Override public BigDecimal getYstepBig()   { return new BigDecimal("0.1");  }
@Override public String getNameY() // { return "<html>y = x<sup>2"; }
                                   { return "y = ln(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.log(x);
        }
    return y;    
    }
}

class LGX extends LNX  // Function description class: y=lg(x)
{
@Override public String getNameY() // { return "<html>y = x<sup>2"; }
                                   { return "y = lg(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.log10(x);
        }
    return y;    
    }
}

class SINX extends X  // Function description class: y=sqrt(x)
{
@Override public BigDecimal getXmin()       { return new BigDecimal("-5.0"); }
@Override public BigDecimal getXmax()       { return new BigDecimal("5.0");  }
@Override public BigDecimal getYmin()       { return new BigDecimal("-1.5"); }
@Override public BigDecimal getYmax()       { return new BigDecimal("1.5");  }
@Override public BigDecimal getXstepSmall() { return new BigDecimal("0.1");  }
@Override public BigDecimal getXstepBig()   { return new BigDecimal("1.0");  }
@Override public BigDecimal getYstepSmall() { return new BigDecimal("0.05");  }
@Override public BigDecimal getYstepBig()   { return new BigDecimal("0.5");  }
// @Override public BigDecimal getTabStep()    { return new BigDecimal("0.01"); }
@Override public String getNameY()          { return "y = sin(x)";           }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.sin(x);
        }
    return y;    
    }
}

class COSX extends SINX  // Function description class: y=cos(x)
{
@Override public String getNameY() { return "y = cos(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.cos(x);
        }
    return y;    
    }
}

class TGX extends SINX  // Function description class: y=tg(x)
{
@Override public BigDecimal getXmin()       { return new BigDecimal("-4.0"); }
@Override public BigDecimal getXmax()       { return new BigDecimal("4.0");  }
@Override public BigDecimal getYmin()       { return new BigDecimal("-3.0"); }
@Override public BigDecimal getYmax()       { return new BigDecimal("3.0");  }
@Override public BigDecimal getXstepSmall() { return new BigDecimal("0.1");  }
@Override public BigDecimal getXstepBig()   { return new BigDecimal("1.0");  }
@Override public BigDecimal getYstepSmall() { return new BigDecimal("0.1");  }
@Override public BigDecimal getYstepBig()   { return new BigDecimal("1.0");  }
// @Override public BigDecimal getTabStep() { return new BigDecimal("0.001"); }
@Override public String getNameY()          { return "y = tg(x)";            }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.tan(x);
        }
    return y;    
    }
}

class CTGX extends TGX  // Function description class: y=lg(x)
{
@Override public String getNameY() { return "y = ctg(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.cos(x) / Math.sin(x);
        }
    return y;    
    }
}

class SECX extends TGX  // Function description class: y=sec(x)
{
@Override public String getNameY() { return "y = sec(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.cos(x);
        }
    return y;    
    }
}

class COSECX extends TGX   // Function description class: y=sec(x)
{
@Override public String getNameY() { return "y = cosec(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.sin(x);
        }
    return y;    
    }
}

class SHX extends TGX  // Function description class: y=sh(x)
{
@Override public String getNameY() { return "y = sh(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.sinh(x);
        }
    return y;    
    }
}

class CHX extends TGX  // Function description class: y=ch(x)
{
@Override public String getNameY() { return "y = ch(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.cosh(x);
        }
    return y;    
    }
}

class THX extends TGX  // Function description class: y=th(x)
{
@Override public String getNameY() { return "y = th(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.tanh(x);
        }
    return y;    
    }
}

class CTHX extends TGX  // Function description class: y=cth(x)
{
@Override public String getNameY() { return "y = cth(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.tanh(x);
        }
    return y;    
    }
}

class SECHX extends TGX  // Function description class: y=sech(x)
{
@Override public String getNameY() { return "y = sech(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.cosh(x);
        }
    return y;    
    }
}

class COSECHX extends TGX  // Function description class: y=cosech(x)
{
@Override public String getNameY() { return "y = cosech(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.sinh(x);
        }
    return y;    
    }
}

class ARCSINX extends TGX  // Function description class: y=arcsin(x)
{
@Override public String getNameY() { return "y = arcsin(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.asin(x);
        }
    return y;    
    }
}

class ARCCOSX extends TGX  // Function description class: y=arccos(x)
{
@Override public String getNameY() { return "y = arccos(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 1.0 / Math.acos(x);
        }
    return y;    
    }
}

class ARCTGX extends TGX  // Function description class: y=arctg(x)
{
@Override public String getNameY() { return "y = arctg(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.atan(x);
        }
    return y;    
    }
}

class ARCCTGX extends TGX  // Function description class: y=arcctg(x)
{
@Override public String getNameY() { return "y = arcctg(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.PI / 2.0 - Math.atan(x);
        }
    return y;    
    }
}

class ARCSECX extends TGX  // Function description class: y=arcsec(x)
{
@Override public String getNameY() { return "y = arcsec(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.acos( 1.0 / x );
        }
    return y;    
    }
}

class ARCCOSECX extends TGX  // Function description class: y=arccosec(x)
{
@Override public String getNameY() { return "y = arccosec(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.asin( 1.0 / x );
        }
    return y;    
    }
}

class ARSHX extends TGX  // Function description class: y=arsh(x)
{
@Override public String getNameY() { return "y = arsh(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.log( x + Math.sqrt( x * x + 1 ) );
        }
    return y;    
    }
}

class ARCHX extends TGX  // Function description class: y=arch(x)
{
@Override public String getNameY() { return "y = arch(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.log( x + Math.sqrt( x * x - 1 ) );
        }
    return y;    
    }
}

class ARTHX extends TGX  // Function description class: y=arth(x)
{
@Override public String getNameY() { return "y = arth(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 0.5 * Math.log( ( 1 + x ) / ( 1 - x ) );
        }
    return y;    
    }
}

class ARCTHX extends TGX  // Function description class: y=arcth(x)
{
@Override public String getNameY() { return "y = arcth(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = 0.5 * Math.log( ( x + 1 ) / ( x - 1 ) );
        }
    return y;    
    }
}

class ARSCHX extends TGX  // Function description class: y=arsch(x)
{
@Override public String getNameY() { return "y = arsch(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        y[1][i] = Math.log( ( 1 + Math.sqrt( 1 - x * x ) ) / x );
        }
    return y;    
    }
}

class ARCSCHX extends TGX  // Function description class: y=arcsch(x)
{
@Override public String getNameY() { return "y = arcsch(x)"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x = x1 + dx * i;
        if ( x == 0.0 )
            {
            y[1][i] = Double.NaN;
            }
        else if ( x < 0.0 )
            {
            y[1][i] = Math.log( ( 1 - Math.sqrt( 1 + x * x ) ) / x );
            }
        else
            {
            y[1][i] = Math.log( ( 1 + Math.sqrt( 1 + x * x ) ) / x );
            }
        }
    return y;    
    }
}

class RAND extends SINX  // Function description class: y=Random
{
@Override public String getNameY() { return "random number"; }
@Override public double[][] function ( double x, double dx, int n )
    {
    double[][] y = new double[2][n];
    double x1 = x;
    for(int i=0; i<n; i++)
        {
        y[0][i] = x1 + dx * i;
        y[1][i] = Math.random();
        }
    return y;    
    }
}


    


