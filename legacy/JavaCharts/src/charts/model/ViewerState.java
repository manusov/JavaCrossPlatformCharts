/*

Class for adjust drawings y=f(x) visualization parameters,
this class provides storage for default constants and current settings.
See detail parameters comments at abstract Function.java class.

*/

package charts.model;

import java.math.BigDecimal;

public class ViewerState 
{
public enum GCOLOR { BACKGROUND_BLACK, BACKGROUND_WHITE };

private final static GCOLOR COLOR_SCHEME     = GCOLOR.BACKGROUND_BLACK;
private final static BigDecimal X_MIN        = new BigDecimal("-1.0");
private final static BigDecimal X_MAX        = new BigDecimal("1.0");
private final static BigDecimal Y_MIN        = new BigDecimal("-1.0");
private final static BigDecimal Y_MAX        = new BigDecimal("1.0");
private final static BigDecimal X_STEP_SMALL = new BigDecimal("0.02");
private final static BigDecimal X_STEP_BIG   = new BigDecimal("0.1");
private final static BigDecimal Y_STEP_SMALL = new BigDecimal("0.02");
private final static BigDecimal Y_STEP_BIG   = new BigDecimal("0.1");
private final static BigDecimal TAB_STEP     = new BigDecimal("0.001");
private final static String NAME_X = "X";
private final static String NAME_Y = "Y";

private GCOLOR colorScheme;
private BigDecimal xMin, xMax, yMin, yMax,
                   xStepSmall, xStepBig, yStepSmall, yStepBig, tabStep;
private String     nameX, nameY;

// load default parameters
// see detail parameters comments at abstract Function.java class

public void init()
    {
    colorScheme = COLOR_SCHEME;
    xMin        = X_MIN;
    xMax        = X_MAX;
    yMin        = Y_MIN;
    yMax        = Y_MAX;
    xStepSmall  = X_STEP_SMALL;
    xStepBig    = X_STEP_BIG;
    yStepSmall  = Y_STEP_SMALL;
    yStepBig    = Y_STEP_BIG;
    tabStep     = TAB_STEP;
    nameX       = NAME_X;
    nameY       = NAME_Y;
    }

// get variables methods
// see detail parameters comments at abstract Function.java class

public GCOLOR getColorScheme()      { return colorScheme; }
public BigDecimal getXmin()         { return xMin;        }
public BigDecimal getXmax()         { return xMax;        }
public BigDecimal getYmin()         { return yMin;        }
public BigDecimal getYmax()         { return yMax;        }
public BigDecimal getXstepSmall()   { return xStepSmall;  }
public BigDecimal getXstepBig()     { return xStepBig;    }
public BigDecimal getYstepSmall()   { return yStepSmall;  }
public BigDecimal getYstepBig()     { return yStepBig;    }
public BigDecimal getTabStep()      { return tabStep;     }
public String getNameX()            { return nameX;       }
public String getNameY()            { return nameY;       }

// set variables methods
// see detail parameters comments at abstract Function.java class

public void setColorScheme(GCOLOR cs)     { colorScheme = cs; }
public void setXmin(BigDecimal bd)        { xMin = bd;        }
public void setXmax(BigDecimal bd)        { xMax = bd;        }
public void setYmin(BigDecimal bd)        { yMin = bd;        }
public void setYmax(BigDecimal bd)        { yMax = bd;        }
public void setXstepSmall(BigDecimal bd)  { xStepSmall = bd;  }
public void setXstepBig(BigDecimal bd)    { xStepBig = bd;    }
public void setYstepSmall(BigDecimal bd)  { yStepSmall = bd;  }
public void setYstepBig(BigDecimal bd)    { yStepBig = bd;    }
public void setTabStep(BigDecimal bd)     { tabStep = bd;     }
public void setNameX(String s)            { nameX = s;        }
public void setNameY(String s)            { nameY = s;        }

/*
// MOVED TO FUNCTION CORE, F(FUNCTION PARAMETERS),
// NO VIEWER-DEFINED DEFAULTS, BECAUSE CAN BE VERY FUNCTION-SPECIFIC
// MOVED, BECAUSE NO PER-FUNCTION DESCRIPTION DATA IN THIS CLASS

// view control methods, associated with control buttons

public void baseLeftX()
    {
    xMin = xMin.add(xStepBig);
    xMax = xMax.add(xStepBig);
    }

public void baseRightX()
    {
    xMin = xMin.subtract(xStepBig);
    xMax = xMax.subtract(xStepBig);
    }

public void baseUpY()
    {
    yMin = yMin.subtract(yStepBig);
    yMax = yMax.subtract(yStepBig);
    }

public void baseDownY()
    {
    yMin = yMin.add(yStepBig);
    yMax = yMax.add(yStepBig);
    }

public void sizeLeftX()
    {
    BigDecimal temp1 = xMin.add(xStepBig);
    BigDecimal temp2 = xMax.subtract(xStepBig);
    if ( temp1.compareTo(temp2) < 0 )
        {
        xMin = temp1;
        xMax = temp2;
        }
    }

public void sizeRightX()
    {
    xMin = xMin.subtract(xStepBig);
    xMax = xMax.add(xStepBig);
    }

public void sizeUpY()
    {
    yMin = yMin.subtract(yStepBig);
    yMax = yMax.add(yStepBig);
    }

public void sizeDownY()
    {
    BigDecimal temp1 = yMin.add(yStepBig);
    BigDecimal temp2 = yMax.subtract(yStepBig);
    if ( temp1.compareTo(temp2) < 0 )
        {
        yMin = temp1;
        yMax = temp2;
        }
    }

// operations possibility detectors for make buttons gray dinamically

public boolean isBaseLeftX()
    {
    return true;
    }

public boolean isBaseRightX()
    {
    return true;
    }

public boolean isBaseUpY()
    {
    return true;
    }

public boolean isBaseDownY()
    {
    return true;
    }

public boolean isSizeLeftX()
    {
    BigDecimal temp1 = xMin.add(xStepBig);
    BigDecimal temp2 = xMax.subtract(xStepBig);
    return temp1.compareTo(temp2) < 0;
    }

public boolean isSizeRightX()
    {
    return true;
    }

public boolean isSizeUpY()
    {
    return true;
    }

public boolean isSizeDownY()
    {
    BigDecimal temp1 = yMin.add(yStepBig);
    BigDecimal temp2 = yMax.subtract(yStepBig);
    return temp1.compareTo(temp2) < 0;
    }

*/

}
