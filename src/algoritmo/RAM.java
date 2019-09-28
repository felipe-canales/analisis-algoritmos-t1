package algoritmo;

import java.io.File;
import java.io.FileInputStream;

public class RAM implements Algoritmo {

    private short[][] res;

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
        short[][] matrix = new short[2][w];
        // 1era fila
        for (int i = 1; i < w; i++) {
            matrix[0][i] = (short)i;
        }
        for (int j = 1; j < h; j++) {
            // 1era columna
            matrix[1][0] = (short)j;
            // Otras celdas
            for (int i = 1; i < w; i++) {
                int izq = matrix[1][i-1] + 1;
                int arr = matrix[0][i] + 1;
                int diag = matrix[0][i-1] + (chars1[j-1] == chars2[i-1]? 0 : 1);
                int min = izq < arr? izq : arr;
                System.out.print(izq);
                System.out.print(arr);
                System.out.println(diag);
                //System.out.println((String.format()));
                matrix[1][i] = (short)(min < diag? min : diag);
            }
            // Cambio de filas
            matrix[0] = matrix[1];
            matrix[1] = new short[w];
        }

        this.res = matrix;
        return matrix[0][largo2];
    }

}
