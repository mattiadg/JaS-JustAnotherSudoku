package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JPanel;

/**
 * 
 * @author mattia
 * 
 * Rappresenta graficamente una griglia di sudoku e si rapporta con l'interfaccia sottostante
 *
 */
public class GraphicalScheme extends JFrame
{
	/*Attributi*/
    private GridLayout bigGrid;			//GridLayout per la pagina
    private GridLayout grids;			//GridLayout per un quadrto 3x3
    private JTextField[][] squares;		//Matrice dei quadrati
    private JPanel bigPanel;			
    private JPanel[][] littlePanels;	//Pannelli contenenti le griglie piccole
    public Scheme scheme;
    int left;							//Caselle vuote rimanenti
    
    /*Costruttore senza parametri*/
    public GraphicalScheme ( )
    {
    	super ("JaS - Just another Sudoku");
    	/*Crea la barra dei menu*/
    	JMenuBar bar = new Menu( this );
    	setJMenuBar( bar );
    	/*Imposta i layouts*/
    	bigGrid = new GridLayout( 3, 3, 5, 5);	//Crea la griglia grande
    	grids = new GridLayout( 3, 3, 2, 2 );				//Crea la matrice di griglie 3x3
    	/*Crea le matrici*/
    	squares = new JTextField[9][9];				//Crea la matrice di quadrati in cui inserire i numeri
    	littlePanels = new JPanel[3][3];				//Crea matrice di pannelli
    	/*Crea e gestisce i pannelli*/
    	bigPanel = new JPanel();
    	bigPanel.setLayout( bigGrid );
    	/*Inizializza lo schema*/
    	scheme = new Scheme();
    	left = 81;

    	/*Crea i textField della matrice*/
    	for(int i = 0; i < 9; i++)
    	{
    		for(int j = 0; j < 9; j++)
    		{
    			squares[i][j] = new JTextField( 1 );
    			squares[i][j].setFont( new Font ( "Serif", Font.PLAIN, 20) );
    			squares[i][j].setAlignmentX(RIGHT_ALIGNMENT);
    			squares[i][j].setAlignmentY(CENTER_ALIGNMENT);
    			squares[i][j].addKeyListener( new KeyHandler() );
    		}
    	}
    	
    	/*Inizializza le griglie della matrice, ne imposta il layout a griglia e lo aggiunge al pannello grande*/
    	for(int i = 0; i < 3; i++)
    	{
    		for(int j = 0; j < 3; j++)
    		{
    			littlePanels[i][j] = new JPanel();
    			littlePanels[i][j].setLayout( grids );
    			for(int k = 0; k < 3; k++)
    			{
    				littlePanels[i][j].add( squares[i * 3 + k][j * 3] );
    				littlePanels[i][j].add( squares[i * 3 + k][j * 3 + 1] );
    				littlePanels[i][j].add( squares[i * 3 + k][j * 3 + 2] );
    			}
    			bigPanel.add( littlePanels[i][j] );
    		}
    	}
    	add( bigPanel );	
    }
    /**
     * 
     * @param row
     * @param col
     * @return boolean
     * 
     * Controlla se il valore inserito era già presente nel quadrato 3x3
     */
    public void checkSquare(int row, int col, Color color)
    {
    	int rowLowLim, rowHighLim;		//Limiti inferiore e superiore di riga
    	int colLowLim, colHighLim;		//Limiti inferiore e superiore di colonna
    	char val = scheme.getSquare( row + 1, col + 1 );
    	   	
    	rowLowLim = (int) (row / 3) * 3;
    	rowHighLim = rowLowLim + 3;
    	colLowLim = (int) (col / 3) * 3;
    	colHighLim = colLowLim + 3;
    	
    	for(int i = rowLowLim; i < rowHighLim; i++)
    	{
    		for(int j = colLowLim; j < colHighLim; j++)
    		{
    			if( i == row && j == col )
    			{
    				continue;
    			}
    			if( scheme.getSquare( i + 1, j + 1 ) == val )
    			{
    				squares[i][j].setForeground( color );
    				squares[row][col].setForeground( color );
    			}
    		}
    	}
    }
    /**
     * Operation
     * @param row
     * @param col
     * @return
     * 
     * Controlla se il valore alla casella row, col è già presente nella riga
     */
    public void checkRow( int row, int col, Color color )
    {
    	char val = scheme.getSquare( row + 1, col + 1 );

    	for( int i = 0; i < 9; i++ )
    	{
    		if( i == col )
    		{
    			continue;
    		}
    		if( val != '@')
    		{
    			if( scheme.getSquare( row + 1, i + 1 ) == val )
    			{
    				squares[row][i].setForeground( color );
    				squares[row][col].setForeground( color );
    			}
    		}
    	}
    }
    
