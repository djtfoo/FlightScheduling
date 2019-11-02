import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearch implements Pathfinder {

    /*
     * Graph to perform BFS on
     */
	private Graph graph;

    /*
     * Store results of BFS
     */
	private ArrayList<String> path;
    public ArrayList<String> getPath() {
        return path;
    }
	private HashMap<String, String> tree;   // key = node, value = previous node that led to key
    public HashMap<String, String> getTree() {
        return tree;
    }

	private long runtime_ns;	// time taken to run BFS (nanoseconds)
	public long getRuntimeNs() {
		return runtime_ns;
	}
	private double runtime_ms;	// time taken to run BFS (milliseconds)
	public double getRuntimeMs() {
		return runtime_ms;
	}

	public BreadthFirstSearch(Graph g) {
		graph = g;
        path = new ArrayList<String>();
		tree = new HashMap<String, String>();
	}
	
    public double findPath(String source, String dest) {
//    	System.out.println("Finding a path from " + source + " to " + dest);
        
        // Create temporary data structures
	    Queue<String> queue = new LinkedList<>();
	    ArrayList<String> marked = new ArrayList<String>();
        // Clear data structures holding results
        path.clear();
        tree.clear();
    
		// start counting runtime
		long startTime = System.nanoTime();
		long endTime;

    	String curr = source;
    	queue.add(curr);
    	marked.add(curr);
    	
    	while (!queue.isEmpty()) {
    		curr = queue.poll();	// dequeue from queue
    		if (curr.equals(dest)) {
    			//System.out.println("Destination found.");
    			break;
    		}
    		else {
    			processNeighbours(curr, queue, marked);
    		}
    	}
		// stop counting runtime after search completes
		endTime = System.nanoTime();
		// calculate runtime
		runtime_ns = endTime - startTime;
    	
    	if (!curr.equals(dest)) {
    		System.out.println("No path found from " + source + " to " + dest + ".");
    	}
        else {
    	    generatePath(dest);
//    	    printPath();
        }

		// print runtime
//		System.out.println("Runtime (ns): " + runtime_ns);
		runtime_ms = runtime_ns / Math.pow(10, 6);
//		System.out.println("Runtime (ms): " + runtime_ms);
		return runtime_ms;
    }
    
	private void processNeighbours(String curr, Queue<String> queue, ArrayList<String> marked) {
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
    // called only if path is found
	private void generatePath(String dest) {
        for (String v = dest; v != null; v = tree.get(v)) {
    		path.add(v);
    	}
    	Collections.reverse(path);
	}
	
    // called only when path is found
	private void printPath() {
		int n = path.size();
		System.out.print("Shortest path: " + path.get(0));
		for (int i = 1; i < n; i++) {
			System.out.print(" -> " + path.get(i));
		}
		System.out.println();
	}
}
