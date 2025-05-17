package org.example;

public class Word {
    private String word;
    private int count;
    private boolean uppercase;
    public Word(String word, int count, boolean uppercase){
        this.word = word;
        this.count = count;
        this.uppercase = uppercase;
    }
    public boolean isUppercase(){
        return this.uppercase;
    }
    public void incrementCount(){
        this.count++;
    }
    public void decrementCount(){
        if(this.count > 0){
            this.count--;
        }
    }
    public void resetCount(){
        this.count = 0;
    }
    public boolean isZeroCount(){
        return this.count == 0;
    }
    public boolean isNonZeroCount(){
        return this.count != 0;
    }
    public boolean equals(Word other){
        return this.word.equals(other.getWord());
    }
    public void setWord(String word){}
    public String getWord(){
        return word;
    }
    public int getCount(){
        return count;
    }
}
