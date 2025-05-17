package org.example;

public class Node {
    private int freq;
    private Word word;
    Node left;
    Node right;
    public Node(Word word){
        this.freq = word.getCount();
        this.word = word;
    }

    public int getFreq(){
        return freq;
    }
    public Word getWord(){
        return word;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
