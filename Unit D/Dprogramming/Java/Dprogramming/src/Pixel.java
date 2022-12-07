/**
 * This class will act as the nodes in the network flow graph that represent the pixels in the image
 * @author Winston Zhang
 *
 */

public class Pixel {
	
	// ultimately useless class, i'm going to use the jgrapht package
	
	public Pixel(int c, int r, boolean bw, boolean tt, boolean t)
	{
		this.column = c;
		this.row = r;
		this.blackWhite = bw;
		this.toTile = tt;
		this.tiled = t;
	}
	
	// location in the image, relative to the upper-left corner, pretty self-explanatory
	public int column;
	public int row;
	
	// i'm going to visualize the pixels as checkerboard boxes for verification purposes, not sure if this will work
	// true = white, false = black
	public boolean blackWhite;
	
	// boolean value storing with this pixel needs to be tiled
	public boolean toTile;
	
	// boolean storing whether this pixel has been tiled or not
	public boolean tiled;
	
	
}
