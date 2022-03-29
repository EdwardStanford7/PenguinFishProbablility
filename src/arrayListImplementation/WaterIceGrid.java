package arrayListImplementation;

import java.util.ArrayList;
import java.util.HashSet;

public class WaterIceGrid 
{
	private int numRows;
	private int numColumns;
	// Boolean value for isIce. True means square is ice, false means square is water.
	private ArrayList<ArrayList<Boolean>> grid;
	
	public WaterIceGrid(int numRows, int numColumns)
	{
		this.numRows = numRows;
		this.numColumns = numColumns;
	}
	
	public void buildRandomGrid()
	{	
		grid = new ArrayList<ArrayList<Boolean>>();
		
		for(int row = 0; row < numRows; ++row)
		{
			grid.add(new ArrayList<Boolean>());
			for(int col = 0; col < numColumns; ++col)
			{
				if(Math.random() < .5)
				{
					grid.get(row).add(true);
				}
				else
				{
					grid.get(row).add(false);
				}
			}
		}
	}
	
	public boolean isWaterPath()
	{
		for(int row = 0; row < grid.size(); ++row)
		{
			if(! grid.get(row).get(0))
			{
				if(grid.get(row).size() == 1)
				{
					return true;
				}
				
				HashSet<String> visitedSquares = new HashSet<String>();
				
				if(isPath(row, 0, visitedSquares))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isIcePath()
	{
		for(int row = 0; row < grid.size(); ++row)
		{
			if(grid.get(row).get(0))
			{
				if(grid.get(row).size() == 1)
				{
					return true;
				}
				
				HashSet<String> visitedSquares = new HashSet<String>();
				
				if(isPath(row, 0, visitedSquares))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean isPath(int row, int col, HashSet<String> visitedSquares)
	{
		visitedSquares.add(row + "x" + col);
		
		if(col == numColumns-1)
		{
			return true;
		}
				
		if(row != 0)
		{
			if(! visitedSquares.contains((row-1) + "x" + col))
			{
				if(grid.get(row).get(col) == grid.get(row-1).get(col))
				{
					if(isPath(row-1, col, visitedSquares))
					{
						return true;
					}
				}
			}
		}
		
		if(row != numRows-1)
		{
			if(! visitedSquares.contains((row+1) + "x" + col))
			{
				if(grid.get(row).get(col) == grid.get(row+1).get(col))
				{
					if(isPath(row+1, col, visitedSquares))
					{
						return true;
					}
				}
			}
		}
		
		if(col != 0)
		{
			if(! visitedSquares.contains(row + "x" + (col-1)))
			{
				if(grid.get(row).get(col) == grid.get(row).get(col-1))
				{
					if(isPath(row, col-1, visitedSquares))
					{
						return true;
					}
				}
			}
		}
		
		if(! visitedSquares.contains(row + "x" + (col+1)))
		{
			if(grid.get(row).get(col) == grid.get(row).get(col+1))
			{
				if(isPath(row, col+1, visitedSquares))
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
