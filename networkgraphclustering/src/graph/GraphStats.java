package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GraphStats {
	NetworkGraph G ;
	
	Map<Integer,Integer> minDistance = new HashMap<Integer,Integer>() ;
	Map<Integer, Integer> threePairsDistance =  new HashMap<Integer,Integer>() ;
	
	public GraphStats(NetworkGraph G){
		this.G = G ;
	}
	
	public void calculate(){
		// do 1 million times
		Set<Integer> v = G.getVertices() ;
		Object[] vertices = v.toArray() ;
		
		Random rng = new Random() ;
		int len = vertices.length ;
		
		for(int i = 0 ; i < 1000000 ; ++i){
			// choose a random node
			
			Integer source = (Integer) vertices[rng.nextInt(len)] ;
			int distance = Integer.MAX_VALUE ;
			// take 3 random nodes			
			//System.out.println( i + ": Choosen random source node " + source);
			BFS bfs = new BFS(G,source) ;
			
			for(int j = 0 ; j < 3 ; ++j){
				Integer index = rng.nextInt(vertices.length) ;
				Integer node = (Integer) vertices[index] ;
				int d = bfs.getDistance(node) ;
				if( d < distance ){
					distance = d ;
				}							
				
				if(threePairsDistance.get(d) != null)
					threePairsDistance.put(d, threePairsDistance.get(d) + 1) ;
				else
					threePairsDistance.put(d, 1) ;
			}
			
			if(minDistance.get(distance) != null)
				minDistance.put(distance, minDistance.get(distance) + 1) ;
			else
				minDistance.put(distance, 1) ;
		}
	}
		
	public void showStats(){
		System.out.println("******************Min of 3 Random Destinations Distance******************");
		for(Map.Entry<Integer,Integer> a : minDistance.entrySet() ){
			System.out.println(a.getKey() + "\t" + a.getValue()) ;			
		}
		
		System.out.println("******************3 Random Pairs Distance******************");
		for(Map.Entry<Integer,Integer> a : threePairsDistance.entrySet() ){
			System.out.println(a.getKey() + "\t" + a.getValue()) ;			
		}
	}
	
	public static void main(String[] args){
		// create the network graph
		NetworkGraph G = new NetworkGraph() ; 
		
		// Read edges from input file
		//try(BufferedReader in = Files.newBufferedReader(Paths.get("testData/itdk0304_rlinks_undirected.gz"), StandardCharsets.UTF_8)){
		try { BufferedReader in = Files.newBufferedReader(Paths.get("testData/skitter_AS_connectivity.txt"), StandardCharsets.UTF_8) ;
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
				
		GraphStats gs = new GraphStats(G) ;
		gs.calculate();
		gs.showStats();
	}
}
