package org.featx.fundament.concurrent;

public class IssueOne {

    private Integer target;

    private Runnable task = () -> {
        for (int i = 0; i < 1000000; i++) {
//            synchronized (this)
            {
                this.target++;
            }
        }
    };

    public IssueOne() {
        this.target = 0;
    }

    public Integer invoke() throws Exception {
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
        return target;
    }
}
