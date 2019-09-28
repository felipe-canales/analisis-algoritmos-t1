package io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ReaderWriter {

    //private File file;
    private RandomAccessFile rw;
    private int blockSize;

    public ReaderWriter(int blockSize) {
        File file = new File("mat.txt");
        try {
            file.createNewFile();
            this.rw = new RandomAccessFile(file, "rwd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.blockSize = blockSize;
    }

    public byte[] read(int block) throws IOException {
        byte[] blockData = new byte[this.blockSize];
        rw.seek(block * blockSize);
        rw.read(blockData);
        return blockData;
    }

    public void write(int block, byte[] blockData) throws IOException {
        rw.seek(block * blockSize);
        rw.write(blockData);
    }

    public void append(byte[] blockData) throws IOException {
        rw.seek(rw.length());
        rw.write(blockData);
    }

    public void close() throws  IOException {
        rw.close();
    }

}
