/**
 * Sample Mouse Maze Puzzle.
 * The mouse(M) is positioned in a cell of the maze.
 * The mouse sequentially moves to open cells until the mouse reaches the exit cell E.
 * An open cell is marked by 0.
 * A closed cell is marked by 1.
 * by : Aragon, Danielle John P.
 *REQUIRED:
 * Algorithm:
 * 1. Start the program.
 * 2. Define the MouseMaze class.
 * 3. Initialize the maze grid, including the entry and exit cells.
 * 4. Implement a method to display the maze.
 * 5. Implement a method to push unvisited cells onto the stack.
 * 6. Implement the findWayOut() method.
 *   a. Set the current cell to the entry cell.
 *   b. Display the initial state of the maze.
 *   c. Inform the user about the maze and the goal.
 *   d. Wait for the user to press the enter key.
 *   e. Repeat the following steps until the current cell is the exit cell:
 *     e1. Get the row and column indices of the current cell.
 *     e2. If the current cell is the exit cell, display the maze and output a success message. Break the loop.
 *     e3. If the current cell is not the entry cell, mark it as visited and display the maze.
 *     e4. Wait for the user to press the enter key.
 *     e5. Push unvisited neighboring cells onto the stack.
 *     e6. If the stack is empty, display the maze and output a failure message. Return from the method.
 *     e7. Pop a cell from the stack and set it as the new current cell.
 * 7. Implement the main() method.
 *   a. Create an instance of the MouseMaze class.
 *   b. Invoke the findWayOut() method.
 * 8. End the program
 **/

import java.io.*;
import java.lang.*;
import java.util.Stack;
import java.util.Scanner;
public class MouseMaze {
    private char[][] myMaze =

            {{'1','1','1','1','1','1'},
                    {'1','1','0','0','E','1'},
                    {'1','0','0','1','1','1'},
                    {'1','0','0','0','0','1'},
                    {'1','1','0','0','M','1'},
                    {'1','0','1','1','1','1'}};
    private int rows= myMaze.length;
    private int cols= myMaze[0].length;
    private MazeCell currentCell= null;
    private MazeCell exitCell = new MazeCell();
    private MazeCell entryCell= new MazeCell();
    private final char EXIT_MARKER = 'E';
    private final char ENTRY_MARKER = 'M';
    private final char VISITED = '.';
    private final char PASSAGE = '0';
    private final char WALL = '1';
    private Stack<MazeCell> mazeStack = new Stack<MazeCell>();
    private FileReader fileReader;
    private BufferedReader bufferReader;
    private Scanner keyboard = new Scanner(System.in);
    public MouseMaze(){
        boolean foundEntryCell = false;
        boolean foundExitCell = false;
/**
 * Look for the entry cell, the initial location of the mouse
 * */
        for (int row=0; row<myMaze.length && !foundEntryCell; row++)
            for (int col = 0; col < myMaze[row].length && !foundEntryCell; col++){
                if (myMaze[row][col] == 'M'){
                    entryCell.setRow(row);
                    entryCell.setColumn(col);
                    foundEntryCell = true;
                }
            }
/**
 * Look for the exit cell, the cell where the mouse may jump out of the maze
 * */
        for (int row=0; row<myMaze.length && !foundExitCell; row++)
            for (int col = 0; col < myMaze[row].length && !foundExitCell; col++){

                if (myMaze[row][col] == 'E'){
                    exitCell.setRow(row);
                    exitCell.setColumn(col);
                    foundExitCell = true;
                }
            }
    } // end of Maze constructor
    /**
     * Show the maze with the current path followed by the mouse if any
     * */
    private void display(char[][] myMaze) {
        for (int row=0; row < myMaze.length; row++) {
            for (int col = 0; col < myMaze[row].length; col++)
                System.out.print(myMaze[row][col]);
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Puts a cell with the given row and col index into a stack of cells to be visited
     * if the cell is marked as an open cell or exit cell
     * */
    private void pushUnvisited(int row, int col) {
        if (myMaze[row][col] == PASSAGE || myMaze[row][col] == EXIT_MARKER)
            mazeStack.push(new MazeCell(row,col));
    }
    /**
     * Let the mouse finds its way to the exit cell
     * */
    public void findWayOut() throws IOException {
        int row=0;
        int col=0;
// Start from the entry cell, the cell where the mouse is initially placed
        currentCell = entryCell;
        System.out.println();
        display(myMaze);
        System.out.println("The above figure shows a maze where a mouse M is in.");
        System.out.println("The Mouse M should move to exhaustively find the Exit cell E");
        System.out.println("A cell marked 0 is an open cell, a cell marked by 1 is a closed cell");
        System.out.println("Keep pressing the enter key until success or failure is reached.");
        System.out.println("Find the way out.");

        keyboard.nextLine();
        while(!currentCell.equals(exitCell)){
            row = currentCell.getRow();
            col = currentCell.getColumn();
            if (currentCell.sameAs(exitCell)){
                display(myMaze);
                System.out.println("Success! Exit found");
                break;
            }
            if (!currentCell.sameAs(entryCell)) {
                myMaze[row][col] = VISITED;
                display(myMaze);
                System.out.println("Find the way out.");
                keyboard.nextLine();
            }
/**
 * Create a Stack of the cells to be explored following the fixed order
 * up, down, left and right.
 *
 * A cell is included in the cell to be explored only if the cell is an open cell.
 * The pushUnvisited method is written such that it will only put the cell in the Stack if
 * the cell is open
 */
            pushUnvisited(row - 1, col); // note if cell up is open
            pushUnvisited(row + 1, col); // note if cell down is open
            pushUnvisited(row, col - 1); // note if cell at left is open
            pushUnvisited(row, col + 1); // note if cell at right is open

            if (mazeStack.isEmpty()) {
                display(myMaze);
                System.out.println("Failure: Exit cannot be reached");
                return;
            }
            else {
                currentCell = (MazeCell) mazeStack.pop(); // try to move to a reachable cell
            }
        }
    }

    public static void main(String[] args) {
        MouseMaze solver;
        try {
            solver = new MouseMaze();
            solver.findWayOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    } // end of main
} // end of class