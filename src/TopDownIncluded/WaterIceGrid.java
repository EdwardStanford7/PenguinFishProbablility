package TopDownIncluded;

import java.util.ArrayList;
import java.util.HashSet;

public class WaterIceGrid extends Thread
{
	private int numRows;
	private int numColumns;
	int numIterations;
	// Boolean value for isIce. True means square is ice, false means square is water.
	private ArrayList<ArrayList<Boolean>> grid;
	ArrayList<Integer> data;
	
	public WaterIceGrid(int numRows, int numColumns, int numIterations)
	{
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.numIterations = numIterations;
		this.data = new ArrayList<Integer>();
	}
	
	/**
	 * Gets the data from the simulations performed by this thread.
	 * @return an ArrayList of the data from all the simulations.
	 */
	public ArrayList<Integer> getData()
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
			
			boolean penguinTopDown = isPath(true, true);
			boolean penguinLeftRight = isPath(true, false);
			boolean FishTopDown = isPath(false, true);
			boolean FishLeftRight = isPath(false, false);

			if(penguinTopDown)
			{
				if(penguinLeftRight)
				{
					if(FishTopDown)
					{
						if(FishLeftRight)
						{
							data.add(1);
						}
						else
						{
							data.add(2);
						}
					}
					else
					{
						if(FishLeftRight)
						{
							data.add(3);
						}
						else
						{
							data.add(4);
						}
					}
				}
				else
				{
					if(FishTopDown)
					{
						if(FishLeftRight)
						{
							data.add(5);
						}
						else
						{
							data.add(6);
						}
					}
					else
					{
						if(FishLeftRight)
						{
							data.add(7);
						}
						else
						{
							data.add(8);
						}
					}
				}
			}
			else
			{
				if(penguinLeftRight)
				{
					if(FishTopDown)
					{
						if(FishLeftRight)
						{
							data.add(9);
						}
						else
						{
							data.add(10);
						}
					}
					else
					{
						if(FishLeftRight)
						{
							data.add(11);
						}
						else
						{
							data.add(12);
						}
					}
				}
				else
				{
					if(FishTopDown)
					{
						if(FishLeftRight)
						{
							data.add(13);
						}
						else
						{
							data.add(14);
						}
					}
					else
					{
						if(FishLeftRight)
						{
							data.add(15);
						}
						else
						{
							data.add(16);
						}
					}
				}
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
	
	private boolean isPath(boolean icePath, boolean topDown)
	{
		if(topDown)
		{
			for(int i = 0; i < numColumns; ++i)
			{
				if(grid.size() == 1)
				{
					return true;
				}
				
				HashSet<Pair<Integer, Integer>> visitedSquares = new HashSet<Pair<Integer, Integer>>();

				if(icePath && grid.get(0).get(i))
				{
					if(isPathRecursive(0, i, topDown, visitedSquares))
					{
						return true;
					}
				}
				else if(!icePath && !grid.get(0).get(i))
				{
					if(isPathRecursive(0, i, topDown, visitedSquares))
					{
						return true;
					}
				}
			}
		}
		else
		{
			for(int i = 0; i < numRows; ++i)
			{
				if(grid.get(0).size() == 1)
				{
					return true;
				}
				
				HashSet<Pair<Integer, Integer>> visitedSquares = new HashSet<Pair<Integer, Integer>>();

				if(icePath && grid.get(i).get(0))
				{
					if(isPathRecursive(i, 0, topDown, visitedSquares))
					{
						return true;
					}
				}
				else if(!icePath && !grid.get(i).get(0))
				{
					if(isPathRecursive(i, 0, topDown, visitedSquares))
					{
						return true;
					}
				}
			}
		}
			
		return false;
	}
	
	private boolean isPathRecursive(int row, int col, boolean topDown, HashSet<Pair<Integer, Integer>> visitedSquares)
	{
		visitedSquares.add(new Pair<Integer, Integer>(row, col));
			
		if(col == numColumns-1 && !topDown)
		{
			return true;
		}
		if(row == numRows-1 && topDown)
		{
			return true;
		}
				
		if(row != 0)
		{
			if(! visitedSquares.contains(new Pair<Integer, Integer>((row-1), col)))
			{
				if(grid.get(row).get(col) == grid.get(row-1).get(col))
				{
					if(isPathRecursive(row-1, col, topDown, visitedSquares))
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
					if(isPathRecursive(row+1, col, topDown, visitedSquares))
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
					if(isPathRecursive(row, col-1, topDown, visitedSquares))
					{
						return true;
					}
				}
			}
		}
		
		if(col != numColumns-1)
		{
			if(! visitedSquares.contains(new Pair<Integer, Integer>(row, (col+1))))
			{
				if(grid.get(row).get(col) == grid.get(row).get(col+1))
				{
					if(isPathRecursive(row, col+1, topDown, visitedSquares))
					{
						return true;
					}
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
