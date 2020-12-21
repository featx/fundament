package org.featx.fundament;

public class IntegerHolder {

    private int value;

    public IntegerHolder(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public int getAndSet(int value) {
        int temp = this.value;
        this.value = value;
        return temp;
    }
}
