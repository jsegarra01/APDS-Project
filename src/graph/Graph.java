package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private float[][] adjacentMatrix;
    private int[] mapping;
    private int numVertices;

    private final List<Distance> distanceList;
    private final List<Place> placeList;

    public Graph() {
        distanceList = new ArrayList<>();
        placeList = new ArrayList<>();
    }

    public Graph(List<Distance> distanceList, List<Place> placeList) {
        this.distanceList = new ArrayList<>(distanceList);
        this.placeList = new ArrayList<>(placeList);

        this.adjacentMatrix = new float[placeList.size()][placeList.size()];
        fillWithZeros();

        numVertices = placeList.size();
        setMapping();
    }

    public List<Distance> getEdgeList() {
        return distanceList;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getNumOfDisconnectedVertices() {
        int numOfDisconnectedVertices = 0;

        for (Place place : placeList) {
            if (getAdjacentVertices(place).size() == 0){
                numOfDisconnectedVertices++;
            }
        }

        return numOfDisconnectedVertices;
    }

    public int[] getMapping() {
        return mapping;
    }

    public Place getVertex(int vertexID) {
        return placeList.get(mapping[vertexID]);
    }

    public Place getUnmappedVertex(int vertexID) {
        return placeList.get(vertexID);
    }

    public boolean isVertex(int vertexID) {
        return mapping[vertexID] != -1;
    }

    public boolean isPointOfInterest(int vertexID) {
        return getVertex(vertexID).getType().equals("INTEREST");
    }

    public boolean isDangerousPlace(int vertexID) {
        return getVertex(vertexID).getType().equals("DANGER");
    }

    public boolean isVertexDisconnected(int vertexID) {
        return getAdjacentVertices(getVertex(vertexID)).size() == 0;
    }

    private void fillWithZeros() {
        for (float[] row : adjacentMatrix) {
            Arrays.fill(row, 0.0f);
        }
    }

    private void setMapping() {
        mapping = new int[placeList.size() * 2];

        Arrays.fill(mapping, -1);
        for (int i = 0; i < numVertices; i++) {
            mapping[placeList.get(i).getId()] = i;
        }
    }

    public void initGraph() {
        for (Distance distance : distanceList) {
            addEdge(distance.getVertexA(), distance.getVertexB(), distance.getWeight());
            addEdge(distance.getVertexB(), distance.getVertexA(), distance.getWeight());
        }
    }

    public void addEdge(int vertexA, int vertexB, float weight) {
        adjacentMatrix[mapping[vertexA]][mapping[vertexB]] = weight;
    }

    public float getEdgeWeight(Place A, Place B) {
        return adjacentMatrix[mapping[A.getId()]][mapping[B.getId()]];
    }

    public List<Place> getAdjacentVertices(Place place) {
        List<Place> adjacents = new ArrayList<>();

        int i = mapping[place.getId()];
        for (int j = 0; j < numVertices; j++) {
            if (adjacentMatrix[i][j] != 0.0f) {
                adjacents.add(placeList.get(j));
            }
        }

        return adjacents;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        for (float[] row : adjacentMatrix) {
            string.append(Arrays.toString(row)).append('\n');
        }

        return string.toString();
    }
}
