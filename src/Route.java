import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.sqrt;
import java.lang.Math;

public class Route {
    //populates the arraylist unvisitedPoints
    private ArrayList<Point> unvisitedPoints;// = new ArrayList<>();
    private ArrayList<Point> visitedPoints;// = new ArrayList<>();
    private ArrayList<Point> routeToDraw;// = new ArrayList<>();
    private double totalDistance;
    private Point startingPoint;
    private Point endingPoint;
    private int numOfPoints;

    public Route(int numOfPoints){
        this.numOfPoints = numOfPoints;

        unvisitedPoints = new ArrayList<>();
        visitedPoints = new ArrayList<>();
        routeToDraw = new ArrayList<>();

        //generates numOfPoints randomly
        for (int i = 0; i< numOfPoints; i++) {
            Point point = generateRandomPoint();
            unvisitedPoints.add(point);
            //System.out.println(point);
        }

        //sets A and B
        startingPoint = unvisitedPoints.get(0);
        endingPoint = unvisitedPoints.get(unvisitedPoints.size()-1);

        visitedPoints.add(startingPoint);
        unvisitedPoints.removeFirst();

    }

    //draw the points on the grid
    //NOW WE USE SWING
    //Swing window =  IGNORE GRAPHICS FOR NOW
    //window.main();

    public void nearestNeighbour() {
        //given a point on the grid, find the N closest points to it, go to
        // the closest of those points, repeat, accounting for the previous point

        for (int j = 0; j < numOfPoints-2; j++) {
            Point candidatePoint;
            Point bestCandidate;
            Point currentPoint = new Point(visitedPoints.get(j).getX(), visitedPoints.get(j).getY());
            ArrayList<double[]> allPointsDistances = new ArrayList<double[]>();
            double[][] fiveClosest = new double[5][];

            double currentToCandidateDistance;
            double candidateToBDistance;

            //finds the n shortest distances between current point and candidate point, and those points distances to B, whichever has shortest dist, GOTO
            for (int i = 0; i < unvisitedPoints.size(); i++) {
                candidatePoint = new Point(unvisitedPoints.get(i).getX(), unvisitedPoints.get(i).getY());
                //distance between current point and candidate point//
                currentToCandidateDistance = distanceBetween(currentPoint, candidatePoint);
                candidateToBDistance = distanceBetween(candidatePoint, endingPoint);

                allPointsDistances.add(new double[]{i,currentToCandidateDistance, candidateToBDistance});
            }
            //sort allPoints, take the 5 closest, not including self

            ArrayList<double[]> sortedAllPointsDistances = bubbleSort(allPointsDistances);

            //adds the 5 closest candidate points to fiveClosest
            for (int i = 0; i < 5; i++){
                fiveClosest[i] = sortedAllPointsDistances.get(i);
                System.out.println(Arrays.toString(fiveClosest[i]));
                //System.out.printf("%05d", (int)Math.floor(sortedAllPointsDistances.get(i)[1]*100)/100);
                //Math.floor(value * 100) / 100;
            }

            System.out.println();

            //pick the point which has the lowest distance to B of the 5 closest points
            double[] closestToB = bubbleSort2(fiveClosest)[0];

            //now next closest point has been found, so it moves onto that as the starter
            visitedPoints.add(unvisitedPoints.get((int) closestToB[0]));
            unvisitedPoints.remove((int)closestToB[0]);

            System.out.println(" "+ currentPoint.getX()+", "+currentPoint.getY() +" to " +unvisitedPoints.get((int) closestToB[0]).getX() +", " +unvisitedPoints.get((int) closestToB[0]).getY() + " ");
            // TO DO LATER routeToDraw.add(new currentPoint, closestCandidate});

            //totalDistance = totalDistance + shortestDistance;
        }
    }

    public double distanceBetween(Point coord1, Point coord2){
        Integer x1 = coord1.getX();
        Integer y1 = coord1.getY();
        Integer x2 = coord2.getX();
        Integer y2 = coord2.getY();

        int distanceX = x2-x1;
        int distanceY = y2-y1;

        return sqrt((distanceX*distanceX) +(distanceY*distanceY));
    }


    public Point generateRandomPoint(){
        Random random = new Random();

        Integer randomX = random.nextInt(500);
        Integer randomY = random.nextInt(500);

        return new Point(randomX, randomY);
    }

    public ArrayList<double[]> bubbleSort(ArrayList<double[]> array){
        boolean swapped = true;
        int passCount = 0;
        double[] temp;

        while(swapped){
            swapped = false;

            for (int i=0; i < array.size()-passCount-1; i++){
                //if second value is smaller than the first value, swap
                if (array.get(i)[1] > array.get(i+1)[1]) {
                    temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                    swapped = true;
                }
            }
            passCount++;
        }

        return array;
    }

    public double[][] bubbleSort2(double[][] array){
        boolean swapped = true;
        int passCount = 0;
        double[] temp;

        while(swapped){
            swapped = false;

            for (int i=0; i < array.length-passCount-1; i++){
                //if second value is smaller than the first value, swap
                if (array[i][2] > array[i+1][2]){
                    temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    swapped = true;
                }
            }
            passCount++;
        }

        return array;
    }
}
