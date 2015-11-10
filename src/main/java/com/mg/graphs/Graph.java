package com.mg.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Directed graph with weighted edges.
 * 
 * @param <E>
 *            the type of elements maintained by this graph
 */
public class Graph<E> {
	/**
	 * Maps each node to its outbound edges.
	 */
	private Map<E, Set<Edge<E>>> edges;

	/**
	 * Creates an empty graph.
	 */
	public Graph() {
		edges = new HashMap<>();
	}

	/**
	 * Adds a node to this graph.
	 * 
	 * @param node
	 *            element to be added to this graph
	 * 
	 * @throws NullPointerException
	 *             if node is null
	 * @throws IllegalArgumentException
	 *             if the graph already contains the given node
	 */
	public void addNode(E node) {
		checkAddNodeArgs(node);

		edges.put(node, new HashSet<>());
	}

	private void checkAddNodeArgs(E node) {
		if (node == null) {
			throw new NullPointerException("node is null");
		}

		if (edges.containsKey(node)) {
			throw new IllegalArgumentException("already existing node");
		}
	}

	/**
	 * Adds an edge to this graph.
	 * 
	 * @param source
	 *            source node
	 * @param destination
	 *            destination node
	 * @param weight
	 *            edge weight
	 * 
	 * @throws NullPointerException
	 *             if source is null
	 * @throws NullPointerException
	 *             if destination is null
	 * @throws IllegalArgumentException
	 *             if weight is less than one
	 * @throws IllegalArgumentException
	 *             if source is equal to destination (no loops allowed)
	 * @throws IllegalArgumentException
	 *             if source does not belong to this graph
	 * @throws IllegalArgumentException
	 *             if destination does not belong to this graph
	 * @throws IllegalArgumentException
	 *             if this graph already contains an edge with this source and
	 *             destination
	 */
	public void addEdge(E source, E destination, int weight) {
		checkAddEdgeArgs(source, destination, weight);

		edges.get(source).add(new Edge<E>(source, destination, weight));
	}

	private void checkAddEdgeArgs(E source, E destination, int weight) {
		if (source == null) {
			throw new NullPointerException("source is null");
		}

		if (destination == null) {
			throw new NullPointerException("destination is null");
		}

		if (weight < 1) {
			throw new IllegalArgumentException("weight is less than one");
		}

		if (source.equals(destination)) {
			throw new IllegalArgumentException("source is equal to destination");
		}

		if (!edges.containsKey(source)) {
			throw new IllegalArgumentException("source not found");
		}

		if (!edges.containsKey(destination)) {
			throw new IllegalArgumentException("destination not found");
		}

		if (edges.get(source).stream().filter((edge) -> edge.getDestination().equals(destination)).count() > 0) {
			throw new IllegalArgumentException("already existing edge");
		}
	}

	/**
	 * Gets all the nodes of this graph.
	 */
	Set<E> getNodes() {
		return edges.keySet();
	}

	/**
	 * Gets all the outbound edges for a given node.
	 */
	Set<Edge<E>> getOutboundEdges(E node) {
		return edges.get(node);
	}
}
