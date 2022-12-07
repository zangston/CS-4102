/**
 * CS4102 Spring 2022 -- Unit C Programming
 *********************************
 * Collaboration Policy: You are encouraged to collaborate with up to 3 other
 * students, but all work submitted must be your own independently written
 * solution. List the computing ids of all of your collaborators in the comment
 * at the top of your java or python file. Do not seek published or online
 * solutions for any assignments. If you use any published or online resources
 * (which may not include solutions) when completing this assignment, be sure to
 * cite them. Do not submit a solution that you are unable to explain orally to a
 * member of the course staff.
 *********************************
 * Your Computing ID: wyz5rge
 * Collaborators: 
 * Sources: Introduction to Algorithms, Cormen
 **************************************/

public class SeamCarving {
    
    public SeamCarving() 
    {
    	
    }
    
    // created a bunch of data structures so the getSeam() can also access them
    int[][][] classImage;
    
    double[][] energies;
    
    double[][] seamWeights;
    
    double minWeight;
    int minPixel;
    
    
    /**
     * This method is the one you should implement.  It will be called to perform
     * the seam carving.  You may create any additional data structures as fields
     * in this class or write any additional methods you need.
     * 
     * @return the seam's weight
     */
    public double run(int[][][] image) {
        // do the seam carving DO THE SEAM CARVING dO tHE SeEEEAaM CaRVING THANKS FOR TELLING ME
    	
    	/*
    	 * Progress:
    	 * 		1. Implemented energy function
    	 * 		2. Implemented difference function
    	 * 		3. fucking retard. I got the rows and columns mixed up. now I have to go back and fix everything.
    	 * 		4. coordinates in energy + difference functions fixed
    	 * 		5. intialized array of energies for each pixel in the image
    	 * 		6. bascially i hate recusrion, implemented a bottom-up memoziation strategy
    	 * 		7. algorithm works for small inputs, but not for ducks.jpg - out of bounds exception
    	 * 		8. out of bounds exception fixed for ducks.jpg, seam not correct though
    	 * 		9. getSeam() is finding the lowest seamweight for that row, not connecting the pixels
    	 * 		10. 
    	 */
    	
    	classImage = image;
    	
    	double weight = 0.0;
    	
    	// initialize energies array, which stores the energy level for each pixel
    	energies = intializeEnergies(image);
    	
    	// testing to see if energies array is properly populated
    	/*
    	System.out.println("Energies array: ");
    	for(int j = 0; j < energies.length; j++)
    	{
    		for(int i = 0; i < energies[0].length; i++)
    		{
    			System.out.print(energies[j][i] + " ");
    		}
    		System.out.println();
    	}
    	*/
    	
    	System.out.println();
    	
    	// initialize an array of doubles to store the seam weight for each pixel
    	seamWeights = new double[energies.length][energies[0].length];
    	
    	// build a table of seam weights up to the jth row using bottom-up memoization
    	seamWeights = seamWeights(seamWeights, energies);
    	
    	// testing to see if seam weights array is properly populated
    	/*
    	System.out.println("Weights Array:");
    	for(int i = 0; i < seamWeights.length; i++)
    	{
    		for(int j = 0; j < seamWeights[0].length; j++)
    		{
    			System.out.print(seamWeights[i][j] + " ");
    		}
    		System.out.println();
    	}
    	*/
    	
    	minWeight = seamWeights[0][0];
    	for(int i = 0; i < seamWeights[0].length; i++)
    	{
    		if(seamWeights[0][i] < minWeight)
    		{
    			minWeight = seamWeights[0][i];
    			minPixel = i;
    		}

    	}
    	
    	weight = minWeight;
    	
        return weight;
    }
    
    /**
     * Get the seam, in order from top to bottom, where the top-left corner of the
     * image is denoted (0,0).
     * 
     * Since the y-coordinate (row) is determined by the order, only return the x-coordinate
     * 
     * @return the ordered list of x-coordinates (column number) of each pixel in the seam
     */
    public int[] getSeam() 
    {
    	int[] xCoord = new int[classImage.length];
    	
    	// search for lowest weight seam at the top row
    	double min = seamWeights[0][0];
    	int index = 0;
    	for(int i = 0; i < seamWeights[0].length; i++)
    	{
    		if(seamWeights[0][i] < min)
    		{
    			min = seamWeights[0][i];
    			index = i;
    		}
    	}

    	for(int j = 0; j < seamWeights.length; j++)
    	{
    		xCoord[j] = index;
    		if(j != seamWeights.length - 1)
    		{
    			int middle = index;
    			// pixel is left edge
    			if(index == 0)
    			{
    				min = seamWeights[j + 1][middle];
    				if(seamWeights[j + 1][middle + 1] < min)
    					index = middle + 1;
    			}
    			// pixel is right edge
    			else if(index == seamWeights[0].length - 1)
    			{
    				min = seamWeights[j + 1][middle];
    				if(seamWeights[j + 1][middle - 1] < min)
    					index = middle - 1;
    			}
    			// pixel is interior
    			else
    			{
    				min = seamWeights[j + 1][middle];
    				if(seamWeights[j + 1][middle - 1] < min)
    				{
    					min = seamWeights[j + 1][middle - 1];
    					index = middle - 1;
    				}
   					if(seamWeights[j + 1][middle + 1] < min)
   					{
   						min = seamWeights[j + 1][middle + 1];
    					index = middle + 1;
   					}
    			}
    		}
    	}
        
    	return xCoord;
    }
    
