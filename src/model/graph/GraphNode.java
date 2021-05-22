package model.graph;

public class GraphNode {

    private final int id;
    private final String name;
    private final String type;

    public GraphNode(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static String toString(GraphNode node) {
        return "(location: " + node.name + " {type: " + node.type + "})";
    }
}
