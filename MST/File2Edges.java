package MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class File2Edges {

    private File2Edges() {
    }

    public static List<Edge> getEdgesFromFile(String filename) {
        List<String> edgeStrings = getDataFromFile(filename);
        List<Edge> edges = new ArrayList<Edge>();
        for (String s : edgeStrings) {
            edges.add(parseEdgeString(s));
        }
        return edges;
        // read the file and return a list of edges
    }

    public static List<String> getDataFromFile(String filename) {
        List<String> result = new ArrayList<String>();
        try {
            BufferedReader incoming = new BufferedReader(new FileReader(filename));
            result.add(incoming.readLine());
            while (result.get(result.size() - 1) != null) {
                result.add(incoming.readLine());
            }
            incoming.close();
        } catch (IOException e) {
            System.err.println("Error: e");
        }
        result.remove(result.size() - 1);
        return result;
        // read the file and return a list of strings
        // each string is a line in the file
    }

    static class Edge {
        public String startNode;
        public String endNode;
        public int weight;

        public Edge(String start, String end, int wt) {
            this.startNode = start;
            this.endNode = end;
            this.weight = wt;
        }

        public String start() {
            return this.startNode;
        }

        public String end() {
            return this.endNode;
        }

        public int weight() {
            return this.weight;
        }
    }

    public static Edge parseEdgeString(String edgeString) {
        String[] parts = edgeString.split(", ", 3);
        return new Edge(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
    // parse the edge string into an Edge object
    // output will be of the form: Edge("A", "B", 5)
}