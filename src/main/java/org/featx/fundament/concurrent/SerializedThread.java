package org.featx.fundament.concurrent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SerializedThread extends Thread {
    private static final int MAX_INDEX = 9;
    private static final AtomicInteger LAST_INDEX = new AtomicInteger(1);

    private final OutputStream outputStream;

    private int index;

    private boolean toWrite = true;

    public void toggle() {
        this.toWrite = !this.toWrite;
    }

    public SerializedThread(int index, OutputStream outputStream) {
        this.index = index;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while(toWrite) {
            synchronized (outputStream) {
                while(index == 1 ? (LAST_INDEX.get() != MAX_INDEX) : (LAST_INDEX.get() != index -1)) {
                    try {
                        outputStream.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    outputStream.write(String.format("t%d ", LAST_INDEX.getAndSet(index)).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputStream.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        List<SerializedThread> list = new ArrayList<>();
        try (FileOutputStream fileOutputStream = new FileOutputStream("file1")) {
            for(int i = 1; i <= MAX_INDEX; i++) {
                list.add(new SerializedThread(i, fileOutputStream));
            }
            list.forEach(Thread::start);
            Thread.sleep(5000L);
            list.forEach(SerializedThread::toggle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
