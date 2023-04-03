public class Lab2 {
    
    // Головний метод для запуску обох програм
    public static void main(String[] args) {
        printTree(5);
        printArray(3, 4);
    }

    // Метод для виводу ялинки на екран
    public static void printTree(int levels) {
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