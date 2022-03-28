package Simulation;

import java.util.ArrayList;

public class WaterIceGrid 
{
	private Graph<String> graph = new Graph<String>();
	private ArrayList<String> row1Vertices = new ArrayList<String>();
	
	public void buildRandomGrid()
	{
		ArrayList<String> sources = new ArrayList<String>();
		ArrayList<String> destinations = new ArrayList<String>();
		
		for(int i = 0; i < 100; ++i)
		{
			for(int j = 0; j < 100; ++j)
			{
				addVertex(sources, destinations, row1Vertices, i, j);
			}
		}
		
		for(int i = 0; i < sources.size(); ++i)
		{
			graph.addEdge(sources.get(i), destinations.get(i));
		}
	}
	
	private void addVertex(ArrayList<String> sources, ArrayList<String> destinations, ArrayList<String> row1Vertices, int i, int j)
	{
		
	}
	
	public boolean isWaterPath()
	{
		for(String vertex: row1Vertices)
		{
			if(! graph.getVertex(vertex).isIce)
			{
				if(graph.isPathAcross(vertex))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isIcePath()
	{
		for(String vertex: row1Vertices)
		{
			if(graph.getVertex(vertex).isIce)
			{
				if(graph.isPathAcross(vertex))
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
