package Simulation;

import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args)
	{
		int timesToLoop = 100000;
		
		WaterIceGrid grid = new WaterIceGrid();
		
		ArrayList<Character> data = new ArrayList<Character>();
		
		for(int i = 0; i < timesToLoop; ++i)
		{
			data.add(runSimulation(grid));
		}
		
		analyzeData(data);
	}
	
	private static char runSimulation(WaterIceGrid grid)
	{
		grid.buildRandomGrid();
		
		if(grid.isWaterPath())
		{
			if(grid.isIcePath())
			{
				return 'c';
			}
			
			return 'a';
		}
		else if(grid.isIcePath())
		{
			return 'b';
		}
		
		return 'd';
	}
	
	private static void analyzeData(ArrayList<Character> data)
	{
		int numA = 0;
		int numB = 0;
		int numC = 0;
		int numD = 0;
		
		for(int i = 0; i < data.size(); ++i)
		{
			if(data.get(i) == 'a')
			{
				numA++;
			}
			else if(data.get(i) == 'b')
			{
				numB++;
			}
			else if(data.get(i) == 'c')
			{
				numC++;
			}
			else
			{
				numD++;
			}
		}
		
		System.out.println("The probability of just the fish having a path is " + (int) (numA/data.size() * 100) + "%");
		System.out.println("The probability of just the penguin having a path is " + (int) (numB/data.size() * 100) + "%");
		System.out.println("The probability of both the fish and the penguin having a path is " + (int) (numC/data.size() * 100) + "%");
		System.out.println("The probability of neither the fish or the penguin having a path is " + (int) (numD/data.size() * 100) + "%");
	}
}
