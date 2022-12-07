/**
 * CS4102 Spring 2022 - Unit A Programming 
 *********************************************
 * Collaboration Policy: You are encouraged to collaborate with up to 3 other
 * students, but all work submitted must be your own independently written
 * solution. List the computing ids of all of your collaborators in the
 * comments at the top of each submitted file. Do not share written notes,
 * documents (including Google docs, Overleaf docs, discussion notes, PDFs), or
 * code. Do not seek published or online solutions, including pseudocode, for
 * this assignment. If you use any published or online resources (which may not
 * include solutions) when completing this assignment, be sure to cite them. Do
 * not submit a solution that you are unable to explain orally to a member of
 * the course staff. Any solutions that share similar text/code will be
 * considered in breach of this policy. Please refer to the syllabus for a
 * complete description of the collaboration policy.
 *********************************
 * Your Computing ID: wyz5rge
 * Collaborators: spj6s, jhs8cue, kd5eyn
 * Sources: Introduction to Algorithms, Cormen
 **************************************/
import java.util.List;

public class ClosestPair {

    /**
     * This is the method that should set off the computation
     * of closest pair.  It takes as input a list containing lines of input
     * as strings.  You should parse that input and then call a
     * subroutine that you write to compute the closest pair distances
     * and return those values from this method.
     *
     * @return the distances between the closest pair and second closest pair 
     * with closest at position 0 and second at position 1 
     */
    public double[] compute(List<String> fileData) {
        
        double[] closest = new double[2];
        closest[0] = 0.0; // closest pair distance
        closest[1] = 0.1; // second closest pair distance
        return closest;
    }
    
    public double distance(Point p1, Point p2)
    {
    	double xDist = p2.x - p1.x;
    	double yDist = p2.y - p1.y;
    	double dist = Math.sqrt(xDist * xDist + yDist * yDist);
    	return dist;
    }
    
    public double bruteForce(Point[] list, int n)
    {
    	double min = Double.MAX_VALUE;
    	for(int i = 0; i < n; i++)
    	{
    		for(int j = i + 1; j < n; j++)
    		{
    			if(distance(list[i], list[j]) < min)
    				min = distance(list[i], list[j]);
    		}
    	}
    	return min;
    }
    
    public void xSort(Point[] array)
    {
    	
    }
    
    public void ySort(Point[] array)
    {
    	
    }
    
    public double findClosestInRunway(Point[] runway, int size, double delta)
    {
    	double min = delta;
    	
    	return min;
    }
    
 class Point
 {
	 double x;
	 double y;
	 
	 public Point(double a, double b)
	 {
		 this.x = a;
		 this.y = b;
	 }
 }
}