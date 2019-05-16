package org.featx.fundament.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class IssueOne {

    private Integer target;

    private ReentrantLock lock;

    private Runnable task = () -> {
        for (int i = 0; i < 1000; i++) {
//            synchronized (this) {
            lock.lock();
            this.target++;
            lock.unlock();
        }
    };

    public IssueOne() {
        this.target = 0;
        this.lock = new ReentrantLock();
    }

    public Integer invoke() throws Exception {
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
        return target;
    }
}
