package org.featx.fundament.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IssueOrder {

    private int i = 1;

    private boolean enable = false;

    private Integer loadValue() throws Exception {
        Thread.sleep(1);
        return 11;
    }

    private Callable<Integer> changeValue = () -> {
        i = loadValue();

        enable = true;

        return -1;
    };
    private Callable<Integer> check = () -> enable ? i : 0;


    public Integer invoke() throws Exception {
        List<Callable<Integer>> list = new ArrayList<>();
        list.add(changeValue);
        list.add(check);
        for(Future<Integer> f: Executors.newFixedThreadPool(2).invokeAll(list)) {
            Integer r = f.get();
            System.out.println(r);
            if(r != -1) {
                return r;
            }
        }
        return 0;
    }
}
