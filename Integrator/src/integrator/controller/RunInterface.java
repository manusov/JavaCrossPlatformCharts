/*

Interface for function Y=F(X) drawings scenario.
4 variants of drawings:
 1) Mathematics function.
 2) Data from file.
 3) JVM benchmarks.
 4) Native application benchmarks.
must support this interface for drawings process unification.

*/

package integrator.controller;

import integrator.model.Function;
import integrator.model.Statistics;
import java.math.BigDecimal;

public interface RunInterface 
{

// options data definitions
public enum Controls { X_AXIS_UP   , X_AXIS_DOWN , Y_AXIS_LEFT , Y_AXIS_RIGHT , 
                       X_SCALE_INC , X_SCALE_DEC , Y_SCALE_INC , Y_SCALE_DEC  ,
                       TAB_INC , TAB_DEC , RESET , RUN , LOAD };
public enum Gcolor   { BACKGROUND_WHITE, BACKGROUND_BLACK };

// functions get
public Function[] getFunctionsList();
public Function   getFunction( int i );
public Function   getSelectedFunction();
public double[][] getFunctionArray();
public Statistics calculateStatistics();

// adjust view options
public void sendFunction( int i );
public void sendControl( Controls control );
public boolean isTabIncable();
public boolean isTabDecable();
public boolean isXincable();
public boolean isXdecable();
public boolean isYincable();
public boolean isYdecable();
public Gcolor getDefaultColorScheme( );

// get variables methods
// see detail parameters comments at abstract Function.java class
public Gcolor getColorScheme();
public BigDecimal getXmin();
public BigDecimal getXmax();
public BigDecimal getYmin();
public BigDecimal getYmax();
public BigDecimal getXstepSmall();
public BigDecimal getXstepBig();
public BigDecimal getYstepSmall();
public BigDecimal getYstepBig();
public BigDecimal getTabStep();
public String getNameX();
public String getNameY();

// set variables methods
// see detail parameters comments at abstract Function.java class
public void setColorScheme( Gcolor cs );
public void setXmin( BigDecimal bd );
public void setXmax( BigDecimal bd );
public void setYmin( BigDecimal bd );
public void setYmax( BigDecimal bd );
public void setXstepSmall( BigDecimal bd );
public void setXstepBig( BigDecimal bd );
public void setYstepSmall( BigDecimal bd );
public void setYstepBig( BigDecimal bd );
public void setTabStep( BigDecimal bd );
public void setNameX( String s );
public void setNameY( String s );

}
