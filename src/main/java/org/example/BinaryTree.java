package org.example;

import java.util.ArrayList;

/**
 * The {@code BinaryTree} class represents a binary search tree specifically designed
 * to organize {@link Word} objects based on their frequency of occurrence.
 * <p>
 * The tree inserts {@code Word} nodes in such a way that nodes with a lower count are placed
 * to the left, and nodes with equal or higher count go to the right subtree. This structure allows
 * for efficient in-order traversal of words sorted by their frequency.
 * <p>
 * The class also supports different methods of populating the tree based on word frequency analysis
 * over a given input string.
 */
public class BinaryTree {

    // Root node of the binary search tree
    private Node root;

    // Input text string used for populating the tree
    private String input;

    /**
     * Constructs a BinaryTree using the provided input text.
     *
     * @param input the string to analyze and build the tree from
     */
    public BinaryTree(String input){
        this.root = null;
        this.input = input;
    }

    /**
     * Recursively inserts a Word into the tree based on its count.
     * Left if smaller, right if equal or larger.
     *
     * @param passNode the current node being inspected
     * @param word the word to be inserted
     * @return the modified tree node (new or existing)
     */
    private Node insertRec(Node passNode, Word word){
        if(passNode == null){
            passNode = new Node(word);
            return passNode;
        }
        if(word.getCount() < passNode.getFreq())
            passNode.left = insertRec(passNode.left, word);
        else
            passNode.right = insertRec(passNode.right, word);
        return passNode;
    }

    /**
     * Public method to insert a word into the binary tree.
     *
     * @param word the word object to insert
     */
    public void insert(Word word){
        root = insertRec(root, word);
    }

    /**
     * Initiates an in-order traversal of the tree and prints words with their counts.
     */
    public void print(){
        printRec(root);
    }

    /**
     * Recursive helper to traverse the tree in-order and print each word and its frequency.
     *
     * @param passNode the current node being printed
     * @return the node after processing
     */
    private Node printRec(Node passNode){
        if(passNode == null) return null;
        if(passNode.getLeft() != null)
            printRec(passNode.getLeft());
        System.out.println(passNode.getWord().getWord() + " " + passNode.getWord().getCount());
        if(passNode.getRight() != null)
            printRec(passNode.getRight());
        return passNode;
    }

    /**
     * Parses the original input, counts word frequencies, and fills the tree with Word nodes.
     *
     * @return the root node of the resulting tree
     */
    public Node fill(){
        try {
            root = null;
            ArrayList<Word> words = countWord();
            for (Word word : words) {
                insert(word);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return root;
    }

    /**
     * An alternative fill method that uses countWord2 to split words into high/low frequency.
     * Only the first (high frequency) group is inserted into the tree.
     *
     * @return the root node of the resulting tree
     */
    public Node fill2(){
        try {
            root = null;
            ArrayList<ArrayList<Word>> wordLists = countWord2(input);
            ArrayList<Word> words = wordLists.get(0);
            for (Word word : words) {
                insert(word);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return root;
    }

    /**
     * A private fill method that can be used to populate tree from a provided text string.
     *
     * @param text the text input to analyze and insert words from
     * @return the root node of the resulting tree
     */
    private Node fill(String text){
        try {
            ArrayList<Word> words = countWord();
            for (Word word : words) {
                insert(word);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return root;
    }

    /**
     * Counts frequency of each word in the class-level input string.
     * Case-insensitive comparison is used for duplicates, but original case is tracked.
     *
     * @return list of unique Word objects with updated counts
     * @throws Exception on processing errors
     */
    private ArrayList<Word> countWord() throws Exception {
        String text = input;
        String[] words = text.split("[,\\.\\\n\\s]");
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<Word> result = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            boolean uppercase = false;
            String word = words[i];
            if (word.isEmpty()) continue;
            if (word.isBlank()) continue;

            // Determine if the first character is uppercase
            String UCopy = word.toUpperCase();
            if(UCopy.substring(0,1).equals(word.substring(0,1))){
                uppercase = true;
            }

            // If word not already processed, add it
            if (!wordList.contains(word)){
                wordList.add(word.toLowerCase());
                result.add(new Word(word, 1, uppercase));
                continue;
            }

            // If duplicate, increment its count
            result.get(wordList.indexOf(word)).incrementCount();
        }

        // Print the frequency of each word (for debugging)
        for (String word : wordList) {
            System.out.println(word + " " + result.get(wordList.indexOf(word)).getCount());
        }

        return result;
    }

    /**
     * Static method that processes a string and splits its words into two groups:
     * those with frequency > 2 and those with frequency <= 2.
     *
     * @param input the input string to process
     * @return a list of two lists: [high frequency words, low frequency words]
     * @throws Exception on processing errors
     */
    public static ArrayList<ArrayList<Word>> countWord2(String input) throws Exception {
        String text = input;
        String[] words = text.split("[,\\.\\\n\\s]");
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<Word> result = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            boolean uppercase = false;
            String word = words[i];
            if (word.isEmpty()) continue;
            if (word.isBlank()) continue;

            // Check capitalization of the first character
            String UCopy = word.toUpperCase();
            if(UCopy.substring(0,1).equals(word.substring(0,1))){
                uppercase = true;
            }

            if (!wordList.contains(word)){
                wordList.add(word.toLowerCase());
                result.add(new Word(word.toLowerCase(), 1, uppercase));
                continue;
            }

            result.get(wordList.indexOf(word)).incrementCount();
        }

        // Categorize into high and low frequency groups
        ArrayList<ArrayList<Word>> result2 = new ArrayList<>();
        ArrayList<Word> highFreqWords = new ArrayList<>();
        ArrayList<Word> lowFreqWords = new ArrayList<>();

        for (Word word : result) {
            if(word.getCount() > 2){
                highFreqWords.add(word);
            } else {
                lowFreqWords.add(word);
            }
        }

        result2.add(highFreqWords);
        result2.add(lowFreqWords);
        return result2;
    }

    /**
     * An advanced word counting method using SentenceManager that filters out
     * words with count less than or equal to 2.
     *
     * @param input the input text to analyze
     * @return a list of Word2 objects with frequency > 2
     * @throws Exception on processing error
     */
    public static ArrayList<Word2> countWord3(String input) throws Exception {
        String text = input;
        ArrayList<Word2> wordList = SentenceManager.toWordList(input);
        ArrayList<Word2> singleWordList = new ArrayList<>();

        for (int i = 0; i < wordList.size(); i++) {
            boolean added = false;
            for (int j = 0; j < singleWordList.size(); j++) {
                if (wordList.get(i).getEditedWord().equals(singleWordList.get(j).getEditedWord())) {
                    singleWordList.get(j).incrementCount();
                    added = true;
                    break;
                }
            }
            if (!added) {
                singleWordList.add(wordList.get(i));
            }
        }

        // Filter for only high-frequency words
        ArrayList<Word2> finalResult = new ArrayList<>();
        for (Word2 word : singleWordList) {
            if(word.getCount() > 2){
                finalResult.add(word);
            }
        }

        return finalResult;
    }


}
