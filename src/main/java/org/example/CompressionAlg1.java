package org.example;

import java.util.ArrayList;

public class CompressionAlg1 {
    private static ArrayList<Word> countWord(String input) throws Exception {
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
    private static ArrayList<Word> sortWord(ArrayList<Word> wordList) throws Exception {
        ArrayList<Word> result = new ArrayList<>();
        while(!wordList.isEmpty()){
            Word max = wordList.getFirst();
            for (int i = 0; i < wordList.size(); i++) {
                if(wordList.get(i).getCount() > max.getCount()){
                    max = wordList.get(i);
                }
            }
            result.add(max);
            wordList.remove(max);
        }
        return result;
    }
    public static String compress(String text) throws Exception {
        String copy = text;
        ArrayList<Word> words = countWord(text);
        words = sortWord(words);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).getCount() > 2) copy = copy.replace(words.get(i).getWord(), String.valueOf(i));
        }
        copy = copy.trim();
        sb.append(copy);
        sb.append("\n");
        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).getCount() > 2) sb.append(i + " " + words.get(i).getCount() + "\n");
        }
        return sb.toString();
    }
}
