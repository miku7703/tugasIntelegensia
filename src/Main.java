// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RealWorldRouting router = new RealWorldRouting();
        String start = "Seattle";
        String goal = "Miami";

        System.out.println("Jalur DFS : " + router.dfs(start, goal));
        System.out.println("Jalur BFS : " + router.bfs(start, goal));
        System.out.println("Jalur UCS  " + router.ucs(start, goal));
    }
}