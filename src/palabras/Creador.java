package palabras;

import java.io.File;
import java.util.Random;
import java.io.FileOutputStream;

public class Creador {

    private Random rng;
    private int largo;
    private File palabra;

    public Creador() {
        this.rng = new Random();
        this.palabra = null;
        this.largo = 0;
    }

    private byte[] makeBytes(int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = (byte)(65 + rng.nextInt(25));
        }
        return bytes;
    }

    private File palabra(int largo) {
        this.palabra = new File(String.format("p_%d.txt", largo));
        this.largo = largo;
        FileOutputStream writer = null;
        try {
            palabra.createNewFile();
            writer = new FileOutputStream(palabra, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            writer.write(makeBytes(largo));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
        return palabra;
    }

    public File palabraPrueba() {
        // largo de 16 a 32
        return palabra(16 + rng.nextInt(16));
    }

    public File palabraCorta() {
        // largo de 1024 a 8192
        return palabra(1024 + rng.nextInt(7168));
    }

    public File palabraLarga() {
        // largo de 1024 a 8192
        return palabra(16384 + rng.nextInt(49152));
    }

    public File getPalabra() {
        return palabra;
    }

    public int getLargo() {
        return largo;
    }
}
