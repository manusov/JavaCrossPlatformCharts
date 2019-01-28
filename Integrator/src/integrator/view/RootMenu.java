/*

Class for up-string root menu.
1) Builder.
2) Components lists.
3) Events listeners.
4) Functions handlers.
5) Child processes run and stop logic.

*/

package integrator.view;

import integrator.controller.Integrator;
import integrator.controller.RunInterface;
import java.awt.Component;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

public class RootMenu 
{
// child menus
private final Object[][] itemsFile = {    
    { "Save text", false,  // false means no checkbox at menu item
      KeyEvent.VK_T, Event.CTRL_MASK, new SaveTextListener() }, 
    { "Save graphics", false,
      KeyEvent.VK_G, Event.CTRL_MASK, new SaveGraphicsListener() },
    { null },
    { "Exit", false,
      KeyEvent.VK_Q, Event.CTRL_MASK, new ExitListener() } };
private final Object[][] itemsCalc = {
    { "Calculator", true,  // true means checkbox at menu item
      KeyEvent.VK_C, Event.CTRL_MASK, new RunCalcListener() },
    { "CPUID", true,
      KeyEvent.VK_P, Event.CTRL_MASK, new RunCpuidListener() },
    { "MathBench", true,
      KeyEvent.VK_M, Event.CTRL_MASK, new RunMathListener() },
    { "NIOBench", true,
      KeyEvent.VK_N, Event.CTRL_MASK, new RunNioListener() },
    { "PowerInfo", true,
      KeyEvent.VK_O, Event.CTRL_MASK, new RunPowerListener() } };
private final Object[][] itemsHelp = {
    { "About", false,
      KeyEvent.VK_B, Event.CTRL_MASK, new AboutListener() } };
// root (parent) menu
private final Object[][] rootItems = { 
    { "File"  , 'F' , itemsFile } ,
    { "Tools" , 'T' , itemsCalc } ,
    { "Help"  , 'H' , itemsHelp } };
// menu bar and it getter method
private final JMenuBar menuBar;
public JMenuBar getMenuBar() { return menuBar; }

// class constructor
public RootMenu()
    {
    // set items names strings and mnemonics
    int n = rootItems.length;
    JMenu[] menu = new JMenu[n];
    for( int i=0; i<n; i++ )
        {
        menu[i] = new JMenu( (String) rootItems[i][0] );
        menu[i].setMnemonic( (char) rootItems[i][1] );
        }
    // Set actions listeners for menu items
    for( int i=0; i<n; i++ )  // this cycle for root (parent) menu items
        {
        Object[][] x = ( Object[][] ) rootItems[i][2];
        int m = x.length;
        for( int j=0; j<m; j++ )  // this cycle for child menus items
            {
            String s = ( String ) x[j][0];
            if ( s == null )
                {
                menu[i].addSeparator();
                }
                else
                {
                JMenuItem item;
                if ( ( boolean ) x[j][1] )
                    {
                    item = new JCheckBoxMenuItem( s );
                    }
                else
                    {
                    item = new JMenuItem( s );
                    }
                item.setAccelerator
                    ( KeyStroke.getKeyStroke( (int) x[j][2], (int) x[j][3] ) );
                item.addActionListener( (ActionListener) x[j][4] );
                menu[i].add(item);
                }
            }
        }
    // Create and return menu bar, caller can connect it to frame
    menuBar = new JMenuBar();
    for( int i=0; i<n; i++ )
        {
        menuBar.add( menu[i] );
        }
    // Add listener for update checkboxes
    menu[1].addMenuListener( new ChildsMenuListener() );
    // Blank child processes data
    blankAllChilds();
    
    // THIS BECAUSE UNDER CONSTRUCTION
    // menu[0].getItem(0).setEnabled( false );
    // menu[0].getItem(1).setEnabled( false );
    // THIS BECAUSE UNDER CONSTRUCTION
    
    }

// child processes control data
private final int childCount = 5;
public enum Childs { CALC, CPUID, MATH, NIO, POWER };
private final Process[] childList = new Process[childCount];
private final File[] childFiles = new File[childCount];

private void blankAllChilds()
    {
    for( int i=0; i<childCount; i++ )
        {
        childList[i] = null;
        childFiles[i] = null;
        }
    }

private void runStopChild( String name, int index )
    {
    String prefix = "java -jar ";
    String suffix = ".jar";
    // Unpack and write temporary file
    File child = childFiles[index];
    if ( child == null )
        {
        final int BLOCK_SIZE = 16384;
        try {
            URL resource = Integrator.class.getResource
                ( "/integrator/resources/" + name + suffix );
            try ( InputStream input = resource.openStream() ) {
            child = File.createTempFile( name, suffix );
            try ( FileOutputStream output = new FileOutputStream( child ) ) {
                  byte[] buffer = new byte[BLOCK_SIZE];
                  for( int i = input.read( buffer ); i != -1;
                       i = input.read( buffer ) )
                        {
                        output.write( buffer, 0, i );
                        }
                    }
                }
            }
        catch ( Throwable e )
            {
            child = null;
            }
            childFiles[index] = child;
        }
    // Create and run child process, use temporary file just unpacked
    if ( child != null )
        {
        String runtext = prefix + child.getAbsolutePath();
        Process process = childList[index];
        if ( ( process == null ) || ( !process.isAlive() ) )
            {
            try {
                process = Runtime.getRuntime().exec( runtext );
                }
            catch ( Exception e ) 
                {
                process = null;
                }
            childList[index] = process;
            }
        else
            {
            process.destroy();
            }
        }
        
    }

public void stopAllChilds()
    {
    // Stop child processes, if process handle exist and process alive
    // Cycle for all possible listed child processes
    for( int i=0; i<childCount; i++ )
        {
        Process p = childList[i];
        if ( ( p != null ) && ( p.isAlive() ) )
            {
            p.destroy();
            try { sleep( 20 ); } catch ( Exception e ) { }
            }
        }
    // Delete child processes temporary files
    boolean b = false;
    int k = 0;
    while( ( !b ) && ( k < 100 ) )
        {
        b = true;
        for( int i=0; i<childCount; i++ )
            {
            // Delete child process file, if file handle exist and object=file
            File f = childFiles[i];
            if ( ( f != null ) && ( f.isFile() ) )
                {
                boolean b1 = f.delete();
                b &= b1;
                if ( b1 )
                    {
                    childFiles[i] = null;
                    }
                else
                    {  // additional delay if error when delete
                    try { sleep( 10 ); } catch ( Exception e ) { }
                    k++;
                    }
                }
            }
        }
    }

// root menu handler = save text report to file
private class SaveTextListener implements ActionListener
{
@Override public void actionPerformed( ActionEvent event ) 
    {
    // get window context
    JFrame parentWin = Integrator.getApplication().getFrame();
    About aboutWin   = Integrator.getAbout();
    String logText   = Integrator.getTextLog().getText().getText();
    AbstractTableModel statTable = Integrator.getStatTable().getModel();
    RunInterface ri = Integrator.getRunInterface();
    // initializing file operations context
    final String DEFAULT_FILE_NAME = "report.txt";
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle( "Report - select directory" );
    FileNameExtensionFilter filter = 
        new FileNameExtensionFilter ( "Text files" , "txt" );
    chooser.setFileFilter( filter );
    chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    chooser.setSelectedFile( new File( DEFAULT_FILE_NAME ) );
    // (re)start dialogue
    boolean inDialogue = true;
    while( inDialogue )
        {
        int select = chooser.showSaveDialog( parentWin );
        // save file
        if( select == JFileChooser.APPROVE_OPTION )
            {
            String filePath = chooser.getSelectedFile().getPath();
            int option = JOptionPane.YES_OPTION;
            // check file exist and warning message
            File file = new File( filePath );
            if( file.exists() == true )
                {
                option = JOptionPane.showConfirmDialog
                    ( null, 
                    "File exist: " + filePath + "\noverwrite?" , "REPORT" ,
                    JOptionPane.YES_NO_CANCEL_OPTION ,
                    JOptionPane.WARNING_MESSAGE );  // or QUESTION_MESSAGE
                }
            // Select operation by user selection
            if ( ( option == JOptionPane.NO_OPTION  ) |
                 ( option == JOptionPane.CLOSED_OPTION ) )
                { 
                continue; 
                }
            if ( option == JOptionPane.CANCEL_OPTION ) 
                { 
                inDialogue = false;
                continue; 
                }
            // continue prepare for save file.
            StringBuilder data = new StringBuilder ( "" );
            // add product and copyright information to report
            data.append( aboutWin.getLongName() );
            data.append( "\r\n" );
            data.append( aboutWin.getWebSite() );
            data.append( "\r\n" );
            data.append( "report file." );
            data.append( "\r\n\r\n" );
            // add text log information to report
            if ( logText != null )
                {
                int n = logText.length();
                boolean b = false;
                for( int i=0; i<n; i++ )
                    {
                    // sequences 0Ah/0Dh, 0Dh/0Ah, 0Dh, 0Ah
                    // must be replaced to sequence 0Dh/0Ah
                    char c = logText.charAt( i );
                    if ( ( c == '\r' ) || ( c == '\n' ) )
                        {
                        if ( b )
                            {
                            b = false;
                            }
                        else
                            {
                            data.append( "\r\n" );
                            b = true;
                            }
                        }
                    else
                        {
                        data.append( c );
                        b = false;
                        }
                    }
                }
            // add statistics table information to report
            if ( statTable != null )
                {
                int columns = statTable.getColumnCount();
                int rows = statTable.getRowCount();
                int colSize;
                int[] maxColSizes = new int[columns];
                int maxColSize = 13;
                // Get column names lengths
                for ( int i=0; i<columns; i++ )
                    {
                    maxColSizes[i] = statTable.getColumnName(i).length(); 
                    }
                // Get column maximum lengths
                for ( int j=0; j<rows; j++ )
                    {
                    for ( int i=0; i<columns; i++ )
                        {
                        colSize = 
                            (( String )( statTable.getValueAt( j, i ) )).
                            length();
                        if ( colSize > maxColSizes[i] )
                            {
                            maxColSizes[i] = colSize; 
                            }
                        }
                    }
                for ( int i=0; i<maxColSizes.length; i++ ) 
                    {
                    maxColSize += maxColSizes[i];
                    }
                // Write table up
                data.append( "\r\n\r\n" );
                for ( int i=0; i<columns; i++ )
                    {
                    data.append( " " );
                    data.append( statTable.getColumnName( i ) );
                    colSize = 
                        maxColSizes[i] - 
                        statTable.getColumnName( i ).length() + 1;
                    for ( int k=0; k<colSize; k++ )
                        {
                        data.append( " " );
                        }
                    }
                // Write horizontal line
                data.append("\r\n" );
                for ( int i=0; i<maxColSize; i++ )
                    {
                    data.append( "-" );
                    }
                data.append("\r\n" );

            // Write table content
            for (int j=0; j<rows; j++)         // this cycle for rows
                {
                for (int i=0; i<columns; i++)  // this cycle for columns
                    {
                    data.append( statTable.getValueAt( j, i ) );
                    colSize = maxColSizes[i] - 
                        ((String)statTable.getValueAt(j,i)).length() + 2;
                    for ( int k=0; k<colSize; k++ )
                        {
                        data.append( " " );
                        }
                    }
                    data.append( "\r\n" );
                }
                // Write horizontal line
                for ( int i=0; i<maxColSize; i++ )
                    {
                    data.append( "-" );
                    }
                data.append( "\r\n\r\n" );
                }
            // add drawings settings information to report, UNDER CONSTRUCTION,
            // settings required or not when statistics table present ?
            // add function Y=F(X) data to report
            double[][] fnArray = ri.getFunctionArray();
            if ( ( fnArray != null )       && ( fnArray.length == 2 ) &&
                 ( fnArray[0] != null )    && ( fnArray[1] != null )  &&
                 ( fnArray[0].length > 0 ) &&
                 ( fnArray[0].length == fnArray[1].length ) )
                {
                int fnCount = fnArray[0].length;
                int fnColSize = 30;
                data.append( "\r\n<START>\r\n\r\n" );
                data.append( " X            Y\r\n" );
                for( int i=0; i<fnColSize; i++ )
                    {
                    data.append( "-" );
                    }
                data.append( "\r\n" );
                for( int i=0; i<fnCount; i++ )
                    {
                    data.append( String.format( " %10.7f   %10.7f\r\n", 
                                 fnArray[0][i], fnArray[1][i] ) );
                    }
                for( int i=0; i<fnColSize; i++ )
                    {
                    data.append( "-" );
                    }
                data.append( "\r\n\r\n<END>\r\n\r\n" );
                }
            // save text report
            String fileData = data.toString();
            int status = 0;
            try ( FileWriter writer = new FileWriter( filePath, false ) )
                {
                writer.write( fileData );
                writer.flush(); 
                }
            catch( Exception ex )
                {
                status = 1; 
                }
            // message box after report saved
            if ( status == 0 )  
                {
                JOptionPane.showMessageDialog
                ( parentWin, "Report saved: " + filePath, "REPORT",
                JOptionPane.WARNING_MESSAGE ); 
                }
            else
                {
                JOptionPane.showMessageDialog
                ( parentWin, "Write report failed", "ERROR",
                JOptionPane.ERROR_MESSAGE ); 
                }
            inDialogue = false;
            }  
        else
            { 
            inDialogue = false; 
            }
        }    // End of save dialogue cycle
    }
}

// root menu handler = save graphics image to file
private class SaveGraphicsListener implements ActionListener
{
@Override public void actionPerformed( ActionEvent event ) 
    {  
    // get window context
    JFrame parentWin = Integrator.getApplication().getFrame();
    JPanel displayWin = Integrator.getDisplay().getPanel();
    // initializing file operations context
    final String DEFAULT_FILE_NAME = "picture.png";
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle( "Pictire - select directory" );
    FileNameExtensionFilter filter = 
        new FileNameExtensionFilter ( "Picture files" , "png" );
    chooser.setFileFilter( filter );
    chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    chooser.setSelectedFile( new File( DEFAULT_FILE_NAME ) );
    // (re)start dialogue
    boolean inDialogue = true;
    while( inDialogue )
        {
        int select = chooser.showSaveDialog( parentWin );
        // save file
        if( select == JFileChooser.APPROVE_OPTION )
            {
            String filePath = chooser.getSelectedFile().getPath();
            int option = JOptionPane.YES_OPTION;
            // check file exist and warning message
            File file = new File( filePath );
            if( file.exists() == true )
                {
                option = JOptionPane.showConfirmDialog
                    ( null, 
                    "File exist: " + filePath + "\noverwrite?" , "IMAGE" ,
                    JOptionPane.YES_NO_CANCEL_OPTION ,
                    JOptionPane.WARNING_MESSAGE );  // or QUESTION_MESSAGE
                }
            // Select operation by user selection
            if ( ( option == JOptionPane.NO_OPTION  ) |
                 ( option == JOptionPane.CLOSED_OPTION ) )
                { 
                continue; 
                }
            if ( option == JOptionPane.CANCEL_OPTION ) 
                { 
                inDialogue = false;
                continue; 
                }
            // generate graphics image from drawings GUI panel
            int w = displayWin.getWidth();
            int h = displayWin.getHeight();
            BufferedImage image = 
                new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );
            Graphics2D g = image.createGraphics();
            g.setClip( 0, 0, w, h );
            displayWin.paint( g );
            g.drawImage( image, w, h, displayWin );
            g.dispose();
            // save graphics image to file
            int status = 0;
            try
                {
                ImageIO.write( image, "png", new File( filePath ) );
                }
            catch( Exception ex )
                {
                status = 1; 
                }
            // message box after report saved
            if ( status == 0 )  
                {
                JOptionPane.showMessageDialog
                ( parentWin, "Image saved: " + filePath, "IMAGE",
                JOptionPane.WARNING_MESSAGE ); 
                }
            else
                {
                JOptionPane.showMessageDialog
                ( parentWin, "Write image failed", "ERROR",
                JOptionPane.ERROR_MESSAGE ); 
                }
            inDialogue = false;
            }  
        else
            { 
            inDialogue = false; 
            }
        }   // End of save dialogue cycle
    }
}