    /**
     * helper method that initializes all of the energy levels of the image
     * @param image - triples containing RGB values
     * @return energies - 2D array containing the energy levels of each pixel in the image
     */
    public double[][] intializeEnergies(int[][][] image)
    {
    	double[][] energies = new double[image.length][image[0].length];
    	
    	for(int j = 0; j < energies.length; j++)
    	{
    		for(int i = 0; i < energies[0].length; i++)
    		{
    			energies[j][i] = pixelEnergy(image, j, i);
    		}
    	}
    	
    	return energies;
    }
    
    
    /**
     * returns the energy level of the specified pixel - the average of the differences in RGB intensities from neighboring pixels
     * @param j - row of the pixel - "vertical coordinate"
     * @param i - column of the pixel - "horizontal coordinate"
     * @return energy - energy level of each pixel
     */
    public double pixelEnergy(int[][][] image, int j, int i)
    {
    	/*
    	 * remember to account for edge pixels that don't have 8 neighbors
    	 * also note that (0,0) refers to the top left edge of the image
    	 * 
    	 */
    	
    	// values needed to calculate the average
    	double energy = 0.0;
    	double sum = 0.0;
    	
    	// set boundaries to adjacent pixels for loop, accounting for edge/corner cases
    	int upperBound = j - 1;
    	if(j == 0)
    		upperBound++;
    	
    	int lowerBound = j + 1;
    	if(j == image.length - 1)
    		lowerBound--;
    	
    	int leftBound = i - 1;
    	if(i == 0)
    		leftBound++;
    	
    	int rightBound = i + 1;
    	if(i == image[0].length - 1)
    		rightBound--;
    	
    	/*
    	 * adjacent cells calculated by finding the area of pixels covered by bounds and subtracting by 1 due to center pixel
    	 * 
    	 * area calculation is defined as (rightBound - leftBound + 1) - the +1 accounts for the fact that we also want to count the edge pixel,
    	 * 		otherwise we would only be counting two pixels. Imagine a pixel centered at [1,1]. The right bound is 2, left bound is 0.
    	 * 		2 - 0 = 2, obviously there are three pixels that should be counted.
    	 * 
    	 */
    	int adjacentCells = ((rightBound - leftBound + 1) * (lowerBound - upperBound + 1)) - 1;
    	
    	// nested for-loop to calculate the sum of differences between center pixel and adjacent pixels
    	for(int j2 = upperBound; j2 < lowerBound + 1; j2++)
    	{
    		for(int i2 = leftBound; i2 < rightBound + 1; i2++)
    		{
    			if((i2 != i) || (j2 != j))
    				sum += difference(image, j, i, j2, i2);
    		}
    	}
    	
    	// calculate average different to get energy level, simple average
    	energy = sum / adjacentCells;
    	
    	return energy;
    }
    
    /**
     * Given two pixels, returns the difference in pixel colors as a 3D Euclidean distance
     * @param image - image array of RGB values
     * @param j1 - row of first pixel
     * @param i1 - column of first pixel
     * @param j2 - row of second pixel
     * @param i2 - column of second pixel
     * @return difference - difference in RGB intensities as a 3D Euclidean distance
     */
    public double difference(int[][][] image, int j1, int i1, int j2, int i2)
    {
    	double difference = 0.0;
    	
    	double r_diff = Math.pow((image[j2][i2][0] - image[j1][i1][0]), 2);
    	double g_diff = Math.pow((image[j2][i2][1] - image[j1][i1][1]), 2);
    	double b_diff = Math.pow((image[j2][i2][2] - image[j1][i1][2]), 2);
    	
    	difference = Math.sqrt(r_diff + g_diff + b_diff);
    	
    	return difference;
    }
    

    /**
     * bottom- up dynamic programming implementation of finding weight of seam protruding from pixel
     * @param image - array of RGB triples
     * @param j - row of pixel
     * @param i - column of pixel
     * @return the weight of the current pixel plus the smallest of the recursive calls to the three adjacent pixels below
     */
    public double[][] seamWeights(double[][] seamWeights, double[][] energies)
    {
    	double weights[][] = new double[seamWeights.length][seamWeights[0].length];
    	
    	// initialize bottom row
    	for(int i = 0; i < seamWeights[0].length; i++)
    	{
    		weights[seamWeights.length - 1][i] = energies[seamWeights.length - 1][i];
    	}
    	
    	for(int j = energies.length - 2; j >= 0; j--)	// bottom-up memoization, each cell contains lowest seam weight up to that row
    	{
    		for(int i = 0; i < energies[0].length; i++)
    		{
    			// pixel is on left edge
    			if(i == 0)
    			{
    				// System.out.println("left edge");
    				weights[j][i] = energies[j][i] + Math.min(weights[j + 1][i], weights[j + 1][i + 1]);
    			}
    			// pixel is on right edge
    			else if(i == energies[0].length - 1)
    			{
    				// System.out.println("right edge");
    				weights[j][i] = energies[j][i] + Math.min(weights[j + 1][i - 1], weights[j + 1][i]);
    			}
    			// pixel is interior
    			else
    			{
    				// System.out.println("interior");
    				weights[j][i] = energies[j][i] + min(weights[j + 1][i - 1], weights[j + 1][i], weights[j + 1][i + 1]);
    			}
    		}
    	}
    	
    	return weights;
    }
    

    /**
     * helper method for returning the minimum of three doubles, since Math.double only works on two values
     * @param x - first value
     * @param y - second value
     * @param z - third value
     * @return m - the minimum value of x, y, z
     */
    public double min(double x, double y, double z)
    {
    	double m = 0.0;
    	
    	m = Math.min(Math.min(x, y), z);
    	
    	return m;
    }
    
    
    
    
}