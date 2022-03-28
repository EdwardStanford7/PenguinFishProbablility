package Simulation;

import java.util.ArrayList;

public class WaterIceGrid 
{
	private Graph<String> graph = new Graph<String>();
	private ArrayList<String> col1Vertices = new ArrayList<String>();
	
	public void buildRandomGrid()
	{
		graph.clear();
			
		for(int row = 1; row <= 100; ++row)
		{
			for(int col = 1; col <= 100; ++col)
			{
				String currentVertex = row + "x" + col;
				String leftVertex = row + "x" + (col-1);
				String aboveVertex = (row-1) + "x" + col;
				
				boolean vertexIsIce;
				if(Math.random() < .5)
				{
					vertexIsIce = true;
				}
				else
				{
					vertexIsIce = false;;
				}
				
				if(col != 1)
				{
					graph.addEdge(currentVertex, leftVertex, vertexIsIce, col);
				}
				else
				{
					col1Vertices.add(currentVertex);
				}
				if(row != 1)
				{
					graph.addEdge(currentVertex, aboveVertex, vertexIsIce, col);
				}
			}
		}
	}
	
	public boolean isWaterPath()
	{
		for(String vertex: col1Vertices)
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
		for(String vertex: col1Vertices)
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
