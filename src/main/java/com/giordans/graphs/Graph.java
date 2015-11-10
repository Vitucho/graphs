/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giordans.graphs;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Administrador
 */
public abstract class Graph<NodeData, EdgeData> {

    public abstract Set<Edge> getEdges();

    public abstract Set<Node> getNodes();

    public class Node {

        private NodeData data;

        Node() {
        }

        public NodeData getData() {
            return data;
        }

        public void setData(NodeData data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return super.toString() + " holding " +  (data != null ? data.toString() : "no data");
        }
        
    }

    public class Edge {
        private EdgeData data;
        private final Node origin;
        private final Node destination;

        protected Edge(EdgeData data, Node origin, Node destination) {
            this.data = data;
            this.origin = origin;
            this.destination = destination;
        }
        
        public EdgeData getData() {
            return data;
        }

        public void setData(EdgeData data) {
            this.data = data;
        }

        public Node getOrigin() {
            return origin;
        }

        public Node getDestination() {
            return destination;
        }

        @Override
        public String toString() {
            return origin.toString() + " -> " + destination.toString() + " associated with " + (data != null ? data.toString() : "no data");
        }
    }
    
    public class Path {
        private final List<Node> nodes;

        protected Path(List<Node> nodes) {
            this.nodes = nodes;
        }

        public List<Node> getNodes() {
            return nodes;
        }

    }
}
