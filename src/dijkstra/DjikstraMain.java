package dijkstra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import javax.swing.text.html.MinimalHTMLWriter;

/**
 * Problem: This is an implementation of Dijkstra's shortest-path algorithm.
 * 
 * The file contains an adjacency list representation of an undirected weighted graph with 200 vertices labeled 1 to 200. 
 * Each row consists of the node tuples that are adjacent to that particular vertex along with the length of that edge. 
 * For example, the 6th row has 6 as the first entry indicating that this row corresponds to the vertex labeled 6. The 
 * next entry of this row "141,8200" indicates that there is an edge between vertex 6 and vertex 141 that has length 8200. 
 * The rest of the pairs of this row indicate the other vertices adjacent to vertex 6 and the lengths of the corresponding 
 * edges.
 * 
 * Your task is to run Dijkstra's shortest-path algorithm on this graph, using 1 (the first vertex) as the source vertex, 
 * and to compute the shortest-path distances between 1 and every other vertex of the graph. If there is no path between 
 * a vertex v and vertex 1, we'll define the shortest-path distance between 1 and v to be 1000000.
 * 
 * You should report the shortest-path distances to the following ten vertices, in order: 7,37,59,82,99,115,133,165,188,197. 
 * You should encode the distances as a comma-separated string of integers. So if you find that all ten of these vertices 
 * except 115 are at distance 1000 away from vertex 1 and 115 is 2000 distance away, then your answer should be 
 * 1000,1000,1000,1000,1000,2000,1000,1000,1000,1000. 
 * 
 * @author JLMehta
 * 
 * Comments: I got this one right in the first attempt!
 */

public class DjikstraMain {
	
	static String inputFile =  new File("").getAbsolutePath() + "/bin/inputFiles/dijkstraData.txt";
	static final int defaultPathLength = 1000000;
	
	public static void main(String[] args) {
		
		UndirectedGraph graph = buildGraph(inputFile);
		
		int[] shortestPaths = getShortestPaths(graph);
		
		System.out.println("7\t"+"37\t"+"59\t"+"82\t"+"99\t"+"115\t"+"133\t"+"165\t"+"188\t"+"197\t");
		System.out.println(shortestPaths[7]+",\t"+shortestPaths[37]+",\t"+shortestPaths[59]+",\t"+shortestPaths[82]+",\t"+shortestPaths[99]+",\t"+shortestPaths[115]+",\t"+shortestPaths[133]+",\t"+shortestPaths[165]+",\t"+shortestPaths[188]+",\t"+shortestPaths[197]);
	}
	
	private static int[] getShortestPaths(UndirectedGraph graph) {
		int[] shortestPaths = new int[201];
		
		graph.AddandGetVertex(1).setSeen(true); //Set boundary to the first node
		shortestPaths[1] = 0;		//Shortest Path of first node to itself is zero
		
		int minScore = Integer.MAX_VALUE;
		int i=0;
		while(i<200-1)
		{
			i++;
			Vertex selectedVertex = null;
			int minScore2 = Integer.MAX_VALUE;
			for(int j=0; j< graph.getTotalEdges(); j++)
			{
				Edge e = graph.getEdgeByIndex(j);
				if(e.getVertex1().isSeen() != e.getVertex2().isSeen())
				{
					Vertex seen=null,unseen=null;
					if(e.getVertex1().isSeen())
					{
						seen = e.getVertex1();
						unseen = e.getVertex2();
					}
					else
					{
						seen = e.getVertex2();
						unseen = e.getVertex1();
					}
					int thisScore = shortestPaths[seen.getValue()]+e.getLength();
					if(thisScore<minScore2)
					{
						minScore = thisScore;
						minScore2 = thisScore;
						selectedVertex = unseen;
					}
				}
			}
			selectedVertex.setSeen(true);
			shortestPaths[selectedVertex.getValue()] = minScore;
		}
		
		return shortestPaths;
	}

	private static UndirectedGraph buildGraph(String inputFile) 
	{
		UndirectedGraph g = new UndirectedGraph();
		try {
	        BufferedReader br = new BufferedReader(new FileReader(inputFile));
	        String line = br.readLine();
	        while (line != null) 
	        {
	            String[] nodeNames = line.split("\t");
	            Vertex v1 = g.AddandGetVertex(Integer.parseInt(nodeNames[0]));
	            for(int i=1; i<nodeNames.length; i++)
	            {
	            	String[] e = nodeNames[i].split(",");
	            	Vertex v2 = g.AddandGetVertex(Integer.parseInt(e[0]));
	            	int len =  Integer.parseInt(e[1]);
	            	if(v1.getValue()>v2.getValue())
	            		g.addEdge(v1, v2, len);
	            }
	            line = br.readLine();
	        }
		}
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
        return g;
	}
}

class UndirectedGraph
{
	private Vertex[] vertices;
	private ArrayList<Edge> edges;
	
	public UndirectedGraph()
	{
		vertices = new Vertex[200];
		edges = new ArrayList<Edge>();
	}
	
	public Edge getEdgeByIndex(int index) {
		return edges.get(index);
	}

	public int getTotalEdges() {
		return edges.size();
	}

	public Vertex[] getAllVertices() 
	{
		return vertices;
	}

	public void addEdge(Vertex v1, Vertex v2, int len) 
	{
		edges.add(new Edge(v1, v2, len));
	}
	
	public Vertex AddandGetVertex(int ver)
	{
		int v = ver-1;
		if(vertices[v] != null)
			return vertices[v];
		else
			return vertices[v] = new Vertex(ver); 
	}
}

class Vertex
{
	private int value;
	private boolean seen;
	
	public  Vertex(int v)
	{
		value = v;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public boolean isSeen() {
		return seen;
	}
	
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	public boolean equals(Object obj)
	{
		 if (obj == null)
			 return false;
		 if (obj == this)
			 return true;
		 if (obj.getClass() != this.getClass())
			 return false;
	     
		 Vertex v = (Vertex)obj;
	     return (v==null) ? false : v.value==this.value;
	}
	
	 public int hashCode() 
	 {
	        return value;
	 }
	 
	 public String toString()
	 {
		 return value+"";
	 }
}

class Edge
{
	private Vertex vertex1;
	private Vertex vertex2;
	private int length;
	
	public Vertex getVertex1() {
		return vertex1;
	}

	public Vertex getVertex2() {
		return vertex2;
	}

	public int getLength() {
		return length;
	}
	
	public Edge(Vertex v1, Vertex v2, int len)
	{
		vertex1 = v1;
		vertex2 = v2;
		length = len;
	}

	public boolean equals(Object obj)
	{
		 if (obj == null)
			 return false;
		 if (obj == this)
			 return true;
		 if (obj.getClass() != this.getClass())
			 return false;
	     
		 Edge e = (Edge)obj;
	     return (e==null) ? false : (e.vertex1==this.vertex1 && e.vertex2==this.vertex2 && e.length==this.length) ;
	}
	
	public String toString()
	{
		return vertex1+"-"+vertex2+"("+length+")";
	}
	
//	 public int hashCode() 
//	 {
//	        return  (13*vertex1.value) + (17*vertex2.value) + (23*length) + 31;
//	 }
}