    /**
     * Operation
     * @param row
     * @param col
     * @return
     * 
     * Controlla se il valore alla casella row, col è già presente nella colonna
     */
    public void checkCol( int row, int col, Color color )
    {
    	char val = scheme.getSquare( row + 1, col + 1 );
    	
    	for( int i = 0; i < 9; i++ )
    	{
    		if( i == row )
    		{
    			continue;
    		}
    		if( scheme.getSquare( i + 1, col + 1 ) == val )
    		{
    			squares[i][col].setForeground( color );
				squares[row][col].setForeground( color );
    		}
    	}
    }
    
    /**
     * Operation
     * @param trgt
     * @return
     * 
     * trova riga e colonna di una casella scritta nella GUI
     */
    private Coord findJTextFieldCoord( JTextField trgt )
    {
    	for( int i = 0; i < 9; i++ )
    	{
    		for(int j = 0; j < 9; j++)
    		{
    			if( trgt.equals(squares[i][j]))
    			{
    				return new Coord( i, j );
    			}
    		}
    	}
    	return new Coord( -1, -1 );
    }
    
    /**
     * Operation
     * 
     * Inizializza lo schema con i valori che si trovano dentro scheme
     */
    public void initializeScheme( )
    {
    	for(int i = 1; i <= 9; i++)
    	{
    		for(int j = 1; j <= 9; j++)
    		{
    			char ch = scheme.getSquare(i,  j);
    			if(ch != '@')
    			{
    				squares[i-1][j-1].setText( ch + "" );
    				if( scheme.isStarting( i, j ) )
    				{
    					squares[i-1][j-1].setEditable( false );
    				}
    				left--;
    			}
    			else
    			{
    				squares[i-1][j-1].setText("");
    			}
    		}
    	}
    }
    /**
     * Operation
     * Annulla l'ultima mossa effettuata
     */
    public void cancel( )
    {
    	Coord c = scheme.cancel();
    	char val = scheme.getSquare( c.getRow() + 1, c.getCol() + 1 );
    	if( val != '@')
    	{
    		squares[c.getRow()][c.getCol()].setText(  val + "" );
    	}
    	else
    	{
    		squares[c.getRow()][c.getCol()].setText( "" );
    	}
    	
    }
    
    public void redo( )
    {
    	Coord c = scheme.redo();
    	squares[c.getRow()-1][c.getCol()-1].setText( scheme.getSquare( c.getRow(), c.getCol() ) + "" );
    }
    /**
     * Operation
     * Rende visibile lo schema
     */
    public void show( )
    {
    	bigPanel.setVisible( true );
    }
    /**
     * Handler degli eventi di tastiera
     */
    private class KeyHandler implements KeyListener
    {
    	public void keyPressed( KeyEvent event )
    	{
    		return;
    	}
    	
    	public void keyReleased( KeyEvent event )
    	{    		
    		Coord coord = findJTextFieldCoord( (JTextField) event.getSource() );
    		int row = coord.getRow(), col = coord.getCol();
    		
    		if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE)
    		{
    			checkSquare( row, col, Color.BLACK );
        		checkRow( row, col, Color.BLACK );
        		checkCol( row, col, Color.BLACK );	
        		left++;
    		}
    		else{
    			scheme.playMove( row + 1, col + 1, event.getKeyChar() );
    			checkSquare( row, col, Color.RED );
    			checkRow( row, col, Color.RED );
    			checkCol( row, col, Color.RED );
    			left--;
    		}
    		return;
    	}
    	
    	public void keyTyped( KeyEvent event)
    	{
    		char typed = event.getKeyChar();
    		JTextField tf = (JTextField) event.getSource();
    		if( typed >= '1' && typed <= '9')
    		{
    			tf.setText("");

    		}
    		else
    		{
    			event.consume();
    		}
    	}
    }
}
