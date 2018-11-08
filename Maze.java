package Maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
	Random seed;
	int dimensions;
	int matrixSize;
	//keeps track of how many nodes there are in the graph
	Node nodes[];
	//a representation of the maze in a 2-D array
	Node matrix[][];
	
	public void printMaze() 
	{
		int step = 1;
		//print the top row
		for(int i = 0; i < dimensions; i++)
		{
			if(i == 0)
			{
				System.out.print("+ ");
			}
			else
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
				System.out.print("|");
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
							if(matrix[j][k].southWall)
							{
								System.out.print(" |");
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
				int random = (int) (seed.nextDouble() * neighbors.size());
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
	 * generates a maze object to test
	 * @param dimension
	 */
	public Maze(int dimension) {
		this.seed = new Random(dimension);
		this.dimensions = dimension;
		this.matrixSize = dimension*dimension;
		this.nodes = new Node[matrixSize];
		this.matrix = new Node[dimension][dimension];
	}

}
