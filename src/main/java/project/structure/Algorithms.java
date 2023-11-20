package project.structure;

import java.util.*;
import java.util.function.BinaryOperator;

public class Algorithms {

    /** Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vOrig vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex of graph g that will be the source of the search

     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V [] pathKeys, E [] dist) {

        int vKey = g.key(vOrig);
        dist[vKey] = zero;
        pathKeys[vKey] = vOrig;

        while (vOrig != null) {
            int keyVOrig = g.key(vOrig);
            visited[keyVOrig] = true;
            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                int keyVAdj = g.key(edge.getVDest());
                E sumLength = sum.apply(dist[keyVOrig], edge.getWeight());
                if (!visited[keyVAdj] && (dist[keyVAdj] == null || ce.compare(dist[keyVAdj], sumLength) > 0)) {
                    dist[keyVAdj] = sumLength;
                    pathKeys[keyVAdj] = vOrig;
                }
            }

            E minDist = null;
            vOrig = null;
            for (V vertex : g.vertices()) {
                int vertexKey = g.key(vertex);
                if (!visited[vertexKey] && dist[vertexKey] != null && (minDist == null || ce.compare(dist[vertexKey], minDist) < 0)) {
                    minDist = dist[vertexKey];
                    vOrig = vertex;
                }
            }
        }
    }


    /** Shortest-path between two vertices
     *
     * @param g graph
     * @param vOrig origin vertex
     * @param vDest destination vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {
//        if(g == null)
//            return null;
//        if(g.key(vOrig) < 0 || g.key(vDest) < 0)
//            return null;
//
//        int size = g.numVertices();
//        boolean[] visited = new boolean[size];
//        ArrayList<V> pathKeys = new ArrayList<>(size);
//        ArrayList<E> dist = new ArrayList<>(size);
//
//        //dão erro mas não sei porquê, os parametros estão direitos
//
//        //shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
//
//        //getPath(g, vOrig, vDest, pathKeys, shortPath);
//        if(shortPath.isEmpty())
//            return null;
//
//        return dist.get(g.key(vDest));

        // Check if vOrig and vDest exist in the application.ESINF.graph
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }

        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices];
        E[] dist = (E[]) new Object[numVertices];
        V[] pathKeys = (V[]) new Object[numVertices];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        if (pathKeys[g.key(vDest)] == null) {
            return null;
        }

        reconstructShortestPath(g, vOrig, vDest, pathKeys, shortPath);

        return dist[g.key(vDest)];
    }

    /**
     * Helper method to reconstruct the shortest path from vOrig to vDest
     *
     * @param g         application.ESINF.graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param pathKeys  minimum path vertices keys
     * @param shortPath returns the vertices which make the shortest path
     */
    private static <V, E> void reconstructShortestPath(Graph<V, E> g, V vOrig, V vDest, V[] pathKeys, LinkedList<V> shortPath) { //novo método --talvez não seja preciso
        int vOrigKey = g.key(vOrig);
        int vDestKey = g.key(vDest);

        // Reconstruct the path in reverse order
        while (!vDest.equals(vOrig)) {
            shortPath.addFirst(vDest);
            vDest = pathKeys[vDestKey];
            vDestKey = g.key(vDest);
        }

        // Add the origin vertex to the path
        shortPath.addFirst(vOrig);
    }

    /** Shortest-path between a vertex and all other vertices
     *
     * @param g graph
     * @param vOrig start vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {
//        if(g == null)
//            return false;
//
//        if(!g.validVertex(vOrig))
//            return false;
//
//        int size = g.numVertices();
//        boolean[] visited = new boolean[size];
//        ArrayList<V> pathKeys = new ArrayList<>(size);
//        ArrayList<E> dist = new ArrayList<>(size);
//
//        //acontece o mesmo aqui
//
//        //shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
//
//        for(V vDest : g.vertices()){
//            dists.add(dist.get(g.key(vDest)));
//            LinkedList<V> path = new LinkedList<>();
//            //getPath(g, vOrig, vDest, pathKeys, path);
//            paths.add(path);
//        }
//
//        return true;

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices];
        V[] pathKeys = (V[]) new Object[numVertices];
        E[] dist = (E[]) new Object[numVertices];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        for (int i = 0; i < numVertices; i++) {
            LinkedList<V> path = new LinkedList<>();
            reconstructShortestPath(g, vOrig, g.vertex(i), pathKeys, path);
            paths.add(path);
            dists.add(dist[i]);
        }

        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V [] pathKeys, LinkedList<V> path) {

        if (vOrig.equals(vDest))
            path.push(vDest);
        else {
            path.push(vDest);
            int keyVDest = g.key(vDest);
            vDest = pathKeys[keyVDest];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

//    /** Calculates the minimum distance graph using Floyd-Warshall
//     *
//     * @param g initial graph
//     * @param ce comparator between elements of type E
//     * @param sum sum two elements of type E
//     * @return the minimum distance graph
//     */
//    public static <V,E> MatrixGraph <V,E> minDistGraph(Graph <V,E> g, Comparator<E> ce, BinaryOperator<E> sum) {
//
//        throw new UnsupportedOperationException("Not supported yet.");
//    }



    /**
     * Calculates the betweenness centrality for each vertex in the graph using Brandes' algorithm.
     * Betweenness centrality measures the extent to which a vertex lies on the shortest paths
     * between other vertices in the graph.
     *
     * @param graph The graph for which to calculate betweenness centrality.
     * @return A map where each vertex is associated with its betweenness centrality value.
     * @param <V> The type of vertex in the graph.
     * @param <E> The type of edge in the graph.
     */
    public static <V, E> Map<V, Integer> betweennessCentrality(Graph<V, E> graph) {
        Map<V, Integer> centrality = new HashMap<>();

        for (V vertex : graph.vertices()) {
            centrality.put(vertex, 0);
        }

        for (V source : graph.vertices()) {
            LinkedList<V> queue = new LinkedList<>();
            queue.add(source);

            Map<V, Integer> numShortestPaths = new HashMap<>();
            numShortestPaths.put(source, 1);

            Map<V, Integer> dependency = new HashMap<>();
            for (V vertex : graph.vertices()) {
                dependency.put(vertex, 0);
            }

            Map<V, Integer> distance = new HashMap<>();
            distance.put(source, 0);

            while (!queue.isEmpty()) {
                V currentVertex = queue.poll();

                for (V neighbor : graph.adjVertices(currentVertex)) {
                    if (!distance.containsKey(neighbor)) {
                        distance.put(neighbor, distance.get(currentVertex) + 1);
                        queue.add(neighbor);
                    }

                    if (distance.get(neighbor) == distance.get(currentVertex) + 1) {
                        numShortestPaths.put(neighbor, numShortestPaths.getOrDefault(neighbor, 0) + numShortestPaths.get(currentVertex));
                        dependency.put(neighbor, dependency.get(neighbor) + 1);
                    }
                }
            }

            for (V vertex : graph.vertices()) {
                if (!vertex.equals(source)) {
                    centrality.put(vertex, centrality.get(vertex) + (dependency.get(vertex) / numShortestPaths.get(vertex)));
                }
            }
        }

        return centrality;
    }
}
