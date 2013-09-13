package sudoku;

public class Coord {
	/**Attributes*/
	private int row;
	private int col;
	
	/**
	 * Builder
	 * @param row
	 * @param col
	 * 
	 * Crea una nuova coordinata con i valori specificati di riga e colonna
	 */
	public Coord( int row, int col )
	{
		this.row = row;
		this.col = col;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
}