// root menu handler = exit application, 
// note required check and terminate child processes
private class ExitListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        boolean b = false;
        for( int i=0; i<childCount; i++ )
            {
            Process p = childList[i];
            if ( ( p != null ) && ( p.isAlive() ) ) b = true;
            }
        if ( b )
            {  
            // This branch if minimum one child tasks active, required stop it
            JFrame f = Integrator.getApplication().getFrame();
            // this must run listener with task stop and file delete
            f.dispose();
            }
        else
            {  
            // This branch if no child tasks
            System.exit(0);    
            }
        }
    }

private class RunCalcListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        runStopChild( "JavaCalc", Childs.CALC.ordinal() );
        }
    }

private class RunCpuidListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        runStopChild( "Cpuid", Childs.CPUID.ordinal() );
        }
    }

private class RunMathListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        runStopChild( "JavaBench", Childs.MATH.ordinal() );
        }
    }

private class RunNioListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        runStopChild( "NIOBench", Childs.NIO.ordinal() );
        }
    }

private class RunPowerListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        runStopChild( "PowerInfo", Childs.POWER.ordinal() );
        }
    }

private class AboutListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event ) 
        {  
        Integrator.getAbout().show( Integrator.getApplication().getFrame() );
        }
    }

private class ChildsMenuListener implements MenuListener
    {
    @Override public void menuSelected( MenuEvent event ) 
        {
        // Get child processes activity list
        boolean[] b = new boolean[childCount];
        for( int i=0; i<childCount; i++ )
            {
            b[i] = false;
            Process p = childList[i];
            if ( ( p != null )&&( p.isAlive() ) )
                {
                b[i] = true;
                }
            }
        // Set selections at menu, selection = f(activity)
        Object ob = event.getSource( );
        if ( ob instanceof JMenu )
            {
            int itemsCount = ((JMenu)ob).getMenuComponentCount();
            Component[] c = ((JMenu)ob).getMenuComponents();
            for( int i=0; i<itemsCount; i++ )
                {
                if ( ( c[i] instanceof JCheckBoxMenuItem ) &&
                     ( i < childCount ) )
                    {
                    ( (JCheckBoxMenuItem)c[i] ).setSelected( b[i] );
                    }
                }
            }
        }
    // Unused events handlers
    @Override public void menuDeselected( MenuEvent event ) { }
    @Override public void menuCanceled( MenuEvent event ) { }
    }

}

