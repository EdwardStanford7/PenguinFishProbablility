package TopDownIncluded;

import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args)
	{
		int timesToLoop = 1000000;
		int numThreads = 10;
		int numRows = 100;
		int numColumns = 100;
		ArrayList<WaterIceGrid> allThreads = new ArrayList<WaterIceGrid>();		
		
		for(int i = 0; i < numThreads; ++i)
		{
			allThreads.add(new WaterIceGrid(numRows, numColumns, timesToLoop/numThreads));
			allThreads.get(i).start();
		}
		
		ArrayList<Integer> data = new ArrayList<Integer>();
		
		for(int i = 0; i < numThreads; ++i)
		{
			try 
			{
				allThreads.get(i).join();
			} 
			catch (InterruptedException e) {}
			
			for(int dataPiece: allThreads.get(i).getData())
			{
				data.add(dataPiece);
			}
		}
			
		analyzeData(data, numRows, numColumns);
	}
	
	private static void analyzeData(ArrayList<Integer> data, int numRows, int numColumns)
	{
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		int seven = 0;
		int eight = 0;
		int nine = 0;

		for(int i = 0; i < data.size(); ++i)
		{
			switch(data.get(i))
			{
			case 1:
				one++;
				continue;
			case 2:
				two++;
				continue;
			case 3:
				three++;		
				continue;
			case 4:
				four++;
				continue;
			case 5:
				five++;
				continue;
			case 6:
				six++;
				continue;
			case 7:
				seven++;
				continue;
			case 8:
				eight++;
				continue;
			case 9:
				nine++;
			}
		}
		
		System.out.println("\nFor " + data.size() + " simulations on a " + numRows + "x" + numColumns + " grid:");
		System.out.println("The penguin made it both directions and the fish couldn't cross at all " + one + " times");
		System.out.println("The penguin and the fish made it top-down " + two + " times");
		System.out.println("The penguin made it top-down and the fish couldn't cross at all " + three + " times");
		System.out.println("The penguin and the fish made it left-right " + four + " times");
		System.out.println("The penguin made it left-right and the fish couldn't cross at all " + five + " times");
		System.out.println("The penguin couldn't cross at all and the fish made it both directions " + six + " times");
		System.out.println("The penguin couldn't cross at all and the fish made it top-down " + seven + " times");
		System.out.println("The penguin couldn't cross at all and the fish made it left-right " + eight + " times");
		System.out.println("The penguin and the fish couldn't cross at all " + nine + " times");
	}
}
