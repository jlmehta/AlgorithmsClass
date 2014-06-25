package classProblems;

import java.io.*;
import java.util.*;

/**
 * Problem: The file contains the adjacency list representation of a simple undirected graph. There are 200 vertices 
 * labeled 1 to 200. The first column in the file represents the vertex label, and the particular row (other entries 
 * except the first column) tells all the vertices that the vertex is adjacent to. So for example, the 6th row looks 
 * like : "6 155 56 52 120 ......". This just means that the vertex with label 6 is adjacent to (i.e., shares an edge 
 * with) the vertices with labels 155,56,52,120,......,etc
 * 
 * Your task is to code up and run the randomized contraction algorithm for the min cut problem and use it on the above 
 * graph to compute the min cut. (HINT: Note that you'll have to figure out an implementation of edge contractions. 
 * Initially, you might want to do this naively, creating a new graph from the old every time there's an edge contraction. 
 * But you should also think about more efficient implementations.) (WARNING: As per the video lectures, please make sure 
 * to run the algorithm many times with different random seeds, and remember the smallest cut that you ever find.) 
 * 
 * @author JLMehta
 */
		
public class GraphMinCut {

	/**
	 * @param To find the Minimum cut of the graph represented by the input file kargerMinCut.txt 
	 */
	static String inputFile = new File("").getAbsolutePath() + "/bin/inputFiles/kargerMinCut.txt"; 
	public static void main(String[] args) {
		int i=0,min=Integer.MAX_VALUE;
		
		while(i<100)	// Run the Algo 100 times just to make sure min cut 
		{
			Graph g = buildGraph(inputFile);
			int m = findMinCut(g);
			min = (m<min) ? m : min;
			System.out.println("__"+i+":"+min+"__");
			i++;
		}
		System.out.println("Min Cut:"+min);

	}

	private static int findMinCut(Graph g) {
		Random rand = new Random();
		
		while (g.nodeCount>2)
		{
			Edge e = null;
			while(true)
			{
				int c =rand.nextInt(g.edges.size());
				e = g.edges.get(c);
				if(e.vertex1>e.vertex2)
					break;
			}
			
			g.removeAndReplaceEdgeVertices(e);
			int sup = e.vertex1;
			/*while(!g.adj.containsKey(sup))
				sup = rand.nextInt(g.maxNodeCount+1);*/
			
			int sub = e.vertex2;//rand.nextInt(g.maxNodeCount+1);
			/*while(true)		//Make sure sup and sub are different and have a common edge
			{
				sub = rand.nextInt(g.maxNodeCount+1);
				if(sub!=sup && g.adj.containsKey(sub) && g.haveCommonEdge(sup, sub))
					break;
			}*/
			
			
			
			contract(g, sup, sub);
		}
		return g.adj.get(g.adj.keySet().iterator().next()).size();
		
	}

	private static void contract(Graph g, int sup, int sub) 
	{
		// Remove the edge i.e. remove sub's entry from sup  
		while(g.adj.get(sup).contains(new Integer(sub)))
			g.adj.get(sup).remove(new Integer(sub));
		
		for(int i = 0; i < g.adj.get(sub).size() ; i++)
		{
			Integer vert =  g.adj.get(sub).get(i);
			if(vert!=sup)							//Avoid creating self loops
			{
				g.addNeighbor(sup, vert);			// Add sub's entries to sup
				g.replaceVertex(vert, sub, sup);	// Replace sub wit sup in the rest of the graph
			}
		}
		
		// Remove sub from the graph
		g.removeVertex(sub);
		
		//System.out.println("sup:"+sup+" sub:"+sub);
	}

	private static Graph buildGraph(String inputFile) {
		//create graph
        Graph g = new Graph();
		try {
	        BufferedReader br = new BufferedReader(new FileReader(inputFile));
	        
	        String line = br.readLine();
	        
	        while (line != null) 
	        {
	            String[] nodeNames = line.split("\t");
	            //read node
	            int vertex = Integer.parseInt(nodeNames[0]);
	            g.addVertex(vertex);
	            //read edges
	            for(int i=1 ; i < nodeNames.length ; i++) 
	            {
	            	int n = Integer.parseInt(nodeNames[i]);
	                g.addNeighbor(vertex, n);
	                //if(vertex>n)
	                	g.edges.add(new Edge(vertex,n));
	            }
	            line = br.readLine();
	        }
	        br.close();
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
		
		return g;
	}

}

class Graph
{	
	//Map of adjacency lists for each node
    Map<Integer, List<Integer>> adj;
    List<Edge> edges;
    int maxNodeCount = 0;
    int nodeCount = 0;
    
    public Graph() {
        adj = new HashMap<Integer, List<Integer>>();
        edges = new ArrayList<Edge>();
    }

	public void removeAndReplaceEdgeVertices(Edge e) 
	{
		edges.remove(e);
		int j = 10000;
		Iterator i = edges.iterator();
		while(i.hasNext())
		{
			Edge ed=(Edge)i.next();
			if((ed.vertex1==e.vertex2 && ed.vertex2==e.vertex1) || (ed.vertex1==e.vertex1 && ed.vertex2==e.vertex2) )
			{
				if(j>edges.size())
				{
					j = edges.indexOf(ed);
					continue;
				}
			}
			if(ed.vertex1==e.vertex2)
				ed.vertex1 = e.vertex1;
			if(ed.vertex2==e.vertex2)
				ed.vertex2 = e.vertex1;
		}
		if (j<edges.size()) edges.remove(j);
		//if (k<edges.size()) edges.remove(k-1);
	}

	public int findLeast() {
		Iterator i = adj.keySet().iterator();
		int min = Integer.MAX_VALUE;
		while(i.hasNext())
		{
			int m = adj.get(i.next()).size();
			min = min < m ? min : m ;
		}
		return min;
	}

	public void replaceVertex(Integer vert, int sub, int sup) 
	{
		List<Integer> l = adj.get(vert);
		l.set(adj.get(vert).indexOf(sub), sup) ;
	}

	public void addVertex(int v)
    {
        adj.put(v, new LinkedList<Integer>());
        maxNodeCount++;
        nodeCount++;
    }
	
	public void removeVertex(int v)
	{
		adj.remove(v);
		nodeCount--;
	}

    public void addNeighbor(int v1, int v2) {
        adj.get(v1).add(v2);
    }

    public List<Integer> getNeighbors(int v) {
        return adj.get(v);
    }
    
    public boolean haveCommonEdge(int sup, int sub)
    {
    	return adj.get(sup).contains(sub);
    }
}

class Edge 
{
	int vertex1;
	int vertex2;
	public Edge(int v1, int v2)
	{
		vertex1 = v1;
		vertex2 = v2;
	}

}
