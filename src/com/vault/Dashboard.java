package com.vault;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class Dashboard extends JFrame {
    private static final long serialVersionUID = 1L;

    public Dashboard() {
        // UI Setup - Simplified (Removed Progress Bar)
        setTitle("Secure Bytes Vault");
        setSize(400, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10)); // Changed to 3 rows

        JLabel titleLabel = new JLabel("Advanced File Encryption System", SwingConstants.CENTER);
        JButton encryptBtn = new JButton("Encrypt File");
        JButton decryptBtn = new JButton("Decrypt File");

        encryptBtn.addActionListener(e -> handleFile(true));
        decryptBtn.addActionListener(e -> handleFile(false));

        add(titleLabel);
        add(encryptBtn);
        add(decryptBtn);

        setLocationRelativeTo(null);
    }

    private void handleFile(boolean isEncrypt) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            String filePassword = JOptionPane.showInputDialog(this, 
                "Enter " + (isEncrypt ? "Encryption" : "Decryption") + " Password:");

            if (filePassword == null || filePassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Operation cancelled: Password required.");
                return;
            }

            new Thread(() -> {
                try {
                    byte[] fileData = Files.readAllBytes(selectedFile.toPath());
                    byte[] processedData;

                    if (isEncrypt) {
                        processedData = CryptoEngine.encrypt(fileData, filePassword);
                        Files.write(new File(selectedFile.getAbsolutePath() + ".vault").toPath(), processedData);
                        JOptionPane.showMessageDialog(this, "Success! File stored in vault.");
                    } else {
                        processedData = CryptoEngine.decrypt(fileData, filePassword);
                
                        String originalPath = selectedFile.getAbsolutePath().replace(".vault", "");
                        int lastDot = originalPath.lastIndexOf('.');

                        String outPath;
                        if (lastDot != -1) {
                           
                            outPath = originalPath.substring(0, lastDot) + "_recovered" + originalPath.substring(lastDot);
                        } else {
                            outPath = originalPath + "_recovered";
                        }
                        
                        Files.write(new File(outPath).toPath(), processedData);
                        JOptionPane.showMessageDialog(this, "Success! File decrypted.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: Check your password or file integrity.");
                    ex.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}