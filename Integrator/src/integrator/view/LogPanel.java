/*

Abstract class for log panel description.
Used for text log and statistics table panels.

*/

package integrator.view;

import javax.swing.JPanel;

public abstract class LogPanel extends JPanel
{
abstract public void dataChanged();

}
