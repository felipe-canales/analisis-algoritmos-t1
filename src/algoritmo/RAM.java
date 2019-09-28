package algoritmo;

import java.io.File;
import java.io.FileInputStream;

public class RAM implements Algoritmo {

    private int[][] res;

    private byte[] getBytes(File palabra) {
        byte[] chars = {0};
        try {
            FileInputStream read = new FileInputStream(palabra);
            chars = read.readAllBytes();
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
        return chars;
    }

    @Override
    public int resolver(File palabra1, int largo1, File palabra2, int largo2) {
        byte[] chars1 = getBytes(palabra1);
        byte[] chars2 = getBytes(palabra2);
        int w = largo2 + 1;
        int h = largo1 + 1;
        int[][] matrix = new int[h][w];
        // 1era fila
        for (int i = 1; i < w; i++) {
            matrix[0][i] = i;
        }
        for (int j = 1; j < h; j++) {
            // 1era columna
            matrix[j][0] = j;
            // Otras celdas
            for (int i = 1; i < w; i++) {
                int izq = matrix[j-1][i] + 1;
                int arr = matrix[j][i-1] + 1;
                int diag = matrix[j-1][i-1] + (chars1[j-1] == chars2[i-1]? 0 : 1);
                int min = izq < arr? izq : arr;
                matrix[j][i] = min < diag? min : diag;
            }
        }

        this.res = matrix;
        return matrix[largo2][largo1];
    }

    public int imprimirMatriz(File palabra1, int largo1, File palabra2, int largo2) {
        int r = resolver(palabra1, largo1, palabra2, largo2);
        for (int[] re : res) {
            String s = "> ";
            for (int x = 0; x < res[0].length; x++) {
                s += String.valueOf(re[x]) + " ";
            }
            System.out.println(s + "<");
        }
        return r;
    }

}
