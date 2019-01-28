/*

Class with Vendor, Product, Version, Release Date, Icon information.
Build "About" window.
This class is update point when application version incremented.

*/

package integrator.view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class About 
{
private final String VERSION_NAME = "v0.00.22";
private final String VENDOR_NAME  = "(C)2019 IC Book Labs";
private final String SHORT_NAME   = "Integrator " + VERSION_NAME;
private final String LONG_NAME    = "Benchmarks " + SHORT_NAME;
private final String WEB_SITE     = "http://icbook.com.ua";
private final String VENDOR_ICON  = "/integrator/resources/icbook.jpg";

private final Color LOGO_COLOR = new Color( 143, 49, 40 );
private final Dimension SIZE_BUTTON_HTTP   = new Dimension ( 180, 25 );
private final Dimension SIZE_BUTTON_CANCEL = new Dimension ( 89, 25 );

public String getVersionName() { return VERSION_NAME; }
public String getVendorName()  { return VENDOR_NAME;  }
public String getShortName()   { return SHORT_NAME;   }
public String getLongName()    { return LONG_NAME;    }
public String getWebSite()     { return WEB_SITE;     }
public String getVendorIcon()  { return VENDOR_ICON;  }

// Handler for "About" dialogue method, show "About" window
public void show( JFrame frame )
    {
    JFrame f = null;
    final JDialog dialog = new JDialog( f, "About", true );
    dialog.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    // Create GUI components
    SpringLayout sl1 = new SpringLayout();
    JPanel p1 = new JPanel(sl1);
    // String for web site
    final String sHttp = getWebSite();
    String sCancel = "Cancel";
    JLabel l1 = new JLabel ();
    try { l1.setIcon(new javax.swing.ImageIcon( getClass().
           getResource( getVendorIcon() ))); } 
    catch ( Exception e ) { }
    JLabel l2 = new JLabel  ( getLongName()   );
    JLabel l3 = new JLabel  ( getVendorName() );
    l2.setForeground( LOGO_COLOR );
    l3.setForeground( LOGO_COLOR );
    Font font1 = new Font ( "Verdana", Font.PLAIN, 12 );
    l2.setFont(font1);
    l3.setFont(font1);
    JButton b1 = new JButton(sHttp);
    JButton b2 = new JButton(sCancel);
    // Set buttons sizes
    b1.setPreferredSize( SIZE_BUTTON_HTTP );
    b2.setPreferredSize( SIZE_BUTTON_CANCEL );
    // Layout management for panels and labels
    sl1.putConstraint ( SpringLayout.NORTH, l1,  24, SpringLayout.NORTH, p1 );
    sl1.putConstraint ( SpringLayout.WEST,  l1,  28, SpringLayout.WEST,  p1 );
    sl1.putConstraint ( SpringLayout.NORTH, l2,  24, SpringLayout.NORTH, p1 );
    sl1.putConstraint ( SpringLayout.WEST,  l2,   4, SpringLayout.EAST,  l1 );
    sl1.putConstraint ( SpringLayout.NORTH, l3,   0, SpringLayout.SOUTH, l2 );
    sl1.putConstraint ( SpringLayout.WEST,  l3,   4, SpringLayout.EAST,  l1 );
    // Layout management for panels and buttons
    sl1.putConstraint ( SpringLayout.SOUTH, b1, -10, SpringLayout.SOUTH, p1 );
    sl1.putConstraint ( SpringLayout.WEST,  b1,   8, SpringLayout.WEST,  p1 );
    sl1.putConstraint ( SpringLayout.SOUTH, b2, -10, SpringLayout.SOUTH, p1 );
    sl1.putConstraint ( SpringLayout.WEST,  b2,   3, SpringLayout.EAST,  b1 );
    // Add labels and buttons to panel
    p1.add( l1 );
    p1.add( l2 );
    p1.add( l3 );
    p1.add( b1 );
    p1.add( b2 );
    // Action listener for web button
    b1.addActionListener( ( ActionEvent ae1 ) -> {
        if(Desktop.isDesktopSupported())
        { try { Desktop.getDesktop().browse(new URI( sHttp )); }  // web access
        catch ( URISyntaxException | IOException ex1 ) { } };
    });
    // Action listener for cancel button
    b2.addActionListener( ( ActionEvent e ) -> { dialog.dispose(); } );
    // Visual window and return    
    dialog.setContentPane( p1 );
    dialog.setSize( 300, 150 );
    dialog.setResizable( false );
    dialog.setLocationRelativeTo( frame );
    dialog.setVisible( true );    
    }

}
