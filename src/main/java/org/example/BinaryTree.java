package org.example;

import java.util.ArrayList;

public class BinaryTree {
    private Node root;
    private String input;
    public BinaryTree(String input){
        this.root = null;
        this.input = input;
    }
    private Node insertRec(Node passNode, Word word){
        if(passNode == null){
            passNode = new Node(word);
            return passNode;
        }
        if(word.getCount() < passNode.getFreq()) passNode.left = insertRec(passNode.left, word);
        else if (word.getCount() > passNode.getFreq()) {
            passNode.right = insertRec(passNode.right, word);
        } else if (word.getCount() == passNode.getFreq()) {
            passNode.right = insertRec(passNode.right, word);
        }
        return passNode;
    }
    public void insert(Word word){
        root = insertRec(root, word);
    }

    public void print(){
        printRec(root);
    }
    private Node printRec(Node passNode){
        if(passNode == null) return null;
        if(passNode.getLeft() != null) printRec(passNode.getLeft());
        System.out.println(passNode.getWord().getWord() + " " + passNode.getWord().getCount());
        if(passNode.getRight() != null) printRec(passNode.getRight());
        return passNode;
    }
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
            String UCopy = word.toUpperCase();
            if(UCopy.substring(0,1).equals(word.substring(0,1))){
                uppercase = true;
            }
            if (!wordList.contains(word)){
                wordList.add(word.toLowerCase());
                result.add(new Word(word, 1, uppercase));
                continue;
            }
            result.get(wordList.indexOf(word)).incrementCount();
        }
        for (String word : wordList) {
            System.out.println(word + " " + result.get(wordList.indexOf(word)).getCount());
        }
        return result;
    }
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
        ArrayList<Word2> finalResult = new ArrayList<>();
        for (Word2 word : singleWordList) {
            if(word.getCount() > 2){
                finalResult.add(word);
            }
        }
        return finalResult;
    }
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree("I am Sam\n" +
                "Sam I am\n" +
                "That Sam-I-am\n" +
                "That Sam-I-am\n" +
                "I do not like that Sam-I-am\n" +
                "Do you like \n" +
                "green eggs and ham\n" +
                "I do not like them Sam-I-am\n" +
                "I do not like\n" +
                "green eggs and ham\n" +
                "Would you like them \n" +
                "here or there\n" +
                "I would not like them\n" +
                "here or there\n" +
                "I would not like them anywhere \n" +
                "I do not like\n" +
                "green eggs and ham\n" +
                "I do not like them Sam-I-am\n" +
                "Would you like them in a house\n" +
                "Would you like them with a mouse\n" +
                "I do not like them\n" +
                "in a house\n" +
                "I do not like them\n" +
                "with a mouse\n" +
                "I do not like them\n" +
                "here or there\n" +
                "I do not like them\n" +
                "anywhere\n" +
                "I do not like \n" +
                "green eggs and ham\n" +
                "I do not like them \n" +
                "Sam-I-am\n" +
                "Would you eat them\n" +
                "in a box\n" +
                "Would you eat them\n" +
                "with a fox\n" +
                "Not in a box \n" +
                "Not with a fox\n" +
                "Not in a house\n" +
                "Not with a mouse\n" +
                "I would not eat them\n" +
                "here or there\n" +
                "I would not eat them anywhere\n" +
                "I would not eat green eggs and ham\n" +
                "I do not like them Sam-I-am\n" +
                "Would you Could you In a car\n" +
                "Eat them Eat them Here they are\n" +
                "I would not could not in a car\n" +
                "You may like them You will see \n" +
                "You may like them in a tree\n" +
                "I would not could not in a tree\n" +
                "Not in a car You let me be\n" +
                "I do not like them in a box\n" +
                "I do not like them with a fox\n");

        // Insert some nodes
        tree.fill();

        // Print inorder traversal of the tree
        tree.print();
    }
}
