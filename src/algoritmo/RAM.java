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
        short[] izq = new short[largo1 + 1];
        short[] arr = new short[largo2];
        for (int i = 1; i < largo1 + 1; i++) {
            izq[i] = (short)i;
        }
        for (int i = 0; i < largo2; i++) {
            arr[i] = (short)(i + 1);
        }
        resolverSubtabla(arr, izq, chars1, largo1, chars2, largo2);
        return aInt(res[0][largo2 - 1]);
    }

    public short[] resolverSubtabla(short[] bordeSup, short[] bordeIzq,
                                    byte[] substr1, int largo1,
                                    byte[] substr2, int largo2) {
        short temp = bordeSup[largo2 - 1];
        res[0] = bordeSup;
        res[1] = new short[largo2];
        for (int j = 0; j < largo1; j++) {
            int diag = aInt(bordeIzq[j]);
            int izq = aInt(bordeIzq[j + 1]) + 1;
            int arr, min;
            for (int i = 0; i < largo2; i++) {
                diag += (substr1[j] == substr2[i]? 0 : 1);
                arr = aInt(res[0][i]) + 1;
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

    private int aInt(short x) {
        int r = x;
        if (r < 0) {
            r += 65536;
        }
        return r;
    }

}
