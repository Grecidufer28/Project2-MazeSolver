package Maze;

/**
 * Used to test the maze generator and solver for the 
 * program
 * @author Anudeep Gogineni
 * @author Janice Lu
 *
 */
public class MazeTester {
	
	//tester for 4x4 maze
	@org.junit.Test
	public void test4by4()
	{
		Maze maze4 = new Maze(4);
		maze4.generateMaze();
		maze4.printMaze();
		maze4.printBFS();
	//	maze4.printDFS();
	}
	
	
	
	//tester for 5x5 maze
	@org.junit.Test
	public void test5x5()
	{
		Maze maze5 = new Maze(5);
		maze5.generateMaze();
		maze5.printMaze();
		maze5.printBFS();
	//	maze5.printDFS();
	}
	
	
	//tester for 6x6 maze
	@org.junit.Test
	public void test6x6() 
	{
		Maze maze6 = new Maze(6);
		maze6.generateMaze();
		maze6.printMaze();
		maze6.printBFS();
	//	maze6.printDFS();
		
	}
	
	
	//tester for 7x7 maze
	@org.junit.Test
	public void test7x7()
	{
		Maze maze7 = new Maze(7);
		maze7.generateMaze();
		maze7.printMaze();
    	maze7.printBFS();
	//  maze7.printDFS();
	}
	

	//tester for 8x8 maze
	@org.junit.Test
	public void test8x8()
	{
		Maze maze8 = new Maze(8);
		maze8.generateMaze();
		maze8.printMaze();
		maze8.printBFS();
	//	maze8.printDFS();
	}
	
	
	//tester for 10x10 maze
	@org.junit.Test
	public void test10x10() 
	{
		Maze maze10 = new Maze(10);
		maze10.generateMaze();
		maze10.printMaze();
		maze10.printBFS();
	//	maze10.printDFS();
	}
	

}
