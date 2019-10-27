import java.util.Map;

public class GraphLogger {

    public static void getDegreeOfEachNode(String outputPath, Graph g) {
        
        Map<String, AdjacencyList> map = g.getAdjacencyLists();
        // go through each node
        int count = 0;
        String contents = "";
        for (Map.Entry<String, AdjacencyList> entry : map.entrySet()) {
            count++;
            String node = entry.getKey();
            AdjacencyList list = entry.getValue();

            // add string contents
            contents += node + "," + list.length() + "\n";
        }
        System.out.println("Number of nodes: " + count);

        // write to file
        TxtFileHandler.writeToTxt(outputPath, contents);
    }

    public static void getConnectivityOfEachNode(String outputPath, Graph g) {

        // create pathfinder
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        String contents = "";

        Map<String, AdjacencyList> map = g.getAdjacencyLists();
        // go through each node
        for (Map.Entry<String, AdjacencyList> entry : map.entrySet()) {
            String node = entry.getKey();
            AdjacencyList list = entry.getValue();

            // find no. nodes reachable by this node
            int numConnected = dfs.findNumConnectedNodes(node);

            // add string contents
            contents += node + "," + numConnected + "\n";
        }

        // write to file
        TxtFileHandler.writeToTxt(outputPath, contents);
    }

    public static void getAdjacentNodes(String outputPath, Graph g, String node) {

        AdjacencyList adjList = g.getAdjacencyList(node);

        String contents = "";
        // iterate adjacency list
        adjList.setStart();
        for (String neighbour = adjList.iterateNext(); neighbour != null; neighbour = adjList.iterateNext()) {
            // add to string contents
            contents += neighbour + "\n";
        }

        // write to file
        TxtFileHandler.writeToTxt(outputPath, contents);
    }
}