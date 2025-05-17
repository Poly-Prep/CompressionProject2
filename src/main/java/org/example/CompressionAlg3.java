package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class CompressionAlg3 {
    public static String compress(String text) throws Exception {
        text = text.replace("`;", "").replace("\n"," \n ");
        ArrayList<Word2> highFreqWords = BinaryTree2.countWord(text);
        Node2 root = new BinaryTree2(text).fill();
        HashMap<String, String> codes = new HashMap<>();
        fillCodes(codes, root);
        String UC = "`;";
        StringBuilder sb = new StringBuilder();
        ArrayList<Word2> textList = SentenceManager.toWordList(text);
        sb.append(FileManager.prepareHM(codes));
        sb.append("\n");
        for (int i = 0; i < textList.size(); i++) {
            boolean match = false;
            for (int j = 0; j < highFreqWords.size(); j++) {
                if (highFreqWords.get(j).getEditedWord().equals(textList.get(i).getEditedWord())) {
                    match = true;
                    break;
                }
            }
            if (i != 0) sb.append(" ");
            if (match) {
                if (textList.get(i).isCapitalized()){ sb.append(UC);;}
                sb.append(textList.get(i).getPunctuationF());
                sb.append(codes.get(textList.get(i).getEditedWord()));
                sb.append(textList.get(i).getPunctuationE());
            } else {
                if (textList.get(i).isCapitalized()){ sb.append(UC); }
                sb.append(textList.get(i).getPunctuationF());
                sb.append(textList.get(i).getEditedWord());
                sb.append(textList.get(i).getPunctuationE());
            }
        }
        return sb.toString();
    }

    public static String decompress(String text) throws Exception {

        String HMString = text.substring(0,text.indexOf("\n"));

        text = text.substring(text.indexOf("\n") + 1);
        HashMap<String, String> codes = FileManager.getHM(HMString);
        HashMap<String, String> reverseCodes = new HashMap<>();
        for (String key : codes.keySet()) {
            String code = codes.get(key);
            reverseCodes.put(code, key);
        }
        System.out.println(reverseCodes.toString());
        codes = null;
        String[] wordArray = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int l = 0; l < wordArray.length; l++) {
            String word = wordArray[l];
            if (!word.contains("0") && !word.contains("1")) {
                if (word.contains("`;")) {
                    word = word.replace("`;", "");
                    char[] wordArray2 = word.toCharArray();
                    for (int i = 0; i < wordArray2.length; i++) {
                        if (wordArray2[i] == '\n') {continue;}
                        if (Character.isAlphabetic(wordArray2[i])) {
                            wordArray2[i] = Character.toUpperCase(wordArray2[i]);
                            break;
                        }
                    }
                    word = new String(wordArray2);
                }
                wordArray[l] = word;
                continue;
            }
            char[] charArray = word.toCharArray();
            boolean started = false;
            StringBuilder codeBuilder = new StringBuilder();
            for (char c : charArray) {
                if (c == '0' || c == '1') {
                    if (!started) {
                        started = true;
                        codeBuilder.append(c);
                        continue;
                    } else {
                        codeBuilder.append(c);
                        continue;
                    }
                }
                if (started) {
                    break;
                }
            }
            String trueWord = reverseCodes.get(codeBuilder.toString());
            if (trueWord == null) trueWord = word;
            if (word.contains("`;")) {
                char[] trueWordArray = trueWord.toCharArray();

                for (int i = 0; i < trueWordArray.length; i++) {
                    if (Character.isAlphabetic(trueWordArray[i])) {
                        trueWordArray[i] = Character.toUpperCase(trueWordArray[i]);
                        break;
                    }
                }
                wordArray[l] = wordArray[l].replace("`;", "").replace(codeBuilder.toString(), new String(trueWordArray));
            }else {
                wordArray[l] = wordArray[l].replace(codeBuilder.toString(), trueWord);
            }

        }
        return SentenceManager.toParagraph(new ArrayList<>(java.util.Arrays.asList(wordArray)));

    }


    private static void fillCodes(HashMap<String, String> codes, Node2 root) {
        fillCodesRec(codes, root, "0");
    }

    private static void fillCodesRec(HashMap<String, String> codes, Node2 root, String path) {
        if (root == null) return;
        codes.put(root.getWord().getEditedWord(), path);

        if (root.getLeft() != null) fillCodesRec(codes, root.getLeft(), path + "0");
        if (root.getRight() != null) fillCodesRec(codes, root.getRight(), path + "1");

    }
//public static String decompress(String text) throws Exception {}

}