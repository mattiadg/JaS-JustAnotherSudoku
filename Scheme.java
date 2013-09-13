package sudoku;

import java.util.Formatter;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.FormatterClosedException;

public class Scheme
{
    /** Attributes */
    private char[][] schema;			//Scheme modified during the game
    private char[][] schemaIniziale;	//Starting scheme. It cannot be modified
    private ExecutionStack mosse;		//Moves sequence
    private Formatter output;			//Puntatore al file di salvataggio

    
    /**
     * Builder
     * 
     */
    public Scheme()
    {
    	schemaIniziale = new char[9][9];
    	schema = new char[9][9];
    	mosse = new ExecutionStack();
    	for(int i = 0; i < 9; i++)
    	{
    		for(int j = 0; j < 9; j++)
    		{
    			schemaIniziale[i][j] = '@';
    			schema[i][j] = '@';
    		}
    	}
    }
    
    public void playMove( int row, int col, char val )
    {
    	char oldVal = getSquare(row, col);
    	setSquare( row, col, val );
    	mosse.addMove( new Move( row, col, oldVal, val) );
    }
    /**
     * Operation
     *
     * @param row
     * @param col
     * @return char
     */
    public char getSquare ( int row, int col )
    {
        if(row >= 1 && row <= 9 && col >= 1 && col <= 9)
        	return schema[row-1][col-1];
        else
        	return 'x';
    }
    /**
     * Operation
     *
     * @param row
     * @param col
     * @param var
     */
    private void setSquare ( int row, int col, char val )
    {
    	if(row >= 1 && row <= 9 && col >= 1 && col <= 9){
    		if(val >= '1' && val <= '9')
    			schema[row-1][col-1] = val;
    		else
    			schema[row-1][col-1] = '@';			
    	}  
    }
    /**
     * Operation
     *
     * @return Move
     */
    public void cancel (  )
    {
    	Move nuova;
        nuova = mosse.cancelMove();
        setSquare(nuova.getRow(), nuova.getCol(), nuova.getPrevVal());
    }
    /**
     * Operation
     *
     * @return Move
     */
    public void redo (  )
    {
    	Move nuova;
        nuova = mosse.redoMove();
        setSquare(nuova.getRow(), nuova.getCol(), nuova.getNewVal());
    }
    /**
     * Operation
     *  
     * @param row
     * @param col
     * @param val
     * 
     * Imposta i valori dello schema iniziale
     */
    public void setStartingScheme(int row, int col, char val)
    {
    	schemaIniziale[row - 1][col - 1] = val;
    	schema[row - 1][col - 1] = val;
    }
    
    /**
     * Operation
     * @param row
     * @param col
     * @return
     * 
     * Restituisce vero se la casella è iniziale (contiene un numero già dallo
     * schema iniziale
     */
    public boolean isStarting( int row, int col )
    {
    	if( schemaIniziale[row-1][col-1] != '@' )
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    /**Operation
     * 
     * @param list
     * 
     * Crea un nuovo ExecutionStack
     */
    public void createExecutionStack( Move list )
    {
    	Move tmp;
    	mosse = new ExecutionStack ( list );
    	tmp = mosse.iterator();
    	while(tmp != null)
    	{
    		setSquare( tmp.getRow(), tmp.getCol(), tmp.getNewVal() );
    		tmp = tmp.getNext();
    	}
    }
    /**
     * Operation
     *
     * @return int
     */
    public int saveGame ( String filename )
    {
        Move print = mosse.iterator();
        openFile( filename );
        /*Salva la matrice originale sul file*/
        for(int i = 0; i < 9; i++ )
        {
        	for(int j = 0; j < 9; j++ )
        	{
        		try
        		{
        			output.format( "%c", schemaIniziale[i][j]);
        			if(j == 8)
        				output.format("\n");
        		}
        		catch( FormatterClosedException fce)
        		{
        			System.err.println("Errore nella scrittura del file");
        			return -1;
        		}
        	}
        }
        
        while(print != null)
        {
        	/*Stampa la coda delle mosse*/
        	try
        	{
        		output.format("%d %d %c %c\n", print.getRow(), print.getCol(), print.getPrevVal(), print.getNewVal());
        	}
        	catch( FormatterClosedException fce)
        	{
        		System.err.println("Errore nell'apertura del file!");
        		return -2;
        	}
        	catch(NullPointerException nullPointer)
        	{
        		System.err.println("Puntatore nullo nel salvataggio delle mosse!");
        		return -3;
        	}
        	/*Dopo aver salvato un record passa al successivo se esiste*/
        	print = print.getNext();
        }
        output.close();
        return 0;
    }
    
    private void openFile( String filename )
    {
    	try
    	{
    		output = new Formatter( filename );
    	}
    	catch( SecurityException securityException)
    	{
    		System.err.println("Non hai i permessi per scrivere questo file");
    		System.exit(1);
    	}
    	catch(FileNotFoundException fnfe)
    	{
    		System.err.println("Errore nella creazione del file");
    		System.exit(1);
    	}
    }

	public void print()
	{
		for(int i = 1; i <= 9; i++)
		{
			for(int j = 1; j <= 9; j++)
			{
				System.out.print( getSquare( i, j ) );
			}
			System.out.println();
		}
		mosse.print();
	}
}