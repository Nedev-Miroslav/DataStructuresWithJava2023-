package implementations;

import java.util.ArrayDeque;
import java.util.Deque;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {

       fillMatrix(this.startRow, this.startCol);



        // решение без рекурсия
//        Deque<int[]> coordinates = new ArrayDeque<>();
//
//        coordinates.add(new int[]{startRow, startCol});
//
//        while (!coordinates.isEmpty()){
//            int[] position = coordinates.poll();
//
//            int row = position[0];
//            int col = position[1];
//
//            this.matrix[row][col] = fillChar;
//
//            if(isInBounds(row + 1, col) && this.matrix[row + 1][col] == toBeReplaced){
//                coordinates.offer(new int[]{row + 1, col});
//            }
//            if(isInBounds(row, col + 1) && this.matrix[row][col + 1] == toBeReplaced){
//                coordinates.offer(new int[]{row, col + 1});
//            }
//            if(isInBounds(row - 1, col) && this.matrix[row - 1][col] == toBeReplaced){
//                coordinates.offer(new int[]{row - 1, col});
//            }
//            if(isInBounds(row, col - 1) && this.matrix[row][col - 1] == toBeReplaced){
//                coordinates.offer(new int[]{row, col - 1});
//            }
//        }


    }

    private boolean isInBounds(int row, int col) {
        return !isOutOfBounds(row, col);
    }

    private void fillMatrix(int row, int col) {

        if(isOutOfBounds(row, col) || this.matrix[row][col] != this.toBeReplaced){
                return;
        }

        this.matrix[row][col] = this.fillChar;

        this.fillMatrix(row + 1, col);
        this.fillMatrix(row, col + 1);
        this.fillMatrix(row - 1, col);
        this.fillMatrix(row, col - 1);


    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= this.matrix.length || col < 0 || col >= this.matrix[row].length;
    }

    public String toOutputString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < this.matrix.length; row++) {
            for (int col = 0; col < this.matrix[row].length; col++) {
                sb.append(this.matrix[row][col]);
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
