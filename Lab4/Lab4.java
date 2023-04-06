package Lab4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

// Клас для роботи з базою даних
class Db {
    String dbUrl = "jdbc:mysql://localhost:3306/myGame?allowPublicKeyRetrieval=true&useSSL=false";
    String user = "Player1";
    String password = "Pass1";
    Connection con;

    // Конструктор класу, який відкриває з'єднання з базою даних
    public Db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(dbUrl, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Закриваємо з'єднання з базою даних
    public void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Перевіряємо чи користувач з таким логіном та паролем існує
    public boolean isUserExists(String username, String password) {
        try {
            if (con == null) {
                System.out.println("Connection is null");
                return false;
            }
            String query = "SELECT count(*) FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
}

// Клас гри "Хрестики-нолики"
class TicTacToe {
    private char[][] board;
    public char currentPlayerMark;

    // Конструктор класу, який ініціалізує ігрове поле та поточного гравця
    public TicTacToe() {
        board = new char[3][3];
        currentPlayerMark = 'X';
        initializeBoard();
    }

    // Ініціалізуємо ігрове поле
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Виводимо ігрове поле на екран
    public void printBoard() {
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

    // Перевіряємо, чи заповнене ігрове поле
    public boolean isBoardFull() {
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

    // Перевіряємо, чи є переможець
    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    // Перевіряємо рядки на наявність переможця
    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        return false;
    }

    // Перевіряємо стовпці на наявність переможця
    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        return false;
    }

    // Перевіряємо діагоналі на наявність переможця
    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board[0][0], board[1][1], board[2][2])) || (checkRowCol(board[0][2], board[1][1], board[2][0])));
    }

    // Перевіряємо рядок чи стовпець наявність однакових знаків
    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    // Перевіряємо можливість поставити знак і ставимо його
    public boolean placeMark(int row, int col) {
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

    // Змінюємо поточного гравця
    public void changePlayer() {
        if (currentPlayerMark == 'X') {
            currentPlayerMark = 'O';
        } else {
            currentPlayerMark = 'X';
        }
    }
}

// Клас гравця
class Player {
    private String name;

    // Конструктор класу
    public Player(String name) {
        this.name = name;
    }
    // Отримуємо ім'я гравця
    public String getName() {
        return name;
    }

    // Запитуємо координати для ходу гравця
    public int[] makeMove(char currentPlayerMark) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(name + ", введіть номер рядка (від 0 до 2): ");
        int row = scanner.nextInt();

        System.out.print(name + ", введіть номер стовпця (від 0 до 2): ");
        int col = scanner.nextInt();

        return new int[] { row, col };
    }
}

public class Lab4 {
    // Отримуємо гравця з бази даних
    public static Player getPlayer(String prompt, Db db) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String username = scanner.nextLine();

            System.out.print("Введіть пароль для " + username + ": ");
            String password = scanner.nextLine();

            if (db.isUserExists(username, password)) {
                return new Player(username);
            } else {
                System.out.println("Неправильне ім'я користувача або пароль. Будь ласка, спробуйте ще раз.");
            }
        }
    }

    // Головна функція
    public static void main(String[] args) {
        Db db = new Db();
        Player player1 = getPlayer("Введіть ім'я першого гравця (X): ", db);
        Player player2 = getPlayer("Введіть ім'я другого гравця (O): ", db);
        db.close();
    
        // Починаємо гру
        TicTacToe game = new TicTacToe();
    
        // Граємо, поки не буде переможця або поле буде повністю заповнено
        while (!game.checkForWin() && !game.isBoardFull()) {
            game.printBoard();
            Player currentPlayer = (game.currentPlayerMark == 'X') ? player1 : player2;
            int[] move = currentPlayer.makeMove(game.currentPlayerMark);
    
            boolean isValidMove = game.placeMark(move[0], move[1]);
            if (!isValidMove) {
                System.out.println("Неправильний хід. Будь ласка, спробуйте ще раз.");
            } else {
                game.changePlayer();
            }
        }
    
        // Відображаємо результат гри
        game.printBoard();
        if (game.checkForWin()) {
            Player winner = (game.currentPlayerMark == 'X') ? player2 : player1;
            System.out.println("Вітаємо, " + winner.getName() + ", ви перемогли!");
        } else {
            System.out.println("Нічия!");
        }
    }
}