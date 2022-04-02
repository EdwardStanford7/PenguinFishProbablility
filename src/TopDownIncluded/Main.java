package TopDownIncluded;

import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args)
	{
		int timesToLoop = 100000;
		int numThreads = 10;
		ArrayList<WaterIceGrid> allThreads = new ArrayList<WaterIceGrid>();		
		
		for(int i = 0; i < numThreads; ++i)
		{
			allThreads.add(new WaterIceGrid(10, 1, timesToLoop/numThreads));
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
			
		analyzeData(data);
	}
	
	private static void analyzeData(ArrayList<Integer> data)
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
		int ten = 0;
		int eleven = 0;
		int twelve = 0;
		int thirteen = 0;
		int fourteen = 0;
		int fifteen = 0;
		int sixteen = 0;

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
				continue;
			case 10:
				ten++;
				continue;
			case 11:
				eleven++;
				continue;
			case 12:
				twelve++;
				continue;
			case 13:
				thirteen++;
				continue;
			case 14:
				fourteen++;
				continue;
			case 15:
				fifteen++;
				continue;
			case 16:
				sixteen++;
			}
		}
		
		System.out.println("\nFor " + data.size() + " simulations: ");
		System.out.println(one + two + three + four + five + six + seven + eight + nine + ten + eleven + twelve + thirteen + fourteen + fifteen + sixteen);
		System.out.println(one + ", " + two + ", " + three + ", " + four + ", " + five + ", " + six + ", " + seven + ", " + eight + ", " + nine + ", " + ten + ", " + eleven + ", " + twelve + ", " + thirteen + ", " + fourteen + ", " + fifteen + ", " + sixteen);
	}
}
