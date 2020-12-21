package org.featx.fundament.concurrent;

import org.featx.fundament.IntegerHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    private void waitUntilMyTurnToWrite(byte[] bytes) throws IOException, InterruptedException {
        synchronized (outputStream) {
            while (notMyTurn()) {
                outputStream.wait();
            }
            outputStream.write(bytes);
            outputStream.notifyAll();
        }
    }

    @Override
    public void run() {
        while (toWrite) {
            try {
                waitUntilMyTurnToWrite(String.format("t%d ", LAST_INDEX.getAndSet(index)).getBytes());
            } catch (Exception e) {
                toWrite = false;
            }
        }
    }

    public static void main(String[] args) {
        List<SerializedThread> list = new ArrayList<>();
        try (FileOutputStream fileOutputStream = new FileOutputStream("file1")) {
            for (int i = 1; i <= MAX_INDEX; i++) {
                list.add(new SerializedThread(i, fileOutputStream));
            }
            list.forEach(Thread::start);
            Thread.sleep(5000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
