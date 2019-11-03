import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private String graphData[];
    private Graph graph = null;
    private String options[] = {
        "Read Graph data",      // 1
        "Find Shortest Path (BFS)", // 2
        "Find A Path (DFS)",  // 3
        "Find Runtime For All Cities To All Other Cities (BFS)",    // 4
        "Find Runtime For All Cities To All Other Cities (DFS)",    // 5
        "Debug Options"     //6
    };

    private String debugOptions[] = {
        "Print Node Degrees (to File)",
        "Print Graph Connectivity (to File)",
        "Print Adjacency List of a Node (to File)"
    };

    public static void main(String[] args) {
        
        App app = new App();
        app.graphData = args.clone();
        app.run();
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
            System.out.println(graph.getAllCities());
            break;
        case 2: // Find Shortest Path (BFS algorithm)
        {
            Pathfinder bfs = new BreadthFirstSearch(graph);
            runPathfinding(bfs);
        }
            break;
        case 3: // Find A Path using DFS algorithm
        {
            Pathfinder dfs = new DepthFirstSearch(graph);
            runPathfinding(dfs);
        }
            break;
        case 4: // Find Runtime For All Cities To All Other Cities (BFS)
        {
            if (graph == null)
                System.out.println("No Graph loaded yet!");
            else {
                Pathfinder bfs = new BreadthFirstSearch(graph);
                getRuntime(bfs);
            }
            break;
        }
        case 5: // Find Runtime For All Cities To All Other Cities (DFS)
        {
            if (graph == null)
                System.out.println("No Graph loaded yet!");
            else {
                Pathfinder dfs = new DepthFirstSearch(graph);
                getRuntime(dfs);
            }
            break;
        }
        case 6: // Debug Options
        {
            debugOptions();
        }
        default:
            break;
        }
    }

    private void getRuntime(Pathfinder searcher) {
        ArrayList<String> cities = graph.getAllCities();
        double runtime_ms = 0;
        for (String source: cities) {
            for (String dest: cities) {
//                System.out.println("source: " + source + "dest: " + dest);
                double iter_runtime = 0;
                for (int j = 0; j < 10; j++) {
                    iter_runtime += searcher.findPath(source, dest);
                }
                runtime_ms += iter_runtime/10;
            }
        }
        System.out.println("Runtime: " + runtime_ms/cities.size()/cities.size());
    }

    // get user selection of graph to load
    private void loadGraph() {
        // print available Graph data
        for (int i = 1; i <= graphData.length; i++) {
            System.out.printf("(%d) %s\n", i, graphData[i-1]);
        }

        // read selected graph number
        Scanner sc = new Scanner(System.in);
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
        for (int i = 0; i < lines.size(); i++) {
            
            String[] data = lines.get(i).split(",");
            //System.out.println("Source: " + data[0] + ", Dest: " + data[1]);
            graph.addEdge(data[0], data[1]);
        }
        return graph;
    }

    private void runPathfinding(Pathfinder searcher) {
        if (graph == null)
            System.out.println("No Graph loaded yet!");
        else {
            // get source and destination cities
            Scanner scr = new Scanner(System.in);
            System.out.print("Enter start city: ");
            String source = scr.nextLine();
            System.out.print("Enter end city: ");
            String dest = scr.nextLine();

            // check if source and destination exist in Graph first
            boolean runAlgo = true;
            if (!graph.containsNode(source)) {
                System.out.println(source + " not in graph.");
                runAlgo = false;
            }
            if (!graph.containsNode(dest)) {
                System.out.println(dest + " not in graph.");
                runAlgo = false;
            }

            // perform search algorithm
            if (runAlgo) {
                double runtime_ms = 0;
                for (int i = 0; i < 50; i++) {
                    runtime_ms += searcher.findPath(source, dest);
                }
                System.out.println("Average runtime: " + runtime_ms/50);
            }
        }
    }

    private void debugOptions() {

        // print options
        int i;
        for (i = 1; i <= debugOptions.length; i++) {
            System.out.printf("(%d) %s\n", i, debugOptions[i-1]);
        }
        // print quit option
        System.out.printf("(%d) Quit\n", i);

        int choice = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter option: ");
        choice = sc.nextInt();
        switch (choice)
        {
        case 1: // Print Node Degrees (to File)
        {
            if (graph == null)
                System.out.println("No Graph loaded yet!");
            else {
                GraphLogger.getDegreeOfEachNode("./output/node_degrees.csv", graph);
                System.out.println("Data logged");
            }
        }
            break;
        case 2: // Print Graph Connectivity (to File)
        {
            if (graph == null)
                System.out.println("No Graph loaded yet!");
            else {
                GraphLogger.getConnectivityOfEachNode("./output/node_connectivity.csv", graph);
                System.out.println("Data logged");
            }
        }
            break;
        case 3: // Print Adjacency List of a Node (to File)
        {
            if (graph == null)
                System.out.println("No Graph loaded yet!");
            else {
                Scanner scr = new Scanner(System.in);
                System.out.print("Enter node: ");
                String node = scr.nextLine();
                if (graph.containsNode(node)) {
                    GraphLogger.getAdjacentNodes("./output/adjlist/" + node + ".csv", graph, node);
                    System.out.println("Data logged");
                } else {
                    System.out.println(node + " is not in graph");
                }
            }
        }
            break;
        default:
            break;
        }
    }

}