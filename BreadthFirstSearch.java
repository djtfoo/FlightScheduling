import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearch implements Pathfinder {
	private Graph graph;
	private Queue<String> queue;
	private ArrayList<String> marked;
	private ArrayList<String> path;
	private HashMap<String, String> tree;
	
	public BreadthFirstSearch(Graph g) {
		graph = g;
		queue = new LinkedList<>();
		marked = new ArrayList<String>();
		path = new ArrayList<String>();
		tree = new HashMap<String, String>();
	}
	
    public ArrayList<String> findPath(String source, String dest) {
    	System.out.println("Finding a path from " + source + " to " + dest + "~");
    	
    	String curr = source;
    	queue.add(curr);
    	marked.add(curr);
    	
    	while (!queue.isEmpty()) {
    		curr = queue.poll();	// dequeue from queue
    		if (curr.equals(dest)) {
    			System.out.println("Destination found.");
    			break;
    		}
    		else {
    			processNeighbours(curr);
    		}
    	}
    	
    	if (!curr.equals(dest)) {
    		System.out.println("No path found from " + source + " to " + dest + ".");
    		return null;
    	}
    	
    	generatePath(dest);
    	printPath();
    	return path;
    }
    
	private void processNeighbours(String curr) {
		// list of curr's neighbors
		AdjacencyList adjList = graph.getAdjacencyList(curr);
		adjList.setStart();
		
		// iterate through neighbours
		for (String neighbour = adjList.iterateNext(); neighbour != null; neighbour = adjList.iterateNext()) {
			// if unmarked, enqueue & mark it, then add the edge to tree
			if (!marked.contains(neighbour)) {
				queue.add(neighbour);
				marked.add(neighbour);
				tree.put(neighbour, curr);
			}
		}
	}
	
	// generates path using edges in the generated tree
	private void generatePath(String dest) {
    	for (String v = dest; v != null; v = tree.get(v)) {
    		path.add(v);
    	}
    	Collections.reverse(path);
	}
	
	private void printPath() {
		int n = path.size();
		
		System.out.print("Shortest path: " + path.get(0));
		for (int i = 1; i < n; i++) {
			System.out.print(" -> " + path.get(i));
		}
		System.out.println();
	}
}
