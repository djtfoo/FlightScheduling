import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private String graphData[];
    private Graph graph = null;
    private String options[] = {
        "Read Graph data",
        "Find Shortest Path (BFS)"
    };

    public static void main(String[] args) {
        
        App app = new App();
        app.graphData = args.clone();
        app.run();

        //Graph graph = app.readGraph("routedata/routeslargest.csv");
        // print an adjacency list as an example
        //System.out.println("\n\nPrint adjacency list");
        //graph.printAdjacencyList("KZN");
        
        // print out BFS test
        //System.out.println("\n===== BFS test uwu =====");
        //BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
        //bfs.findPath("ASF", "AER");
    }

    private void run() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        do {
            // print menu
            printMenu();
            // get user choice
            System.out.print("Enter option: ");
            choice = sc.nextInt();
            // perform user choice
            execute(choice);
        } while (choice != options.length + 1);
    }

    private void printMenu() {
        // print menu header
        System.out.println("== FLIGHT SCHEDULING PROGRAM ==");
        // print options
        int i;
        for (i = 1; i <= options.length; i++) {
            System.out.printf("(%d) %s\n", i, options[i-1]);
        }
        // print quit option
        System.out.printf("(%d) Quit\n", i);
    }

    private void execute(int choice) {
        switch (choice) {
        case 1: // Read Graph data from CSV
            loadGraph();
            break;
        case 2: // Find Shortest Path (BFS algorithm)
        {
            BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
            runPathfinding(bfs);
        }
            break;
        default:
            break;
        }
    }

    // get user selection of graph to load
    private void loadGraph() {
        // print available Graph data
        for (int i = 1; i <= graphData.length; i++) {
            System.out.printf("(%d) %s\n", i, graphData[i-1]);
        }

        // read selected graph number
        Scanner sc = new Scanner(System.in);
        System.out.print("Select graph to load: ");
        int choice = 0;
        do {
            System.out.print("Select graph to load: ");
            choice = sc.nextInt();
        } while (choice < 1 && choice > graphData.length);

        // read graph from CSV
        graph = readGraph(graphData[choice-1]);
        if (graph != null)
            System.out.println("Graph loaded successfully.");
    }

    private Graph readGraph(String filepath) {
        
        ArrayList<String> lines = TxtFileHandler.readTxt(filepath, '#');
        Graph graph = new Graph();

        // import graph from CSV data
        for (int i = 1; i < lines.size(); i++) {    // ignore first line, column headers
            
            String[] data = lines.get(i).split(",");
            //System.out.println("Source: " + data[0] + ", Dest: " + data[1]);
            graph.addEdge(data[0], data[1]);
        }
        return graph;
    }

    private void runPathfinding(Pathfinder searcher) {
        if (graph == null)
            System.out.println("Graph is empty!");
        else {
            // get source and destination cities
            Scanner scr = new Scanner(System.in);
            System.out.print("Enter start city: ");
            String source = scr.nextLine();
            System.out.print("Enter end city: ");
            String dest = scr.nextLine();

            // perform search algorithm
            searcher.findPath(source, dest);
        }
    }
}