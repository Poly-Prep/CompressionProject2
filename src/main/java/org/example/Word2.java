package org.example;

import java.util.ArrayList;
//TODO APOSTROPHES
//TO-DO: COMPLETE TOENGLISH FUNCTION
//reminder to self, don't feed in spaces
//for revision at end: possible bloat in unused instance variables
public class Word2 {
    //so that we can work with words easier, automatic handling of capitalization and storing of punctuation.
    //punctuation can be "," "!" ";" ":" "." "/" """ "'" etc.
    //punctuation on front (@ # etc.)
    private String punctuationF;
    //punctuation on end " " "," etc.
    private String punctuationE;
    private String originalWord;
    private String editedWord;
    private boolean isCapitalized;
    private boolean containsLetters;
    private int count;
    private ArrayList<Character> vowels = new ArrayList<Character>();
    public Word2(String newWord) {
        count = 1;
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');
        originalWord = new String(newWord);

        //convert word into a character array.
        //https://www.digitalocean.com/community/tutorials/string-char-array-java
        char[] charArray = newWord.toCharArray();
        containsLetters = false;
        for (int i = 0; i < charArray.length; i++) {
            //https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html
            if (Character.isLetter(charArray[i])) {
                containsLetters = true;
                break;
            }
        }
        if (!containsLetters) {
            punctuationF = "";
            punctuationE = "";
            isCapitalized = false;
            editedWord = newWord;
            return;
        }


        //if the first character isn't alphabetic call it front punctuation
        if (!Character.isLetter(charArray[0])) {
            punctuationF = "";
            //loop through all of end until we hit a letter to accommodate "---" etc.
            for (int i = 0; i < charArray.length; i++) {
                if(charArray[i] == '\n'){punctuationF = punctuationF + charArray[i]; continue;}
                if (Character.isLetter(charArray[i])) {
                    break;
                }
                punctuationF = punctuationF + charArray[i];
            }
        } else {
            punctuationF = "";
        }
        //if the first character isn't alphabetic call it end punctuation
        if (!Character.isLetter(charArray[charArray.length - 1])) {
            punctuationE = "";
            //TODO APOSTROPHES
            //loop through all of end until we hit a letter to accommodate "..." etc.
            for (int i = charArray.length - 1; i > 0; i--) {
                if(charArray[i] == '\n'){punctuationE = punctuationE + charArray[i]; continue;}
                if (Character.isLetter(charArray[i])) {
                    break;
                }
                punctuationE = charArray[i] + punctuationE;
            }

        } else {
            punctuationE = "";
        }
        editedWord = "";
        //build an edited version of the word without punctuation and lowercased
        for (int i = 0; i < charArray.length; i++) {
            //dont include punctuation or anything other than a letter that's at the front or back of a word
            if (punctuationF != "" && i < punctuationF.length()) {
                continue;
            }
            if (punctuationE != "" && i > charArray.length - 1 - punctuationE.length()) {
                continue;
            }
            editedWord = editedWord + charArray[i];

        }
        //TODO DOES THIS CRAP WORK
        if (editedWord.contains("'")) {
            String temp = editedWord.substring(editedWord.indexOf("'"));
            temp = temp + punctuationE;
            punctuationE = temp;
            editedWord = editedWord.substring(0, editedWord.lastIndexOf("'"));
        }
        //fill out is capitalized field before turning all to lowercase
        boolean first = true;
        isCapitalized = false;
        for (int i = 0; i < editedWord.length(); i++) {
            if(Character.isAlphabetic(editedWord.charAt(i))){
                if(!first){ break;}
                if(Character.isUpperCase(editedWord.charAt(i))) {
                    isCapitalized = true;
                    break;
                }
                first = false;
            }
        }
        //all to lowercase
        editedWord = editedWord.toLowerCase();
    }


    public String getPunctuationF() {
        return punctuationF;
    }

    public void setPunctuationF(String punctuationF) {
        this.punctuationF = punctuationF;
    }

    public String getPunctuationE() {
        return punctuationE;
    }

    public void setPunctuationE(String punctuationE) {
        this.punctuationE = punctuationE;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getEditedWord() {
        return editedWord;
    }

    public void setEditedWord(String editedWord) {
        this.editedWord = editedWord;
    }

    public boolean isCapitalized() {
        return isCapitalized;
    }

    public void setCapitalized(boolean capitalized) {
        isCapitalized = capitalized;
    }
    public void incrementCount(){
        this.count++;
    }
    public void decrementCount(){
        if(this.count > 0){
            this.count--;
        }
    }
    public int getCount(){
        return count;
    }
}
