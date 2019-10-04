package algoritmo;

import java.io.File;

public class Adaptado extends AbstractAlgoritmoEnDisco {

    private byte[][] bloques;

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
        super(largoBloque);
        this.bloques = new byte[bloques][largoBloque];
    }

    private int indiceBloqueEnArchivo(int x, int y, int factor) {
        //return 2 * (factor * y + x);
        return 2 * x;
    }

    // Guarda el bloque actual en disco
    private void guardarBloqueMatriz(int x, int y, int factor) {
        int i = indiceBloqueEnArchivo(x, y, factor);
        try {
            getRwMat().write(i, bloques[Bloques.ACTUAL_U.index]);
            getRwMat().write(i + 1, bloques[Bloques.ACTUAL_L.index]);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    // Carga el bloque anterior y la palabra 2 a memoria
    private void cargarBloqueMatriz (int x, int y, int factor) {
        int i = indiceBloqueEnArchivo(x, y, factor);
        try {
            bloques[Bloques.PALABRA_2.index] = getReader2().read(x);
            bloques[Bloques.ANTERIOR_U.index] = getRwMat().read(i);
            bloques[Bloques.ANTERIOR_L.index] = getRwMat().read(i + 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    // Carga la palabra 1 a memoria
    private void cargarBloquePalabra1 (int nuevo) {
        try {
            bloques[Bloques.PALABRA_1.index] = getReader1().read(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    @Override
    public int resolver(File palabra1, int largo1, File palabra2, int largo2) {
        setReaders(palabra1, palabra2);
        int bloquesHor = (largo2 / getLargoBloque()) + 1;

        // 1era fila
        for (int i = 0; i < largo2 + 1; i++) {
            guardarEnActual(i % getLargoBloque(), i);
            if ((i + 1) % getLargoBloque() == 0) { // fin del bloque
                guardarBloqueMatriz(i / getLargoBloque(), 0, bloquesHor);
            }
        }
        if ((largo2 + 1) % getLargoBloque() != 0) { // último bloque no se ha guardado
            guardarBloqueMatriz(largo2 / getLargoBloque(), 0, bloquesHor);
        }
        cargarBloquePalabra1(0);

        for (int j = 1; j < largo1 + 1; j++) {
            // 1era columna
            guardarEnActual(0, j);
            cargarBloqueMatriz(0, j - 1, bloquesHor);
            // Otras celdas
            int izq = j + 1;
            int diag = j - 1 + compararLetras(0, (j - 1) % getLargoBloque());
            int arr, min;
            for (int i = 1; i < largo2 + 1; i++) {
                if (i % getLargoBloque() == 0) { // inicio del bloque
                    cargarBloqueMatriz(i / getLargoBloque(), j - 1, bloquesHor);
                }

                arr = obtenerDeAnterior(i % getLargoBloque()) + 1;
                min = izq < arr? izq : arr;
                min = min < diag? min : diag;
                guardarEnActual(i % getLargoBloque(), min);
                izq = min + 1;
                diag = arr - 1 + compararLetras(i % getLargoBloque(), (j - 1) % getLargoBloque());

                if ((i + 1) % getLargoBloque() == 0) { // fin del bloque
                    guardarBloqueMatriz(i / getLargoBloque(), j, bloquesHor);
                }
            }

            if ((largo2 + 1) % getLargoBloque() != 0) { // último bloque no se ha guardado
                guardarBloqueMatriz(largo2 / getLargoBloque(), j, bloquesHor);
            }

            if (j % getLargoBloque() == 0) { // fin de bloque palabra 1
                cargarBloquePalabra1(j / getLargoBloque());
            }
        }
        return obtenerDeActual(largo2 % getLargoBloque());
    }

    private void guardarEnActual(int x, int val) {
        bloques[Bloques.ACTUAL_U.index][x] = (byte)((val & 65280) >> 8); //byte superior
        bloques[Bloques.ACTUAL_L.index][x] = (byte)(val & 255); //byte inferior
    }

    private int obtenerDeAnterior(int x) {
        int inf = bloques[Bloques.ANTERIOR_L.index][x];
        if (inf < 0) inf += 256;
        int sup = bloques[Bloques.ANTERIOR_U.index][x];
        if (sup < 0) sup += 256;
        return (sup << 8) + inf;
    }

    private int obtenerDeActual(int x) {
        int inf = bloques[Bloques.ACTUAL_L.index][x];
        if (inf < 0) inf += 256;
        int sup = bloques[Bloques.ACTUAL_U.index][x];
        if (sup < 0) sup += 256;
        return (sup << 8) + inf;
    }

    private int compararLetras(int x, int y) {
        byte char1 = bloques[Bloques.PALABRA_1.index][y];
        byte char2 = bloques[Bloques.PALABRA_2.index][x];
        return char1 == char2? 0 : 1;
    }
}
