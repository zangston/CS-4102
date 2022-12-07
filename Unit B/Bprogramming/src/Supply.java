/**
 * CS4102 Spring 2022 - Unit B Programming
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
 * Collaborators: 
 * Sources: Introduction to Algorithms, Cormen
 **************************************/
// import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Supply {

    /**
     # This is the method that should set off the computation
     # of the supply chain problem.  It takes as input a list containing lines of input
     # as strings.  You should parse that input and then call a
     # subroutine that you write to compute the total edge-weight sum
     # and return that values from this method
     #
     # @return the total edge-weight sum of a tree that connects nodes as described
     # in the problem statement
     */
	
	/*
	 * notes:
	 * algorithm must use kruskal's MST algorithm
	 * work on the graph in multiple stages/ways
	 * if using heaps, may use library code
	 * implement disjoint sets data structure for kruskal's
	 * path compression not required
	 * make sure edges are correct and connect nodes according to assignment description
	 * 
	 */
	
	// this is the method that is called in the main file, as mentioned above, don't modify this!!!
    public int compute(List<String> fileData) {
        
        int edgeWeightSum = 0;

        // your function to compute the result should be called here
        edgeWeightSum = findTree(fileData);

        return edgeWeightSum;
    }
    
    /**
     * this is the method that actually does the work in finding the tree we're looking for
     * @param fileData
     * @return integer; sum of all edge weights, this is fed into edgeWeightSum in compute(), which is then returned to main
     */
    public int findTree(List<String> fileData)
    {
    	/*
    	 * PROGRAMMING CHECKLIST:
    	 * 1. properly read in the input		Done!
    	 * 		a. process nodes and edges		Done!
    	 * 		b. check edges for validity		Done!
    	 * 		c. split nodes and edges into separate arrays	Done!
    	 * 
    	 * 2. create a minimum spanning tree using Kruskal's MST algorithm and disjoint set data structure
    	 * 		a. sort edges by ascending weight	Done!
    	 * 		b. create disjoint set structure, path compression optional		Done!
    	 * 			i. create makeset method		Done!
    	 * 			ii. create findset method		Done!
    	 * 			iii. create union method		Done!
    	 * 			iv. implement path compression (optional)
    	 * 		c. Kruskal's MST, make sure it conforms to the constraints
    	 * 			constraints: all nodes connected, while maintaining valid connections
    	 * 
    	 * 	3. create a method that returns the total sum of weights
    	 */
    	
    	int sum = 0;
    	
    	/*
    	 * do the input reading here
    	 * mayhaps we can have a void method that we pass the fileData into that instantiates our nodes and edges
    	 * 
    	 * Updates:
    	 * 		1: created an empty readData method, program still not crashing...
    	 *		2: we have a 2D array of nodes and edges together, but we need to eliminate invalid edges and split them
    	 *		3: need to implement a way of checking if a store to store connection is valid, but need to keep track of which 
    	 *			distribution center a store belongs to; otherwise everything else in the data reading method is working
    	 */
    	
    	// create ArrayLists for nodes, edges
    	// I originally started with arrays but I opted for ArrayLists because L is number of possible connections, some of which can be invalid
    	/*
    	 * fuck the arraylists, i don't know how to construct things in a loop
    	ArrayList<Node> nodes = new ArrayList<Node>();
    	ArrayList<Edge> edges = new ArrayList<Edge>();
    	*/
    	
    	// maybe if I just split the filedata into arrays of strings and access them like a 2D array...?
    	
    	String[][] data = readData(fileData);
    	
    	// extracting the integers again so we know how big our new arrays are
    	String[] nL = fileData.get(0).split(" ");
    	int[] nLValues = new int[2];
    	nLValues[0] = Integer.parseInt(nL[0]);
    	nLValues[1] = data.length - nLValues[0];
    	String[][] nodes = new String[nLValues[0]][]; 	
    	String[][] edges = new String[nLValues[1]][];
    	
    	// split data array into node and edge arrays
    	splitData(data, nodes, edges);
    	
    	// sort edge array by lowest weight
    	Arrays.sort(edges, (a, b) -> Integer.compare(Integer.parseInt(a[2]), Integer.parseInt(b[2])));
    	
    	for(int i = 0; i < edges.length; i++)
    	{
    		// System.out.println(edges[i][0] + " " + edges[i][1] + " " + edges[i][2]);	// edges sorted!!!!
    	}
    	
    	
    	int[] sets = makeSet(edges);
    	
    	/*
    	for(int i = 0; i < sets.length; i++)
    	{
    		System.out.println(sets[i]);	// makeset method works!
    	}
    	*/
    	
    	/*
    	int[] testSet = {0, 1, 2, 3, 0};
    	System.out.println(findSet(testSet, 4));	// findSet method works!
    	union(testSet, 0, 2);
    	for(int i = 0; i < testSet.length; i++)
    	{
    		System.out.println(testSet[i]);	// union method works!!!
    	}
    	*/
    	
    	ArrayList<String> nodesInSolution = new ArrayList<String>();
    	int connections = 0;
    	for(int i = 0; i < edges.length; i++)
    	{
    		if(!redundantEdge(edges[i], nodesInSolution) && sets[i] == i)
    		{
    			union(sets, 0, i);
    			connections++;
    			updateSolution(nodesInSolution, edges[i][0], edges[i][1]);
    		}
    		// printSet(sets);
    	}
    	
    	/*
    	int iterate = 0;
    	while(connections < nodes.length - 1)	// this is because a graph isn't fully connected until there are n - 1 edges
    	{
    		if(sets[iterate] != 0)
    		{
    			union(sets, 0, iterate);
    			connections++;
    		}
    		// printSet(sets);
    		iterate++;
    	}
    	*/
    	
    	for(int i =  0; i < edges.length; i++)
    	{
    		if((connections < nodes.length - 1) && sets[i] != 0)
    		{
    			union(sets, 0, i);
    			connections++;
    		}
    	}
    	
    	
    	sum = findSum(sets, edges);
    	
    	return sum;		// i still know how to code, yay! (dumbass...)
    }
    
    /**
     * This method should read in the filedata and correctly populate the lists with nodes and valid connecting edges
     * 
     * @param fileData this is the file that's being read into the program - list of strings
     * 		first line is N number of nodes, L number of possible connections
     * 		next N lines is the nodes, the next L lines is the connections between nodes, not all of which may be valid
     * @param nodes empty ArrayList of nodes that should be populated
     * @param edges empty ArrayList of edges that should be populated, and may or may not be smaller than the specified L in the filedata
     * @return void
     */
    public String[][] readData(List<String> fileData)
    {
    	// System.out.println(fileData);	// i don't know why this is here...
    	
    	/*
    	 * START ALL OVER! i think lines 120-124 were a good start though...
    	// Extract integers from first line of fileData because we need them to iterate through the rest of the list
    	String[] nL = fileData.get(0).split(" ");
    	// System.out.println(nL[0] + " " + nL[1]);	// seems legit
    	int n = Integer.parseInt(nL[0]);
    	int l = Integer.parseInt(nL[1]);
    	
    	// loop for populating node ArrayList
    	for(int i = 1; i < n + 1; i++)
    	{	
    	}
    	*/
    	
    	String[][] data = new String[fileData.size() - 1][];
    	
    	// Extract integers from first line of fileData because we need them to iterate through the rest of the list
    	String[] nL = fileData.get(0).split(" ");
    	int n = Integer.parseInt(nL[0]);
    	int l = Integer.parseInt(nL[1]);
    	String dc = "";
    	
    	// Okay we're just going to split up the strings so that we have names of the nodes isolated
    	for(int i = 0; i < n; i++)
    	{
    		data[i] = fileData.get(i + 1).split(" ");
    		
    		// Identify which distribution center a store belongs to
    		if(data[i][0].startsWith("dc"))
    			dc = data[i][0];
    		if(data[i][0].startsWith("s"))
    			data[i][1] = dc;	// god, this is spaghetti code if I've ever seen it
    		
    		// System.out.println(data[i][0] + " " + data[i][1]);	// we now have our list of nodes B)
    	}
    	
    	// Same deal for the rest of the strings which are edges
    	int remove = 0;
    	for(int i = 0; i < l; i++)
    	{
    		String[] s = fileData.get(i + n + 1).split(" ");
    		String[] invalid = {"invalid", ""};
    		
    		
    		if(validEdge(s, data))
    			data[i + n] = s;
    		else
    		{
    			data[i + n] = invalid;
    			remove++;
    		}
    		// System.out.println(data[i + n][0]);	// we now have our connected edges appended to our list
    	}
    	
    	// System.out.println("DEMARKUS");	// print out some sort of demarcation, thought that "DEMARKUS" was funny
    	// I sure hope the professors don't read my lines that are commented out
    	
    	String[][] processedData = new String[data.length - remove][];

    	int j = 0;
    	for(int i = 0; i < processedData.length; i++)
    	{
    		while(data[j][0].equals("invalid"))
    			j++;
    		processedData[i] = data[j];
    		j++;
    		// System.out.println(processedData[i][0] + " " + processedData[i][1]);	// NOW WE HAVE EXLUDED THE INVALID CONNECTIONS, AT LONG LAST!!!
    	}
    	
    	return processedData;
    }
    
    /**
     * This boolean method is used to determine where an edge being passed in is a valid connection
     * @param s array of strings that represents an edge
     * @return boolean representing whether that edge is valid or not
     */
    public boolean validEdge(String[] s, String[][] data)
    {
    	// save the distribution center that a store belongs to as a string
    	String dc1 = "";
    	String dc2 = "";
    	
    	// search data array to find the store node's distribution center
    	for(int i = 0; i < data.length; i++)
    	{
    		if(data[i][0].equals(s[0]))
    		{
    			dc1 = data[i][1];
    			break;
    		}
    		if(data[i][0].equals(s[1]))
    			dc2 = data[i][1];
    	}
    	for(int i = 0; i < data.length; i++)
    	{
    		if(data[i][0].equals(s[1]))
    		{
    			dc2 = data[i][1];
    			break;
    		}
    	}
    	
    	if((s[0].startsWith("p") || s[0].startsWith("r")) && s[1].startsWith("s"))	// connection is invalid if between port/railhub and store
    		return false;
    	else if((s[0].startsWith("s") && s[1].startsWith("s")) && !dc1.equals(dc2))	// connection is invalid if between stores of different dc
    		return false;
    	else
    		return true;
    }
    
    /**
     * This method will separate the processed data into two separate arrays
     * @param data populated array of nodes and edges
     * @param nodes empty array that will be populated with node string arrays
     * @param edges	empty array that will be populated with edge string arrays
     */
    public void splitData(String[][]data, String[][] nodes, String[][] edges)
    {
    	for(int i = 0; i < nodes.length; i++)
    	{
    		nodes[i] = data[i];
    		// System.out.println(nodes[i][0]);	// nodes array successfully populated
    	}
    	
    	for(int i = 0; i < edges.length; i++)
    	{
    		edges[i] = data[i + nodes.length];
    		// System.out.println(edges[i][0]);	// edges array succesfully populated
    	}
    }

    // Disjoint sets data structure related methods
    
    /**
     * This method creates the disjoint set data structure in the form of an array
     * @param edges - array of string arrays that represent edges
     * @return array of integers who represent which set the edge at that index points to
     */
    public int[] makeSet(String[][] edges)
    {
    	int[] sets = new int[edges.length];
    	
    	for(int i = 0; i < sets.length; i++)
    	{
    		sets[i] = i;
    	}
    	
    	return sets;
    }

    /**
     * Given the array of ints from makeSet, find the label that a particular position is pointing to
     * @param set - array of ints that represents our disjoin sets
     * @param i - index of the position we want to examine
     * @return integer that tells us what position is being pointed at by set[i]
     */
    public int findSet(int[] set, int i)
    {
    	int label = i;
    	while(true) 
    	{
    		if(set[label] == label)
    			break;
    		else
    			label = set[label];
    	}
    	return label;
    }
    
    /**
     * Merge two sets together by having them point to the same index
     * @param set - array of ints for our disjoint sets
     * @param i - position we want to merge
     * @param j - other posistion we want to merge
     */
    public void union(int[] set, int i, int j)
    {
    	int label1 = findSet(set, i);
    	int label2 = findSet(set, j);
    	
    	set[label2] = label1;
    }
    
    /**
     * Ideally this boolean method will tell us whether the nodes in a given edge are already included in the MST
     * @param edge - we are checking to see if either of the nodes the edge connects to is already in the MST
     * @param nodes - list of nodes that are in the solution
     * @return - boolean on whether an edge is redundant in the MST
     */
    public boolean redundantEdge(String[] edge, ArrayList<String> nodes)
    {
    	if(nodes.contains(edge[0]) && nodes.contains(edge[1]))
    		return true;
    	return false;
    }
    
    /**
     * Add nodes to the solution list
     * @param nodes
     * @param node1
     * @param node2
     */
    public void updateSolution(ArrayList<String> nodes, String node1, String node2)
    {
    	if(!nodes.contains(node1))
    		nodes.add(node1);
    	if(!nodes.contains(node2))
    		nodes.add(node2);
    }
 
    
    /**
     * This method will return the total weight of the minimum spanning tree
     * @param set - disjoint set data structure - we will use this to see what's in the MST
     * @param edges - list of edges - we will use this to retrieve weights
     * @return - integer sum of total weight of MST
     */
    public int findSum(int[] set, String[][] edges)
    {
    	int sum = 0;
    	
    	for(int i = 0; i < set.length; i++)
    	{
    		if(set[i] == 0)
    			sum += Integer.parseInt(edges[i][2]);
    	}
    	
    	return sum;
    }
    
    /**
     * simple method to print the disjoint set array for testing purposes
     * @param set
     */
    public void printSet(int[] set)
    {
    	String s = "";
    	for(int i = 0; i < set.length; i++)
    	{
    		s += Integer.toString(set[i]);
    		s += " ";
    	}
    	System.out.println(s);
    }
    
    
    
}
