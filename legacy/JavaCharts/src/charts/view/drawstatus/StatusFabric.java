/*

Fabric class for build status view panel.
Yet only one mode supported: DRAWS.SIMPLE,
for this mode, data source = calculate mathematics functions,
alternative variants is: data file, real-time JVM benchmarks,
real-time native benchmarks.

*/

package charts.view.drawstatus;

import charts.Charts.DRAWS;
import charts.view.DescriptLabel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class StatusFabric 
{

public static StatusPanel[] getStatusPanels(DRAWS type)
    {
    StatusPanel[] statusPanels;
    StatusPanel sp;
    
    switch(type)
        {
        //
        // Reserved for add new advanced modes
        //
        case SIMPLE:
        default:
            {
            sp = new StatusPanel();
            break;
            }
        }
    
    DescriptLabel[] labelsList = sp.getLabelsList();
    int n = labelsList.length;
    JLabel[] l = new JLabel[n];
    sp.setupLabels(labelsList, l);  // required for next dynamically updates
    
    for(int i=0; i<n; i++)
        {
        l[i] = new JLabel( labelsList[i].getName() );
        l[i].setToolTipText ( labelsList[i].getText() );
        }
        
    SpringLayout s = new SpringLayout();
    sp.setLayout(s);
    
    s.putConstraint( SpringLayout.WEST, l[0], 4 , SpringLayout.WEST, sp );
    s.putConstraint( SpringLayout.WEST, l[1], 4 , SpringLayout.WEST, sp );
    s.putConstraint( SpringLayout.WEST, l[2], 4 , SpringLayout.WEST, sp );
    
    s.putConstraint( SpringLayout.SOUTH, l[2], -6 , SpringLayout.SOUTH, sp );
    s.putConstraint( SpringLayout.SOUTH, l[1], -1 , SpringLayout.NORTH, l[2] );
    s.putConstraint( SpringLayout.SOUTH, l[0], -1 , SpringLayout.NORTH, l[1] );
    
    for(int i=0; i<n; i++)
        {
        sp.add( l[i] );
        }

    
// ************** THIS FRAGMENT FOR UNDER CONSTRUCTION STAGE *******************
//    l[0].setEnabled(false);
//    l[1].setEnabled(false);
//    l[2].setEnabled(false);
// ************** END OF FRAGMENT FOR UNDER CONSTRUCTION STAGE *****************


    statusPanels = new StatusPanel[1];
    statusPanels[0] = sp; 
    return statusPanels;
    }        

}
