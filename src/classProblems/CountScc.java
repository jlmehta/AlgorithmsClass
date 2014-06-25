package classProblems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Problem: The file contains the edges of a directed graph. Vertices are labeled as positive integers from 1 to 875714. 
 * Every row indicates an edge, the vertex label in first column is the tail and the vertex label in second column is the 
 * head (recall the graph is directed, and the edges are directed from the first column vertex to the second column vertex). 
 * So for example, the 11th row looks liks : "2 47646". This just means that the vertex with label 2 has an outgoing edge to 
 * the vertex with label 47646
 * 
 * Your task is to code up the algorithm from the video lectures for computing strongly connected components (SCCs), and to 
 * run this algorithm on the given graph.
 * 
 * Output Format: You should output the sizes of the 5 largest SCCs in the given graph, in decreasing order of sizes, 
 * separated by commas (avoid any spaces). So if your algorithm computes the sizes of the five largest SCCs to be 
 * 500, 400, 300, 200 and 100, then your answer should be "500,400,300,200,100". 
 * 
 * @author JLMehta
 */
public class CountScc {

	/**
	 * @param To count the Strongly Connected Components of the graph represented by the input file G:\AlgoClass\PrgAssn4\SCC.txt
	 */
	static String inputFile =  new File("").getAbsolutePath() + "/bin/inputFiles/SCC.txt"; //test1(4,3,3,1,0)  test3(6,3,2,1,0)  test4(8,5,2,1,0)
	static int t=0; 						//Tracks number of nodes processed so far, For Finishing times in the 1st Pass
	static Vertex s=null; 					//Current source vertex, for leaders in 2nd pass
	
	public static void main(String[] args) 
	{
		
		Date startTime = new Date();
		
		DirectedGraph graph = new DirectedGraph();
		DirectedGraph revGraph = new DirectedGraph();
		
		buildGraph(inputFile, graph, revGraph);
		
		Date endTime = new Date();
		
		System.out.println("File Load Time :" + ((float)(endTime.getTime() -startTime.getTime()))/1000 + "s");
//		System.out.println("Original Graph \n------------\n"+graph+"\n------------");
//		System.out.println("Reversed Graph \n------------\n"+revGraph+"\n------------");
		
		KosaRaju(graph, revGraph);
//		System.out.println("Reversed Graph \n------------\n"+revGraph+"\n------------");

		printAnswer(graph);
		
	}
	private static void printAnswer(DirectedGraph graph) 
	{
		 Collection<Vertex> val = graph.vertices.values();
		 int[] finalAns = new int[val.size()]; 
		 Iterator<Vertex> i = val.iterator();
		 int j=0, Scc=0;
		 while(i.hasNext())
		 {
			 finalAns[j]=((Vertex)(i.next())).time;
			 if(finalAns[j++]>0) Scc++;
		 }
		 Arrays.sort(finalAns);
		 
		 for(int x=0; x<10; x++)
		 {
			 System.out.println(finalAns[finalAns.length-1-x]);
		 }
		 System.out.println("Total Vertices: "+graph.size());
		 System.out.println("Scc's: "+Scc);
	}

	private static void KosaRaju(DirectedGraph graph, DirectedGraph revGraph) 
	{
		Date d1=new Date();
		t = 0;
		for(int i= revGraph.getNoOfVertices(); i>0; i-- )
		{
			Vertex v = revGraph.getOrAddVertexFromInt(i);
			if(!v.explored)
			{
				DFS(revGraph,v, graph);
			}
		}
		Date d2=new Date();
		System.out.println("First Pass with Stack:" + ((float)(d2.getTime() -d1.getTime()))/1000 + "s");
		for(int i=graph.getNoOfVertices(); i>0; i--)
		{
			Vertex v = revGraph.getVertexFromTime(i);
			v = graph.getOrAddVertexFromInt(v.node);
			if(!v.explored)
			{
				s=v;
				DFS2(graph,v);
			}
		}
		Date d3=new Date();
		System.out.println("Second Pass with Stack:" + ((float)(d3.getTime() -d2.getTime()))/1000 + "s");
	}
	
	private static void DFS2(DirectedGraph graph, Vertex v) 
	{
		Stack<Vertex> stack = new Stack<Vertex>();
		stack.push(v);
		v.explored=true;
		v.leader = s;
		while(!stack.isEmpty())
		{
			Vertex u = stack.pop();
			u.explored = true;
			u.leader = s;
			s.time++;
			List<Vertex> edges = graph.getChildren(u);
			if(edges==null) 
				continue;
			for(int i =0; i<edges.size(); i++)
			{
				if(!edges.get(i).explored)
				{
					edges.get(i).explored = true;
					stack.push(edges.get(i));
				}
			}
		}
	}
	
