package com.mg.graphs;

/**
 * Edge of a directed weighted graph.
 *
 * @param <E>
 *            the type of elements connected by this edge
 */
class Edge<E> {
	/**
	 * Source node.
	 */
	private E source;
	/**
	 * Destination node.
	 */
	private E destination;
	/**
	 * Edge weight.
	 */
	private int weight;

	/**
	 * Creates a new edge.
	 * 
	 * @param source
	 *            the source node
	 * @param destination
	 *            the destination node
	 * @param weight
	 *            the edge weight
	 */
	Edge(E source, E destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	/**
	 * @return The source node.
	 */
	E getSource() {
		return source;
	}

	/**
	 * @return The destination node.
	 */
	E getDestination() {
		return destination;
	}

	/**
	 * @return The edge weight.
	 */
	int getWeight() {
		return weight;
	}
}
