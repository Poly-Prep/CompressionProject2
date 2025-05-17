package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The {@code CrappyGUI} class provides a graphical user interface (GUI) for compressing and decompressing
 * text using either a normal or Huffman-based compression algorithm. Users can either input text manually
 * or select a file, switch between compression and decompression modes, and save the output.
 * <p>
 * The GUI includes options to:
 * <ul>
 *     <li>Input or load text</li>
 *     <li>Select between compression and decompression modes</li>
 *     <li>Choose compression algorithm (normal vs Huffman)</li>
 *     <li>Display and save compressed or decompressed output</li>
 * </ul>
 * <p>
 * File selection is limited to `.txt` files in a predefined directory. Output is saved in the same directory.
 */
public class CrappyGUI implements ActionListener {

    // UI components for user interaction
    private JButton switchModeButton;     // Switch between compression and decompression modes
    private JComboBox fileSelect;         // Dropdown to select .txt files
    private JButton selectFileButton;     // Button to load selected file
    private JTextArea inputText;          // Area to enter or load text input
    private JButton compressButton;       // Button to compress or decompress
    private JTextArea outputText;         // Displays output text
    private JButton saveAsButton;         // Saves output to file
    private JTextField filenameField;     // Field to specify output filename
    private JPanel mainPanel;             // Main content panel
    private JCheckBox huffmanAlgCheckBox; // Checkbox to select Huffman algorithm
    private JFrame frame;                 // Main application window

    // Flags to track selected compression algorithm and whether file is Huffman-compressed
    private boolean huffmanAlg = false;
    private boolean selectedHuff = false;

    /**
     * Constructor initializes and sets up the GUI components,
     * populates file selection dropdown, and sets default properties.
     */
    public CrappyGUI() {
        // Register all action listeners
        switchModeButton.addActionListener(this);
        selectFileButton.addActionListener(this);
        compressButton.addActionListener(this);
        saveAsButton.addActionListener(this);

        // Enable word-wrapping and auto-scrolling
        inputText.setLineWrap(true);
        inputText.setAutoscrolls(true);
        outputText.setLineWrap(true);
        outputText.setAutoscrolls(true);

        // Populate dropdown with .txt files in the working directory
        ArrayList<String> fileNames = new ArrayList<>();
        File[] files = new File("/Users/beckm/IdeaProjects/CompressionProject2").listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                fileNames.add(file.getName());
            }
        }
        fileSelect.setModel(new DefaultComboBoxModel<>(fileNames.toArray(new String[0])));

        // Default field behavior
        outputText.setEditable(false);
        inputText.setEditable(true);
        filenameField.setText("File.txt");

        // Configure and display main application frame
        frame = new JFrame("Compressor");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Handles all button and component actions triggered by the user.
     *
     * @param e the action event representing the user's interaction
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Toggle between compression and decompression modes
        if (command.equals(switchModeButton.getActionCommand())) {
            fileSelect.removeAllItems();
            if (inputText.isEditable()) {
                // Switch to decompression mode
                huffmanAlgCheckBox.setVisible(false);
                ArrayList<String> fileNames = new ArrayList<>();
                File[] files = new File("/Users/beckm/IdeaProjects/CompressionProject2").listFiles();
                for (File file : files) {
                    if (file.getName().endsWith(".txt") && file.getName().contains("compressed")) {
                        fileNames.add(file.getName());
                    }
                }
                fileSelect.setModel(new DefaultComboBoxModel<>(fileNames.toArray(new String[0])));
                inputText.setEditable(false);
                outputText.setEditable(true);
                compressButton.setText("\\/ Decompress \\/");
                switchModeButton.setText("Switch to Compression Mode");
            } else {
                // Switch to compression mode
                huffmanAlgCheckBox.setVisible(true);
                ArrayList<String> fileNames = new ArrayList<>();
                File[] files = new File("/Users/beckm/IdeaProjects/CompressionProject2").listFiles();
                for (File file : files) {
                    if (file.getName().endsWith(".txt")) {
                        fileNames.add(file.getName());
                    }
                }
                fileSelect.setModel(new DefaultComboBoxModel<>(fileNames.toArray(new String[0])));
                compressButton.setText("\\/ Compress \\/");
                inputText.setEditable(true);
                outputText.setEditable(false);
                switchModeButton.setText("Switch to Decompression Mode");
            }
        }

        // Load file content into the input area
        if (command.equals(selectFileButton.getActionCommand())) {
            if (fileSelect.getSelectedIndex() == -1 || fileSelect.getSelectedItem() == null) {
                showMessageDialog(null, "Please select a file or add text to the input area.");
                return;
            }
            try {
                String fileName = (String) fileSelect.getSelectedItem();
                selectedHuff = fileName.contains("compressedHuff");
                String content = FileManager.readFile(fileName);
                inputText.setText(content);
            } catch (Exception ex) {
                showMessageDialog(null, "Error in reading file");
            }
        }

        // Compress or decompress text based on current mode
        if (command.equals(compressButton.getActionCommand())) {
            if (inputText.getText().isEmpty() || inputText.getText().isBlank()) {
                showMessageDialog(null, "Please select a file or add text to the input area.");
                return;
            }

            if (inputText.isEditable()) {
                // Compression logic
                try {
                    String compressedText;
                    if (huffmanAlgCheckBox.isSelected()) {
                        huffmanAlg = true;
                        compressedText = CompressionAlg3.compress(inputText.getText());
                    } else {
                        huffmanAlg = false;
                        compressedText = CompressionAlg2.compress(inputText.getText());
                    }
                    outputText.setText(compressedText);
                } catch (Exception ex) {
                    showMessageDialog(null, "Error in compressing text");
                }
            } else {
                // Decompression logic
                try {
                    String decompressedText;
                    if (selectedHuff) {
                        decompressedText = CompressionAlg3.decompress(inputText.getText());
                    } else {
                        decompressedText = CompressionAlg2.decompress(inputText.getText());
                    }
                    outputText.setText(decompressedText);
                    if (decompressedText.isEmpty() || decompressedText.isBlank()) {
                        showMessageDialog(null, "Decompression failed. Please try again.");
                    }
                } catch (Exception ex) {
                    showMessageDialog(null, "Error in decompressing text");
                }
            }
        }

        // Save output to a file
        if (command.equals(saveAsButton.getActionCommand())) {
            if (outputText.getText().isEmpty() || outputText.getText().isBlank()) {
                showMessageDialog(null, "Please select a file or add text to the input area and compress or decompress it.");
                return;
            }
            if (filenameField.getText().isEmpty() || filenameField.getText().isBlank()) {
                showMessageDialog(null, "Please enter a filename.");
                return;
            }

            try {
                String filename;
                if (inputText.isEditable()) {
                    // Determine file prefix based on selected compression algorithm
                    filename = huffmanAlg ? "compressedHuff" + filenameField.getText()
                            : "compressedNorm" + filenameField.getText();
                } else {
                    filename = filenameField.getText();
                }

                // Ensure the filename ends with .txt
                if (!filename.endsWith(".txt")) {
                    filename += ".txt";
                }

                FileManager.writeFile(filename, outputText.getText());
                showMessageDialog(null, "Success! File saved as " + filename + " in the same directory as the program.");
            } catch (Exception ex) {
                showMessageDialog(null, "Error in writing file");
            }
        }
    }
}
