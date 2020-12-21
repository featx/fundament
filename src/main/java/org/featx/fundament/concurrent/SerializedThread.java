package org.featx.fundament.concurrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.featx.fundament.IntegerHolder;

public class SerializedThread extends Thread {
    public static final int MAX_INDEX = 9;
    private static final IntegerHolder LAST_INDEX = new IntegerHolder(1);

    private final OutputStream outputStream;

    private final int index;

    private boolean toWrite = true;

    public SerializedThread(int index, OutputStream outputStream) {
        this.index = index;
        this.outputStream = outputStream;
    }

    private boolean notMyTurn() {
        return index == 1 ? (LAST_INDEX.value() != MAX_INDEX) : (LAST_INDEX.value() != index - 1);
    }

    private void waitUntilMyTurnToDo(Runnable runnable) {
        // try-catch for not more code in run()
        try {
            // Fixed format  synchronized(lock) {while(some condition){lock.wait()} do sth... lock.notifyAll();}
            // Both outputStream and LAST_INDEX are OK
            synchronized (LAST_INDEX) {
                while (notMyTurn()) {
                    LAST_INDEX.wait();
                }
                runnable.run();
                LAST_INDEX.notifyAll();
            }
        } catch (InterruptedException interruptedException) {
            this.interrupt();
        }
    }

    @Override
    public void run() {
        while (toWrite) {
            waitUntilMyTurnToDo(() -> {
                try {
                    outputStream.write(String.format("t%d ", LAST_INDEX.getAndSet(index)).getBytes());
                } catch (IOException e) {
                    toWrite = false;
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try (FileOutputStream fileOutputStream = new FileOutputStream("file1")) {
            for (int i = 1; i <= MAX_INDEX; i++) {
                new SerializedThread(i, fileOutputStream).start();
            }
            Thread.sleep(5000L);
        }
    }
}
