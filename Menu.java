package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JMenuBar
{
	/*Attributi*/
	private JMenu fileMenu;
	private JMenuItem newGame;
	private JMenuItem loadGame;
	private JMenuItem saveGame;
	private JMenuItem exit;
	private JMenu editMenu;
	private JMenuItem undo;
	private JMenuItem redo;
	private GraphicalScheme parent;
	
	/**
	 * Builder
	 * 
	 * Crea la barra dei menu
	 */
	public Menu( final GraphicalScheme parent )
	{
		/*Riferimento al frame che lo contiene*/
		this.parent = parent;
		/*Crea barra del menu*/
		fileMenu = new JMenu( "File" );			
    	fileMenu.setMnemonic( 'f' );
    	newGame = new JMenuItem( "Nuova Partita" );
    	newGame.setMnemonic( 'n' );
    	fileMenu.add( newGame );
    	loadGame = new JMenuItem( "Carica Partita" );
    	loadGame.setMnemonic( 'l' );
    	fileMenu.add( loadGame );
    	saveGame = new JMenuItem( "Salva partita" );
    	saveGame.setMnemonic( 's' );
    	fileMenu.add( saveGame );
    	exit = new JMenuItem( "Esci" );
    	exit.setMnemonic( 'x' );
    	fileMenu.add( exit );
    	add( fileMenu );
    	editMenu = new JMenu( "Modifica" );
    	editMenu.setMnemonic( 'e' );
    	undo = new JMenuItem( "Annulla" );
    	undo.setMnemonic( 'u' );
    	editMenu.add( undo );
    	redo = new JMenuItem( "Ripeti" );
    	redo.setMnemonic( 'r' );
    	editMenu.add( redo );
    	add( editMenu );
    	/*Aggiunge handlers degli eventi*/
    	loadGame.addActionListener(					//Aggiunge handler al MenuItem loadGame
    		new ActionListener() //Classe inner anonima
    		{
    			//Sceglie un file con JFileChooser e carica la partita
    			public void actionPerformed( ActionEvent event )
    			{
    				final JFileChooser chooser = new JFileChooser();
    				chooser.setFileFilter( new FileNameExtensionFilter( "sudoku savings", "sdk" ) );
    				int returnVal = chooser.showOpenDialog( Menu.this );
    				if( returnVal == JFileChooser.APPROVE_OPTION )
    				{
    					parent.scheme = SchemeCreator.loadFromFile( chooser.getName( chooser.getSelectedFile() ));
    					parent.initializeScheme();
    					parent.show();
    				}
    			}
    		});
    	saveGame.addActionListener(
    		new ActionListener()	//Classe inner anonima
    		{
    			//Salva la partita in un file scelto con JFileChooser
				@Override
				public void actionPerformed(ActionEvent e) {
					final JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter( new FileNameExtensionFilter( "sudoku savings", "sdk" ) );
					int returnVal = chooser.showSaveDialog( Menu.this );
					if( returnVal == JFileChooser.APPROVE_OPTION )
    				{
						parent.scheme.saveGame(chooser.getName( chooser.getSelectedFile() ));
    				}
				}
    			
    			
    		});
    	undo.addActionListener(
    		new ActionListener()	//Classe inner anonima
    		{
    			//Annulla l'ultima mossa fatta
				@Override
				public void actionPerformed(ActionEvent arg0) {
					parent.cancel();					
				}
    			
    		});
    	redo.addActionListener(
        		new ActionListener()	//Classe inner anonima
        		{
        			//Ripete l'ultima mossa annullata
    				@Override
    				public void actionPerformed(ActionEvent arg0) {
    					parent.redo();					
    				}
        		});
	}
}

