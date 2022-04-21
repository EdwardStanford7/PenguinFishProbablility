package arrayListImplementation;

import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args)
	{
		int timesToLoop = 100000;
		
		WaterIceGrid grid = new WaterIceGrid(100, 100);
		
		ArrayList<Character> data = new ArrayList<Character>();
		
		for(int i = 0; i < timesToLoop; ++i)
		{
			if(i % 1000 == 0 && i <= timesToLoop - 1000)
			{
				System.out.println("running simulations " + (i+1) + "-" + (i+1000));
			}
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
		
		
		System.out.println("\nFor " + data.size() + " simulations: ");
		System.out.println("The fish was able to make it across " + numA + " times.");
		System.out.println("The penguin was able to make it across " + numB + " times.");
		System.out.println("Both the fish and the penguin were able to make it across " + numC + " times.");
		System.out.println("Neither the fish nor the penguin were able to make it across " + numD + " times.");
	}
}
