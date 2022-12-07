/**
 * CS4102 Spring 2022 -- Unit D Programming
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
 * Collaborators: N/A
 * Sources: Introduction to Algorithms, Cormen
 * 			Discrete Mathematics, Reza Zadeh, Standord University
 * 			
 **************************************/

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.*;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.interfaces.FlowAlgorithm;
import org.jgrapht.alg.interfaces.FlowAlgorithm.FlowImpl;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.interfaces.MatchingAlgorithm.MatchingImpl;
import org.jgrapht.alg.interfaces.MaximumFlowAlgorithm;
import org.jgrapht.alg.matching.MaximumWeightBipartiteMatching;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.traverse.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class TilingDino {

    /**
     * This is the method that should set off the computation
     * of tiling dino.  It takes as input a list lines of input
     * as strings.  You should parse that input, find a tiling,
     * and return a list of strings representing the tiling
     *
     * @return the list of strings representing the tiling
     */
    public List<String> compute(List<String> fileLines) 
    {
    	List<String> result = new ArrayList<>();
        
    	/*
    	 * I am going to take a chekerboard approach, assigning pixels we want to tile to be
    	 * either white or black, then finding a bipartite matching based off the flow newtwork
    	 * created by that
    	 */
    	
    	int pixels = 0;
    	int rows = fileLines.size();
    	int columns = fileLines.get(0).length();
    	
    	// DefaultDirectedGraph instead of DefaultDirectedWeightGraph because default edges are treated as having a weight of 1
    	Graph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    	
    	// initialize source and sink nodes
    	g.addVertex("source");
    	g.addVertex("sink");
    	
    	// nested for-loop that populates the graph by traversing the input "grid"
    	for(int r = 0; r < rows; r++)
    	{
    		for(int c = 0; c < columns; c++)
    		{
    			if(fileLines.get(r).charAt(c) == '#')
    			{	
    				pixels++;
    				
    				/*
    				if(r % 2 == 0)
    				{
    					if(c % 2 == 0)
    					{
    						g.addVertex("w " + Integer.toString(c) + " " + Integer.toString(r));
    						// add edge between source node and node representing "white" pixel
    						g.addEdge("source", "w " + Integer.toString(c) + " " + Integer.toString(r));
    					}
    					else
    					{
    						g.addVertex("b " + Integer.toString(c) + " " + Integer.toString(r));
    						// add edge between "black" pixel node and sink node
    						g.addEdge("b " + Integer.toString(c) + " " + Integer.toString(r), "sink");
    					}
    				}
    				else
    				{
    					if(c % 2 == 0)
    					{
    						g.addVertex("b " + Integer.toString(c) + " " + Integer.toString(r));
    						g.addEdge("b " + Integer.toString(c) + " " + Integer.toString(r), "sink");
    					}
    					else
    					{
    						g.addVertex("w " + Integer.toString(c) + " " + Integer.toString(r));
    						g.addEdge("source", "w " + Integer.toString(c) + " " + Integer.toString(r));
    					}
    				}
    				*/
    				
    				// the following code block is the same as above, just in fewer lines
    				if(r % 2 == c % 2)
    				{
    					// instatiate the vertices with c first to stay consistent with the assignment description
    					g.addVertex("w " + Integer.toString(c) + " " + Integer.toString(r));
    					// add edge between source node and node representing "white" pixel
						g.addEdge("source", "w " + Integer.toString(c) + " " + Integer.toString(r));
    				}
    				else
    				{
    					g.addVertex("b " + Integer.toString(c) + " " + Integer.toString(r));
    					// add edge between "black" pixel node and sink node
						g.addEdge("b " + Integer.toString(c) + " " + Integer.toString(r), "sink");
    				}
    			}
    		}
    	}

    	/*
    	 * return impossible if there aren't an even number of pixels - obviously no perfect bartite matching
    	 * can tell this works as intended - test1 returns impossible and test2 does not
    	 */
    	if(pixels % 2 != 0)
    	{
    		result.add("impossible");
    		return result;
    	}
    	
    	/*
    	 * traverse input agian to instatiate edges between black and white edges
    	 * i tried to do all of this within the same nested for-loop but it was hard to do that 
    	 * when some nodes weren't created yet
    	 */
    	for(int r = 0; r < rows; r++)
    	{
    		for(int c = 0; c < columns; c++)
    		{
    			if(fileLines.get(r).charAt(c) == '#')
    			{
    				/*
    				 *  since we're iterating left-right, up-down, we only need to care about
    				 *  the pixels below and to the right of the current pixel
    				 */
    				if(c + 1 < columns && fileLines.get(r).charAt(c + 1) == '#')
    				{
    					if(r % 2 == c % 2)
    						g.addEdge("w " + Integer.toString(c) + " " + Integer.toString(r) , "b " + Integer.toString(c + 1) + " " + Integer.toString(r));
    					else
    						g.addEdge("w " + Integer.toString(c + 1) + " " + Integer.toString(r) , "b " + Integer.toString(c) + " " + Integer.toString(r));
    				}
    				if(r + 1 < rows && fileLines.get(r + 1).charAt(c) == '#')
    				{
    					if(r % 2 == c % 2)
    						g.addEdge("w " + Integer.toString(c) + " " + Integer.toString(r) , "b " + Integer.toString(c) + " " + Integer.toString(r + 1));
    					else
    						g.addEdge("w " + Integer.toString(c) + " " + Integer.toString(r + 1) , "b " + Integer.toString(c) + " " + Integer.toString(r));
    				}
    			}
    		}
    	}
    	
    	/*
    	 * the algorithm only reaches this point if the number of pixels is even,
    	 * thus the number of pairs must be the number of pixels divided by two
    	 */
    	int pairs = pixels / 2;
    	
    	
    	/*
    	 * create set of all edges in the bipartite graph,
    	 * declare matching algorithm class, set a new Set structure to the getEdges method
    	 */
    	/*
    	Set<DefaultEdge> edges = g.edgeSet();
    	
    	
    	
		MatchingAlgorithm.MatchingImpl<String, DefaultEdge> match = new MatchingImpl<String, DefaultEdge>(g, edges, pairs);
		Set<DefaultEdge> matching = match.getEdges();
		*/
		/*
		for(DefaultEdge e : matching) 
		{
			System.out.println(e.toString());
		}
		*/
		
		/*
		 * print loop to see if we got the right edges
		 * i think it works??
		 * okay yeah this definintely works, it output the source for all of the expected nodes
		 * now time to figure out how to convert all of these to strings...		
		for(DefaultEdge e : matching) 
		{
			System.out.println("source: " + g.getEdgeSource(e) + "; target: " + g.getEdgeTarget(e));
		}
		*/
		
		/*
		// we have the correct edges, but it also includes edges connecting to source and sink nodes, we need to remove those
		ArrayList<String> list = new ArrayList<String>();
		
		for(DefaultEdge e : matching)
		{
			String s = e.toString();
			if(!s.contains("source") && !s.contains("sink"))
			{
				list.add(s);
			}
		}
		
		// System.out.println(list);
		
		ArrayList<String> list2 = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++)
		{
			list2.add(list.get(i).replaceAll("\\D", " "));
		}
		
		// System.out.println(list2);
		
		ArrayList<String> list3 = new ArrayList<String>();
		for(int i = 0; i < list2.size(); i++)
		{
			list3.add(list2.get(i).replaceAll("\\s+", " "));
		}
				
		for(int i = 0; i < list3.size(); i++)
		{
			System.out.println(list3.get(i));;
		}
		*/

    	EdmondsKarpMFImpl<String, DefaultWeightedEdge> maxFlow = new EdmondsKarpMFImpl<String, DefaultWeightedEdge>(g);
    	System.out.println(maxFlow.getMaximumFlow("source", "sink"));	
		
        return result;
    }   
}
