package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code CompressionAlg3} class provides a custom compression and decompression algorithm
 * for text based on word frequency analysis and binary encoding.
 * <p>
 * High-frequency words are replaced with binary codes, preserving punctuation and capitalization.
 * A dictionary of codes is prepended to the compressed string for later reconstruction.
 * <p>
 * Capitalized words are marked with a special marker "`;" for restoration during decompression.
 */
public class CompressionAlg3 {

    /**
     * Compresses a given input text by replacing high-frequency words with binary codes.
     *
     * @param text the input text to compress
     * @return the compressed string
     * @throws Exception if word processing or encoding fails
     */
    public static String compress(String text) throws Exception {
        // Remove compression marker from input and normalize newlines
        text = text.replace("`;", "").replace("\n", " \n ");

        // Get list of frequently used words (count > 2, length > 3)
        ArrayList<Word2> highFreqWords = BinaryTree2.countWord(text);

        // Build binary tree from text, which assigns frequency-based structure
        Node2 root = new BinaryTree2(text).fill();

        // HashMap to store binary codes for high-frequency words
        HashMap<String, String> codes = new HashMap<>();

        // Recursively fill codes based on tree structure
        fillCodes(codes, root);

        // Marker used to indicate capitalization
        String UC = "`;";

        // StringBuilder for efficient string construction
        StringBuilder sb = new StringBuilder();

        // Convert input into list of processed Word2 tokens
        ArrayList<Word2> textList = SentenceManager.toWordList(text);

        // Add header containing encoded dictionary
        sb.append(FileManager.prepareHM(codes));
        sb.append("\n");

        // Iterate through all words in the text
        for (int i = 0; i < textList.size(); i++) {
            boolean match = false;

            // Check if current word is in high-frequency list
            for (int j = 0; j < highFreqWords.size(); j++) {
                if (highFreqWords.get(j).getEditedWord().equals(textList.get(i).getEditedWord())) {
                    match = true;
                    break;
                }
            }

            // Add space between words (but not before the first word)
            if (i != 0) sb.append(" ");

            // If word is high-frequency, use compressed code
            if (match) {
                if (textList.get(i).isCapitalized()) {
                    sb.append(UC);
                }
                sb.append(textList.get(i).getPunctuationF());
                sb.append(codes.get(textList.get(i).getEditedWord())); // Insert binary code
                sb.append(textList.get(i).getPunctuationE());
            } else {
                // For low-frequency words, include original text
                if (textList.get(i).isCapitalized()) {
                    sb.append(UC);
                }
                sb.append(textList.get(i).getPunctuationF());
                sb.append(textList.get(i).getEditedWord()); // Use uncompressed word
                sb.append(textList.get(i).getPunctuationE());
            }
        }

        return sb.toString();
    }

    /**
     * Decompresses a string encoded using the {@link #compress(String)} method.
     *
     * @param text the compressed input string
     * @return the reconstructed original text
     * @throws Exception if decoding fails
     */
    public static String decompress(String text) throws Exception {
        // Extract header containing encoded word map
        String HMString = text.substring(0, text.indexOf("\n"));

        // Strip off dictionary to leave compressed content
        text = text.substring(text.indexOf("\n") + 1);

        // Restore original word-code mapping
        HashMap<String, String> codes = FileManager.getHM(HMString);

        // Invert the code map to decode from binary to words
        HashMap<String, String> reverseCodes = new HashMap<>();
        for (String key : codes.keySet()) {
            String code = codes.get(key);
            reverseCodes.put(code, key);
        }

        // Output reverse map (debugging purpose)
        System.out.println(reverseCodes.toString());

        codes = null; // Release memory if needed

        // Split compressed text into words
        String[] wordArray = text.split(" ");

        // Result builder for reconstructed text
        StringBuilder sb = new StringBuilder();

        // Iterate through each word in the compressed text
        for (int l = 0; l < wordArray.length; l++) {
            String word = wordArray[l];

            // If no binary digits are present, skip decoding
            if (!word.contains("0") && !word.contains("1")) {

                // Check and restore capitalization
                if (word.contains("`;")) {
                    word = word.replace("`;", "");
                    char[] wordArray2 = word.toCharArray();
                    for (int i = 0; i < wordArray2.length; i++) {
                        if (wordArray2[i] == '\n') continue;
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

            // Extract binary code from the word
            char[] charArray = word.toCharArray();
            boolean started = false;
            StringBuilder codeBuilder = new StringBuilder();
            for (char c : charArray) {
                if (c == '0' || c == '1') {
                    if (!started) {
                        started = true;
                    }
                    codeBuilder.append(c);
                    continue;
                }
                if (started) break;
            }

            // Lookup actual word using the extracted binary code
            String trueWord = reverseCodes.get(codeBuilder.toString());

            // If not found, use the raw word
            if (trueWord == null) trueWord = word;

            // Check if capitalization marker is present
            if (word.contains("`;")) {
                char[] trueWordArray = trueWord.toCharArray();
                for (int i = 0; i < trueWordArray.length; i++) {
                    if (Character.isAlphabetic(trueWordArray[i])) {
                        trueWordArray[i] = Character.toUpperCase(trueWordArray[i]);
                        break;
                    }
                }
                // Replace marker and code with the capitalized original word
                wordArray[l] = wordArray[l]
                        .replace("`;", "")
                        .replace(codeBuilder.toString(), new String(trueWordArray));
            } else {
                // Replace just the code with the decoded word
                wordArray[l] = wordArray[l].replace(codeBuilder.toString(), trueWord);
            }
        }

        // Convert word list back into paragraph form
        return SentenceManager.toParagraph(new ArrayList<>(java.util.Arrays.asList(wordArray)));
    }

    /**
     * Populates the binary code map for each word in the tree.
     *
     * @param codes the HashMap to populate
     * @param root the root of the binary tree
     */
    private static void fillCodes(HashMap<String, String> codes, Node2 root) {
        fillCodesRec(codes, root, "0");
    }

    /**
     * Recursive helper method to assign binary paths to each word node.
     *
     * @param codes the code map being populated
     * @param root the current node
     * @param path the binary path string leading to this node
     */
    private static void fillCodesRec(HashMap<String, String> codes, Node2 root, String path) {
        if (root == null) return;

        // Store current word's binary path
        codes.put(root.getWord().getEditedWord(), path);

        // Recurse left with appended 0
        if (root.getLeft() != null)
            fillCodesRec(codes, root.getLeft(), path + "0");

        // Recurse right with appended 1
        if (root.getRight() != null)
            fillCodesRec(codes, root.getRight(), path + "1");
    }
}
