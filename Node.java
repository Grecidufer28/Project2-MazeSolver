package Maze;

import java.util.LinkedList;

/**
 * this class is for each individual node in
 * the maze
 * @author Anudeep Gogineni
 * @author Janice Lu 
 *
 */
public class Node {
	//holds the links between nodes
	LinkedList<Node> adjacencyList; 
	int row;
	int column;
	/*
	 * startTime used for both BFS and DFS
	 * endTime used for only DFS
	 */
	int startTime;
	int endTime;
	/* used for holding values for printing to screen (i.g. '#', ' ', or a
	 * numerical value for BFS/DFS
	 */
	String value;
	//parent of the node, used for DFS and BFS
	Node pi;
	//white = 0, grey = 1, black = 2
	int color;
	/* if TRUE, then the wall exists
	 * if FALSE, the wall does not exist
	 */
	boolean northWall = true;
	boolean southWall = true;
	boolean eastWall = true;
	boolean westWall = true;
	
	/**
	 * constructor for the vertex class
	 * @param row - the row in which the node resides in
	 * @param column - the column in which the column resides in
	 */
	public Node(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjacencyList = new LinkedList<Node>();
		this.startTime = 0;
		this.endTime = 0;
		this.value = "";
		this.pi = null;
		this.color = 0;
	}
	
	/**
	 * toString function to show adjacency list of node
	 */
	public String toString() 
	{
		String result = "";
		LinkedList<Node> placeHolder = adjacencyList;
		result += placeHolder.size();
		return result;
	}

}
