package org.example;

import java.util.ArrayList;

public class BinaryTree2 {
    private Node2 root;
    private String input;
    public BinaryTree2(String input){
        this.root = null;
        this.input = input;
    }

    private Node2 insertRec(Node2 passNode, Word2 word){
        if(passNode == null){
            passNode = new Node2(word);
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
    public void insert(Word2 word){
        root = insertRec(root, word);
    }
    public void print(){
        printRec(root);
    }
    private Node2 printRec(Node2 passNode){
        if(passNode == null) return null;
        if(passNode.getLeft() != null) printRec(passNode.getLeft());
        System.out.println(passNode.getWord().getOriginalWord() + " " + passNode.getWord().getCount());
        if(passNode.getRight() != null) printRec(passNode.getRight());
        return passNode;
    }
    public Node2 fill(){
        try {
            root = null;
            ArrayList<Word2> words = countWord(input);
            for (Word2 word : words) {
                insert(word);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return root;
    }


    public static ArrayList<Word2> countWord(String input) throws Exception {
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
            if(word.getCount() > 2 && word.getEditedWord().length()>3){
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
