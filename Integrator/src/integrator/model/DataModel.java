/*

Data model class for analysing functions Y=F(X).
Contains implementation for statistics methods.

*/

package integrator.model;

import java.math.BigDecimal;
import java.util.Arrays;

public class DataModel 
{

public Statistics calculateStatistics( double[][] f )
    {
    Statistics st = new Statistics();
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
        st.xMin = new BigDecimal( min );
        st.xMax = new BigDecimal( max );
        st.xAverage = new BigDecimal( average );
        st.xMedian = new BigDecimal( median );

        findMinMaxAverageMedian( f[1], f[0].length );
        st.yMin = new BigDecimal( min );
        st.yMax = new BigDecimal( max );
        st.yAverage = new BigDecimal( average );
        st.yMedian = new BigDecimal( median );
        
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
        st.dMin = new BigDecimal( min );
        st.dMax = new BigDecimal( max );
        st.dAverage = new BigDecimal( average );
        st.dMedian = new BigDecimal( median );
        
        for( int i=0; i<m; i++ )
            {
            g[i] = Math.abs( g[i] );
            }

        findMinMaxAverageMedian( g, m );
        st.mdMin = new BigDecimal( min );
        st.mdMax = new BigDecimal( max );
        st.mdAverage = new BigDecimal( average );
        st.mdMedian = new BigDecimal( median );
        
        double[] h = new double[n];
        for( int i =0; i<n; i++ )
            {
            h[i] = Math.abs( f[1][i] );
            }
        
        findMinMaxAverageMedian( h, n );
        st.myMin = new BigDecimal( min );
        st.myMax = new BigDecimal( max );
        st.myAverage = new BigDecimal( average );
        st.myMedian = new BigDecimal( median );
        
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
