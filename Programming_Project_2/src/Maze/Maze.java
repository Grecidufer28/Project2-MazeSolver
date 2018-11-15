package Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import java.util.Queue;

public class Maze {
	private Random seed;
	int dimensions;
	int matrixSize;
	//keeps track of how many nodes there are in the graph
	Node nodes[];
	//a representation of the maze in a 2-D array
	Node matrix[][];
	
	/**
	 * resets the nodes for solving the maze
	 */
	public void reset()
	{
		//resets the linkedlist representation of the maze 
		for(int i = 0; i < nodes.length; i++)
		{
			nodes[i].startTime = 0;
			nodes[i].endTime = 0;
			nodes[i].value = " ";
			nodes[i].pi = null;
			nodes[i].color = 0;
		}
		
		//resets the 2d array matrix representation of the maze
		for(int i = 0; i < dimensions; i++)
		{
			for(int j = 0; j < dimensions; j++)
			{
				matrix[i][j].startTime = 0;
				matrix[i][j].endTime = 0;
				matrix[i][j].value = " ";
				matrix[i][j].pi = null;
				matrix[i][j].color = 0;
			}
		}
	}
	
	/**
	 * function to determine if there is a path between the two nodes
	 * @param first - initial node to compare 
	 * @param second - the second node to compare 
	 * @return TRUE if there is a path between the two nodes that is not the path that they
	 * 			came from
	 *         else FALSE
	 */
	public static boolean foundPath(Node first, Node second) 
	{
		if((!first.eastWall) && (!second.westWall) && (first.column == second.column-1)
				&&(first.row == second.row))
		{
			return true;
		}
		else if((!first.westWall) && (!second.eastWall) && (first.column == second.column+1)
				&&(first.row == second.row)) 
		{
			return true;
		}
		else if((!first.northWall) && (!second.southWall) && (first.column == second.column)
				&&(first.row == second.row+1))
		{
			return true;
		}
		else if((!first.southWall) && (!second.northWall) && (first.column == second.column)
				&&(first.row == second.row-1))
		{
			return true;
		}
		else
		{
			return false;
		}
			
		
	}
	
	/**
	 * Solves the maze using Breadth First Search
	 */
	public void solveBFSMaze() 
	{
		reset();
		Node current = matrix[0][0];
		Queue<Node> queue = new LinkedList<>();
		queue.add(current);
		//continues to explore the maze until queue reaches the last node
		while(!queue.isEmpty() && (!current.equals(matrix[dimensions-1][dimensions-1])))
		{
			current = queue.remove();
			for(int i = 0; i < current.adjacencyList.size(); i++)
			{
				Node check = current.adjacencyList.get(i);
				if(check.color == 0 && foundPath(current, check))
				{
					check.color = 1;
					check.startTime = current.startTime + 1;
					if(check.startTime == 10)
					{
						check.startTime = 0;
					}
					check.pi = current;
					queue.add(check);
				}
			}
			current.color = 2;
		}
		//while loop finds the solution path for the maze
		current = matrix[dimensions-1][dimensions-1];
		while(current != matrix[0][0]) 
		{
			current.value = "#";
			current = current.pi;
			current.inPath = true;
		}
		matrix[0][0].value = "#";
		matrix[0][0].inPath = true;
	}
	
	/**
	 * Solves the maze using Depth First Search
	 */
	public void solveDFSMaze() {
		reset();
		Node current = matrix[0][0];
		//continues to explore the maze until queue reaches the last node
//		while((!current.equals(matrix[dimensions-1][dimensions-1])))
//		{
//			visitDFS(current);
//		}
		int time = 0;
		visitDFS(current, time);
		//while loop finds the solution path for the maze
		current = matrix[dimensions-1][dimensions-1];
		while(current != matrix[0][0]) 
		{
			current.value = "#";
			current = current.pi;
			current.inPath = true;
		}
		matrix[0][0].value = "#";
		matrix[0][0].inPath = true;
	}
	
	/**
	 * Visits each space in maze using Depth First Search
	 */
	private static void visitDFS(Node current, int time) {
		current.startTime = time%10;
		time++;
		if(current.startTime == 10) {
			current.startTime = 0;
		}
		current.color = 1;
		for(int i = 0; i < current.adjacencyList.size(); i++) {
			Node check = current.adjacencyList.get(i);
			if(check.color == 0 && foundPath(current, check)) {
				check.pi = current;
				visitDFS(check, time);
			}
		}
		current.color = 2;
		time++;
		current.endTime = time;
	}
	
