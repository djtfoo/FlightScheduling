import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;

public class DepthFirstSearch implements Pathfinder {

    /*
     * Graph to perform DFS on
     */
	private Graph graph;

    /*
     * Store results of DFS
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
	
	public DepthFirstSearch(Graph g) {
		graph = g;
        path = new ArrayList<String>();
		tree = new HashMap<String, String>();
	}

	/*
	 * Find all nodes connected to a source node
	 */
    public int findNumConnectedNodes(String source) {
        //System.out.println("Finding all nodes connected to " + source);

        // Create temporary data structures
        ArrayList<String> marked = new ArrayList<String>();
		
        // run search
        dfs(source, marked);
        // marked nodes are nodes that source can reach
        return marked.size();
    }

	private void dfs(String curr, ArrayList<String> marked) {

		// mark curr as visited
		marked.add(curr);
		//System.out.println(curr);

        // list of curr's neighbors
        AdjacencyList adjList = graph.getAdjacencyList(curr);
        adjList.setStart();
        for (String neighbour = adjList.iterateNext(); neighbour != null; neighbour = adjList.iterateNext()) {
			if (!marked.contains(neighbour)) {  // if unmarked
                dfs(neighbour, marked); // mark as visited & push to stack (implicitly)
				tree.put(neighbour, curr);
				
				// System.out.println(tree);
            }
        }
	}

	/*
	 * Find a path between the source and destination node
	 */
    public double findPath(String source, String dest) {
		ArrayList<String> marked = new ArrayList<String>();
		System.out.println(marked);
		
		// start counting runtime
		long startTime = System.nanoTime();
		long endTime;

		dfs(source, dest, marked);
		
		// stop counting runtime after search completes
		endTime = System.nanoTime();
		// calculate runtime
		runtime_ns = endTime - startTime;

		// get path
		System.out.println(runtime_ns);
		generatePath(dest);
		printPath();


		// print runtime
		System.out.println("Runtime (ns): " + runtime_ns);
		runtime_ms = runtime_ns / Math.pow(10, 6);
		System.out.println("Runtime (ms): " + runtime_ms);
		return runtime_ms;
    }

    private void dfs(String curr, String target, ArrayList<String> marked) {
		if (curr.equals(target)) {
			return;
		}

        // mark curr as visited
		marked.add(curr);
		//System.out.println(curr);

        // list of curr's neighbors
        AdjacencyList adjList = graph.getAdjacencyList(curr);
        adjList.setStart();
        for (String neighbour = adjList.iterateNext(); neighbour != null; neighbour = adjList.iterateNext()) {
            if (!marked.contains(neighbour)) {  // if unmarked
                dfs(neighbour, target, marked); // mark as visited & push to stack (implicitly)
				tree.put(neighbour, curr);
				// System.out.println(tree);
            }
        }
    }
	
	// generates path using edges in the generated tree
    // called only if path is found
	private void generatePath(String dest) {
		System.out.println(tree);
        for (String v = dest; v != null; v = tree.get(v)) {
			path.add(v);
    	}
		Collections.reverse(path);
	}
	
    // called only when path is found
	private void printPath() {
		int n = path.size();
		System.out.print("DFS Path: " + path.get(0));
		for (int i = 1; i < n; i++) {
			System.out.print(" -> " + path.get(i));
		}
		System.out.println();
	}
}
