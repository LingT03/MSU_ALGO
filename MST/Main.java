package MST;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    private String dataFileName = "town.txt";
    private List<File2Edges.Edge> graphEdges;

    public static void main(String[] args) {
        Main Tree = new Main();
        Tree.graphEdges = File2Edges.getEdgesFromFile(Tree.dataFileName);
        Set<String> nodes = new TreeSet<String>();

        // embedded for loop to create the set of nodes
        for (File2Edges.Edge edge : Tree.graphEdges) {
            nodes.add(edge.start());
            nodes.add(edge.end());
        }

        System.out.println("Graph Nodes:");
        nodes.forEach(System.out::println);

        System.out.println("Graph Edges:");
        Tree.graphEdges.forEach(e -> System.out.println(e.start() + " -> " + e.end() + " : " + e.weight()));

        // represent graph via an adajency list

    }

    // constructor
    public Main() {
        // initialize the list of edges
        this.graphEdges = new LinkedList<File2Edges.Edge>();
        try {
            // create the data file if it doesn't exist
            File datafile = new File(dataFileName);
            datafile.createNewFile();
        } catch (IOException e) {
            // Auto-generated catch block
            System.out.println("Error creating test data file." + dataFileName);
            e.printStackTrace();
        }
    }

}
