package algoritmo;

import java.io.File;
import java.io.FileInputStream;

public class RAM implements Algoritmo {

    private short[][] res;

    public RAM () {
        res = new short[2][0];
    }

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
        res = new short[2][w];
        // 1era fila
        for (int i = 1; i < w; i++) {
            res[0][i] = (short)i;
        }
        for (int j = 1; j < h; j++) {
            // 1era columna
            res[1][0] = (short)j;
            // Otras celdas
            for (int i = 1; i < w; i++) {
                int izq = res[1][i-1] + 1;
                int arr = res[0][i] + 1;
                int diag = res[0][i-1] + (chars1[j-1] == chars2[i-1]? 0 : 1);
                int min = izq < arr? izq : arr;
                res[1][i] = (short)(min < diag? min : diag);
            }
            // Cambio de filas
            res[0] = res[1];
            res[1] = new short[w];
        }

        return res[0][largo2];
    }

    public short[] resolverSubtabla(short[] bordeSup, short[] bordeIzq,
                                    byte[] substr1, int largo1,
                                    byte[] substr2, int largo2) {
        short temp = bordeSup[largo2 - 1];
        res[0] = bordeSup;
        res[1] = new short[largo2];
        for (int j = 0; j < largo1; j++) {
            int diag = bordeIzq[j];
            int izq = bordeIzq[j + 1] + 1;
            int arr, min;
            for (int i = 0; i < largo2; i++) {
                diag += (substr1[j] == substr2[i]? 0 : 1);
                arr = res[0][i] + 1;
                min = izq < arr? izq : arr;
                min = min < diag? min : diag;
                res[1][i] = (short)min;
                izq = min + 1;
                diag = arr - 1;
            }
            // Actualizar el borde vertical
            bordeIzq[j] = temp;
            temp = res[1][largo2 - 1];
            // Cambio de filas
            res[0] = res[1];
            res[1] = new short[largo2];
        }
        bordeIzq[largo1] = temp;
        return res[0];
    }

}
