package MultiThreadExperiment;

import java.util.ArrayList;
import java.util.HashSet;

public class WaterIceGrid extends Thread
{
	private int numRows;
	private int numColumns;
	int numIterations;
	// Boolean value for isIce. True means square is ice, false means square is water.
	private ArrayList<ArrayList<Boolean>> grid;
	ArrayList<Character> data;
	
	public WaterIceGrid(int numRows, int numColumns, int numIterations)
	{
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.numIterations = numIterations;
		this.data = new ArrayList<Character>();
	}
	
	/**
	 * Gets the data from the simulations performed by this thread.
	 * @return an ArrayList of the data from all the simulations.
	 */
	public ArrayList<Character> getData()
	{
		return data;
	}
	
	@Override
	public void run()
	{
		for(int i = 0; i < numIterations; ++i)
		{
			if(i % 1000 == 0)
			{
				System.out.println("Simulation " + (i+1));
			}
			buildRandomGrid();
			
			if(isWaterPath())
			{
				if(isIcePath())
				{
					data.add('c');
				}
				else
				{
					data.add('a');
				}
			}
			else if(isIcePath())
			{
				data.add('b');
			}
			else
			{
				data.add('d');
			}
		}
	}
	
	private void buildRandomGrid()
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
	
	private boolean isWaterPath()
	{
		for(int row = 0; row < grid.size(); ++row)
		{
			if(! grid.get(row).get(0))
			{
				if(grid.get(row).size() == 1)
				{
					return true;
				}
				
				HashSet<Pair<Integer, Integer>> visitedSquares = new HashSet<Pair<Integer, Integer>>();
				
				if(isPath(row, 0, visitedSquares))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean isIcePath()
	{
		for(int row = 0; row < grid.size(); ++row)
		{
			if(grid.get(row).get(0))
			{
				if(grid.get(row).size() == 1)
				{
					return true;
				}
				
				HashSet<Pair<Integer, Integer>> visitedSquares = new HashSet<Pair<Integer, Integer>>();
				
				if(isPath(row, 0, visitedSquares))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean isPath(int row, int col, HashSet<Pair<Integer, Integer>> visitedSquares)
	{
		visitedSquares.add(new Pair<Integer, Integer>(row, col));
			
		if(col == numColumns-1)
		{
			return true;
		}
				
		if(row != 0)
		{
			if(! visitedSquares.contains(new Pair<Integer, Integer>((row-1), col)))
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
			if(! visitedSquares.contains(new Pair<Integer, Integer>((row+1), col)))
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
			if(! visitedSquares.contains(new Pair<Integer, Integer>(row, (col-1))))
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
		
		if(! visitedSquares.contains(new Pair<Integer, Integer>(row, (col+1))))
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
	
	private class Pair<A, B> 
	{
	    private A first;
	    private B second;

	    public Pair(A first, B second) 
	    {
	        super();
	        this.first = first;
	        this.second = second;
	    }

	    public int hashCode() 
	    {
	        int hashFirst = first != null ? first.hashCode() : 0;
	        int hashSecond = second != null ? second.hashCode() : 0;

	        return (hashFirst + hashSecond) * hashSecond + hashFirst;
	    }

	    public boolean equals(Object other) 
	    {
	        if (other instanceof Pair) 
	        {
	            @SuppressWarnings("rawtypes")
				Pair otherPair = (Pair) other;
	            return 
	            ((  this.first == otherPair.first ||
	                ( this.first != null && otherPair.first != null &&
	                  this.first.equals(otherPair.first))) &&
	             (  this.second == otherPair.second ||
	                ( this.second != null && otherPair.second != null &&
	                  this.second.equals(otherPair.second))) );
	        }

	        return false;
	    }

	    public String toString()
	    { 
	           return "(" + first + ", " + second + ")"; 
	    }
	}
}
