package cluster;

import graph.BFS;
import graph.NetworkGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ClusterManager {
	
	private int k ; // number of clusters to be formed
	private NetworkGraph G ;
	Map< Integer, Set<Integer>> clusters ; // K bags containing the nodes belonging together
	
	public ClusterManager(NetworkGraph G, int k){
		this.k = k ; 
		this.G = G ;
		
		clusters = new HashMap<Integer, Set<Integer>>() ;
		
		clusterize() ;
	}
	
	// This logic is not very good, reimplement. !! 
	private int chooseRandomVertex(){
		Iterable<Integer> nodeList = G.getVertices() ;
		Random r = new Random() ;
		int n = r.nextInt(G.size()) ;
		int node = 0 ;
		int count = 0 ;
		for( int i : nodeList){
			node = i ;
			if(++count == n)
				break ;
		}
		
		return node ;
	}
	
	private void addNodeToCluster(int node , int clusterId){
		Set<Integer> set = clusters.get(clusterId) ;
		if(set == null){
			clusters.put(clusterId, set = new HashSet<Integer>()) ;
		}
		set.add(node) ;
	}
	
	private int getClusterSize(int clusterId) {
		Set<Integer> set = clusters.get(clusterId) ;
		if(set == null){
			return 0 ;
		}else{
			return set.size() ;
		}
	}
	
	/**
	 * Although this method is supposed to generate K clusters based on input parameters.
	 * For now, I have manually added 3 nodes which have maximum degrees in our test network graph
	 * And it has resulted in 3 clusters containing 3068 nodes each
	 * - Maneesh 11/11/2014
	 * 
	 * The list of 10 highest degree nodes in the graph
	 *  Node: 668,  Degree: 2070
	    Node: 701,  Degree: 1417
		Node: 1239, Degree: 1289
		Node: 3356, Degree: 1177
		Node: 7018, Degree: 926
		Node: 3549, Degree: 762
		Node: 6461, Degree: 723
		Node: 209,  Degree: 665
		Node: 2914, Degree: 659
		Node: 3561, Degree: 545
		
	 */
	private void clusterize(){
		//int server = 3356 ; // need to keep this vertex outside any cluster
		int [] arr = {668,701} ; // These nodes have maximum connectoivity in our graph
		
		BFS [] shortestPaths = new BFS[arr.length] ;
		
		// get shortest paths from source node to all other nodes using BFS
		for(int node = 0 ; node < arr.length ; node++ )
			shortestPaths[node] = new BFS(G,arr[node]) ;
		
				
		Iterable<Integer> nodeList = G.getVertices() ;
		for(int i : nodeList){
			int len = 100, index = 0 ;
			for(int j = 0 ; j < arr.length ; ++j){
				int temp = shortestPaths[j].getDistance(i) ;
				if (temp < len){
					len = temp ;
					index = j ;
				}else if (temp == len){
					// Distribution should be based on the size of the cluster. Smaller one should get this node	
					// index has the previous clusterID, j has the current one, we have to
					// choose in which to put the i node.
					
					int a = getClusterSize(arr[index]) ;
					int b = getClusterSize(arr[j]) ;
					
					if(a > b){
						index = j ;
						// so that when the control leaves the loop, i is added to the correct cluster
					}
				}
			}
			addNodeToCluster(i , arr[index]) ;
		}
	}
	
	public void printResults(){
		for(Map.Entry<Integer, Set<Integer>> a : clusters.entrySet() ){
			Set<Integer> s = a.getValue() ;
			System.out.println("Center Node " + a.getKey() + " Connected to "+ s.size() + " Nodes : [" + s + "]" );
			
		}
		
	}
	
	public static void main(String[] args){
		// create the network graph
		NetworkGraph G = new NetworkGraph() ; 
		
		// Read edges from input file
		try(BufferedReader in = Files.newBufferedReader(Paths.get("testData/skitter_AS_connectivity.txt"), StandardCharsets.UTF_8)){
			String line ; 
			while( (line = in.readLine()) != null){
			 
				String[] numbers = line.split(" ") ;
				Integer source = Integer.parseInt(numbers[0]) ;
				
				assert(numbers.length > 1) ;
				for(int a = 1 ; a < numbers.length ; ++a){
					G.addEdge(source, Integer.parseInt(numbers[a]));
				}				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot oopen testGraph.txt");
			e.printStackTrace();
		}
				
		ClusterManager cm = new ClusterManager(G, 3) ;
		cm.printResults();
	}
}
