package toe.tictac;

import java.util.InputMismatchException;

public class Board {
    private String[][] board;
    private int size;
    private String turn = "X";
    private String winner = null;

    Board(int size) {
        this.size = size;
        board = new String[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                board[i][j] = String.valueOf(i) + String.valueOf(j);
            }
        }
    }

    void playerMove(Board brd, int winSize) {
        int firstIndex, secondIndex;
        String position;
        int numberOfX = 0;
        int numberOfO = 0;
        if (brd.size >= winSize) {
            System.out.println("X's will play first. Please enter a cell number to place X in:");
            while (winner == null) {
                position = InputReader.readStr();
                try {
                    firstIndex = Integer.parseInt("" + position.charAt(0));
                    secondIndex = Integer.parseInt("" + position.charAt(1));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid input number. Please re-enter a number from board cells:");
                    continue;
                }
                try {
                    if (!((firstIndex >= 0 && firstIndex < (brd.size)) && (secondIndex >= 0 && secondIndex < (brd.size)))) {
                        System.out.println("Invalid input number. Please re-enter a number from board cells:");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input number. Please re-enter a number from board cells:");
                    continue;
                }
                if (brd.board[firstIndex][secondIndex].equals(position)) {
                    brd.board[firstIndex][secondIndex] = turn;
                    if (turn.equals("X")) {
                        turn = "O";
                        numberOfX++;
                    } else {
                        turn = "X";
                        numberOfO++;
                    }
                    printBoard(brd);
                    winner = checkWinner(brd, winSize);
                } else {
                    System.out.println("Cell already taken. Re-enter another cell number:");
                    continue;
                }
            }
            if (winner.equals("full")) {
                System.out.println("Board is full! Game over and nobody has won! Thanks for playing.");
                System.out.println("X's move count = " + numberOfX + "\nO's move count = " + numberOfO);
            } else {
                System.out.println("Congratulations! " + winner + " has won! Thanks for playing.");
                System.out.println("X's move count = " + numberOfX + "\nO's move count = " + numberOfO);
            }
        } else {
            System.out.println("Winning condition size must be less than Board size.");
        }
    }

    String checkWinner(Board brd, int winSize) {
        String winSizeX = new String(new char[winSize]).replace("\0", "X");
        String winSizeO = new String(new char[winSize]).replace("\0", "O");
        String row, column, diagonal, secDiagonal;
        if (checkFullness(brd)) {
            return "full";
        }
        diagonal = "";
        secDiagonal = "";
        for (int i = 0; i < brd.size; i++) {
            row = "";
            column = "";
            for (int j = 0; j < brd.size; j++) {
                row += brd.board[i][j];
                column += brd.board[j][i];
                if (i == j) {
                    diagonal += brd.board[i][j];
                }
                if (i == brd.size - j - 1) {
                    secDiagonal += brd.board[i][j];
                }
            }
            if (row.contains(winSizeX) || column.contains(winSizeX) || diagonal.contains(winSizeX) || secDiagonal.contains(winSizeX)) {
                return "X";
            } else if (row.contains(winSizeO) || column.contains(winSizeO) || diagonal.contains(winSizeO) || secDiagonal.contains(winSizeO)) {
                return "O";
            }
        }

        System.out.println(turn + "'s turn. Please enter a cell number to place " + turn + " in: ");
        return null;
    }

    boolean checkFullness(Board brd) {
        for (int i = 0; i < brd.size; i++) {
            for (int j = 0; j < brd.size; j++) {
                if (brd.board[i][j].equals(String.valueOf(i) + String.valueOf(j)))
                    return false;
            }
        }
        return true;
    }

    public void printBoard(Board brd) {
        System.out.print(new String(new char[5 * size]).replace("\0", "-"));
        for (int i = 0; i < size; i++) {
            System.out.println(" ");
            for (int j = 0; j < size; j++) {
                System.out.print(" " + brd.board[i][j] + " |");
            }
        }
        System.out.println(" ");
        System.out.println(new String(new char[5 * size]).replace("\0", "-"));
    }
}
