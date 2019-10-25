import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        
        ArrayList<String> lines = TxtFileHandler.readTxt(args[0], '#');

        Graph graph = new Graph();
        for (int i = 1; i < lines.size(); i++) {    // ignore first line, column headers
            
            String[] data = lines.get(i).split(",");
            System.out.println("Source: " + data[0] + ", Dest: " + data[1]);
            graph.addEdge(data[0], data[1]);
        }

        // print an adjacency list as an example
        System.out.println("\n\nPrint adjacency list");
        graph.printAdjacencyList("KZN");
        
        System.out.println("\n===== BFS test uwu =====");
        BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
        bfs.findPath("ASF", "AER");
    }
}