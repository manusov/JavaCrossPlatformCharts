// List of math. functions with calculation method, names and
// default drawing parameters.
// IMPORTANT NOTE FOR TABULATION CYCLES:
// x += dx;  // BAD: this causes additivity of approximation error
// x = x1 + dx * i;  // IMPROVE PRECISION QUALITY

package charts.model;

import java.math.BigDecimal;

public class FunctionsList 
{

// list of supported functions
private final static Function[] FUNCTIONS_LIST =
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
public Function[] getFunctionsList()
    {
    return FUNCTIONS_LIST;
    }

// method get one math. function description from list, by index
public Function getFunction(int i)
    {
    if ( i >= FUNCTIONS_LIST.length ) i = 0;
    return FUNCTIONS_LIST[i];
    }

// Functions descriptions as internal classes, peer extends possible,
// see detail parameters comments at abstract Function.java class

static class X extends Function  // Function description class: y=x
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

static class XPM1 extends X  // Function description class: y=1/x
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

static class XP2 extends X  // Function description class: y=x^2
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

static class XP3 extends X  // Function description class: y=x^3
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

static class SQRTX extends X  // Function description class: y=sqrt(x)
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

static class EXPX extends X  // Function description class: y=e^x
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

static class XP10 extends X  // Function description class: y=e^x
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

static class LNX extends X  // Function description class: y=ln(x)
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

static class LGX extends LNX  // Function description class: y=lg(x)
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

static class SINX extends X  // Function description class: y=sqrt(x)
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

static class COSX extends SINX  // Function description class: y=cos(x)
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

static class TGX extends SINX  // Function description class: y=tg(x)
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

static class CTGX extends TGX  // Function description class: y=lg(x)
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

static class SECX extends TGX  // Function description class: y=sec(x)
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

static class COSECX extends TGX   // Function description class: y=sec(x)
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

static class SHX extends TGX  // Function description class: y=sh(x)
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

static class CHX extends TGX  // Function description class: y=ch(x)
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

static class THX extends TGX  // Function description class: y=th(x)
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

static class CTHX extends TGX  // Function description class: y=cth(x)
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

static class SECHX extends TGX  // Function description class: y=sech(x)
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

static class COSECHX extends TGX  // Function description class: y=cosech(x)
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

static class ARCSINX extends TGX  // Function description class: y=arcsin(x)
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

static class ARCCOSX extends TGX  // Function description class: y=arccos(x)
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

static class ARCTGX extends TGX  // Function description class: y=arctg(x)
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

static class ARCCTGX extends TGX  // Function description class: y=arcctg(x)
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

static class ARCSECX extends TGX  // Function description class: y=arcsec(x)
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

static class ARCCOSECX extends TGX  // Function description class: y=arccosec(x)
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

static class ARSHX extends TGX  // Function description class: y=arsh(x)
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

static class ARCHX extends TGX  // Function description class: y=arch(x)
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

static class ARTHX extends TGX  // Function description class: y=arth(x)
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

static class ARCTHX extends TGX  // Function description class: y=arcth(x)
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

static class ARSCHX extends TGX  // Function description class: y=arsch(x)
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

static class ARCSCHX extends TGX  // Function description class: y=arcsch(x)
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

static class RAND extends SINX  // Function description class: y=Random
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


}
