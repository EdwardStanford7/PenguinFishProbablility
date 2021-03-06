package graphImplementation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph<T> 
{
	// the graph -- a set of vertices (String name mapped to Vertex instance)
	private HashMap<T, Vertex> vertices;
	private int numColumns;

	/**
	 * Constructs an empty graph.
	 */
	public Graph(int numColumns) 
	{
		this.numColumns = numColumns;
		vertices = new HashMap<T, Vertex>();
	}

	/**
	 * Clears the graph.
	 */
	public void clear()
	{
		vertices.clear();
	}
	
	public void resetVisitedFlags()
	{
		for(T vertex: vertices.keySet())
		{
			vertices.get(vertex).hasBeenVisited = false;
		}
	}
	
	/**
	 * Getter for specific vertex within the graph.
	 */
	public Vertex getVertex(T vertexName)
	{
		return vertices.get(vertexName);
	}
	
	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" 
	 * to the vertex with name "name2".  (If either vertex does not already 
	 * exist in the graph, it is added.)
	 * 
	 * @param name1 - generic type T name for source vertex
	 * @param name2 - generic type T name for destination vertex
	 */
	public void addEdge(T name1, T name2, boolean srcVertexIsIce, int srcVertexcol)
	{
		Vertex vertex1;
		// if vertex already exists in graph, get its object
		if(vertices.containsKey(name1))
		{
			vertex1 = vertices.get(name1);
		}
		// else, create a new object and add to graph
		else 
		{
			vertex1 = new Vertex(name1, srcVertexIsIce, srcVertexcol);
			vertices.put(name1, vertex1);
		}

		Vertex vertex2;
		if(vertices.containsKey(name2))
		{
			vertex2 = vertices.get(name2);
		}
		else 
		{			
			boolean vertexIsIce;
			if(Math.random() < .5)
			{
				vertexIsIce = true;
			}
			else
			{
				vertexIsIce = false;;
			}
			
			vertex2 = new Vertex(name2, vertexIsIce, 1);
			vertices.put(name2, vertex2);
		}

		// add new undirected edge from vertex1 to vertex2
		if(vertex1.isIce == vertex2.isIce)
		{
			vertex1.addEdge(vertex2);
			vertex2.addEdge(vertex1);
		}
	}
	
	/**
	 * Checks if there is a path from the src vertex across to any vertex in the last column of the grid.
	 * @param src the starting vertex
	 * @return true if there is a path across, false otherwise.
	 */
	public boolean isPathAcross(T src)
	{
		if(vertices.get(src).column == numColumns)
		{
			return true;
		}
		
		return isPathAcrossRecursive(src);
	}
	
	/**
	 * Performs a depth first search to check if there is a path from the given vertex to the other end of the grid.
	 * @param src the source vertex to start from.
	 * @return true if a path exists, false otherwise.
	 */
	private boolean isPathAcrossRecursive(T src)
	{		
		vertices.get(src).hasBeenVisited = true;
		
		Iterator<Edge> iterator = vertices.get(src).edges();
		
		while(iterator.hasNext())
		{
			T vertex = iterator.next().getOtherVertex().name;
			
			if(vertices.get(vertex).hasBeenVisited)
			{
				continue;
			}
			
			if(vertices.get(vertex).column == numColumns)
			{
				return true;
			}
			
			vertices.get(vertex).hasBeenVisited = true;
			if(isPathAcross(vertex))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This class represents a vertex (AKA node) in a directed graph.
	 * The vertex is generic.
	 *  
	 * @author Erin Parker & Edward Stanford & Sara Fong
	 * @version March 14, 2022
	 */
	public class Vertex
	{
		// used to id the Vertex
		public T name;
		public boolean hasBeenVisited;
		public boolean isIce;
		public int column;
	
		// adjacency list
		private LinkedList<Edge> adj;

		/**
		 * Creates a new Vertex object, using the given name.
		 * 
		 * @param name - object T used to identify this Vertex
		 */
		public Vertex(T name, boolean isIce, int column) 
		{
			this.name = name;
			this.adj = new LinkedList<Edge>();
			this.hasBeenVisited = false;
			this.isIce = isIce;
			this.column = column;
		}

		/**
		 * Adds a directed edge from this Vertex to another.
		 * 
		 * @param otherVertex - the Vertex object that is the destination of the edge
		 */
		public void addEdge(Vertex otherVertex) 
		{
			adj.add(new Edge(otherVertex));
		}
	 
		/**
		 * @return a iterator for accessing the edges for which this Vertex is the source
		 */
		public Iterator<Edge> edges() 
		{
			return adj.iterator();
		}
	}
	
	/**
	 * This class represents an edge between a source vertex and a destination
	 * vertex in a directed graph.
	 * 
	 * The source of this edge is the Vertex whose object has an adjacency list
	 * containing this edge.
	 * 
	 * @author Erin Parker
	 * @version March 3, 2022
	 */
	public class Edge
	{

		// destination of this directed edge
		private Vertex dst;

		/**
		 * Creates an Edge object, given the Vertex that is the destination.
		 * (The Vertex that stores this Edge object is the source.)
		 * 
		 * @param dst - the destination Vertex
		 */
		public Edge(Vertex dst) 
		{
			this.dst = dst;
		}

		/**
		 * @return the destination Vertex of this Edge
		 */
		public Vertex getOtherVertex() 
		{
			return this.dst;
		}
	}
}
