package graphImplementation;

import java.util.ArrayList;

public class WaterIceGrid 
{
	private int numRows;
	private int numColumns;
	private Graph<String> graph;
	private ArrayList<String> col1Vertices;
	
	public WaterIceGrid(int numRows, int numColumns)
	{
		this.numRows = numRows;
		this.numColumns = numColumns;
		graph = new Graph<String>(numColumns);
		col1Vertices = new ArrayList<String>();
	}
	
	public void buildRandomGrid()
	{
		graph.clear();
			
		for(int row = 1; row <= numRows; ++row)
		{
			for(int col = 1; col <= numColumns; ++col)
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
				graph.resetVisitedFlags();
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
				graph.resetVisitedFlags();
			}
		}
		
		return false;
	}
}
