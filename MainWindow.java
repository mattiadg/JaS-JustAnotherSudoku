package sudoku;

import javax.swing.JFrame;

/**
 * 
 * @author mattia
 * 
 * Visualizza la Gui del programma
 *
 */
public class MainWindow extends JFrame
{
	public static void main( String args[] )
	{
		GraphicalScheme graphScheme = new GraphicalScheme();
		graphScheme.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		graphScheme.setSize( 400, 500 );
		graphScheme.setVisible(true);
	}
}
