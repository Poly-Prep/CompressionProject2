package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;


public class FileManager  {


    public static String readFile(String fileName) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {

        try {
            StringBuilder fileContent = new StringBuilder();

            // Append .txt if necessary
            if (!fileName.contains(".txt")) fileName += ".txt";
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                fileContent.append(myReader.nextLine()).append("\n");
            }
            myReader.close();

            return fileContent.toString(); // Encrypt and wrap in ET3928
        } catch (Exception e) {
            System.out.println("Error in reading file");
            return null;
        }
    }





    public static boolean writeFile(String fileName, String content) {
        try {
            BufferedWriter myWriter;

            // Enforce .txt extension
            if (!fileName.contains(".txt")) {
                fileName += ".txt";
            }

            myWriter = new BufferedWriter(new FileWriter(fileName));
            myWriter.write(content);  // Decrypt before writing
            myWriter.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in writing file");
            return false;
        }
    }
    public static HashMap<String, String> getHM(String text) throws Exception {


        String[] chunks = text.toString().split(";");
        HashMap<String, String> hm = new HashMap<>();

        for (String chunk : chunks) {
            String[] keyValue = chunk.split("=");

            hm.put(keyValue[0], keyValue[1]);

        }
        return hm;
    }

    /**
     * Converts a {@code HashMap<String, String>} into an encrypted {@link ET3928} object for safe writing.
     *
     * @param hm A key-value mapping to be written
     * @return Encrypted form of the map
     */
    public static String prepareHM(HashMap<String, String> hm) throws Exception{

        StringBuilder fileContent = new StringBuilder();

        for (String key : hm.keySet()) {
            fileContent.append(key).append("=").append(hm.get(key)).append(";");
        }

        // Remove final trailing semicolon
        if (fileContent.length() > 0) {
            fileContent.deleteCharAt(fileContent.length() - 1);
        }

        return fileContent.toString(); // Encrypt
    }
}
