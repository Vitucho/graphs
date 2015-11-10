package com.mg.graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GraphTester {
	private Graph<Integer> graph;
	private Random random;
	private PathFinder<Integer> pathFinder;

	@Before
	public void before() {
		graph = new Graph<>();
		random = new Random();
		pathFinder = new PathFinder<>();

		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);
		graph.addNode(5);
		graph.addNode(6);

		graph.addEdge(1, 2, 2);
		graph.addEdge(1, 3, 1);
		graph.addEdge(2, 4, 1);
		graph.addEdge(3, 4, 3);
		graph.addEdge(3, 5, 4);
		graph.addEdge(4, 6, 2);
		graph.addEdge(5, 6, 2);
	}

	@Test(expected = NullPointerException.class)
	public void testAddNode1() {
		graph.addNode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNode2() {
		graph.addNode(random.nextInt(6) + 1);
	}

	@Test(expected = NullPointerException.class)
	public void testAddEdge1() {
		graph.addEdge(null, 3, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testAddEdge2() {
		graph.addEdge(2, null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge3() {
		graph.addEdge(2, 3, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge4() {
		graph.addEdge(1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge5() {
		graph.addEdge(0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge6() {
		graph.addEdge(1, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge7() {
		graph.addEdge(1, 2, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testFindPathArgs1() {
		pathFinder.findPath(null, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testFindPathArgs2() {
		pathFinder.findPath(graph, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindPathArgs3() {
		pathFinder.findPath(graph, 0);
	}

	@Test
	public void testFindPath1() {
		pathFinder.findPath(graph, 1);

		Assert.assertEquals(pathFinder.getDist(1), 0);
		Assert.assertEquals(pathFinder.getDist(2), 2);
		Assert.assertEquals(pathFinder.getDist(3), 1);
		Assert.assertEquals(pathFinder.getDist(4), 3);
		Assert.assertEquals(pathFinder.getDist(5), 5);
		Assert.assertEquals(pathFinder.getDist(6), 5);

		Assert.assertEquals(pathFinder.getPrev(1), new HashSet<>());
		Assert.assertEquals(pathFinder.getPrev(2), new HashSet<>(Arrays.asList(1)));
		Assert.assertEquals(pathFinder.getPrev(3), new HashSet<>(Arrays.asList(1)));
		Assert.assertEquals(pathFinder.getPrev(4), new HashSet<>(Arrays.asList(2)));
		Assert.assertEquals(pathFinder.getPrev(5), new HashSet<>(Arrays.asList(3)));
		Assert.assertEquals(pathFinder.getPrev(6), new HashSet<>(Arrays.asList(4)));
	}

	@Test
	public void testFindPath2() {
		Graph<String> graph = new Graph<>();
		PathFinder<String> pathFinder = new PathFinder<>();

		graph.addNode("Buenos Aires");
		graph.addNode("Ezeiza");
		graph.addNode("Cañuelas");
		graph.addNode("Lobos");
		graph.addNode("La Plata");
		graph.addNode("Baradero");
		graph.addNode("Navarro");
		graph.addNode("Monte");
		graph.addNode("Rosario");
		graph.addNode("Córdoba");

		graph.addEdge("Buenos Aires", "Ezeiza", 30);
		graph.addEdge("Ezeiza", "Cañuelas", 30);
		graph.addEdge("Cañuelas", "Lobos", 40);
		graph.addEdge("Lobos", "Cañuelas", 40);
		graph.addEdge("Cañuelas", "Ezeiza", 30);
		graph.addEdge("Ezeiza", "Buenos Aires", 30);
		graph.addEdge("Buenos Aires", "La Plata", 50);
		graph.addEdge("La Plata", "Cañuelas", 50);
		graph.addEdge("Cañuelas", "La Plata", 50);
		graph.addEdge("La Plata", "Buenos Aires", 50);
		graph.addEdge("Buenos Aires", "Baradero", 100);
		graph.addEdge("Baradero", "Navarro", 100);
		graph.addEdge("Navarro", "Lobos", 30);
		graph.addEdge("Lobos", "Navarro", 30);
		graph.addEdge("Navarro", "Baradero", 100);
		graph.addEdge("Baradero", "Buenos Aires", 100);
		graph.addEdge("Cañuelas", "Monte", 30);
		graph.addEdge("Monte", "Cañuelas", 30);
		graph.addEdge("Lobos", "Monte", 30);
		graph.addEdge("Monte", "Lobos", 30);
		graph.addEdge("Rosario", "Córdoba", 400);
		graph.addEdge("Córdoba", "Rosario", 400);

		pathFinder.findPath(graph, "Buenos Aires");

		Assert.assertEquals(pathFinder.getDist("Buenos Aires"), 0);
		Assert.assertEquals(pathFinder.getDist("Ezeiza"), 30);
		Assert.assertEquals(pathFinder.getDist("Cañuelas"), 60);
		Assert.assertEquals(pathFinder.getDist("Lobos"), 100);
		Assert.assertEquals(pathFinder.getDist("La Plata"), 50);
		Assert.assertEquals(pathFinder.getDist("Baradero"), 100);
		Assert.assertEquals(pathFinder.getDist("Navarro"), 130);
		Assert.assertEquals(pathFinder.getDist("Monte"), 90);
		Assert.assertEquals(pathFinder.getDist("Rosario"), Integer.MAX_VALUE);
		Assert.assertEquals(pathFinder.getDist("Córdoba"), Integer.MAX_VALUE);

		Assert.assertEquals(pathFinder.getPrev("Buenos Aires"), new HashSet<>());
		Assert.assertEquals(pathFinder.getPrev("Ezeiza"), new HashSet<>(Arrays.asList("Buenos Aires")));
		Assert.assertEquals(pathFinder.getPrev("Cañuelas"), new HashSet<>(Arrays.asList("Ezeiza")));
		Assert.assertEquals(pathFinder.getPrev("Lobos"), new HashSet<>(Arrays.asList("Cañuelas")));
		Assert.assertEquals(pathFinder.getPrev("La Plata"), new HashSet<>(Arrays.asList("Buenos Aires")));
		Assert.assertEquals(pathFinder.getPrev("Baradero"), new HashSet<>(Arrays.asList("Buenos Aires")));
		Assert.assertEquals(pathFinder.getPrev("Navarro"), new HashSet<>(Arrays.asList("Lobos")));
		Assert.assertEquals(pathFinder.getPrev("Monte"), new HashSet<>(Arrays.asList("Cañuelas")));
		Assert.assertEquals(pathFinder.getPrev("Rosario"), new HashSet<>());
		Assert.assertEquals(pathFinder.getPrev("Córdoba"), new HashSet<>());
	}

	@Test
	public void testFindPath3() {
		Graph<Integer> graph = new Graph<>();
		PathFinder<Integer> pathFinder = new PathFinder<>();

		graph.addNode(1);

		pathFinder.findPath(graph, 1);

		Assert.assertEquals(pathFinder.getDist(1), 0);
		Assert.assertEquals(pathFinder.getPrev(1), new HashSet<>());
	}

	@Test
	public void testFindPath4() {
		graph.addNode(7);

		graph.addEdge(1, 7, 2);
		graph.addEdge(7, 6, 3);

		pathFinder.findPath(graph, 1);

		Assert.assertEquals(pathFinder.getDist(1), 0);
		Assert.assertEquals(pathFinder.getDist(2), 2);
		Assert.assertEquals(pathFinder.getDist(3), 1);
		Assert.assertEquals(pathFinder.getDist(4), 3);
		Assert.assertEquals(pathFinder.getDist(5), 5);
		Assert.assertEquals(pathFinder.getDist(6), 5);
		Assert.assertEquals(pathFinder.getDist(7), 2);

		Assert.assertEquals(pathFinder.getPrev(1), new HashSet<>());
		Assert.assertEquals(pathFinder.getPrev(2), new HashSet<>(Arrays.asList(1)));
		Assert.assertEquals(pathFinder.getPrev(3), new HashSet<>(Arrays.asList(1)));
		Assert.assertEquals(pathFinder.getPrev(4), new HashSet<>(Arrays.asList(2)));
		Assert.assertEquals(pathFinder.getPrev(5), new HashSet<>(Arrays.asList(3)));
		Assert.assertEquals(pathFinder.getPrev(6), new HashSet<>(Arrays.asList(4, 7)));
		Assert.assertEquals(pathFinder.getPrev(7), new HashSet<>(Arrays.asList(1)));
	}

	@Test(expected = IllegalStateException.class)
	public void testGetDist1() {
		pathFinder.getDist(1);
	}

	@Test(expected = NullPointerException.class)
	public void testGetDist2() {
		pathFinder.findPath(graph, 1);
		pathFinder.getDist(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDist3() {
		pathFinder.findPath(graph, 1);
		pathFinder.getDist(0);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetPrev1() {
		pathFinder.getPrev(1);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPrev2() {
		pathFinder.findPath(graph, 1);
		pathFinder.getPrev(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPrev3() {
		pathFinder.findPath(graph, 1);
		pathFinder.getPrev(0);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPathsArgs1() {
		pathFinder.getPaths(null, 1, 6);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPathsArgs2() {
		pathFinder.getPaths(graph, null, 6);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPathsArgs3() {
		pathFinder.getPaths(graph, 1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPathsArgs4() {
		pathFinder.getPaths(graph, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPathsArgs5() {
		pathFinder.getPaths(graph, 0, 6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPathsArgs6() {
		pathFinder.getPaths(graph, 1, 7);
	}

	@Test
	public void testGetPaths1() {
		Set<List<Integer>> expected = new HashSet<>();
		Set<List<Integer>> actual = pathFinder.getPaths(graph, 1, 6);

		expected.add(Arrays.asList(1, 2, 4, 6));

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGetPaths2() {
		graph.addNode(7);

		graph.addEdge(1, 7, 2);
		graph.addEdge(7, 6, 3);

		Set<List<Integer>> expected = new HashSet<>();
		Set<List<Integer>> actual = pathFinder.getPaths(graph, 1, 6);

		expected.add(Arrays.asList(1, 2, 4, 6));
		expected.add(Arrays.asList(1, 7, 6));

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGetPaths3() {
		Graph<Integer> graph = new Graph<>();
		PathFinder<Integer> pathFinder = new PathFinder<>();

		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);
		graph.addNode(5);
		graph.addNode(6);
		graph.addNode(7);
		graph.addNode(8);
		graph.addNode(9);

		graph.addEdge(1, 2, 1);
		graph.addEdge(1, 4, 1);
		graph.addEdge(2, 1, 1);
		graph.addEdge(2, 3, 1);
		graph.addEdge(2, 5, 1);
		graph.addEdge(3, 2, 1);
		graph.addEdge(3, 6, 1);
		graph.addEdge(4, 1, 1);
		graph.addEdge(4, 5, 1);
		graph.addEdge(4, 7, 1);
		graph.addEdge(5, 2, 1);
		graph.addEdge(5, 4, 1);
		graph.addEdge(5, 6, 1);
		graph.addEdge(5, 8, 1);
		graph.addEdge(6, 3, 1);
		graph.addEdge(6, 5, 1);
		graph.addEdge(6, 9, 1);
		graph.addEdge(7, 4, 1);
		graph.addEdge(7, 8, 1);
		graph.addEdge(8, 7, 1);
		graph.addEdge(8, 5, 1);
		graph.addEdge(8, 9, 1);
		graph.addEdge(9, 6, 1);
		graph.addEdge(9, 8, 1);

		Set<List<Integer>> expected = new HashSet<>();
		Set<List<Integer>> actual = pathFinder.getPaths(graph, 1, 9);

		expected.add(Arrays.asList(1, 2, 3, 6, 9));
		expected.add(Arrays.asList(1, 2, 5, 6, 9));
		expected.add(Arrays.asList(1, 2, 5, 8, 9));
		expected.add(Arrays.asList(1, 4, 5, 6, 9));
		expected.add(Arrays.asList(1, 4, 5, 8, 9));
		expected.add(Arrays.asList(1, 4, 7, 8, 9));

		Assert.assertEquals(expected, actual);
	}
}
