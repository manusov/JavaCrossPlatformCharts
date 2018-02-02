// Function calculation, call target method, accepted as parameter,
// this assist method for convert input parameters.

package charts.model;

import java.math.BigDecimal;

public class FunctionCalc 
{
// x1 = start value, x2 = end value, dx = tabulation step
private double x1 = 0.0 , x2 = 0.0 , dx = 0.0;
private double[][] array = null;

public double[][] getArray()        { return array; }
public void setArray(double[][] f)  { array = f;    }

public double[][] calculate
        ( BigDecimal bd1, BigDecimal bd2, BigDecimal bd3, Function f )
    {
    x1 = bd1.doubleValue();  // convert input parameters, assist part
    x2 = bd2.doubleValue();
    dx = bd3.doubleValue();
    double m = ( x2 - x1 ) / dx;
    // int n = (int)m;                // n = number of tabulation steps
    int n = (int)m + 1;               // n = number of tabulation steps
    array = f.function(x1, dx, n);    // call target method and return
    return array;
    }

public double[] findMinMaxValid()
    {
    double[] minMaxValid = null;
    if ( ( array != null ) && ( array.length > 0 ) )
        {
        int n = array[1].length;
        double min = Double.NaN;
        double max = Double.NaN;
        int i = 0;
        for(; i<n; i++)  // this cycle returns X[i] for first valid Y[i]
            {
            if ( Double.isFinite(array[1][i]) )
                {
                min = array[0][i];
                break;
                }
            }
        for(; i<n; i++)  // this cycle returns X[i] for last valid Y[i]
            {
            if ( Double.isFinite(array[1][i]) )
                {
                max = array[0][i];
                }
            }
        minMaxValid = new double[] { min, max };
        }
    return minMaxValid;    
    }
        
        
public double[] findMinMax()
    {
    double[] minMax = null;
    if ( ( array != null ) && ( array.length > 0 ) )
        {
        int n = array[1].length;
        double min = array[1][0];
        double max = min;
        for(int i=0; i<n; i++)
            {
            if ( ( min > array[1][i] ) && 
                 ( Double.isFinite( array[1][i] ) ) ||
                 ( ! Double.isFinite(min) ) )
                {
                min = array[1][i]; 
                }
            if ( ( max < array[1][i] ) && 
                 ( Double.isFinite( array[1][i] ) ) ||
                 ( ! Double.isFinite(max) ) )
                {
                max = array[1][i]; 
                }
            }
        minMax = new double[] { min, max };
        }
    return minMax;
    }

public double findAverage()
    {
    double average = Double.NaN;
    if ( ( array != null ) && ( array.length > 0 ) )
        {
        average = 0.0;
        int n = array[1].length;
        for(int i=0; i<n; i++)
            {
            if ( Double.isFinite(array[1][i] ) ) average += array[1][i];
            }
        average /= n;
        }
    return average;
    }

public double findMedian()
    {
    double median = Double.NaN;
    if ( ( array != null ) && ( array.length > 0 ) )
        {
        // create temporary array
        int n = array[1].length;
        int m = 0;
        double[] temp = new double[n];
        for(int i=0; i<n; i++)
            {
            if ( Double.isFinite( array[1][i] ) )
                {
                temp[m] = array[1][i];
                m++;
                }
            }
        // ordering temporary array
        
/*
        boolean f = true;
        while(f)
            {
            f = false;
            for(int i=0; i<(m-1); i++)
                {
                if ( temp[i] > temp[i+1] )
                    {
                    double a = temp[i];
                    temp[i] = temp[i+1];
                    temp[i+1] = a;
                    f = true;
                    }
                }
            }

*/        
        // m = length of used part, note m < temp.length
        FastSorting.sorting(temp, m);
        // FastSorting.linearSorting(temp, m);
        // speed optimized
        
        // get median
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
    return median;
    }

}

        
/*
// CONTAIN INFINITY BUGS        
public double[] findMinMax()
    {
    double[] minMax = null;
    if ( ( array != null ) && ( array.length > 0 ) )
        {
        int n = array[1].length;
        double min = array[1][0];
        double max = min;
        for(int i=0; i<n; i++)
            {
            if ( ( min > array[1][i] ) && ( ! Double.isNaN( array[1][i] ) ) ||
                 ( Double.isNaN(min) ) )
                {
                min = array[1][i]; 
                }
            if ( ( max < array[1][i] ) && ( ! Double.isNaN( array[1][i] ) ) ||
                 ( Double.isNaN(max) ) )
                {
                max = array[1][i]; 
                }
            }
        minMax = new double[] { min, max };
        }
    return minMax;
    }
*/

/*

OLD

public class FunctionState 
{
private double[][] function;  // array of pairs (x,y)


public FunctionState()
    {
    function = new double[2][];
    }


// methods for function data access

public double[][] getFunction()        { return function; }
public void setFunction(double[][] f)  { function = f;    }



// THIS FUNCTIONALITY MUST BE MOVED TO FunctionCalc.java class.

// get one value (x,y) by index = i

public double[] getValue(int i)
    {
    double[] v = new double[2];
    if ( ( function.length == 2 ) && 
         ( i < function[0].length ) && ( i < function[1].length ) )
        {
        v[0] = function[0][i];
        v[1] = function[1][i];
        }
    return v;
    }

// set one value (x,y) by index = i

public void setValue(double[] v, int i)
    {
    if ( ( v != null ) && ( v.length == 2 ) && ( function.length == 2 ) && 
         ( i < function[0].length ) && ( i < function[1].length ) )
        {
        function[0][i] = v[0];
        function[1][i] = v[1];
        }
    }

// get maximum and minimum for function array

public double[] getMinMax()
    {
    int n = function[1].length;
    double min = function[1][0];
    double max = min;
    for (int i=0; i<n; i++)
        {
        if ( min > function[1][i] ) { min = function[1][i]; }
        if ( max < function[1][i] ) { max = function[1][i]; }
        }
    double[] a = { min, max };
    return a;
    }

// calculate average of function values (y), for all array

public double calculateAverage()
    {
    double average = Double.NaN;
    if ( ( function != null ) && ( function.length == 2 ) &&
         ( function[1].length > 0 ) )
        {
        int n = function[1].length;
        double sum = 0.0;
        for (int i=0; i<n; i++)
            {
            sum += function[1][i];
            }
        average = sum / n;
        }
    return average;
    }

// calculate average of function values (y), for sub-array k1...k2

public double calculateAverage(int k1, int k2)
    {
    double average = Double.NaN;
    if ( ( function != null ) && ( function.length == 2 ) &&
         ( function[1].length > 0 ) )
        {
        int n = function[1].length;
        if ( ( k1 > 0 ) && ( k2 > 0 ) && ( k1 < n ) &&
             ( k2 < n ) && ( k1 <= k2 ) )
            {
            double sum = 0.0;
            for (int i=k1; i<=k2; i++)
                {
                sum += function[1][i];
                }
            average = sum / n;
            }
        }
    return average;
    }

// calculate median of function values (y), for all array

public double calculateMedian()
    {
    double median = Double.NaN;
    if ( ( function != null ) && ( function.length == 2 ) &&
         ( function[1].length > 0 ) )
        {
        int n = function[1].length;
        double[] temp = new double[n];
        System.arraycopy( function[1], 0, temp, 0, n );
        median = calcMedian(temp, n);
        }
    return median;
    }

// calculate median of function values (y), for sub-array k1...k2

public double calculateMedian(int k1, int k2)
    {
    double median = Double.NaN;
    if ( ( function != null ) && ( function.length == 2 ) &&
         ( function[1].length > 0 ) )
        {
        int n = function[1].length;
        if ( ( k1 > 0 ) && ( k2 > 0 ) && ( k1 < n ) &&
             ( k2 < n ) && ( k1 <= k2 ) )
            {
            int m = k2 - k1 + 1;
            double[] temp = new double[m];
            System.arraycopy( function[1], k1, temp, 0, m );
            median = calcMedian(temp, m);
            }
        }
    return median;
    }

// private helper method for calculate median
// temp = array of doubles, can be sorted for calculate median
// n = length of array

private double calcMedian(double[] temp, int n)
    {
    double median;
    n--;
    if ( n == 0 )
        {
        median = temp[0];
        }
    else
        {
        boolean swapped = true;
        while (swapped)
            {
            swapped = false;
            for(int i=0; i<n; i++)
                {
                if ( temp[i] > temp[i+1] )
                    {
                    double swap = temp[i];
                    temp[i] = temp[i+1];
                    temp[i] = swap;
                    swapped = true;
                    }
                }
            }
        if ( n%2 == 0 )
            {
            int m = n/2;
            median = ( temp[m] + temp[m+1] ) / 2.0;
            }
        else
            {
            int m = n/2;
            median = temp[m];
            }
        }
    return median;
    }



}

*/

