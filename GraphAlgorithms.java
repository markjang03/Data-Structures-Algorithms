import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.PriorityQueue;


/**
 * Your implementation of various different graph algorithms.
 *
 * @author Mark Jang
 * @version 1.0
 * @userid yjang95
 * @GTID 903730588
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start,
                                          Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("start vertex or graph cannot be null or missing");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        HashSet<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> returnLL = new LinkedList<>();

        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> currentV = queue.remove();
            if (currentV == null) {
                throw new IllegalArgumentException("current vertex is null");
            }
            returnLL.add(currentV);
            for (VertexDistance<T> n : adjList.get(currentV)) {
                if (!visited.contains(n.getVertex())) {
                    visited.add(n.getVertex());
                    queue.add(n.getVertex());
                }
            }
        }
        return returnLL;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("start vertex or graph cannot be null and graph should contain start.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> returnList = new LinkedList<>();

        dfsHelper(start, returnList, graph, visited, adjList);
        return returnList;
    }

    /**
     * recursive helper method for DFS
     *
     * @param vertex  Current vertex
     * @param graph   the graph to search
     * @param visited set of the vertices in the graph that I visited
     * @param result  result of list of vertices
     * @param <T>     generic type
     * @param adj     adjacency list
     */
    private static <T> void dfsHelper(Vertex<T> vertex,
                                      List<Vertex<T>> result, Graph<T> graph,
                                      Set<Vertex<T>> visited, Map<Vertex<T>,
            List<VertexDistance<T>>> adj) {
        if (visited.size() == graph.getVertices().size()) {
            return;
        }
        visited.add(vertex);
        result.add(vertex);
        List<VertexDistance<T>> vertices = adj.get(vertex);
        for (VertexDistance<T> v : vertices) {
            if (!visited.contains(v.getVertex())) {
                dfsHelper(v.getVertex(), result, graph, visited, adj);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("start vertex or graph cannot be null and graph should contain start.");
        }

        PriorityQueue<VertexDistance<T>> pqueue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> result = new HashMap<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> visited = new HashSet<>();

        for (Vertex<T> v : adjList.keySet()) {
            result.put(v, Integer.MAX_VALUE);
        }
        result.put(start, 0);
        pqueue.add(new VertexDistance<>(start, 0));

        while (visited.size() < result.size() && !pqueue.isEmpty()) {
            VertexDistance<T> curr = pqueue.remove();
            visited.add(curr.getVertex());
            for (VertexDistance<T> v : adjList.get(curr.getVertex())) {
                int currDistance = curr.getDistance() + v.getDistance();
                if (!visited.contains(v.getVertex()) && result.get(v.getVertex()).compareTo(currDistance) > 0) {
                    result.put(v.getVertex(), currDistance);
                    pqueue.add(new VertexDistance<>(v.getVertex(), currDistance));
                }
            }
        }
        return result;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * An MST should NOT have self-loops or parallel edges.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        Set<Edge<T>> result = new HashSet<>();
        DisjointSet<T> disjointSet = new DisjointSet<>();

        for (Vertex<T> vertex : graph.getVertices()) {
            disjointSet.find(vertex.getData());
        }
        PriorityQueue<Edge<T>> pqueue = new PriorityQueue<>(graph.getEdges());
        while ((result.size() < (2 * (graph.getVertices().size()) - 2))) {
            Edge<T> currEdge = pqueue.poll();
            if (currEdge == null) {
                return null;
            } else if (!disjointSet.find(currEdge.getU()).equals(disjointSet.find(currEdge.getV()))) {
                result.add(currEdge);
                result.add(new Edge<>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                disjointSet.union(currEdge.getU().getData(), currEdge.getV().getData());
            }
        }
        return result;
    }
}