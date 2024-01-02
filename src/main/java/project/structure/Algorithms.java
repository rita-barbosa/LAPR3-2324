package project.structure;

import java.util.*;
import java.util.function.BinaryOperator;

public class Algorithms {


    private static <V> boolean isNotVisited(V x, List<V> path) {
        return !path.contains(x);
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g           Graph instance
     * @param vDest       Vertex that will be the end of the path
     * @param currentPath LinkedList of the vertices from the current path
     * @param allPaths    ArrayList with all the paths (in correct order)
     */
    private static <V, E> void findPaths(Graph<V, E> g, V vDest, ArrayList<LinkedList<V>> allPaths, LinkedList<V> currentPath, int aut, int dist) {
        V last = currentPath.get(currentPath.size() - 1);

        if (last.equals(vDest) && dist <= aut) {
            allPaths.add(new LinkedList<>(currentPath));
        }

        Collection<Edge<V, E>> incomingEdges = g.incomingEdges(last);

        for (Edge<V, E> edge : incomingEdges) {
            V neighbor = edge.getVOrig();
            if (isNotVisited(neighbor, currentPath)) {
                int newDist = dist + (int) edge.getWeight();
                currentPath.add(neighbor);
                findPaths(g, vDest, allPaths, currentPath, aut, newDist);
                currentPath.removeLast();
            }
        }
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> findPaths(Graph<V, E> g, V vOrig, V vDest, int autonomia) {
        ArrayList<LinkedList<V>> allPaths = new ArrayList<>();
        LinkedList<V> initialPath = new LinkedList<>();
        initialPath.add(vOrig);
        findPaths(g, vDest, allPaths, initialPath, autonomia, 0);

        return allPaths;
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g       Graph instance
     * @param vOrig   vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs    return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        visited[g.key(vOrig)] = true;

        qdfs.add(vOrig);

        for (V vertex : g.adjVertices(vOrig)) {
            if (!visited[g.key(vertex)]) {
                DepthFirstSearch(g, vertex, visited, qdfs);
            }
        }
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex of graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.validVertex(vert)) {
            return null;
        }

        boolean visited[] = new boolean[g.numVertices()];

        LinkedList<V> result = new LinkedList<>();

        DepthFirstSearch(g, vert, visited, result);

        return result;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    public static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                       LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        visited[g.key(vOrig)] = true;
        path.push(vOrig);
        for (V vAdj : g.adjVertices(vOrig)) {
            if (vAdj.equals(vDest)) {
                path.push(vDest);
                paths.add(revPath(path));
                path.pop();
            } else {
                if (!visited[g.key(vAdj)]) {
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }
        V vertex = path.pop();
        visited[g.key(vertex)] = false;
    }

    /**
     * Reverses the path
     *
     * @param path stack with path
     */
    public static <V> LinkedList<V> revPath(List<V> path) {
        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty()) {
            pathrev.push(pathcopy.pop());
        }
        return pathrev;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> path = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];

        if (g.validVertex(vOrig) && g.validVertex(vDest)) {
            allPaths(g, vOrig, vDest, visited, path, paths);
        }
        return paths;
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
    public static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                   Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                   boolean[] visited, V[] pathKeys, E[] dist) {

        int vKey = g.key(vOrig);
        dist[vKey] = zero;
        pathKeys[vKey] = vOrig;

        while (vOrig != null) {
            vKey = g.key(vOrig);
            visited[vKey] = true;
            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                int keyVAdj = g.key(edge.getVDest());
                if (!visited[keyVAdj]) {
                    E s = sum.apply(dist[vKey], edge.getWeight());
                    if (dist[keyVAdj] == null || ce.compare(dist[keyVAdj], s) > 0) {
                        dist[keyVAdj] = s;
                        pathKeys[keyVAdj] = vOrig;
                    }
                }
            }

            E minDist = null;
            vOrig = null;
            for (V vertex : g.vertices()) {
                int vertexKey = g.key(vertex);
                if (!visited[vertexKey] && (dist[vertexKey] != null) && ((minDist == null) || ce.compare(dist[vertexKey], minDist) < 0)) {
                    minDist = dist[vertexKey];
                    vOrig = vertex;
                }
            }
        }
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
    public static <V, E> void shortestPathDijkstraWithAutonomy(Graph<V, E> g, E autonomia, V vOrig,
                                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                               boolean[] visited, V[] pathKeys, E[] dist) {

        int vkey = g.key(vOrig);
        dist[vkey] = zero;
        pathKeys[vkey] = vOrig;

        while (vOrig != null) {
            vkey = g.key(vOrig);
            visited[vkey] = true;

            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                int vkeyAdj = g.key(edge.getVDest());
                if (!visited[vkeyAdj]) {
                    E remainingAutonomy = sum.apply(autonomia, dist[vkey]);
                    if (ce.compare(edge.getWeight(), remainingAutonomy) <= 0) {
                        E s = sum.apply(dist[vkey], edge.getWeight());
                        if (dist[vkeyAdj] == null || ce.compare(dist[vkeyAdj], s) > 0) {
                            dist[vkeyAdj] = s;
                            pathKeys[vkeyAdj] = vOrig;
                        }
                    }
                }
            }

            E minDist = null;
            vOrig = null;

            for (V vert : g.vertices()) {
                int i = g.key(vert);
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) < 0)) {
                    minDist = dist[i];
                    vOrig = vert;
                }
            }
        }
    }


    /**
     * Shortest-path between two vertices
     *
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        V[] pathKeys = (V[]) new Object[numVerts];
        E[] dist = (E[]) new Object[numVerts];
        initializePathDist(numVerts, pathKeys, dist);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        E lengthPath = dist[g.key(vDest)];

        if (lengthPath != null) {
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            return lengthPath;
        }

        return null;
    }

    /**
     * Shortest-path between two vertices
     *
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPathWithAutonomy(Graph<V, E> g, E autonomy, V vOrig, V vDest,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    LinkedList<V> shortPath) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        V[] pathKeys = (V[]) new Object[numVerts];
        E[] dist = (E[]) new Object[numVerts];
        initializePathDist(numVerts, pathKeys, dist);

        shortestPathDijkstraWithAutonomy(g, autonomy, vOrig, ce, sum, zero, visited, pathKeys, dist);

        E lengthPath = dist[g.key(vDest)];

        if (lengthPath != null) {
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            return lengthPath;
        }

        return null;
    }

    public static <V, E> void initializePathDist(int numVerts, V[] pathKeys, E[] dist) {
        for (int i = 0; i < numVerts; i++) {
            pathKeys[i] = null;
            dist[i] = null;
        }
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param g     graph
     * @param vOrig start vertex
     * @param ce    comparator between elements of type E
     * @param sum   sum two elements of type E
     * @param zero  neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if (!g.validVertex(vOrig)) {
            return false;
        }
        paths.clear();
        dists.clear();
        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices];
        V[] pathKeys = (V[]) new Object[numVertices];
        E[] dist = (E[]) new Object[numVertices];
        initializePathDist(numVertices, pathKeys, dist);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < numVertices; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (V vDist : g.vertices()) {
            int i = g.key(vDist);
            if (dist[i] != null) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDist, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
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
                                       V[] pathKeys, LinkedList<V> path) {

        if (vOrig.equals(vDest))
            path.push(vDest);
        else {
            path.push(vDest);
            int keyVDest = g.key(vDest);
            vDest = pathKeys[keyVDest];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    /**
     * Calculates the betweenness centrality for each vertex in the graph using Brandes' algorithm.
     * Betweenness centrality measures the extent to which a vertex lies on the shortest paths
     * between other vertices in the graph.
     *
     * @param graph The graph for which to calculate betweenness centrality.
     * @param <V>   The type of vertex in the graph.
     * @param <E>   The type of edge in the graph.
     * @return A map where each vertex is associated with its betweenness centrality value.
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


    // following Brandes' algorithm to calculate the betweeness/centrality of an edge with BFS
    public static <V, E> Map<Map<Edge<V, E>, Double>, Set<Set<V>>> edgeBetweennessCentrality(Graph<V, E> graph, List<V> vertexes) {

        // Vertice -> (Adjacente, Betweenness)
        Map<V, Map<V, Double>> edgeBetweenness = new HashMap<>();

        // Vertice -> (Adjacente, 0.0)
        for (V v : vertexes) {
            Map<V, Double> adjacentMap = new HashMap<>();
            for (V adj : graph.adjVertices(v)) {
                adjacentMap.put(adj, 0.0);
            }
            edgeBetweenness.put(v, adjacentMap);
        }

        // set com as comunidades que passam a existir
        Set<Set<V>> comRes = new HashSet<>();
        // valores default para o score do vertice e da edge
        List<Double> nodeScoreValuesDefault = new ArrayList<>();
        nodeScoreValuesDefault.add(0.0);
        nodeScoreValuesDefault.add(1.0);


        // first we select a node v to start, and perform bfs to assign node score and edge credits
        // where node_score contains {node: [node_score, edge_credit]}
        for (V vertex : graph.vertices()) {
            Set<V> visited = new HashSet<>();
            List<V> src = new ArrayList<>();
            src.add(vertex);
            Map<V, List<Double>> nodeScore = new HashMap<>();
            nodeScore.put(vertex, new ArrayList<>(nodeScoreValuesDefault));
            nodeScore.get(vertex).set(0, nodeScore.get(vertex).get(0) + 1.0);
            List<Map<V, List<V>>> edgePath = new ArrayList<>();
            Map<V, List<V>> curLevelEdge = new HashMap<>();


            // calcular o score dos vértices
            while (true) {
                visited.addAll(src);
                Set<V> nextSrc = new HashSet<>();
                curLevelEdge.clear();

                for (V node : src) {
                    List<V> destinations = graph.adjVertices(node).stream().toList();
                    for (V nextNode : destinations) {
                        if (!visited.contains(nextNode)) {
                            nextSrc.add(nextNode);
                            if (!nodeScore.containsKey(nextNode)) {
                                nodeScore.put(nextNode, new ArrayList<>(nodeScoreValuesDefault));
                            }
                            double scoreNode = nodeScore.get(node).get(0);
                            nodeScore.get(nextNode).set(0, nodeScore.get(nextNode).get(0) + scoreNode);
                            // check this
                            curLevelEdge.computeIfAbsent(nextNode, k -> new ArrayList<>()).add(node);
                        }
                    }
                }

                //alocacao de comunidades
                if (nextSrc.isEmpty()) {
                    HashSet<V> set = new HashSet<>();
                    if (!graph.vertices().contains(vertex)) {
                        set.add(vertex);
                        comRes.add(set);
                    } else {
                        set.addAll(visited);
                        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! suposed to sort them
                        comRes.add(set);
                    }
                    break;
                } else {
                    edgePath.add((curLevelEdge));
                    src = new ArrayList<>(nextSrc);
                }
            }

            // next, we compute the betweenness value and the edge credit to the next node
            for (int i = edgePath.size() - 1; i >= 0; i--) {
                Map<V, List<V>> levelNodes = edgePath.get(i);

                for (V vNode : levelNodes.keySet()) {
                    Map<V, Double> edgeMap = edgeBetweenness.getOrDefault(vNode, new HashMap<>());
                    List<V> edges = levelNodes.get(vNode);
                    for (V nextNode : edges) {
                        // Edge(vNode, nextNode) -> (1 + Sum(incoming edge credit)) * [(scoreVDest) / (scoreVOrig)]
                        Double btwVal = nodeScore.get(vNode).get(1) * nodeScore.get(nextNode).get(0) / nodeScore.get(vNode).get(0);
                        edgeMap.put(nextNode, btwVal);
                        edgeBetweenness.put(vNode, edgeMap);
                        nodeScore.get(nextNode).set(1, nodeScore.get(nextNode).get(1) + btwVal);
                    }
                }
            }
        }
        Map<Map<Edge<V, E>, Double>, Set<Set<V>>> result = new HashMap<>();
        result.put(scaleBetweenness(graph, edgeBetweenness), comRes);
        return result;
    }

    private static <E, V> Map<Edge<V, E>, Double> scaleBetweenness(Graph<V, E> graph, Map<V, Map<V, Double>> edgeBetweenness) {
        Map<Edge<V, E>, Double> result = new HashMap<>();
        for (V source : edgeBetweenness.keySet()) {
            Map<V, Double> edges = edgeBetweenness.get(source);
            for (V dest : edges.keySet()) {
                Double betweenness = edges.get(dest) / 2;
                result.put(graph.edge(source, dest), betweenness);
            }
        }
        // sort, maybe?
        return result;
    }

    public static <V, E> double computeModularity(MapGraph<V, E> originalGraph, MapGraph<V, E> updateGraph, int numEdges, Set<Set<V>> communities) {
        double res = 0;
        for (Set<V> com : communities) {
            for (Edge<V, E> edge : getCommunityEdges(originalGraph, com)) {
                V vOrig = edge.getVOrig();
                V vDest = edge.getVDest();
                int A = 0;
                if (originalGraph.adjVertices(vOrig).contains(vDest) && originalGraph.adjVertices(vDest).contains(vOrig)) {
                    A = 1;
                }
                int ki = updateGraph.adjVertices(vOrig).size();
                int kj = updateGraph.adjVertices(vDest).size();
                res += A - (ki * kj) / (2.0 * numEdges);
            }
        }
        return res / (2.0 * numEdges);
    }

    private static <E, V> Set<Edge<V, E>> getCommunityEdges(Graph<V, E> originalGraph, Set<V> com) {
        Set<Edge<V, E>> edges = new HashSet<>();
        List<Edge<V, E>> originalGraphEdges = originalGraph.edges().stream().toList();
        for (V source : com) {
            for (V dest : com) {
                Edge<V, E> testEdge = new Edge<>(source, dest);
                if (source != dest && originalGraphEdges.contains(testEdge)) {
                    edges.add(testEdge);
                }
            }
        }
        return edges;
    }


    public static <V, E> void removeHighestBetweennessEdge(MapGraph<V, E> edgeDict, Map<Edge<V, E>, Double> edgeBtwDict) {
        double largestBtwVal = Double.NEGATIVE_INFINITY;

        for (Edge<V, E> edge : edgeBtwDict.keySet()) {
            if (edgeBtwDict.get(edge) > largestBtwVal) {
                largestBtwVal = edgeBtwDict.get(edge);
            }
        }

        //assinalar para remover todas as edges que tenham o valor da betweenness maior
        List<Edge<V, E>> removeEdgeList = new ArrayList<>();
        for (Edge<V, E> edge : edgeBtwDict.keySet()) {
            if (Math.round(edgeBtwDict.get(edge) * 100000) == Math.round(largestBtwVal * 100000)) {
                removeEdgeList.add(edge);
            }
        }

        System.out.println("Remove edges " + removeEdgeList + " with betweenness value around " + Math.round(largestBtwVal));

        // remove betweenness maior + a edge reversa
        for (Edge<V, E> edge : removeEdgeList) {
            V vOrig = edge.getVOrig();
            V vDest = edge.getVDest();
            edgeDict.removeEdge(vOrig, vDest);
            edgeDict.removeEdge(vDest, vOrig);
        }
    }


    public static <V, E> Set<Set<V>> computeOptCommunities(MapGraph<V, E> edgeDict, List<V> vertices, int numClusters, boolean verbose) {

        double maxModularity = Double.NEGATIVE_INFINITY;
        Set<Edge<V, E>> edgeSet = new HashSet<>(edgeDict.edges());

        int edgeCount = edgeSet.size();
        MapGraph<V, E> updateGraph = edgeDict;

        Map<Map<Edge<V, E>, Double>, Set<Set<V>>> btwResult = Algorithms.edgeBetweennessCentrality(updateGraph, vertices);
        Map.Entry<Map<Edge<V, E>, Double>, Set<Set<V>>> firstEntry = btwResult.entrySet().iterator().next();
        Map<Edge<V, E>, Double> edgeBtwDict = firstEntry.getKey();
        Set<Set<V>> currentBestCommunity = new HashSet<>();

        //int iterations = 0; // Track the number of iterations

        while (true) {
            removeHighestBetweennessEdge(updateGraph, edgeBtwDict);

            btwResult = Algorithms.edgeBetweennessCentrality(updateGraph, vertices);
            firstEntry = btwResult.entrySet().iterator().next();
            edgeBtwDict = firstEntry.getKey();

            Set<Set<V>> nextCommunity = firstEntry.getValue();

            double currentModularity = computeModularity(edgeDict, updateGraph, edgeCount, nextCommunity);

            if (currentModularity >= maxModularity) {
                if (verbose) {
                    System.out.println("Update best modularity of community split " + maxModularity + " ---> " + currentModularity + "\n");
                }
                maxModularity = currentModularity;
                currentBestCommunity = nextCommunity;
            } else {
                if (verbose) {
                    System.out.println("Modularity after split = " + currentModularity + ", which is lower than best split " + maxModularity + "\n");
                }
                break;
            }

            // iterations++; // Increment the iteration counter
        }
        return currentBestCommunity;
    }


}