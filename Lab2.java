public class Lab2 {
    
    // Головний метод для запуску обох програм
    public static void main(String[] args) {
        printTree(5);
        printArray(3, 4);
    }

    // Метод для виводу ялинки на екран
    public static void printTree(int levels) {

        // Перевірка на вірно введені дані в перемінну (перевірка на негативні значення та нульові значення)
        if (levels <= 0) {
            System.out.println("Некоректне значення параметру levels.");
            return;
        }
        for (int i = 1; i <= levels; i++) {
            for (int j = 1; j <= levels - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // Метод для створення та виводу двовимірного масиву
    public static void printArray(int rows, int cols) {

        // Перевірка на вірно введені дані в перемінну (перевірка на негативні значення та нульові значення)
        if (rows <= 0 || cols <= 0) {
            System.out.println("Некоректне значення параметрів rows або cols.");
            return;
        }
        int[][] arr = new int[rows][cols];
        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = num;
                num += 3;
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}