	private static void DFS(DirectedGraph graph, Vertex v, DirectedGraph strtGraph) 
	{
		Stack<Vertex> stack = new Stack<Vertex>();
		stack.push(v);
		v.explored=true;
		while(!stack.isEmpty())
		{
			Vertex u = stack.peek();
			if(u.unExploredchildren<1) 
			{	 
				u = stack.pop();
				u.time = ++t;
				graph.putVertexByTime(t,u);
			}
			else
			{
				List<Vertex> edges = graph.getChildren(u);
				for(int i =edges.size()-u.unExploredchildren; i<edges.size(); i++)
				{
					u.unExploredchildren--;
					if(!edges.get(i).explored)
					{
						edges.get(i).explored = true;
						stack.push(edges.get(i));
						break;
					}
				}
			}
		}
	}
	
	private static DirectedGraph buildGraph(String inputFile, DirectedGraph g, DirectedGraph rg) {
		try {
	        BufferedReader br = new BufferedReader(new FileReader(inputFile));
	        String line = br.readLine();
	        while (line != null) 
	        {
	            String[] nodeNames = line.split(" ");
	            //read node
	            Vertex v1 = g.getOrAddVertexFromInt(Integer.parseInt(nodeNames[0]));
	            Vertex v2 = g.getOrAddVertexFromInt(Integer.parseInt(nodeNames[1]));
	            g.addEdge(v1, v2);
	            v1 = rg.getOrAddVertexFromInt(Integer.parseInt(nodeNames[0]));
	            v2 = rg.getOrAddVertexFromInt(Integer.parseInt(nodeNames[1]));
	            rg.addEdge(v2, v1);
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

class DirectedGraph
{
	HashMap<Vertex,List<Vertex>> g;
	HashMap<Integer,Vertex> vertices;
	HashMap<Integer,Vertex> times;
	
	public int getNoOfVertices()
	{
		return vertices.size();
	}
	
	public Vertex getOrAddVertexFromInt(Integer v) {
		if(vertices.containsKey(v))
			return vertices.get(v);
		else
		{
			Vertex vr = new Vertex(v);
			vertices.put(v, vr);
			return vr;
		}
	}

	public Vertex getVertexFromTime(int t) 
	{
		if(times.containsKey(t))
			return times.get(t);
		
		return null;
	}
	
	public void putVertexByTime(Integer t, Vertex v) 
	{
		times.put(t, v);
	}

	public List<Vertex> getChildren(Vertex v) 
	{
		if(g.containsKey(v))
			return g.get(v);
		
		return null;
	}

	public DirectedGraph()
	{
		g = new HashMap<Vertex,List<Vertex>>();
		vertices = new HashMap<Integer, Vertex>();
		times  = new HashMap<Integer, Vertex>();
	}
	
	public boolean addNodetoGraph(Vertex v)
	{	
		if(g.containsKey(v))
			return false;
		else
		g.put(v, new ArrayList<Vertex>());
		return true;		
	}
	
	public void addEdge(Vertex v1, Vertex v2)
	{
		addNodetoGraph(v1);
		addNodetoGraph(v2);
		
		if(!g.get(v1).contains(v2))
		{
			g.get(v1).add(v2);
			v1.unExploredchildren++;
		}
	}
	
	public int size()
	{
		return g.size();
	}
	
	public String toString()
	{
		String str = "";
		
		for(int i = 1; i<g.size()+1; i++)
		{
			Vertex v = vertices.get(i);
			str += v.node + "(" + v.time+"): ";
			int j=0;
			while( g.get(v) != null && j < g.get(v).size())
			{
				if(g.get(v)!=null)
				str+= g.get(v).get(j).node + " ";
				j++;
			}
			str+="\n";
		}
		
		return str;
	}
}

class Vertex
{
	int node;
	boolean explored;
	int unExploredchildren;
	int time;
	Vertex leader;
	
	public Vertex(int n)
	{
		node = n;
		explored=false;
		time = 0;
		leader = null;
	}
	
	public boolean equals(Object obj)
	{
		 if (obj == null)
			 return false;
		 if (obj == this)
			 return true;
		 if (obj.getClass() != getClass())
			 return false;
	     
		 Vertex v = (Vertex)obj;
	     return (v==null) ? false : v.node==this.node;
	}
	
	 public int hashCode() 
	 {
	        return node;
	 }	       
}