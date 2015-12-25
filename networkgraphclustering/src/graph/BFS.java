package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;



// Class for carrying out a breadth first search on the network topology stored in
// the NetworkGraph .

public class BFS {
	// Maps the destination node's ID to the number of hops away it is from the starting node.
	private Map<Integer, Integer> dist  ;
	private int sourceNode ;
	
	public BFS(NetworkGraph G, int s){
		dist = new HashMap<Integer, Integer>() ;
		sourceNode = s ;
		
		bfs(G,s) ;
	}

	// Add an entry for the destination Vertex and the number of hops it is away
	// from the source Vertex
	private void putDistance(int node, int distance) {
		dist.put(node, distance);
		//System.out.println("Node " + node + " Dist " + distance) ;
	}

	// Get the hop count or the number of edges from source Vertex to the
	// destination Vertex
	public int getDistance(int node) {
		if (dist.containsKey(node))
			return dist.get(node);
		else
			return Integer.MAX_VALUE;
	}

	// Returns true if the entry for the node exists in our map else false
	private boolean isVisited(int node) {
		return dist.containsKey(node);
	}

	// the actual implementation of the Breadth first search algorithm over the
	// network graph G
	private void bfs(NetworkGraph G, int s) {
		// will be using add and poll operations of LinkedList for FIFO
		// operations on a queue
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s);
		putDistance(s, 0); // all vertices have 0 distance from themselves

		while (!q.isEmpty()) {
			int v = q.poll(); // remove a vertex from the queue
			int len = getDistance(v);
			for (int w : G.getAdj(v)) {
				if (!isVisited(w)) {
					q.add(w);
					// as this vertex is just one edge away from the vertex v,
					// we just increment
					// distance of v by one and store it against vertex w
					putDistance(w, len + 1);
				}
			}
		}
	}
	
	public Set<Map.Entry<Integer, Integer>> getDistanceMapping(){
		return dist.entrySet() ;
	}
	
	public void printResults(){
		System.out.println("Distance from Node : " + sourceNode) ;
		for( Map.Entry<Integer, Integer>  entry : dist.entrySet() ){
			System.out.println("Vertex: " + entry.getKey() + ", Dist: " + entry.getValue() ) ;
		}
	}
}
