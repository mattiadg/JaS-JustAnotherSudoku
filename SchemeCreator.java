package sudoku;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author mattia
 *
 *Crea uno Scheme a partire da un file salvato in memoria o lo genera casualmente
 */


public class SchemeCreator
{
    /**
     * Operation
     *
     * @param filename
     * @return Scheme
     * 
     * Carica uno Scheme da un file e lo restituisce al chiamante
     */
    public static Scheme loadFromFile ( String filename )
    {
    	Scheme sch = new Scheme();		//Scheme da restituire
    	Scanner input = null;			//Usato per leggere dal file
    	Move list = null;				//Lista di mosse usata per inizializzare ExecutionStack
    	Move next = null;				//Puntatore a Move usato per memorizzare le mosse lette
    	char[] buffer = new char[9];	//Buffer dove posizionare le righe del sudoku lette dal file
    	
    	int row, col;					//Valori della lista delle mosse
    	char prev, act;					//Letti e da memorizzare in un oggeto Move
    	
	   	/*Apre il file*/
    	try
    	{
    		input = new Scanner( new File(filename) );
    	}catch(FileNotFoundException fileNotFoundException)
    	{
    		System.err.println("Errore! Il file non esiste!");
    		System.exit(1);
    	}
    	
    	/*Legge lo schema iniziale*/
    	try
    	{
    		for(int i = 0; i < 9; i++)
    		{
    			buffer = input.next().toCharArray();
    			for(int j = 0; j < 9; j++)
    			{
    				if(buffer[j] != '@')
    					sch.setStartingScheme(i + 1, j + 1, buffer[j]);
    			}
    		}
    	}catch( NoSuchElementException noSuchElementException)
    	{
    		System.err.println("Il file non è congruente1");
    		input.close();
    		System.exit(1);
    	}
    	catch( IllegalStateException illegalStateException)
    	{
    		System.err.println("Errore nella lettura dal file");
    		System.exit(1);
    	}
    	
    	/*Legge l'ExecutionStack*/
    	try
    	{
    		while(input.hasNext())
    		{
    			row = input.nextInt();
    			col = input.nextInt();
    			act = input.next().toCharArray()[0];
    
    			sch.playMove(row, col, act);
    		}
    	}catch( NoSuchElementException noSuchElementException)
    	{
    		System.err.println("Il file non è congruente2");
    		input.close();
    		System.exit(1);
    	}
    	catch( IllegalStateException illegalStateException)
    	{
    		System.err.println("Errore nella lettura dal file");
    		System.exit(1);
    	}
    	
    	/*Letto tutto il file è necessario chiuderlo*/
    	if(input != null)
    		input.close();
    	
    	/*Crea l'ExecutionStack a partire da list*/
    	sch.print();
    	
    	return sch;
    }
    /**
     * Operation
     *
     * @param difficulty
     * @return Scheme
     */
    public static Scheme randomGenerator ( /*String difficulty*/ )
    {
    	Scheme sch;							//Schema da ritornare
    	int[][][] matrix = new int[9][][];					//Matrice base per generare la partita
    	matrix[0] = createRandomMatrix( );
    	
    	return new Scheme();    	
    }
    /**
     * Crea una matrice casuale 3x3
     * 
     */
    private static int[][] createRandomMatrix( )
    {
    	int[][] matrix = new int[3][3];							//Matrice daritornare
    	ArrayList<Integer> l = new ArrayList<Integer>(9);		//Lista dinamica contenente i valori da 1 a 9
    	Random gen = new Random();								//Pseudorandom generator
    	int seed = 9;												//Seme di pseudo-causalità
    	
    	/*Inizializza l*/
    	for(int i = 0; i < 9; i++)
    	{
    		l.add(i+1);
    	}
    	/*Crea la matrice randomica*/
    	for(int i = 0; i < 3; i++)
    	{
    		for(int j = 0; j < 3; j++)
    		{
    			matrix[i][j] = l.remove(gen.nextInt(seed));
    			seed--;
    		}
    	}
    	return matrix;    	
    }
    
}