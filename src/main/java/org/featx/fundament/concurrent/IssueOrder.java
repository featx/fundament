package org.featx.fundament.concurrent;

import java.text.MessageFormat;

public class IssueOrder {

    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;

    public void init() {
        i = 0;
        j = 0;
        k = 0;
        l = 0;
    }

    private Runnable one = () -> {
        i = 1;
        k = j;
    };

    private Runnable two = () -> {
        j = 1;
        l = i;
    };

    public String invoke() throws Exception {
        Thread t1 = new Thread(one);
        Thread t2 = new Thread(two);
        t1.start(); t2.start();
        t1.join();  t2.join();
        String result = MessageFormat.format("({0}, {1})", k, l);
        System.out.println(result);
        return result;
    }
}
