package sudoku;

public class Move
{
    /** Attributes */
    private int row;
    private int col;
    private char previousVal;
    private char newVal;
    private Move previousMove;
    private Move nextMove;
 

    /** Builder */
    public Move( int row, int col, char oldVal, char newVal )
    {
    	this.row = row;
    	this.col = col;
    	this.previousVal = oldVal;
    	this.newVal = newVal;
    	this.previousMove = null;
    	this.nextMove = null;
    }
    /**
     * Operation
     *
     * @return byte
     */
    public int getRow (  )
    {
        return row;
    }
    /**
     * Operation
     *
     * @return byte
     */
    public int getCol (  )
    {
        return col;
    }
    /**
     * Operation
     *
     * @return char
     */
    public char getPrevVal ( )
    {
    	return previousVal;
    }
    /**
     * Operation
     *
     * @return char
     */
    public char getNewVal ( )
    {
    	return newVal;
    }
    /**
     * Operation
     *
     * @return Move
     */
    public Move getPrevious (  )
    {
        return previousMove;
    }
    /**
     * Operation
     *
     * @return Move
     */
    public Move getNext (  )
    {
        return nextMove;
    }
    /**
     * Operation
     *
     * @param next
     */
    public void setNext ( Move next )
    {
        nextMove = next;
    }
    /**
     * Operation
     *
     * @param prev
     */
    public void setPrevious ( Move prev )
    {
        previousMove = prev;
    }
    /**Operation
     * Restituisce vero se l'oggetto ha un successivo
     * 
     */
    public boolean hasNext()
    {
    	if(nextMove != null)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}