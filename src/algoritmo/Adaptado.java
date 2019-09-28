package algoritmo;

import io.Reader;
import io.MatrixReaderWriter;

import java.io.File;

public class Adaptado implements Algoritmo {

    private byte[][] bloques;
    private int largoBloque;
    private Reader reader1;
    private Reader reader2;
    private MatrixReaderWriter rwMat;

    // Indices de bloques usados
    private enum Bloques {
        PALABRA_1(0),
        PALABRA_2(1),
        ANTERIOR_U(2), // fila anterior, byte superior
        ANTERIOR_L(3), // fila anterior, byte inferior
        ACTUAL_U(4), // fila actual, byte superior
        ACTUAL_L(5); // fila actual, byte inferior

        public final int index;
        private Bloques(int val) {
            this.index = val;
        }
    }

    public Adaptado(int bloques, int largoBloque) {
        this.bloques = new byte[bloques][largoBloque];
        this.largoBloque = largoBloque;
    }

    private int indiceBloqueEnArchivo(int x, int y, int factor) {
        return 2 * (factor * y + x);
    }

    // Guarda el bloque actual en disco
    private void guardarBloqueMatriz(int x, int y, int factor) {
        int i = indiceBloqueEnArchivo(x, y, factor);
        try {
            rwMat.write(i, bloques[Bloques.ACTUAL_U.index]);
            rwMat.write(i + 1, bloques[Bloques.ACTUAL_L.index]);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    // Carga el bloque anterior y la palabra 2 a memoria
    private void cargarBloqueMatriz (int x, int y, int factor) {
        int i = indiceBloqueEnArchivo(x, y, factor);
        try {
            bloques[Bloques.PALABRA_2.index] = reader2.read(x);
            bloques[Bloques.ANTERIOR_U.index] = rwMat.read(i);
            bloques[Bloques.ANTERIOR_L.index] = rwMat.read(i + 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    // Carga la palabra 1 a memoria
    private void cargarBloquePalabra1 (int nuevo) {
        try {
            bloques[Bloques.PALABRA_1.index] = reader1.read(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    @Override
    public int resolver(File palabra1, int largo1, File palabra2, int largo2) {
        try {
            reader1 = new Reader(palabra1, largoBloque);
            reader2 = new Reader(palabra2, largoBloque);
            rwMat = new MatrixReaderWriter(largoBloque);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(4);
        }
        int bloquesHor = (largo2 / largoBloque) + 1;

        // 1era fila
        for (int i = 0; i < largo2 + 1; i++) {
            guardarEnActual(i % largoBloque, i);
            if ((i + 1) % largoBloque == 0) { // fin del bloque
                guardarBloqueMatriz(i / largoBloque, 0, bloquesHor);
            }
        }
        if ((largo2 + 1) % largoBloque != 0) { // último bloque no se ha guardado
            guardarBloqueMatriz(largo2 / largoBloque, 0, bloquesHor);
        }
        cargarBloquePalabra1(0);

        for (int j = 1; j < largo1 + 1; j++) {
            // 1era columna
            guardarEnActual(0, j);
            cargarBloqueMatriz(0, j - 1, bloquesHor);
            // Otras celdas
            int izq = j + 1;
            int diag = j - 1 + compararLetras(0, (j - 1) % largoBloque);
            int arr, min;
            for (int i = 1; i < largo2 + 1; i++) {
                if (i % largoBloque == 0) { // inicio del bloque
                    cargarBloqueMatriz(i / largoBloque, j - 1, bloquesHor);
                }

                arr = obtenerDeAnterior(i % largoBloque) + 1;
                min = izq < arr? izq : arr;
                min = min < diag? min : diag;
                //System.out.println(String.format("En %d %d\n i: %d, a: %d, d: %d", j, i, izq, arr, diag));
                guardarEnActual(i % largoBloque, min);
                izq = min + 1;
                diag = arr - 1 + compararLetras(i % largoBloque, (j - 1) % largoBloque);

                if ((i + 1) % largoBloque == 0) { // fin del bloque
                    guardarBloqueMatriz(i / largoBloque, j, bloquesHor);
                }
            }

            if ((largo2 + 1) % largoBloque != 0) { // último bloque no se ha guardado
                guardarBloqueMatriz(largo2 / largoBloque, j, bloquesHor);
            }

            if (j % largoBloque == 0) { // fin de bloque palabra 1
                cargarBloquePalabra1(j / largoBloque);
            }
        }
        return obtenerDeActual(largo2 % largoBloque);
    }

    private void guardarEnActual(int x, int val) {
        bloques[Bloques.ACTUAL_U.index][x] = (byte)((val & 65280) >> 8); //byte superior
        bloques[Bloques.ACTUAL_L.index][x] = (byte)(val & 255); //byte inferior
    }

    private int obtenerDeAnterior(int x) {
        return (bloques[Bloques.ANTERIOR_U.index][x] << 8) +
                bloques[Bloques.ANTERIOR_L.index][x];
    }

    private int obtenerDeActual(int x) {
        return (bloques[Bloques.ACTUAL_U.index][x] << 8) +
                bloques[Bloques.ACTUAL_L.index][x];
    }

    private int compararLetras(int x, int y) {
        byte char1 = bloques[Bloques.PALABRA_1.index][y];
        byte char2 = bloques[Bloques.PALABRA_2.index][x];
        System.out.println(String.format("Letras %d = %c, %d = %c", y,char1, x,char2));
        return char1 == char2? 0 : 1;
    }
}
