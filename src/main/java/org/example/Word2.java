package org.example;

import java.util.ArrayList;

/**
 * The {@code Word2} class encapsulates a word token from a text input,
 * separating and preserving punctuation, casing, and providing a normalized (lowercased) version
 * for processing such as frequency analysis or compression.
 * <p>
 * Each instance keeps track of:
 * - Original word with punctuation
 * - Edited version (lowercase, no punctuation)
 * - Whether it was capitalized
 * - Leading and trailing punctuation
 * - A frequency count
 * <p>
 * This class is used heavily in preprocessing text for compression, tree-based encoding,
 * and semantic analysis.
 */
public class Word2 {

    // Punctuation at the beginning of the word (e.g., quotes, symbols)
    private String punctuationF;

    // Punctuation at the end of the word (e.g., periods, commas, etc.)
    private String punctuationE;

    // The original unmodified word (as in the input text)
    private String originalWord;

    // The processed, lowercased version of the word without punctuation
    private String editedWord;

    // Whether the original word started with a capital letter
    private boolean isCapitalized;

    // Whether the original word contains any letter characters
    private boolean containsLetters;

    // Frequency count of how many times this word occurs
    private int count;

    // Vowels list (currently unused in logic, reserved for future expansion)
    private ArrayList<Character> vowels = new ArrayList<>();

    /**
     * Constructs a {@code Word2} object by processing the given raw string input.
     * Extracts punctuation, normalizes casing, and determines word characteristics.
     *
     * @param newWord the raw input word from the text
     */
    public Word2(String newWord) {
        count = 1;

        // Populate vowel list
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');

        originalWord = new String(newWord);
        char[] charArray = newWord.toCharArray();
        containsLetters = false;

        // Determine if the word contains any alphabetic characters
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                containsLetters = true;
                break;
            }
        }

        // If the word has no alphabetic letters, treat it as pure punctuation
        if (!containsLetters) {
            punctuationF = "";
            punctuationE = "";
            isCapitalized = false;
            editedWord = newWord;
            return;
        }

        // Detect leading punctuation (e.g., "#", "...")
        if (!Character.isLetter(charArray[0])) {
            punctuationF = "";
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == '\n') {
                    punctuationF += charArray[i];
                    continue;
                }
                if (Character.isLetter(charArray[i])) break;
                punctuationF += charArray[i];
            }
        } else {
            punctuationF = "";
        }

        // Detect trailing punctuation (e.g., ".", "!", "..." etc.)
        if (!Character.isLetter(charArray[charArray.length - 1])) {
            punctuationE = "";
            for (int i = charArray.length - 1; i > 0; i--) {
                if (charArray[i] == '\n') {
                    punctuationE = punctuationE + charArray[i];
                    continue;
                }
                if (Character.isLetter(charArray[i])) break;
                punctuationE = charArray[i] + punctuationE;
            }
        } else {
            punctuationE = "";
        }

        editedWord = "";

        // Build the edited version of the word (no punctuation)
        for (int i = 0; i < charArray.length; i++) {
            if (!punctuationF.isEmpty() && i < punctuationF.length()) continue;
            if (!punctuationE.isEmpty() && i > charArray.length - 1 - punctuationE.length()) continue;
            editedWord += charArray[i];
        }

        // Handle apostrophes within words (e.g., "don't" → "dont" and move "'t" to punctuation)
        if (editedWord.contains("'")) {
            String temp = editedWord.substring(editedWord.indexOf("'"));
            temp = temp + punctuationE;
            punctuationE = temp;
            editedWord = editedWord.substring(0, editedWord.lastIndexOf("'"));
        }

        // Detect if the word is capitalized (first alphabetic char is uppercase)
        boolean first = true;
        isCapitalized = false;
        for (int i = 0; i < editedWord.length(); i++) {
            if (Character.isAlphabetic(editedWord.charAt(i))) {
                if (!first) break;
                if (Character.isUpperCase(editedWord.charAt(i))) {
                    isCapitalized = true;
                    break;
                }
                first = false;
            }
        }

        // Convert final edited word to lowercase
        editedWord = editedWord.toLowerCase();
    }

    // Getters and setters for all fields

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

    /**
     * Increments the frequency count of this word by one.
     */
    public void incrementCount() {
        this.count++;
    }

    /**
     * Decrements the frequency count of this word by one,
     * ensuring it does not fall below zero.
     */
    public void decrementCount() {
        if (this.count > 0) {
            this.count--;
        }
    }

    /**
     * Returns the current frequency count of the word.
     *
     * @return number of occurrences of this word
     */
    public int getCount() {
        return count;
    }
}
