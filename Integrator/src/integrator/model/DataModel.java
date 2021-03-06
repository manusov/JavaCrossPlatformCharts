/*

Data model class for analysing functions Y=F(X).
Contains implementation for statistics methods.

*/

package integrator.model;

import integrator.controller.RunInterface;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

public abstract class DataModel 
{

// get per-function defaults and values
abstract public Function[] getFunctionsList();

// load function data, optional supported, example = load data from file
// parent class provides dummy handler for this function
public void loadFunction
    ( int index, String xname, String yname, double[][] data )
    {
    // parent class provides dummy handler 
    }

// unified defaults, for all functions,
// this data can be customized by per-function data in the controller logic.
class UnifiedDefaults extends FunctionDefaults
{
@Override public BigDecimal getDefaultXmin()
    { return new BigDecimal("-1.0");  }
@Override public BigDecimal getDefaultXmax() 
    { return new BigDecimal("1.0");   }
@Override public BigDecimal getDefaultYmin() 
    { return new BigDecimal("-1.0");  }
@Override public BigDecimal getDefaultYmax() 
    { return new BigDecimal("1.0");   }
@Override public BigDecimal getDefaultXstepSmall()  
    { return new BigDecimal("0.02");  }
@Override public BigDecimal getDefaultXstepBig()    
    { return new BigDecimal("0.1");   }
@Override public BigDecimal getDefaultYstepSmall()  
    { return new BigDecimal("0.02");  }
@Override public BigDecimal getDefaultYstepBig()    
    { return new BigDecimal("0.1");   }
@Override public BigDecimal getDefaultTabStep()     
    { return new BigDecimal("0.001"); }

@Override public String getDefaultNameX()
    { return "X"; }
@Override public String getDefaultNameY()           
    { return "Y"; }
@Override public RunInterface.Gcolor getDefaultColor()    
    { return RunInterface.Gcolor.BACKGROUND_WHITE; }

@Override public BigDecimal[] getDefaultTabSteps()
    {
    return new BigDecimal[]
        {
        new BigDecimal("100.0") ,
        new BigDecimal("10.0")  ,
        new BigDecimal("1.0")   ,
        new BigDecimal("0.1")   ,
        new BigDecimal("0.05")
        };
    }

@Override public int getDefaultTabStepsCount()   
    { return getDefaultTabSteps().length; }
@Override public int getDefaultTabStepsDefault() 
    { return 2; }

@Override public BigDecimal[] getDefaultXrange()
    {
    return new BigDecimal[]
        {
        new BigDecimal("0.5")   ,
        new BigDecimal("1.0")   ,
        new BigDecimal("2.0")  ,
        };
    }

@Override public int getDefaultXrangeCount()    
    { return getDefaultXrange().length; }
@Override public int getDefaultXrangeDefault()  
    { return 1; }

@Override public BigDecimal[] getDefaultYrange()
    {
    return new BigDecimal[]
        {
        new BigDecimal("0.5")   ,
        new BigDecimal("1.0")   ,
        new BigDecimal("2.0")  ,
        };
    }

@Override public int getDefaultYrangeCount()   
    { return getDefaultYrange().length; }
@Override public int getDefaultYrangeDefault() 
    { return 1; }
    
}

// get unified defaults for all functions
public FunctionDefaults getUnifiedDefaults()
    {
    return new UnifiedDefaults(); 
    }

// statistic helper for function Y=F(X)
public Statistics calculateStatistics( double[][] f )
    {
    Statistics st = new Statistics();
    MathContext mc = new MathContext( 3 );
    BigDecimal blank = new BigDecimal( 0.0 );
    st.xAverage = blank;
    st.xMedian = blank;
    st.xMin = blank;
    st.xMax = blank;
    st.yAverage = blank;
    st.yMedian = blank;
    st.yMin = blank;
    st.yMax = blank;
    st.dAverage = blank;
    st.dMedian = blank;
    st.dMin = blank;
    st.dMax = blank;
    st.myAverage = blank;
    st.myMedian = blank;
    st.myMin = blank;
    st.myMax = blank;
    st.mdAverage = blank;
    st.mdMedian = blank;
    st.mdMin = blank;
    st.mdMax = blank;

    if ( ( f != null )       && ( f.length == 2 )   &&
         ( f[0] != null )    && ( f[1] != null )    &&
         ( f[0].length > 0 ) && ( f[1].length > 0 ) &&
         ( f[0].length == f[1].length ) )
        {

        findMinMaxAverageMedian( f[0], f[0].length );
        st.xMin = new BigDecimal( min, mc );
        st.xMax = new BigDecimal( max, mc );
        st.xAverage = new BigDecimal( average, mc );
        st.xMedian = new BigDecimal( median, mc );

        findMinMaxAverageMedian( f[1], f[0].length );
        st.yMin = new BigDecimal( min, mc );
        st.yMax = new BigDecimal( max, mc );
        st.yAverage = new BigDecimal( average, mc );
        st.yMedian = new BigDecimal( median, mc );
        
        int n = f[0].length - 1;
        double[] g = new double[n];
        int m = 0;
        for ( int i=0; i<n; i++ )
            {
            double x1 = f[0][i];
            double y1 = f[1][i];
            double x2 = f[0][i+1];
            double y2 = f[1][i+1];
            if ( Double.isFinite( x1 ) && Double.isFinite( y1 ) &&
                 Double.isFinite( x2 ) && Double.isFinite( y2 ) )
                {
                double dx = x2 - x1;
                double dy = y2 - y1;
                if ( dx != 0.0 )
                    {
                    g[m] = dy / dx;
                    m++;
                    }
                }
            }
            
        findMinMaxAverageMedian( g, m );
        st.dMin = new BigDecimal( min, mc );
        st.dMax = new BigDecimal( max, mc );
        st.dAverage = new BigDecimal( average, mc );
        st.dMedian = new BigDecimal( median, mc );
        
        for( int i=0; i<m; i++ )
            {
            g[i] = Math.abs( g[i] );
            }

        findMinMaxAverageMedian( g, m );
        st.mdMin = new BigDecimal( min, mc );
        st.mdMax = new BigDecimal( max, mc );
        st.mdAverage = new BigDecimal( average, mc );
        st.mdMedian = new BigDecimal( median, mc );
        
        double[] h = new double[n];
        for( int i =0; i<n; i++ )
            {
            h[i] = Math.abs( f[1][i] );
            }
        
        findMinMaxAverageMedian( h, n );
        st.myMin = new BigDecimal( min, mc );
        st.myMax = new BigDecimal( max, mc );
        st.myAverage = new BigDecimal( average, mc );
        st.myMedian = new BigDecimal( median, mc );
        
        }
    return st;    
    }

private double min, max, average, median;

private void findMinMaxAverageMedian( double[] a, int n )
    {
    min = Double.NaN;
    max = Double.NaN;
    average = Double.NaN;
    median = Double.NaN;
    // find first valid as start value for find minimum and maximum
    for( int i=0; i<n; i++ )
        {
        if ( Double.isFinite( a[i] ) )
            {
            min = a[i];
            max = a[i];
            average = 0.0;
            break;
            }
        }
    // find minimum, maximum and sum
    for( int i=0; i<n; i++ )
        {
        if ( Double.isFinite( a[i] ) )
            {
            if ( a[i] < min ) min = a[i];
            if ( a[i] > max ) max = a[i];
            average += a[i];
            }
        }
    // convert sum to average
    average /= n;
    // find median
    int m = 0;
    double[] temp = new double[n];
    for( int i=0; i<n; i++ )
        {
        if ( Double.isFinite( a[i] ) )
            {
            temp[m] = a[i];
            m++;  // m = length of used part, note m < temp.length
            }
        }
    // make and sort new array, m = it size
    temp = Arrays.copyOf( temp, m );
    Arrays.sort( temp );
    // get median from middle of array
    if ( m%2 == 0 )
        {
        m /= 2;
        median = ( temp[m-1] + temp[m] ) / 2.0;
        }
    else
        {
        m /= 2;
        median = temp[m];
        }
    }

}
