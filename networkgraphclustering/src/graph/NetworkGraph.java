package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NetworkGraph {

	private Map<Integer, HashSet<Integer>> adjacencyMap  ; // As the AS id is different from index in the internal Adjacency List
	
	
	public NetworkGraph(){
		adjacencyMap = new HashMap<Integer, HashSet<Integer>>() ;		
	}
	
	// After reading the edge information from the input file. The pair will be sent to this function
	// this function should register an entry in the adjacencyMap with ḱey = source and destination 
	// being stored in the corresponding hash set in the map
	private void addDirectedEdge(Integer source, Integer destination){
		HashSet<Integer> adj = adjacencyMap.get(source) ;
		// If an entry does not exist then create one		
		if(adj == null){
			adjacencyMap.put(source, adj = new HashSet<Integer>()) ;
		}
		// add the destination to the adjacency list of the source node in the map
		adj.add(destination) ;
	}
	
	public void addEdge(Integer a, Integer b){
		addDirectedEdge(a,b) ;
		addDirectedEdge(b,a) ;
	}
	
	// get the adjacency list of the requested node
	public Iterable<Integer> getAdj(Integer node){
		// returns the set of adjacent nodes 
		return adjacencyMap.get(node) ;
	}
	
	public Set<Integer> getVertices(){
		// returns an iterable set of networkgraph vertices which are stored as the keys in the adjacencyMap
		return adjacencyMap.keySet() ;
	}
	
	public int size(){
		return adjacencyMap.size() ;
	}
	
	public static void main(String[] args){
		// create the network graph
		NetworkGraph G = new NetworkGraph() ; 
		
		// Read edges from input file
		try{
			BufferedReader in = Files.newBufferedReader(Paths.get("testData/skitter_AS_connectivity.txt"), StandardCharsets.UTF_8) ;
			String line ; 
			while( (line = in.readLine()) != null){
			 
				String[] numbers = line.split(" ") ;
				Integer source = Integer.parseInt(numbers[0]) ;
				
				assert(numbers.length > 1) ;
				for(int a = 1 ; a < numbers.length ; ++a){
					G.addEdge(source, Integer.parseInt(numbers[a]));
				}				
			}
			
			// test to check whether the graph got initialised
			Iterable<Integer> vertices = G.getVertices() ;
			ArrayList<Degree> resultList = new ArrayList<Degree>() ;
			
			for(int i : vertices)
			{	
				Iterable<Integer> list = G.getAdj(i) ;
			
				if(list != null){
					int count=0 ;
					//System.out.println("Node " + i + " Connected to Nodes " + list);
					for(int node : list){
						count++ ;
					}
					
					if(count > 500){
						System.out.println("Node: " + i + ", Degree: " + count);
					}
					
				}				
			}
			System.out.println("Graph has " + G.size() + " vertices");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot oopen testGraph.txt");
			e.printStackTrace();
		}
		
		
	}
	
	class Degree{
		public final int nodeId ;
		public final int degree ;
		
		public Degree(int n, int d){
			nodeId = n ;
			degree = d ;
		}
	}
}