	/**
	 * prints the generated maze without any
	 * solutions or annotations
	 */
	public void printMaze() 
	{
		System.out.println(dimensions + " by " + dimensions + " Maze");
		int step = 1;
		//print the top row
		for(int i = 0; i < dimensions; i++)
		{
			if(i == 0)
			{
				if(!matrix[0][i].northWall)
				{
					System.out.print("+ ");
				}
				else
				{ 
					System.out.print("+-");
				}
			}
			else if(matrix[0][i].northWall)
			{ 
				System.out.print("+-");
			}
		}
		System.out.print("+\n");
		step++;
		for(int j = 0; j < dimensions; j++)
		{
			if(step == 2)
			{
				System.out.print("|"); //outer wall
				for(int k = 0; k < dimensions; k++)
				{
					if(k == dimensions - 1)
					{
						System.out.print(" |");
					}
					else
					{
						if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall))
						{
							System.out.print(" |");
						}
						else
						{
							System.out.print("  ");
						}
					}
					
				}
				System.out.print("\n");
				step++;
			}
			if(step == 3)
			{
				System.out.print("+");
				for(int k = 0; k < dimensions; k++)
				{
					if(k == dimensions - 1)
					{
						if(j == dimensions - 1)
						{
							System.out.print(" +");
						}
						else
						{
							if((matrix[j][k].southWall) && (matrix[j-1][k].northWall))
							{
								System.out.print("-+");
							}
							else
							{
								System.out.print(" +");
							}
						}
					}
					else
					{
						if(j == dimensions - 1)
						{
							if(matrix[j][k].southWall)
							{
								System.out.print("-+");
							}
							else
							{
								System.out.print(" +");
							}
						}
						else  
						{
							if((matrix[j][k].southWall) && (matrix[j+1][k].northWall))
							{
								System.out.print("-+");
							}
							else
							{
								System.out.print(" +");
							}
						}
					}		
				}
				System.out.print("\n");
				step--;
			}
		}
		System.out.print("\n");
		
	}
	
	/**
	 * Prints out the BFS solutions for
	 * the maze, including both solution path
	 * and the BFS visit order 
	 */
	public void printBFS() 
	{
		solveBFSMaze();
		printBFSPath();
		printBFSHPath();
	}
	
	/**
	 * Prints out the numbered version of BFS solved maze.
	 * Starts from zero and follow the path of the maze using BFS
	 */
	public void printBFSPath()
	{
		//used to iterate between the two layers of the maze
		int step = 1;
		System.out.print("BFS:\n");
		//print the top row
		for(int i = 0; i < dimensions; i++)
		{
			if(i == 0) //the first row top layer
			{
				if(!matrix[0][i].northWall)
				{
					System.out.print("+ ");
				}
				else
				{ 
					System.out.print("+-");
				}
			}
			else if(matrix[0][i].northWall)
			{ 
				System.out.print("+-");
			}
		}
		System.out.print("+\n"); //last plus on the row and new line
		step++;
		/*
		 * the nested for loops print out the maze, separated into two layers
		 * in the step == 2 loop, the inner for loop prints out the node 
		 * representation of the maze
		 * in the step == 3 loop, the inner for loop prints out the lines 
		 * of the maze 
		 */
		for(int j = 0; j < dimensions; j++)
		{
			if(step == 2)
			{
				System.out.print("|"); //outer wall
				for(int k = 0; k < dimensions; k++)
				{
					if((k == dimensions - 1) && (matrix[j][k].inPath))
					{
						System.out.print(matrix[j][k].startTime + "|");
					}
					else if((k == dimensions - 1) && (!matrix[j][k].inPath))
					{
						if(j == dimensions - 1)
						{
							System.out.print(matrix[j][k].startTime+"|");
						}
						else
						{
							System.out.print(" |");
						}
					}
					else
					{
						if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].inPath))
						{
							System.out.print(matrix[j][k].startTime + "|");
						}
						else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (!matrix[j][k].inPath))
						{
							System.out.print(" |");
						}
						else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].equals(matrix[0][0])) && (matrix[j][k].inPath))
						{
							System.out.print(matrix[j][k].startTime+ " ");
						}
						else if((!matrix[j][k].eastWall) && (!matrix[j][k+1].westWall) && (matrix[j][k].inPath))
						{
							System.out.print(matrix[j][k].startTime + " ");
						}
						else
						{
							System.out.print("  ");
						}
					}
					
				}
				System.out.print("\n");
				step++;
			}
			if(step == 3)
			{
				System.out.print("+");
				for(int k = 0; k < dimensions; k++)
				{
					if(k == dimensions - 1)
					{
						if(j == dimensions - 1) //the exit
						{
							System.out.print(" +");
						}
						else
						{
							if((matrix[j][k].southWall) && (matrix[j-1][k].northWall))
							{
								System.out.print("-+");
							}
							else
							{
								System.out.print(" +");
							}
						}
					}
					else
					{
						if(j == dimensions - 1)
						{
							if(matrix[j][k].southWall) //bottom row
							{
								System.out.print("-+");
							}
						}
						else  
						{
							if((matrix[j][k].southWall) && (matrix[j+1][k].northWall))
							{
								System.out.print("-+");
							}
							else
							{
								System.out.print(" +");
							}
						}
					}		
				}
				System.out.print("\n");
				step--;
			}
		}
		System.out.print("\n");
	}
	
	/**
	 * Prints out the solution path of BFS solved maze using
	 * # to mark the path
	 */
	public void printBFSHPath()
	{
		//used to iterate between the two layers of the maze
		int step = 1;
		//print the top row
		for(int i = 0; i < dimensions; i++)
		{
			if(i == 0) //the first row top layer
			{
				if(!matrix[0][i].northWall)
				{
					System.out.print("+#");
				}
				else
				{ 
					System.out.print("+-");
				}
			}
			else if(matrix[0][i].northWall)
			{ 
				System.out.print("+-");
			}
		}
		System.out.print("+\n"); //prints the last corner before a new line
		step++;
		/*
		 * the nested for loops print out the maze, separated into two layers
		 * in the step == 2 loop, the inner for loop prints out the node 
		 * representation of the maze
		 * in the step == 3 loop, the inner for loop prints out the lines 
		 * of the maze 
		 */
		for(int j = 0; j < dimensions; j++)
		{
			if(step == 2)
			{
				System.out.print("|"); //outer wall
				for(int k = 0; k < dimensions; k++)
				{
					if((k == dimensions - 1) && (matrix[j][k].inPath))
					{
						System.out.print(matrix[j][k].value + "|");
					}
					else if((k == dimensions - 1) && (!matrix[j][k].inPath))
					{
						if(j == dimensions - 1)
						{
							System.out.print(matrix[j][k].value+"|");
						}
						else
						{
							System.out.print(" |");
						}
					}
					else
					{
						if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].inPath))
						{
							System.out.print(matrix[j][k].value + "|");
						}
						else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (!matrix[j][k].inPath))
						{
							System.out.print(" |");
						}
						else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].equals(matrix[0][0])) && (matrix[j][k].inPath))
						{
							System.out.print(matrix[j][k].value + matrix[j][k].value);
						}
						else if((!matrix[j][k].eastWall) && (!matrix[j][k+1].westWall) && (matrix[j][k].inPath))
						{
							System.out.print(matrix[j][k].value + matrix[j][k].value);
						}
						else
						{
							System.out.print("  ");
						}
					}
					
				}
				System.out.print("\n");
				step++;
			}
			if(step == 3)
			{
				System.out.print("+");
				for(int k = 0; k < dimensions; k++)
				{
					if(k == dimensions - 1)
					{
						if(j == dimensions - 1) //the exit
						{
							System.out.print(matrix[j][k].value + "+");
						}
						else
						{
							if((matrix[j][k].southWall) && (matrix[j-1][k].northWall))
							{
								System.out.print("-+");
							}
							else
							{
								if(matrix[j][k].inPath)
								{
									System.out.print("#+");
								}
								else
								{
									System.out.print(" +");
								}
							}
						}
					}
					else
					{
						if(j == dimensions - 1)
						{
							if(matrix[j][k].southWall) //bottom row
							{
								System.out.print("-+");
							}
						}
						else  
						{
							if((matrix[j][k].southWall) && (matrix[j+1][k].northWall))
							{
								System.out.print("-+");
							}
							else
							{
								if(matrix[j][k].inPath)
								{
									System.out.print("#+");
								}
								else
								{
									System.out.print(" +");
								}
							}
						}
					}		
				}
				System.out.print("\n");
				step--;
			}
		}
		System.out.print("\n");
	}
	
	/**
	 * Prints out the DFS solutions for
	 * the maze, including both solution path
	 * and the DFS visit order 
	 */
	public void printDFS() { //not done
		solveDFSMaze();
		printDFSPath();
		printDFSHPath();
	}
	
	/**
	 * Prints out the numbered version of DFS solved maze.
	 * Starts from zero and follow the path of the maze using DFS
	 */
	public void printDFSPath() { //not done
		//used to iterate between the two layers of the maze
				int step = 1;
				System.out.print("DFS:\n");
				//print the top row
				for(int i = 0; i < dimensions; i++)
				{
					if(i == 0) //the first row top layer
					{
						if(!matrix[0][i].northWall)
						{
							System.out.print("+ ");
						}
						else
						{ 
							System.out.print("+-");
						}
					}
					else if(matrix[0][i].northWall)
					{ 
						System.out.print("+-");
					}
				}
				System.out.print("+\n"); //last plus on the row and new line
				step++;
				/*
				 * the nested for loops print out the maze, separated into two layers
				 * in the step == 2 loop, the inner for loop prints out the node 
				 * representation of the maze
				 * in the step == 3 loop, the inner for loop prints out the lines 
				 * of the maze 
				 */
				for(int j = 0; j < dimensions; j++)
				{
					if(step == 2)
					{
						System.out.print("|"); //outer wall
						for(int k = 0; k < dimensions; k++)
						{
							if((k == dimensions - 1) && (matrix[j][k].inPath))
							{
								System.out.print(matrix[j][k].startTime + "|");
							}
							else if((k == dimensions - 1) && (!matrix[j][k].inPath))
							{
								if(j == dimensions - 1)
								{
									System.out.print(matrix[j][k].startTime+"|");
								}
								else
								{
									System.out.print(" |");
								}
							}
							else
							{
								if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].inPath))
								{
									System.out.print(matrix[j][k].startTime + "|");
								}
								else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (!matrix[j][k].inPath))
								{
									System.out.print(" |");
								}
								else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].equals(matrix[0][0])) && (matrix[j][k].inPath))
								{
									System.out.print(matrix[j][k].startTime+ " ");
								}
								else if((!matrix[j][k].eastWall) && (!matrix[j][k+1].westWall) && (matrix[j][k].inPath))
								{
									System.out.print(matrix[j][k].startTime + " ");
								}
								else
								{
									System.out.print("  ");
								}
							}
							
						}
						System.out.print("\n");
						step++;
					}
					if(step == 3)
					{
						System.out.print("+");
						for(int k = 0; k < dimensions; k++)
						{
							if(k == dimensions - 1)
							{
								if(j == dimensions - 1) //the exit
								{
									System.out.print(" +");
								}
								else
								{
									if((matrix[j][k].southWall) && (matrix[j-1][k].northWall))
									{
										System.out.print("-+");
									}
									else
									{
										System.out.print(" +");
									}
								}
							}
							else
							{
								if(j == dimensions - 1)
								{
									if(matrix[j][k].southWall) //bottom row
									{
										System.out.print("-+");
									}
								}
								else  
								{
									if((matrix[j][k].southWall) && (matrix[j+1][k].northWall))
									{
										System.out.print("-+");
									}
									else
									{
										System.out.print(" +");
									}
								}
							}		
						}
						System.out.print("\n");
						step--;
					}
				}
				System.out.print("\n");
	}
	
	/**
	 * Prints out the solution path of DFS solved maze using
	 * # to mark the path
	 */
	public void printDFSHPath() { //not done
		//used to iterate between the two layers of the maze
				int step = 1;
				//print the top row
				for(int i = 0; i < dimensions; i++)
				{
					if(i == 0) //the first row top layer
					{
						if(!matrix[0][i].northWall)
						{
							System.out.print("+#");
						}
						else
						{ 
							System.out.print("+-");
						}
					}
					else if(matrix[0][i].northWall)
					{ 
						System.out.print("+-");
					}
				}
				System.out.print("+\n"); //prints the last corner before a new line
				step++;
				/*
				 * the nested for loops print out the maze, separated into two layers
				 * in the step == 2 loop, the inner for loop prints out the node 
				 * representation of the maze
				 * in the step == 3 loop, the inner for loop prints out the lines 
				 * of the maze 
				 */
				for(int j = 0; j < dimensions; j++)
				{
					if(step == 2)
					{
						System.out.print("|"); //outer wall
						for(int k = 0; k < dimensions; k++)
						{
							if((k == dimensions - 1) && (matrix[j][k].inPath))
							{
								System.out.print(matrix[j][k].value + "|");
							}
							else if((k == dimensions - 1) && (!matrix[j][k].inPath))
							{
								if(j == dimensions - 1)
								{
									System.out.print(matrix[j][k].value+"|");
								}
								else
								{
									System.out.print(" |");
								}
							}
							else
							{
								if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].inPath))
								{
									System.out.print(matrix[j][k].value + "|");
								}
								else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (!matrix[j][k].inPath))
								{
									System.out.print(" |");
								}
								else if((matrix[j][k].eastWall) && (matrix[j][k+1].westWall) && (matrix[j][k].equals(matrix[0][0])) && (matrix[j][k].inPath))
								{
									System.out.print(matrix[j][k].value + matrix[j][k].value);
								}
								else if((!matrix[j][k].eastWall) && (!matrix[j][k+1].westWall) && (matrix[j][k].inPath))
								{
									System.out.print(matrix[j][k].value + matrix[j][k].value);
								}
								else
								{
									System.out.print("  ");
								}
							}
							
						}
						System.out.print("\n");
						step++;
					}
					if(step == 3)
					{
						System.out.print("+");
						for(int k = 0; k < dimensions; k++)
						{
							if(k == dimensions - 1)
							{
								if(j == dimensions - 1) //the exit
								{
									System.out.print(matrix[j][k].value + "+");
								}
								else
								{
									if((matrix[j][k].southWall) && (matrix[j-1][k].northWall))
									{
										System.out.print("-+");
									}
									else
									{
										if(matrix[j][k].inPath)
										{
											System.out.print("#+");
										}
										else
										{
											System.out.print(" +");
										}
									}
								}
							}
							else
							{
								if(j == dimensions - 1)
								{
									if(matrix[j][k].southWall) //bottom row
									{
										System.out.print("-+");
									}
								}
								else  
								{
									if((matrix[j][k].southWall) && (matrix[j+1][k].northWall))
									{
										System.out.print("-+");
									}
									else
									{
										if(matrix[j][k].inPath)
										{
											System.out.print("#+");
										}
										else
										{
											System.out.print(" +");
										}
									}
								}
							}		
						}
						System.out.print("\n");
						step--;
					}
				}
				System.out.print("\n");
	}
	
	/**
	 * Creates a perfect maze using Depth First Search algorithm
	 */
	public void generateMaze() 
	{
		populateNodes();
		int totalCells = matrixSize;
		Node currentCell = matrix[0][0];
		int visitedCells = 1;
		Stack<Node> cellStack = new Stack<Node>();
		
		while(visitedCells < totalCells)
		{
			ArrayList<Node> neighbors = checkNeighbors(currentCell);
			
			if(neighbors.size() > 0)
			{
				int random = (int) (myRandom() * neighbors.size());
				Node selectedCell = neighbors.get(random);
				knockDownWall(currentCell, selectedCell);
				cellStack.push(currentCell);
				currentCell = selectedCell;
				visitedCells++;
			}
			else
			{
				currentCell = cellStack.pop();
			}
		}
		matrix[0][0].northWall = false;
		matrix[dimensions-1][dimensions-1].southWall = false; 
		return;
	}
	
	/**
	 * populates all the nodes with basic information
	 */
	public void populateNodes() 
	{
		//used for filling the linked list (LList)
		int i = 0;
		//populates the adjacency matrix and LList with empty nodes
		for(int row = 0; row < this.dimensions; row++) 
		{
			for(int column = 0; column < this.dimensions; column++)
			{
				Node space = new Node(row, column);
				matrix[row][column] = space;
				nodes[i] = space;
				i++;
			}
		}
		setNeighbors();
	}
	
	/**
	 * sets the adjacency lists for all the nodes
	 */
	public void setNeighbors() 
	{
		for(int row = 0; row < dimensions; row++)
		{
			for(int column = 0; column < dimensions; column++)
			{
				if(row == 0)
				{
					if(column == 0) //top left corner
					{
						matrix[row][column].adjacencyList.add(matrix[row+1][column]); //below
						matrix[row][column].adjacencyList.add(matrix[row][column+1]); //to the right
					}
					else if(column == (dimensions-1)) //top right corner
					{
						matrix[row][column].adjacencyList.add(matrix[row+1][column]); //below
						matrix[row][column].adjacencyList.add(matrix[row][column-1]); //to the left
						
					}
					else //all others
					{
						matrix[row][column].adjacencyList.add(matrix[row+1][column]); //below
						matrix[row][column].adjacencyList.add(matrix[row][column+1]); //to the right
						matrix[row][column].adjacencyList.add(matrix[row][column-1]); //to the left
					}
				}
				else if(row == (dimensions-1))
				{
					if(column == 0) //bottom left corner
					{
						matrix[row][column].adjacencyList.add(matrix[row-1][column]); //above
						matrix[row][column].adjacencyList.add(matrix[row][column+1]); //to the right
					}
					else if(column == (dimensions-1)) //bottom right corner
					{
						matrix[row][column].adjacencyList.add(matrix[row-1][column]); //above
						matrix[row][column].adjacencyList.add(matrix[row][column-1]); //to the left
						
					}
					else //all others
					{
						matrix[row][column].adjacencyList.add(matrix[row-1][column]); //above
						matrix[row][column].adjacencyList.add(matrix[row][column+1]); //to the right
						matrix[row][column].adjacencyList.add(matrix[row][column-1]); //to the left
					}
				}
				else //middle rows
				{
					if(column == 0)
					{
						matrix[row][column].adjacencyList.add(matrix[row-1][column]); //above
						matrix[row][column].adjacencyList.add(matrix[row+1][column]); //below
						matrix[row][column].adjacencyList.add(matrix[row][column+1]); //to the right
					}
					else if(column == (dimensions-1))
					{
						matrix[row][column].adjacencyList.add(matrix[row-1][column]); //above
						matrix[row][column].adjacencyList.add(matrix[row+1][column]); //below
						matrix[row][column].adjacencyList.add(matrix[row][column-1]); //to the left
					}
					else
					{
						matrix[row][column].adjacencyList.add(matrix[row-1][column]); //above
						matrix[row][column].adjacencyList.add(matrix[row+1][column]); //below
						matrix[row][column].adjacencyList.add(matrix[row][column+1]); //to the right
						matrix[row][column].adjacencyList.add(matrix[row][column-1]); //to the left
					}
				}
			}
		}
	}

	/**
	 * Checks all neighbors to see if they have all walls up AKA
	 * they have not been explored
	 * @param cell - cell to check unexplored neighbors
	 * @return an arraylist with all unexplored neighbors
	 */
	public ArrayList<Node> checkNeighbors(Node cell)
	{
		ArrayList<Node> unexplored = new ArrayList<>();
		for(int i = 0; i < cell.adjacencyList.size(); i++)
		{
			Node neighbor = cell.adjacencyList.get(i);
			if((neighbor != null) && (neighbor.northWall) && 
					(neighbor.westWall) && (neighbor.eastWall) && (neighbor.southWall))
			{
				unexplored.add(neighbor);
			}
		}
		return unexplored;
	}
	
	/**
	 * breaks down wall between current cell and chosen cell
	 * @param n1 current cell
	 * @param n2 chosen cell 
	 */
	public void knockDownWall(Node n1, Node n2) 
	{
		if(n1.row == 0)
		{
			if(n1.column == 0)
			{
				if(matrix[n1.row+1][n1.column].equals(n2)) // n2 below n1
				{
					n1.southWall = false;
					n2.northWall = false;
				}
				else if(matrix[n1.row][n1.column+1].equals(n2)) // n2 to the right of n1
				{
					n1.eastWall = false;
					n2.westWall = false;
				}
			}
			else if(n1.column == dimensions)
			{
				if(matrix[n1.row+1][n1.column].equals(n2)) // n2 below n1
				{
					n1.southWall = false;
					n2.northWall = false;
				}
				else if(matrix[n1.row][n1.column-1].equals(n2)) // n2 to the left of n1
				{
					n1.westWall = false;
					n2.eastWall = false;
				}
			}
			else
			{
				if(matrix[n1.row+1][n1.column].equals(n2)) // n2 below n1
				{
					n1.southWall = false;
					n2.northWall = false;
				}
				else if(matrix[n1.row][n1.column-1].equals(n2)) // n2 to the left of n1
				{
					n1.westWall = false;
					n2.eastWall = false;
				}
				else if(matrix[n1.row][n1.column+1].equals(n2)) // n2 to the right of n1
				{
					n1.eastWall = false;
					n2.westWall = false;
				}
			}
		}
		else if(n1.row == (dimensions-1))
		{
			if(n1.column == 0)
			{
				if(matrix[n1.row-1][n1.column].equals(n2)) // n2 above n1
				{
					n1.northWall = false;
					n2.southWall = false;
				}
				else if(matrix[n1.row][n1.column+1].equals(n2)) // n2 to the right of n1
				{
					n1.eastWall = false;
					n2.westWall = false;
				}
			}
			else if(n1.column == (dimensions-1))
			{
				if(matrix[n1.row-1][n1.column].equals(n2)) // n2 above n1
				{
					n1.northWall = false;
					n2.southWall = false;
				}
				else if(matrix[n1.row][n1.column-1].equals(n2)) // n2 to the left of n1
				{
					n1.westWall = false;
					n2.eastWall = false;
				}
			}
			else
			{
				if(matrix[n1.row-1][n1.column].equals(n2)) // n2 above n1
				{
					n1.northWall = false;
					n2.southWall = false;
				}
				else if(matrix[n1.row][n1.column-1].equals(n2)) // n2 to the left of n1
				{
					n1.westWall = false;
					n2.eastWall = false;
				}
				if(matrix[n1.row][n1.column+1].equals(n2)) // n2 to the right of n1
				{
					n1.eastWall = false;
					n2.westWall = false;
				}
			}
		}
		else
		{
			if(n1.column == 0)
			{
				if(matrix[n1.row-1][n1.column].equals(n2)) // n2 above n1
				{
					n1.northWall = false;
					n2.southWall = false;
				}
				else if(matrix[n1.row+1][n1.column].equals(n2)) // n2 below n1
				{
					n1.southWall = false;
					n2.northWall = false;
				}
				else if(matrix[n1.row][n1.column+1].equals(n2)) // n2 to the right of n1
				{
					n1.eastWall = false;
					n2.westWall = false;
				}
			}
			else if(n1.column == dimensions)
			{
				if(matrix[n1.row-1][n1.column].equals(n2)) // n2 above n1
				{
					n1.northWall = false;
					n2.southWall = false;
				}
				else if(matrix[n1.row+1][n1.column].equals(n2)) // n2 below n1
				{
					n1.southWall = false;
					n2.northWall = false;
				}
				else if(matrix[n1.row][n1.column-1].equals(n2)) // n2 to the left of n1
				{
					n1.westWall = false;
					n2.eastWall = false;
				}
			}
			else
			{
				if(matrix[n1.row-1][n1.column].equals(n2)) // n2 above n1
				{
					n1.northWall = false;
					n2.southWall = false;
				}
				else if(matrix[n1.row+1][n1.column].equals(n2)) // n2 below n1
				{
					n1.southWall = false;
					n2.northWall = false;
				}
				else if(matrix[n1.row][n1.column-1].equals(n2)) // n2 to the left of n1
				{
					n1.westWall = false;
					n2.eastWall = false;
				}
				else if(matrix[n1.row][n1.column+1].equals(n2)) // n2 to the right of n1
				{
					n1.eastWall = false;
					n2.westWall = false;
				}
			}
		}
	}

	/**
	 * generates a random double to use for selecting
	 * an adjacent node
	 * @return a random double
	 */
	public double myRandom()
	{
		return seed.nextDouble();
	}
	
	/**
	 * generates a maze object to test
	 * @param dimension
	 */
	public Maze(int dimension) {
		seed = new java.util.Random(0);
		this.dimensions = dimension;
		this.matrixSize = dimension*dimension;
		this.nodes = new Node[matrixSize];
		this.matrix = new Node[dimension][dimension];
	}

}
