import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearch implements Pathfinder {
	private Graph graph;
	private Queue<String> queue;
	private ArrayList<String> marked;
	private ArrayList<String> directions;
	private HashMap<String, String> previous;
	
	public BreadthFirstSearch(Graph g) {
		graph = g;
		queue = new LinkedList<>();
		marked = new ArrayList<String>();
		directions = new ArrayList<String>();
		previous = new HashMap<String, String>();
	}
	
    public ArrayList<String> findPath(String source, String dest) {
    	System.out.println("Finding a path from " + source + " to " + dest + "~");
    	
    	String curr = source;
    	queue.add(curr);
    	marked.add(curr);
    	
    	while (!queue.isEmpty()) {
    		curr = queue.poll();	// dequeue
    		if (curr.equals(dest)) {
    			System.out.println("Destination found.");
    			break;
    		}
    		else {
    			// list of curr's neighbors
    			AdjacencyList adjList = graph.getAdjacencyList(curr);
    			adjList.setStart();
    			String neighbour = adjList.iterateNext();
    			
    			// iterate through neighbours
    			while (neighbour != null) {
    				// if unmarked, enqueue and mark it
    				if (!marked.contains(neighbour)) {
    					queue.add(neighbour);
    					marked.add(neighbour);
    					previous.put(neighbour, curr);
    				}
    				neighbour = adjList.iterateNext();
    			}
    		}
    	}
    	System.out.println(previous);
    	
    	if (!curr.equals(dest)) {
    		System.out.println("No path found from " + source + " to " + dest);
    	}
    	
    	for (String v = dest; v != null; v = previous.get(v)) {
    		directions.add(v);
    	}
    	Collections.reverse(directions);
    	
    	System.out.println(directions);
    	return directions;
    }
}
