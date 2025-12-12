package agent;
/**
 * Represents a node in the binary tree.
 *
 * 
 */

public class TreeNode 
{
	// instance variables
	private int coordinate;
	private int xCoordinates;
	private int yCoordinates;
	
	
	/**
	 * Constructor for objects
	 */
	public TreeNode(int coordinate, int xCoordinates, int yCoordinates) 
	{
		//initialise instance variables
		this.coordinate = coordinate;
		this.xCoordinates = xCoordinates;
		this.yCoordinates = yCoordinates;
		
	}
	
	
	
	/** 
	 * Get the x coordinate
	 * @return x coordinate of current state
	 */
	public int getXCoordinates()
	{
		return xCoordinates;
	}
	
	/** 
	 * Get the y coordinate
	 * @return y coordinate of current state
	 */
	public int getYCoordinates()
	{
		return yCoordinates;
	}
	
	/** 
	 * Get the coordinate
	 * @return coordinate of current state
	 */
	public int getCoordinate()
	{
		return coordinate;
	}
	
	/**
	 *  Set coordinate of node
	 * @param coordinate	 */
	public void setCoordinate(int coordinate)
	{
		this.coordinate = coordinate;
	}
	
	/**
	 * Set x coordinate of node
	 * @param x coordinate
	 */
	public void setXCoordinates(int xCoordinates)
	{
		this.xCoordinates = xCoordinates;
	}
	
	/**
	 *  Set y coordinate of node
	 * @param y coordinate
	 */
	public void setYCoordinates(int yCoordinates)
	{
		this.yCoordinates = yCoordinates;
	}
	
	

	
	
}
