public class Main {
    public static void main(String[] args) {

        //from a to b
        //using 2 randomly generated points from 10 (initially)
        //use nearest neighbour as an initial tactic
        //from the nearest possible n points, pick the one closest to B

        Route route = new Route(10);

        route.nearestNeighbour();

    }
}