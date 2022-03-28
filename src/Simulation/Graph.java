package Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a sparse, unweighted, directed graph (a set of vertices and a set of edges). 
 * The graph is generic.
 * 
 * @author Erin Parker & Edward Stanford & Sara Fong 
 * @version March 14, 2022
 */
public class Graph<T> 
{
	// the graph -- a set of vertices (String name mapped to Vertex instance)
	private HashMap<T, Vertex> vertices;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() 
	{
		vertices = new HashMap<T, Vertex>();
	}

	/**
	 * Getter for the number of vertices within the graph.
	 */
	public int getNumberVertices()
	{
		return vertices.size();
	}
	
	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" 
	 * to the vertex with name "name2".  (If either vertex does not already 
	 * exist in the graph, it is added.)
	 * 
	 * @param name1 - generic type T name for source vertex
	 * @param name2 - generic type T name for destination vertex
	 */
	public void addEdge(T name1, T name2, boolean vertex1IsIce, boolean vertex2IsIce, int col1, int col2)
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
			vertex1 = new Vertex(name1, vertex1IsIce, col1);
			vertices.put(name1, vertex1);
		}

		Vertex vertex2;
		if(vertices.containsKey(name2))
		{
			vertex2 = vertices.get(name2);
		}
		else 
		{
			vertex2 = new Vertex(name2, vertex2IsIce, col2);
			vertices.put(name2, vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}
	
	/**
	 * Generates the DOT encoding of this graph as string, which can be 
	 * pasted into http://www.webgraphviz.com to produce a visualization.
	 */
	public String generateDot() 
	{
		StringBuilder dot = new StringBuilder("digraph d {\n");
		
		// for every vertex 
		for(Vertex v: vertices.values()) 
		{
			// for every edge
			Iterator<Edge> edges = v.edges();
			while(edges.hasNext()) 
			{
				dot.append("\t\"" + v.getName() + "\" -> \"" + edges.next() + "\"\n");
			}
			
		}
		
		return dot.toString() + "}";
	}
	
	/**
	 * Generates a simple textual representation of this graph.
	 */
	public String toString() 
	{
		StringBuilder result = new StringBuilder();
		
		for(Vertex v: vertices.values()) 
		{
			result.append(v + "\n");
		}
		
		return result.toString();
	}
	
	/**
	 * Performs a depth first search to find a path from the given vertex to all other vertices.
	 * @param src the source vertex to start from.
	 * @return a list of lists that each represent a path from the source vertex to another vertex.
	 */
	public List<List<T>> depthFirstSearch(T src)
	{
		List<List<T>> allPaths = new ArrayList<List<T>>();
		List<T>	 currentPath = new ArrayList<T>();
		
		currentPath.add(src);
		vertices.get(src).hasBeenVisited = true;
		
		depthFirstRecursive(allPaths, currentPath, src);
		
		for(T vertex: vertices.keySet())
		{
			vertices.get(vertex).hasBeenVisited = false;
		}
		
		return allPaths;
	}
	
	/**
	 * Private recursive depth first search aogorithm.
	 * @param allPaths a list of lists that each represent a path from the source vertex to another vertex.
	 * @param currentPath a list that represents the current path from the source vertex.
	 * @param visitedVertices a list that contains all vertices that have already been visited.
	 * @param currentVertex
	 */
	private void depthFirstRecursive(List<List<T>> allPaths, List<T> currentPath, T currentVertex)
	{	
		Iterator<Edge> iterator = vertices.get(currentVertex).edges();
		
		while(iterator.hasNext())
		{
			T vertex = iterator.next().getOtherVertex().getName();
			
			if(vertices.get(vertex).hasBeenVisited)
			{
				continue;
			}
			if(vertices.get(currentVertex).isIce != vertices.get(vertex).isIce)
			{
				continue;
			}
			
			currentPath.add(vertex);
			if(vertices.get(vertex).column == 100)
			{
				allPaths.add(List.copyOf(currentPath));
			}
			
			vertices.get(vertex).hasBeenVisited = true;
			depthFirstRecursive(allPaths, currentPath, vertex);
			currentPath.remove(vertex);
		}		
	}
	
	/**
	 * This class represents a vertex (AKA node) in a directed graph.
	 * The vertex is generic.
	 *  
	 * @author Erin Parker & Edward Stanford & Sara Fong
	 * @version March 14, 2022
	 */
	private class Vertex
	{
		// used to id the Vertex
		private T name;
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
		 * @return the object T used to identify this Vertex
		 */
		public T getName() 
		{
			return name;
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

		/**
		 * Generates and returns a textual representation of this Vertex.
		 */
		public String toString() 
		{
			String s = "Vertex " + name + " adjacent to vertices ";
			Iterator<Edge> itr = adj.iterator();
			while(itr.hasNext())
			{
				s += itr.next() + "  ";
			}
			return s;
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

		/**
		 * Returns the name of the destination Vertex as a textual representation of this Edge.
		 */
		public String toString() 
		{
			return this.dst.getName().toString();
		}
	}
}