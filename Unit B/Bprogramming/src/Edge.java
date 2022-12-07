/**
 * This class file was ultimately deemed useless in favor of string arrays
 * @author Winston Zhang, wyz5rge
 * This class represent the edges that connect the hubs/stores together in the supply chain problem
 *
 */
public class Edge {

	public int weight;
	public Node n1;
	public Node n2;
	
	public Edge(int w, Node n1, Node n2)
	{
		this.weight = w;
		this.n1 = n1;
		this.n2 = n2;	// these helper classes seem a little too simple...
	}
}
