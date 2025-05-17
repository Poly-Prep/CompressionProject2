package org.example;

import java.util.ArrayList;

//basic *ss class to keep everything dealing with parsing out and rebuilding sentences in one place
public class SentenceManager {
    public static ArrayList<Word2> toWordList(String paragraph) {
        //https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
        //uses split to parse out all the words and then the word class takes care of
        //the rest after each word is passed in
        ArrayList<Word2> wordList = new ArrayList<Word2>();
        String[] words = paragraph.split(" ");
        for (String word : words) {
            wordList.add(new Word2(word));
        }
        return wordList;
    }

    //new line variant
    public static ArrayList<String> toWordListNL(String paragraph) {
        //https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
        //uses split to parse out all the words
        ArrayList<String> wordList = new ArrayList<String>();
        String[] words = paragraph.split("\n");
        for (String word : words) {
            wordList.add(word);
        }
        return wordList;
    }





    public static String toParagraph(ArrayList<String> wordList) {
        String sentence = "";
        for (String word : wordList) {
            sentence = sentence + " " + word;
        }
        return sentence;
    }
}
