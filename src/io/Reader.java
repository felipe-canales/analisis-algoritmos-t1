package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Reader {

    private RandomAccessFile reader;
    int blockSize;

    public Reader(File file, int blockSize) throws FileNotFoundException {
        reader = new RandomAccessFile(file, "r");
        this.blockSize = blockSize;
    }

    public byte[] read(int block) throws IOException {
        byte[] blockData = new byte[this.blockSize];
        // 1st block is 0
        reader.seek(blockSize * block);
        reader.read(blockData);
        return blockData;
    }

    public void close() throws IOException {
        reader.close();
    }
}
