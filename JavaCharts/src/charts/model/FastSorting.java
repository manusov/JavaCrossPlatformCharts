// Helper class for fast sorting.
// Note sorting required for find median algorithm.

package charts.model;

public class FastSorting 
{
private final static int BLOCK_SIZE = 512;
    
protected static void sorting( double[] a , int correctedLength )
    {
    int n = correctedLength;            // source array length
    int count = n / BLOCK_SIZE + 1;     // number of blocks, include last tail
    int tail  = n % BLOCK_SIZE;         // tail size
    int offset = 0;                     // offset in the source array
    double[][] b = new double[count][BLOCK_SIZE];  // array of blocks
    b[count-1] = new double[tail];                 // last block size = tail
    // build multiple arrays: one array per block
    int i;  // blocks counter
    for( i=0; i<count; i++ )
        {
        int length = b[i].length;  // length can be BLOCK_SIZE or tail
        System.arraycopy( a , offset , b[i] , 0 , length );
        offset += length;  // modify source array pointer
        }
    // separate sorting each array
    for( i=0; i<count; i++ )
        {
        linearSorting( b[i] , b[i].length );
        }
    // sorting arrays
    boolean f = true;
    while(f)
        {
        f = false;
        for ( i=0; i<(count-1); i++ )
            {
            if ( b[i][0] > b[i+1][0] )
                {
                double[] c = b[i];
                b[i] = b[i+1];
                b[i+1] = c;
                f = true;
                }
            }
        }
    // copy back to single array
    offset = 0;
    for( i=0; i<count; i++ )
        {
        int length = b[i].length;
        System.arraycopy( b[i] , 0 , a , offset , length );
        offset += length;
        }
    // final sorting single array
    linearSorting(a, n);
    }
    
private static void linearSorting( double[] a , int correctedLength )
    {
    int n = correctedLength - 1;  
    boolean f = true;
    while(f)
        {
        f = false;
        for (int i=0; i<n; i++)
            {
            if ( a[i] > a[i+1] )
                {
                double temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
                f = true;
                }
            }
        }
    }
    
}
