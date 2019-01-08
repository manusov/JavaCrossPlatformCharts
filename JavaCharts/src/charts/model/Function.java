/*

Abstract class for mathematics function Y=F(X) description template.
Get calculated function results and function visualization optimal conditions.

*/

package charts.model;

import java.math.BigDecimal;

public abstract class Function 
{
// get X-axis minimum viewable limit
abstract public BigDecimal getXmin();
// get X-axis maximum viewable limit
abstract public BigDecimal getXmax();
// get Y-axis minimum viewable limit
abstract public BigDecimal getYmin();
// get Y-axis maximum viewable limit
abstract public BigDecimal getYmax();
// get X-axis small graduation default step
abstract public BigDecimal getXstepSmall();
// get X-axis big graduation default step
abstract public BigDecimal getXstepBig();
// get Y-axis small graduation default step
abstract public BigDecimal getYstepSmall();
// get Y-axis big graduation default step
abstract public BigDecimal getYstepBig();
// get function tabulation default step
abstract public BigDecimal getTabStep();
// get name for X-axis, argument value
abstract public String getNameX();
// get name for Y-axis, function value
abstract public String getNameY();
// calculate function, return array of pairs (x,y)
abstract public double[][] function( double x, double dx, int n );
}
