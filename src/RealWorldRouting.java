
import java.util.*;

public class RealWorldRouting {

    private Map<String, Map<String, Integer>> graph = new HashMap<>();

    public RealWorldRouting() {
        // Inisialisasi graf dengan data nyata (jarak antar kota)
        addEdge("Seattle", "San Francisco", 1306);
        addEdge("Seattle", "Chicago", 1737);
        addEdge("San Francisco", "Los Angeles", 381);
        addEdge("San Francisco", "Chicago", 1858);
        addEdge("Los Angeles", "Houston", 1542);
        addEdge("Los Angeles", "Miami", 2728);
        addEdge("Chicago", "Houston", 937);
        addEdge("Houston", "Miami", 1187);
    }

    public void addEdge(String from, String to, int distance) {
        graph.putIfAbsent(from, new HashMap<>());
        graph.get(from).put(to, distance);
    }

    public List<String> dfs(String start, String goal) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        Map<String, String> parent = new HashMap<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            visited.add(current);

            if (current.equals(goal)) {
                return buildPath(parent, start, goal);
            }

            for (String neighbor : graph.getOrDefault(current, new HashMap<>()).keySet()) {
                if (!visited.contains(neighbor)) {
                    parent.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    public List<String> bfs(String start, String goal) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(goal)) {
                return buildPath(parent, start, goal);
            }

            for (String neighbor : graph.getOrDefault(current, new HashMap<>()).keySet()) {
                if (!visited.contains(neighbor)) {
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    public List<String> ucs(String start, String goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            String current = node.vertex;
            int cost = node.cost;

            if (current.equals(goal)) {
                return buildPath(parent, start, goal);
            }

            if (!visited.contains(current)) {
                visited.add(current);
                for (Map.Entry<String, Integer> neighbor : graph.getOrDefault(current, new HashMap<>()).entrySet()) {
                    String neighborVertex = neighbor.getKey();
                    int neighborCost = neighbor.getValue();

                    if (!visited.contains(neighborVertex)) {
                        parent.put(neighborVertex, current);
                        pq.add(new Node(neighborVertex, cost + neighborCost));
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<String> buildPath(Map<String, String> parent, String start, String goal) {
        List<String> path = new ArrayList<>();
        String current = goal;
        while (!current.equals(start)) {
            path.add(current);
            current = parent.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    private static class Node {
        String vertex;
        int cost;

        public Node(String vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }
}
