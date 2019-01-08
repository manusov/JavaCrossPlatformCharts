/*

Helper static class for convert floating-point numbers to strings,
this helper optimizes visualization style.
Note for show numbers with periods, example 28/3
delete extra right zeroes, example
1.0 must not changed
1.00 must be changed to 1.0

*/

package charts.view;

import java.math.BigDecimal;
import java.util.Locale;

public class FloatPrintUtil 
{

public static String printCorrectedBigDecimal(BigDecimal bdX, boolean result)
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

    
public static String printCorrected(double x, boolean result)
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
