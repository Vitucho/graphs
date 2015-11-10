package com.mg.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Searches the shortest path from a source node to all the other nodes of a
 * directed weighted graph.
 *
 * @param <E>
 *            the type of elements maintained by the given graphs
 */
public class PathFinder<E> {
	/**
	 * Maps each node to the distance from a given source.
	 */
	private Map<E, Integer> dist;
	/**
	 * Maps each node to the previous node in the shortest path (a set is
	 * provided to handle multiple paths)
	 */
	private Map<E, Set<E>> prev;
	/**
	 * Set of unvisited nodes.
	 */
	private Set<E> unvisited;

	/**
	 * Creates a new path finder.
	 */
	public PathFinder() {
		dist = null;
		prev = null;
		unvisited = null;
	}

	/**
	 * <p>
	 * Computes the shortest paths from a source node to all the other nodes of
	 * a directed weighted graph.
	 * </p>
	 * <p>
	 * The info can be retrieved using the following methods:
	 * </p>
	 * <ul>
	 * <li>{@link #getDist(Object)}</li>
	 * <li>{@link #getPrev(Object)}</li>
	 * </ul>
	 * 
	 * @param graph
	 *            the graph reference
	 * @param source
	 *            the source node reference
	 * 
	 * @throws NullPointerException
	 *             if graph is null
	 * @throws NullPointerException
	 *             if source is null
	 * @throws IllegalArgumentException
	 *             if the graph does not contain the source node
	 */
	public void findPath(Graph<E> graph, E source) {
		checkFindPathArgs(graph, source);
		init(graph, source);
		start(graph);
	}

	private void checkFindPathArgs(Graph<E> graph, E source) {
		if (graph == null) {
			throw new NullPointerException("graph is null");
		}

		if (source == null) {
			throw new NullPointerException("source is null");
		}

		if (!graph.getNodes().contains(source)) {
			throw new IllegalArgumentException("source not found");
		}
	}

	private void init(Graph<E> graph, E source) {
		dist = new HashMap<>();
		prev = new HashMap<>();
		unvisited = new HashSet<>();

		for (E node : graph.getNodes()) {
			// set each node at the maximum distance
			dist.put(node, Integer.MAX_VALUE);
			// no previous node for now...
			prev.put(node, new HashSet<>());
			// set unvisited
			unvisited.add(node);
		}

		// source is at distance zero
		dist.put(source, 0);
	}

	private void start(Graph<E> graph) {
		E min;

		while ((min = getMin()) != null) {
			// label min as visited
			unvisited.remove(min);

			// for each min neighbor...
			for (Edge<E> edge : graph.getOutboundEdges(min)) {
				int alt = dist.get(min) + edge.getWeight();

				if (alt < dist.get(edge.getDestination())) {
					// a shorter path was found!
					dist.put(edge.getDestination(), alt);
					prev.get(edge.getDestination()).clear();
					prev.get(edge.getDestination()).add(min);
				} else if (alt == dist.get(edge.getDestination())) {
					// another path was found!
					prev.get(edge.getDestination()).add(min);
				}
			}
		}
	}

	/**
	 * @return Unvisited node at the minimum distance from source or null if it
	 *         does not exist (i.e., unvisited is empty or every node in
	 *         unvisited is at the maximum distance)
	 */
	private E getMin() {
		return unvisited.stream().filter((node) -> dist.get(node) != Integer.MAX_VALUE)
				.min((node1, node2) -> dist.get(node1) - dist.get(node2)).orElse(null);
	}

	/**
	 * @return The distance from source to the given node or
	 *         {@link Integer#MAX_VALUE} if the given node is unreachable.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before
	 *             {@link #findPath(Graph, Object)}
	 * @throws NullPointerException
	 *             if node is null
	 * @throws IllegalArgumentException
	 *             if the given node does not belong to the treated graph
	 */
	public int getDist(E node) {
		checkGetDistCall(node);

		return dist.get(node);
	}

	private void checkGetDistCall(E node) {
		if (dist == null) {
			throw new IllegalStateException("calling getDist before calling findPath");
		}

		if (node == null) {
			throw new NullPointerException("node is null");
		}

		if (!dist.containsKey(node)) {
			throw new IllegalArgumentException("node not found");
		}
	}

	/**
	 * @return The previous node in the shortest path from source. It returns an
	 *         empty set if the given node is unreachable or equal to source,
	 *         and it might return more than one node if there are multiple
	 *         paths.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before
	 *             {@link #findPath(Graph, Object)}
	 * @throws NullPointerException
	 *             if node is null
	 * @throws IllegalArgumentException
	 *             if the given node does not belong to the treated graph
	 */
	public Set<E> getPrev(E node) {
		checkGetPrevCall(node);

		return prev.get(node);
	}

	private void checkGetPrevCall(E node) {
		if (prev == null) {
			throw new IllegalStateException("calling getPrev before calling findPath");
		}

		if (node == null) {
			throw new NullPointerException("node is null");
		}

		if (!prev.containsKey(node)) {
			throw new IllegalArgumentException("node not found");
		}
	}

	/**
	 * Gets all the shortest paths from source to destination.
	 * 
	 * @param graph
	 *            the given graph
	 * @param source
	 *            the source node
	 * @param destination
	 *            the destination node
	 * 
	 * @return A set containing all the shortest paths from source to
	 *         destination. If destination is unreachable then it returns an
	 *         empty set. Each path consists of a list of nodes, where the
	 *         elements are listed in order from source to destination.
	 * 
	 * @throws NullPointerException
	 *             if graph is null
	 * @throws NullPointerException
	 *             if source is null
	 * @throws NullPointerException
	 *             if destination is null
	 * @throws IllegalArgumentException
	 *             if source and destination are equals
	 * @throws IllegalArgumentException
	 *             if source does not belong to the given graph
	 * @throws IllegalArgumentException
	 *             if destination does not belong to the given graph
	 */
	public Set<List<E>> getPaths(Graph<E> graph, E source, E destination) {
		checkGetPathsArgs(graph, source, destination);
		findPath(graph, source);

		Set<List<E>> paths = new HashSet<>();

		if (!prev.get(destination).isEmpty()) {
			List<E> path = new LinkedList<>();

			path.add(destination);
			getPaths(paths, path);
		}

		return paths;
	}

	private void checkGetPathsArgs(Graph<E> graph, E source, E destination) {
		if (graph == null) {
			throw new NullPointerException("graph is null");
		}

		if (source == null) {
			throw new NullPointerException("source is null");
		}

		if (destination == null) {
			throw new NullPointerException("destination is null");
		}

		if (source.equals(destination)) {
			throw new IllegalArgumentException("source is equal to destination");
		}

		if (!graph.getNodes().contains(source)) {
			throw new IllegalArgumentException("source not found");
		}

		if (!graph.getNodes().contains(destination)) {
			throw new IllegalArgumentException("destination not found");
		}
	}

	private void getPaths(Set<List<E>> paths, List<E> path) {
		E node = path.get(0);

		while (prev.get(node).size() == 1) {
			node = prev.get(node).iterator().next();

			path.add(0, node);
		}

		if (prev.get(node).isEmpty()) {
			paths.add(path);
		} else {
			Iterator<E> iterator = prev.get(node).iterator();

			while (iterator.hasNext() && paths.size() < LIMIT) {
				List<E> extendedPath = new LinkedList<>(path);

				extendedPath.add(0, iterator.next());

				getPaths(paths, extendedPath);
			}
		}
	}

	private static final int LIMIT = 10;
}
