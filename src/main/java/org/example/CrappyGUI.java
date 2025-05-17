package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class CrappyGUI implements ActionListener {
    private JButton switchModeButton;
    private JComboBox fileSelect;
    private JButton selectFileButton;
    private JTextArea inputText;
    private JButton compressButton;
    private JTextArea outputText;
    private JButton saveAsButton;
    private JTextField filenameField;
    private JPanel mainPanel;
    private JFrame frame;

    public CrappyGUI() {
        switchModeButton.addActionListener(this);
        selectFileButton.addActionListener(this);
        compressButton.addActionListener(this);
        saveAsButton.addActionListener(this);
        inputText.setLineWrap(true);
        inputText.setAutoscrolls(true);
        outputText.setLineWrap(true);
        outputText.setAutoscrolls(true);
        ArrayList<String> fileNames = new ArrayList<>();
        File[] files = new File("/Users/beckm/IdeaProjects/CompressionProject2").listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                fileNames.add(file.getName());
            }
        }
        fileSelect.setModel(new DefaultComboBoxModel<>(fileNames.toArray(new String[0])));
        outputText.setEditable(false);
        inputText.setEditable(true);
        filenameField.setText("File.txt");
        frame = new JFrame("Compressor");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(switchModeButton.getActionCommand())) {
            fileSelect.removeAllItems();
            if (inputText.isEditable()) {
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
                ArrayList<String> fileNames = new ArrayList<>();
                File[] files = new File("/Users/beckm/IdeaProjects/CompressionProject2").listFiles();
                for (File file : files) {
                    if (file.getName().endsWith(".txt")) {
                        fileNames.add(file.getName());
                    }
                }
                compressButton.setText("\\/ Compress \\/");

                fileSelect.setModel(new DefaultComboBoxModel<>(fileNames.toArray(new String[0])));
                inputText.setEditable(true);
                outputText.setEditable(false);
                switchModeButton.setText("Switch to Decompression Mode");
            }
        }
        if (command.equals(selectFileButton.getActionCommand())) {
            if (fileSelect.getSelectedIndex() == -1) {
                showMessageDialog(null, "Please select a file or add text to the input area.");
                return;
            }
            if (fileSelect.getSelectedItem() == null) {
                showMessageDialog(null, "Please create a file or add text to the input area.");
                return;
            }
            try {
                String content = FileManager.readFile((String) fileSelect.getSelectedItem());
                inputText.setText(content);
            } catch (Exception ex) {
                showMessageDialog(null, "Error in reading file");
                return;
            }
        }
        if (command.equals(compressButton.getActionCommand())) {
            if (inputText.isEditable()) {
                try {
                    if (inputText.getText().equals("")) {
                        showMessageDialog(null, "Please select a file or add text to the input area.");
                        return;
                    }
                    if (inputText.getText().isBlank()) {
                        showMessageDialog(null, "Please select a file or add text to the input area.");
                        return;
                    }
                    String compressedText = CompressionAlg3.compress(inputText.getText());
                    outputText.setText(compressedText);
                } catch (Exception ex) {
                    showMessageDialog(null, "Error in compressing text");
                }
            } else {
                try {
                    if (inputText.getText().equals("")) {
                        showMessageDialog(null, "Please select a file or add text to the input area.");
                        return;
                    }
                    if (inputText.getText().isBlank()) {
                        showMessageDialog(null, "Please select a file or add text to the input area.");
                    }
                    String decompressedText = CompressionAlg3.decompress(inputText.getText());
                    outputText.setText(decompressedText);
                    if (decompressedText.equals("")) {
                        showMessageDialog(null, "Decompression failed. Please try again.");
                    }
                    if (decompressedText.isBlank()) {
                        showMessageDialog(null, "Decompression failed. Please try again.");
                    }
                } catch (Exception ex) {
                    showMessageDialog(null, "Error in decompressing text");
                    return;
                }
            }

        }
        if (command.equals(saveAsButton.getActionCommand())) {
            if (outputText.getText().equals("")) {
                showMessageDialog(null, "Please select a file or add text to the input area and compress or decompress it.");
                return;
            }
            if (outputText.getText().isBlank()) {
                showMessageDialog(null, "Please select a file or add text to the input area and compress or decompress it.");
                return;
            }
            if (filenameField.getText().equals("")) {
                showMessageDialog(null, "Please enter a filename.");
                return;
            }
            if (filenameField.getText().isBlank()) {
                showMessageDialog(null, "Please enter a filename.");
                return;
            }
            try {
                String filename;
                if (inputText.isEditable()) {
                    filename = "compressed" + filenameField.getText();
                } else {
                    filename = filenameField.getText();
                }
                if (!filenameField.getText().endsWith(".txt")) filename = filename + ".txt";

                FileManager.writeFile(filename, outputText.getText());
                showMessageDialog(null,"Success! File saved as " + filenameField.getText() + " in the same directory as the program.");
            } catch (Exception ex) {
                showMessageDialog(null, "Error in writing file");
            }
        }
    }
}
