/*

Abstract class for mathematics function Y=F(X) axis default settings template.
Get all functions visualization unified optimal conditions,
this data can be customized by per-function data in the controller logic.

*/

package integrator.model;

import integrator.controller.RunInterface.Gcolor;
import java.math.BigDecimal;

public abstract class FunctionDefaults 
{
abstract public BigDecimal getDefaultXmin();
abstract public BigDecimal getDefaultXmax();
abstract public BigDecimal getDefaultYmin();
abstract public BigDecimal getDefaultYmax();
abstract public BigDecimal getDefaultXstepSmall();
abstract public BigDecimal getDefaultXstepBig();
abstract public BigDecimal getDefaultYstepSmall();
abstract public BigDecimal getDefaultYstepBig();
abstract public BigDecimal getDefaultTabStep();

abstract public String getDefaultNameX();
abstract public String getDefaultNameY();
abstract public Gcolor getDefaultColor();

abstract public BigDecimal[] getDefaultTabSteps();
abstract public int getDefaultTabStepsCount();
abstract public int getDefaultTabStepsDefault();

abstract public BigDecimal[] getDefaultXrange();
abstract public int getDefaultXrangeCount();
abstract public int getDefaultXrangeDefault();

abstract public BigDecimal[] getDefaultYrange();
abstract public int getDefaultYrangeCount();
abstract public int getDefaultYrangeDefault();

}
