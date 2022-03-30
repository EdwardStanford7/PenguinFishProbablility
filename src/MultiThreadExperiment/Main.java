package MultiThreadExperiment;

import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args)
	{
		int timesToLoop = 10000000;
		int numThreads = 10;
		ArrayList<WaterIceGrid> allThreads = new ArrayList<WaterIceGrid>();		
		
		for(int i = 0; i < numThreads; ++i)
		{
			allThreads.add(new WaterIceGrid(42, 49, timesToLoop/numThreads));
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
