import java.util.HashMap;

public class Graph {

    private HashMap<String, AdjacencyList> adjacencyLists;

    public Graph() {
        
        adjacencyLists = new HashMap<String, AdjacencyList>();
    }

    public void printAdjacencyList(String node) {
        AdjacencyList list = adjacencyLists.get(node);
        if (list == null) {
            System.out.println(node + " is not in graph");
        }
        else {
            list.setStart();
            String item = list.iterateNext();
            
            System.out.print(node + ": ");
            while (item != null) {
                System.out.print(item);
                item = list.iterateNext();
                if (item != null)
                    System.out.print(", ");
            }
            System.out.println();
        }
    }

    public boolean addEdge(String source, String target) {
        addVertex(source);
        if (!addEdgeToList(source, target))
            return false;
        addVertex(target);
        addEdgeToList(target, source);
        return true;
    }

    private boolean addEdgeToList(String source, String target) {
        if (adjacencyLists.get(source).contains(target)) {
            System.out.println(source + " to " + target + " edge already exists");
            return false;
        }
        adjacencyLists.get(source).add(target);
            System.out.println(source + " to " + target + " edge added!");
        return true;
    }

    private boolean addVertex(String source) {
        if (adjacencyLists.containsKey(source)) {
            System.out.println(source + " vertex already exists");
            return false;
        }
        
        adjacencyLists.put(source, new AdjacencyList());
        System.out.println(source + " vertex added!");
        return true;
    }
}