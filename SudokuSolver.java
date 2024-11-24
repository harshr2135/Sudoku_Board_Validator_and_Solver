import java.util.*;

public class SudokuSolver {
    //method to validate the board before solving
    public static boolean isValid(int[][] board){
        //checking rows
        for(int i = 0; i < 9; i++){
            int[] rows = new int[10];
            for(int j = 0; j<9; j++){
                if(board[i][j]!=0 && rows[board[i][j]]==1)
                    return false;
                rows[board[i][j]] = 1;
            }
        }

        //checking columns
        for(int j = 0; j<9; j++){
            int[] cols = new int[10];
            for(int i = 0; i<9; i++){
                if(board[i][j]!=0 && cols[board[i][j]]==1)
                    return false;
                cols[board[i][j]] = 1;
            }
        }

        //checking 3*3 grids
        for(int block = 0; block<9; block++){
            int[] grid = new int[10];
            for(int i = block/3*3; i<block/3*3+3; i++){
                for(int j = block%3*3; j<block%3*3+3; j++){
                    if(board[i][j]!=0 && grid[board[i][j]]==1)
                        return false;
                    grid[board[i][j]] = 1;
                }
            }
        }
        return true;
    }

    //method to print the board
    static void printBoard(int[][] board){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){    
                System.out.print(board[i][j] +" ");
            }
            System.out.println("");
            
        }
    }

    //method to check if it is number at the index is valid to be inserted
    public static boolean numValid(int[][] board, int row, int col, int i) {
        //checking row
        for(int a=0; a<board.length; a++) {
            if(board[a][col]==i) 
                return false;
        }
        //checking column
        for(int b=0; b<board.length; b++) {
            if(board[row][b]==i) 
                return false;
        }
        //checking grid
        int strow = row-(row%3);
        int stcol = col-(col%3);
        
        for(int x=strow; x<strow+3; x++) {
            for(int y=stcol; y<stcol+3; y++) {
                if(board[x][y]==i)
                    return false;
            }
        }
        return true;
    }

    public static boolean solver(int [][] board) {
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                int current = board[i][j];
                if (current == 0) {
                    for (int ch = 1; ch <= 9; ch++) {
                        if (numValid(board, i, j, ch)) {
                            board[i][j] = ch;
                            if (solver(board))
                                return true;
                            board[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int [][] board = new int[9][9];
        System.out.println("Enter the board to be validated and solved: ");     //inputting the board from the user
        for(int i=0; i<9; i++) {
            for(int j = 0; j<9; j++)
                board[i][j] = sc.nextInt();
        }

        System.out.println("The  given board is:");     //displaying the board before validating
        printBoard(board);

        if(isValid(board)){             //validating the board before solving it by callign the isValid method
            solver(board);              //solving  the board by calling the solver method
            System.out.println("Solution for the given board: ");
            printBoard(board);
        }
        else
            System.out.println("Not Valid");
    }
}