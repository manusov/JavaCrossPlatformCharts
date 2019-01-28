/*

Data model class for scenario: Native application benchmarks.

*/

package integrator.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FileDataSource extends DataModel
{
// function Y=F(X)
private FileData fileData = new FileData();

// list of supported functions
private final Function[] DATA_SOURCE_LIST =
    {
    fileData
    };

// get list of supported functions
@Override public Function[] getFunctionsList()  
    { 
    return DATA_SOURCE_LIST;
    }

// create new function by data, loaded from file
@Override public void loadFunction
    ( int index, String xname, String yname, double[][] data )
    {
    if ( index < DATA_SOURCE_LIST.length )
        {
        DATA_SOURCE_LIST[index] = new FileData( xname, yname, data, this );
        }
    }
}

class FileData extends Function
{
private BigDecimal xmin       = new BigDecimal("-4.0");
private BigDecimal xmax       = new BigDecimal("4.0");
private BigDecimal ymin       = new BigDecimal("-4.0");
private BigDecimal ymax       = new BigDecimal("4.0");
private BigDecimal xstepsmall = new BigDecimal("0.2");
private BigDecimal xstepbig   = new BigDecimal("1.0");
private BigDecimal ystepsmall = new BigDecimal("0.2");
private BigDecimal ystepbig   = new BigDecimal("1.0");
private BigDecimal tabstep    = new BigDecimal("0.001");
private String namex          = "x";
private String namey          = "y=f(x)";
private double[][] function   = null;

// default constructor
public FileData()
    {
    
    }

// overloaded constructor for load function data from file
// with recalculate statistics
public FileData( String xname, String yname, double[][] data, DataModel dm )
    {
    // function Y=F(X) data X, Y
    function = data;
    // axis parameters setup by statistics
    Statistics st = dm.calculateStatistics( function );
    MathContext mc = new MathContext( 6, RoundingMode.UP );
    xmin = st.xMin;
    xmax = st.xMax;
    ymin = st.yMin;
    ymax = st.yMax;
    BigDecimal bd = new BigDecimal( 10 );
    xstepbig = ( xmax.subtract( xmin )).divide( bd, mc );
    xstepsmall = xstepbig.divide( bd, mc );
    ystepbig = ( ymax.subtract( ymin )).divide( bd, mc );
    ystepsmall = ystepbig.divide( bd, mc );
    tabstep    = new BigDecimal("0.001");
    // axis names strings
    namex = xname;
    namey = yname;
    }

@Override public BigDecimal getXmin()       { return xmin;       }
@Override public BigDecimal getXmax()       { return xmax;       }
@Override public BigDecimal getYmin()       { return ymin;       }
@Override public BigDecimal getYmax()       { return ymax;       }
@Override public BigDecimal getXstepSmall() { return xstepsmall; }
@Override public BigDecimal getXstepBig()   { return xstepbig;   }
@Override public BigDecimal getYstepSmall() { return ystepsmall; }
@Override public BigDecimal getYstepBig()   { return ystepbig;   }
@Override public BigDecimal getTabStep()    { return tabstep;    }
@Override public String getNameX()          { return namex;      }
@Override public String getNameY()          { return namey;      }

@Override public double[][] function ( double x, double dx, int n )
    {
    if ( function != null )
        {
        return function;
        }
    else
        {
        double[][] y = new double[2][n];
        for(int i=0; i<n; i++)
            {
            y[0][i] = x + dx * i;
            y[1][i] = 0.0;
            }
        return y;
        }
    }

}