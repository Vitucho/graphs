package com.giordans.graphs;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Administrador
 */
public class SimpleGraph extends Graph<String, Void> {

    private final boolean[][] adjacencyMatrix;
    private final Node[] nodes;
    private int size;

    public SimpleGraph(int maxNodes) {
        this.adjacencyMatrix = new boolean[maxNodes][maxNodes];
        for (boolean[] adjacencyMatrix1 : this.adjacencyMatrix) {
            for (int j = 0; j < adjacencyMatrix1.length; j++) {
                adjacencyMatrix1[j] = false;
            }
        }
        this.size = 0;
        this.nodes = (Node[]) Array.newInstance(Node.class, maxNodes);
    }

    public void addNode(String nodeLabel) {
        Node node = new Node();
        node.setData(nodeLabel);
        if (this.size < this.nodes.length) {
            this.nodes[this.size] = node;
            this.size++;
        }
    }

    public void addEdge(String nodeOriginLabel, String nodeDestinationLabel) {
        int indexOrigin = indexOf(nodeOriginLabel);
        int indexDestination = indexOf(nodeDestinationLabel);
        if (indexOrigin >= 0 && indexDestination >= 0) {
            this.adjacencyMatrix[indexOrigin][indexDestination] = true;
        }
    }

    public Path getPath(String nodeOriginLabel, String nodeDestinationLabel) {
        Path path = null;
        int indexOrigin = indexOf(nodeOriginLabel);
        int indexDestination = indexOf(nodeDestinationLabel);
        if (indexOrigin >= 0 && indexDestination >= 0) {
            List<Node> nodesBuffer = new LinkedList<>();
            nodesBuffer.add(this.nodes[indexOrigin]);
            if (indexOrigin != indexDestination) {
                boolean found = searchAPath(nodesBuffer, indexOrigin, indexDestination);
                if (!found) {
                    nodesBuffer.remove(this.nodes[indexOrigin]);
                }
            }
            path = new Path(nodesBuffer);
        }
        return path;
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < this.size; i++) {
            Node nodeOrigin = this.nodes[i];
            for (int j = 0; j < this.size; j++) {
                if (this.adjacencyMatrix[i][j]) {
                    Node nodeDestination = this.nodes[j];
                    Edge edge = new Edge(null, nodeOrigin, nodeDestination);
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    @Override
    public Set<Node> getNodes() {
        HashSet<Node> setOfNodes = new HashSet<>(Arrays.asList(nodes));
        setOfNodes.remove(null);
        return setOfNodes;
    }

    private int indexOf(String nodeLabel) {
        for (int i = 0; i < this.size; i++) {
            if (this.nodes[i].getData().equals(nodeLabel)) {
                return i;
            }
        }
        return -1;
    }

    private boolean searchAPath(List<Node> nodesBuffer, int from, int to) {
        boolean found = false;
        for (int i = 0; !found && i < this.size; i++) {
            if (adjacencyMatrix[from][i]) {
                if (i == to) {
                    nodesBuffer.add(this.nodes[i]);
                    found = true;
                } else {
                    if (!nodesBuffer.contains(this.nodes[i])) {
                        nodesBuffer.add(this.nodes[i]);
                        found = searchAPath(nodesBuffer, i, to);
                        if (!found) {
                            nodesBuffer.remove(this.nodes[i]);
                        }
                    }
                }
            }
        }
        return found;
    }
}
