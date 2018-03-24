package com.comet.myapplication.dic;


public class Node {
    public char[] value;
    public Node[] children;
    public boolean stop;
    public int count;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Node(char c) {
        this.children = new Node[0];
        this.value = new char[]{c};
    }

    public char[] getValue() {
        return value;
    }

    public Node[] getChildren() {
        return children;
    }

}