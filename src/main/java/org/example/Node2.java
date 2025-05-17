package org.example;

public class Node2 {
    private int freq;
    private Word2 word;
    Node2 left;
    Node2 right;
    public Node2(Word2 word){
        this.freq = word.getCount();
        this.word = word;
    }

    public int getFreq(){
        return freq;
    }
    public Word2 getWord(){
        return word;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setWord(Word2 word) {
        this.word = word;
    }

    public Node2 getLeft() {
        return left;
    }

    public void setLeft(Node2 left) {
        this.left = left;
    }

    public Node2 getRight() {
        return right;
    }

    public void setRight(Node2 right) {
        this.right = right;
    }
}
