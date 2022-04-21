package MultiThreadExperiment;

import java.util.ArrayList;

public class Main 
{
	/**
	 * Arguments:
	 * arg1: number of rows
	 * arg2: number of columns
	 * arg3: number of simulations
	 * arg4: number of threads (must be an even divisor of number of simulations)
	 * @param args
	 */
	public static void main(String[] args)
	{
		int numRows = Integer.valueOf(args[0]);
		int numColumns = Integer.valueOf(args[1]);
		int timesToLoop = Integer.valueOf(args[2]);
		int numThreads = Integer.valueOf(args[3]);
		
		ArrayList<WaterIceGrid> allThreads = new ArrayList<WaterIceGrid>();		
		
		for(int i = 0; i < numThreads; ++i)
		{
			allThreads.add(new WaterIceGrid(numRows, numColumns, timesToLoop/numThreads));
			allThreads.get(i).start();
		}
		
		ArrayList<Character> data = new ArrayList<Character>();
		
		for(int i = 0; i < numThreads; ++i)
		{
			try 
			{
				allThreads.get(i).join();
			} 
			catch (InterruptedException e) {}
			
			for(char dataPiece: allThreads.get(i).getData())
			{
				data.add(dataPiece);
			}
		}
	
		analyzeData(data);
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
			else if((data.get(i) == 'd'))
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
