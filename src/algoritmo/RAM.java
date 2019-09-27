package algoritmo;

import java.io.File;
import java.io.FileInputStream;
import java.io.OptionalDataException;

public class RAM implements Algoritmo {

    private byte[] getBytes(File palabra) {
        byte[] chars = {0};
        try {
            FileInputStream read1 = new FileInputStream(palabra);
            chars = read1.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
        return chars;
    }

    @Override
    public int resolver(File palabra1, File palabra2) {
        byte[] chars1 = getBytes(palabra1);
        byte[] chars2 = getBytes(palabra2);
        int w = chars2.length + 1;
        int h = chars1.length + 1;
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
                int diag = matrix[j-1][i-1] + 1 * (chars1[j-1] == chars2[i-1]? 0 : 1);
                int min = izq < arr? izq : arr;
                matrix[j][i] = min < diag? min : diag;
            }
        }/*
        for (int y = 0; y < h; y++) {
            String s = "> ";
            for (int x = 0; x < w; x++) {
                s += String.valueOf(matrix[y][x]) + " ";
            }
            System.out.println(s + "<");
        }*/
        return matrix[h-1][w-1];
    }

}
