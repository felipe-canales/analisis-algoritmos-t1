package algoritmo;

import io.MatrixReaderWriter;
import io.Reader;

import java.io.File;

abstract class AbstractAlgoritmoEnDisco implements Algoritmo {

    private int largoBloque;
    private Reader reader1;
    private Reader reader2;
    private MatrixReaderWriter rwMat;

    AbstractAlgoritmoEnDisco(int largoBloque) {
        this.largoBloque = largoBloque;
    }

    int getLargoBloque() {
        return largoBloque;
    }

    Reader getReader1() {
        return reader1;
    }

    Reader getReader2() {
        return reader2;
    }

    MatrixReaderWriter getRwMat() {
        return rwMat;
    }

    void setReaders(File palabra1, File palabra2) {
        try {
            reader1 = new Reader(palabra1, largoBloque);
            reader2 = new Reader(palabra2, largoBloque);
            rwMat = new MatrixReaderWriter(largoBloque);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(4);
        }
    }

}
