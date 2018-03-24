package com.comet.myapplication.dic;

/**
 * Created by nujjj on 2018/3/13.
 */

public class WordBean {
    private CharSequence word = "";
    private int count;

    public WordBean(CharSequence word, int count) {
        this.word = word;
        this.count = count;
    }

    public CharSequence getWord() {
        return word;
    }

    public void setWord(CharSequence word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
