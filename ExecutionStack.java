/*Mantiene la coda di esecuzione delle mosse effettuate dal giocatore
 * Implementa le operazione di Annulla / Rifai mossa
 */
package sudoku;
import java.lang.NullPointerException;

public class ExecutionStack
{
    /** Attributes */
    private Move first;
    private Move last;
    
    /**Builder
     * Crea una lista vuota
     * 
     */
    public ExecutionStack(){
    	first = null;
    	last = null;
    }
    /**
     * Builder
     * Crea un loadExecutionStack dalla lista di mosse passata come parametro
     *
     * @param Move
     * @return int
     */
    public ExecutionStack ( Move list )
    {
    	first = null;
    	last = null;
    	while(list != null)
    	{
    		if(first == null)
    		{
    			first = list;
    		}
    		else
    		{
    			if( list.hasNext() == false )
    				last = list;
    		}
    		list = list.getNext();
    	}
    }
    /**
     * Operation
     *
     * @param move
     */
    public void addMove ( Move move )
    {
    	if(first == null)
    	{
    		first = move;
    		last = move;
    	}
    	else
    	{
    		last.setNext(move);
    		move.setPrevious(last);
    		last = move;
    	}
    }
    /**
     * Operation
     * Annulla l'ultima mossa effettuata dall'utente
     *
     * @return Move
     */
    public Move cancelMove (  )
    {
    	Move cnc = last;
    	try
    	{
    		last = last.getPrevious();
    	}catch(NullPointerException npe)
    	{
    		System.err.println("Non sono state effettuate mosse!");
    	}
    	return cnc;
    	
    }
    
    /**
     * Operation
     * Ripete l'ultima mossa annullata
     *
     */
    public Move redoMove (  )
    {
    	try
    	{
    		last = last.getNext();
    	}catch(NullPointerException npe)
    	{
    		System.err.println("Non sono state annullate mosse!");
    	}
    	return last;
    }
    
    /**
     * Operation
     * Restituisce un iteratore
     * 
     */
    protected Move iterator()
    {
    	return first;
    }
    
    public void print()
    {
    	Move tmp = first;
    	System.out.println("first="+first.getRow()+" "+first.getCol()+" "+first.getPrevVal()+" "+first.getNewVal()+" "+first.getPrevious());
    	while( tmp != null )
    	{
    		System.out.println(tmp.getRow() + " " + tmp.getCol() + " " + tmp.getPrevVal() + " " + tmp.getNewVal() + " " + tmp.getPrevious() );
    		tmp = tmp.getNext();
    	}
    	System.out.println("last="+last.getRow()+" "+last.getCol()+" "+last.getPrevVal()+" "+last.getNewVal()+" " + last.getPrevious());
    }
     
    /**Operation
     * Ritorna vero se lo Stack è vuoto
     */
    public boolean isEmpty()
    {
    	if( last == null)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}