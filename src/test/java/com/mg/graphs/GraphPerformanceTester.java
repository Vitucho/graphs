package com.mg.graphs;

import java.util.List;
import org.junit.Test;


public class GraphPerformanceTester {

    @Test
	public void testPerformance() {
		Graph<String> graph = new Graph<>();
		PathFinder<String> pathFinder = new PathFinder<>();

		int rows = 101;
		int cols = 101;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				graph.addNode(getNodeName(i, j));
			}
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (j + 1 < cols) {
					graph.addEdge(getNodeName(i, j), getNodeName(i, j + 1), 1);
				}
				if (i + 1 < rows) {
					graph.addEdge(getNodeName(i, j), getNodeName(i + 1, j), 1);
				}
				if (j > 0) {
					graph.addEdge(getNodeName(i, j), getNodeName(i, j - 1), 1);
				}
				if (i > 0) {
					graph.addEdge(getNodeName(i, j), getNodeName(i - 1, j), 1);
				}
			}
		}

		for (List<String> path : pathFinder.getPaths(graph, getNodeName(0, 0), getNodeName(rows - 1, cols - 1))) {
			System.out.println(path);
		}
	}

	private String getNodeName(int i, int j) {
		return "A[" + i + "][" + j + "]";
	}
}
