package Lab3;


import java.util.Scanner;

 class TicTacToe {
    private char[][] board;
    public char currentPlayerMark;

    


    public TicTacToe() {
        board = new char[3][3];
        currentPlayerMark = 'X';
        initializeBoard();
    }

    public void initializeBoard() {
        // Ініціалізуємо ігрове поле
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void printBoard() {
        // Виводимо ігрове поле на екран
        System.out.println("-------------");

        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public boolean isBoardFull() {
        // Перевіряємо, чи заповнене ігрове поле
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

    public boolean checkForWin() {
        // Перевіряємо, чи є переможець
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private boolean checkRowsForWin() {
        // Перевіряємо рядки на наявність переможця
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        // Перевіряємо стовпці на наявність переможця
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        // Перевіряємо діагоналі на наявність переможця
        return ((checkRowCol(board[0][0], board[1][1], board[2][2])) || (checkRowCol(board[0][2], board[1][1], board[2][0])));
    }

    private boolean checkRowCol(char c1, char c2, char c3) {
        // Перевіряємо рядок чи стовпець наявність однакових знаків
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    public boolean placeMark(int row, int col) {
            // Перевіряємо можливість поставити знак і ставимо його
            if ((row >= 0) && (row < 3)) {
                if ((col >= 0) && (col < 3)) {
                    if (board[row][col] == '-') {
                        board[row][col] = currentPlayerMark;
                        return true;
                    }
                }
            }
    
            return false;
        }
    
        public void changePlayer() {
            // Змінюємо поточного гравця
            if (currentPlayerMark == 'X') {
                currentPlayerMark = 'O';
            } else {
                currentPlayerMark = 'X';
            }
        }
    }

    class Player {
        private String name;
    
        public Player(String name) {
            this.name = name;
        }
    
        public String getName() {
            return name;
        }
    
        public int[] makeMove(char currentPlayerMark) {
            // Запитуємо координати для ходу гравця
            Scanner scanner = new Scanner(System.in);
    
            System.out.print(name + ", введите номер строки (от 0 до 2): ");
            int row = scanner.nextInt();
    
            System.out.print(name + ", введите номер столбца (от 0 до 2): ");
            int col = scanner.nextInt();
    
            return new int[] { row, col };
        }
    }

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Створюємо гравців
        System.out.print("Введите имя первого игрока (знак X): ");
        Player player1 = new Player(scanner.nextLine());

        System.out.print("Введите имя второго игрока (знак O): ");
        Player player2 = new Player(scanner.nextLine());

        // Створюємо гру
        TicTacToe game = new TicTacToe();

        // Граємо, поки немає переможця і не заповнено поле
        while (!game.checkForWin() && !game.isBoardFull()) {
            // Поточний гравець робить хід
            game.printBoard();
            Player currentPlayer = (game.currentPlayerMark == 'X') ? player1 : player2;
            int[] move = currentPlayer.makeMove(game.currentPlayerMark);

            // Перевіряємо можливість поставити фігуру та ставимо її
            boolean isValidMove = game.placeMark(move[0], move[1]);
            if (!isValidMove) {
                System.out.println("Некорректный ход. Попробуйте еще раз.");
            } else {
                game.changePlayer();
            }
        }

        // Виводимо результат гри
        game.printBoard();
        if (game.checkForWin()) {
            Player winner = (game.currentPlayerMark == 'X') ? player2 : player1;
            System.out.println("Поздравляем, " + winner.getName() + ", вы победили!");
        } else {
            System.out.println("Ничья!");
        }
    }
}
