package org.example;

import java.util.ArrayList;

/**
 * The {@code BinaryTree2} class represents a binary search tree used to store {@link Word2} objects.
 * <p>
 * Words are inserted based on their frequency (count), with lower frequencies placed to the left
 * and equal or higher frequencies to the right. The class provides mechanisms to parse and count words
 * from an input string using {@link SentenceManager}, then insert them into the tree if they meet
 * certain conditions (e.g., count > 2, length > 3).
 * <p>
 * This structure is primarily used for efficient sorting and retrieval (via in-order traversal) of
 * word frequencies.
 */
public class BinaryTree2 {

    // Root node of the binary tree
    private Node2 root;

    // Input string used to populate the tree
    private String input;

    /**
     * Constructor initializes the tree with the provided input string.
     *
     * @param input the string input from which the tree will be built
     */
    public BinaryTree2(String input){
        this.root = null;
        this.input = input;
    }

    /**
     * Recursive helper method to insert a {@link Word2} into the tree.
     * Words are inserted based on their frequency (count).
     *
     * @param passNode the current node being inspected
     * @param word the word to be inserted into the tree
     * @return the updated node after insertion
     */
    private Node2 insertRec(Node2 passNode, Word2 word){
        // Base case: empty subtree, create new node
        if(passNode == null){
            passNode = new Node2(word);
            return passNode;
        }

        // Insert into left subtree if count is less than current node's frequency
        if(word.getCount() < passNode.getFreq())
            passNode.left = insertRec(passNode.left, word);

            // Insert into right subtree if count is greater or equal
        else
            passNode.right = insertRec(passNode.right, word);

        return passNode;
    }

    /**
     * Public method to insert a {@link Word2} into the binary tree.
     *
     * @param word the word to insert
     */
    public void insert(Word2 word){
        root = insertRec(root, word);
    }

    /**
     * Initiates an in-order traversal of the tree to print each word's original form and count.
     */
    public void print(){
        printRec(root);
    }

    /**
     * Recursively traverses the tree in-order and prints each word and its frequency.
     *
     * @param passNode current node being visited
     * @return the node after processing
     */
    private Node2 printRec(Node2 passNode){
        // Base case: reached null node
        if(passNode == null) return null;

        // Traverse left subtree
        if(passNode.getLeft() != null)
            printRec(passNode.getLeft());

        // Print current node's word and count
        System.out.println(passNode.getWord().getOriginalWord() + " " + passNode.getWord().getCount());

        // Traverse right subtree
        if(passNode.getRight() != null)
            printRec(passNode.getRight());

        return passNode;
    }

    /**
     * Parses the input string, counts word frequencies using {@link #countWord(String)},
     * and fills the binary tree with qualified {@link Word2} objects.
     *
     * @return the root node of the constructed tree
     */
    public Node2 fill(){
        try {
            // Reset tree before filling
            root = null;

            // Count valid words and insert into tree
            ArrayList<Word2> words = countWord(input);
            for (Word2 word : words) {
                insert(word);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return root;
    }

    /**
     * Static utility method to count the frequency of each word in the input string,
     * using {@link SentenceManager#toWordList(String)}. Only words with a count > 2
     * and length > 3 are included in the final result.
     *
     * @param input the text to analyze
     * @return a list of unique {@link Word2} objects with sufficient frequency and length
     * @throws Exception if input processing fails
     */
    public static ArrayList<Word2> countWord(String input) throws Exception {
        String text = input;

        // Convert input into a list of Word2 objects (including edited forms)
        ArrayList<Word2> wordList = SentenceManager.toWordList(input);

        // List to store unique words by their edited form
        ArrayList<Word2> singleWordList = new ArrayList<>();

        // Count frequencies by checking for duplicates of edited word
        for (int i = 0; i < wordList.size(); i++) {
            boolean added = false;
            for (int j = 0; j < singleWordList.size(); j++) {
                if (wordList.get(i).getEditedWord().equals(singleWordList.get(j).getEditedWord())) {
                    // Match found, increment count
                    singleWordList.get(j).incrementCount();
                    added = true;
                    break;
                }
            }
            // No match found, add new word
            if (!added) {
                singleWordList.add(wordList.get(i));
            }
        }

        // Filter only frequently occurring, long-enough words
        ArrayList<Word2> finalResult = new ArrayList<>();
        for (Word2 word : singleWordList) {
            if(word.getCount() > 2 && word.getEditedWord().length() > 3){
                finalResult.add(word);
            }
        }

        return finalResult;
    }


}
