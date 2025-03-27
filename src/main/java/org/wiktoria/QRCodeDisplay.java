package org.wiktoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRCodeDisplay {
    private JLabel qrLabel;

    public QRCodeDisplay() {
        qrLabel = new JLabel();
        qrLabel.setHorizontalAlignment(JLabel.CENTER);
        qrLabel.setVerticalAlignment(JLabel.CENTER);
    }

    public void createDisplayWindow() {
        JFrame frame = new JFrame("QR Code Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(20, 20, 40)); // Dark background for a cybernetic look

        qrLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(qrLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(30, 30, 60));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // Text field to enter URL with dark background and white text
        JTextField textField = new JTextField("Enter URL...");
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(Color.WHITE); // White text
        textField.setBackground(new Color(30, 30, 60));  // Background in the section's color
        textField.setCaretColor(Color.WHITE);  // Cursor color
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Thin white border
        textField.setCaretPosition(0);  // Cursor at the beginning

        // Removing text when clicked
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Enter URL...")) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE); // Change text color to black
                }
            }

            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("Enter URL...");
                    textField.setForeground(Color.WHITE); // Restore white text color
                }
            }
        });

        // Larger field with rounded corners
        textField.setPreferredSize(new Dimension(380, 26));  // Larger field
        textField.setHorizontalAlignment(JTextField.LEFT); // Align text to the left
        inputPanel.add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(30, 30, 60));
        JButton generateButton = createStyledButton("Generate QR");
        JButton saveButton = createStyledButton("Save QR");

        // Shrinking buttons
        generateButton.setPreferredSize(new Dimension(132, 38));
        saveButton.setPreferredSize(new Dimension(132, 38));

        buttonPanel.add(generateButton);
        buttonPanel.add(saveButton);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(inputPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> {
            String url = textField.getText();
            if (!url.isEmpty() && !url.equals("Enter URL...")) {
                try {
                    QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
                    // Background and QR code colors
                    BufferedImage qrImage = qrCodeGenerator.generateQRCodeImage(url, 300, 300, new Color(30, 30, 60), Color.WHITE);
                    showQRCode(qrImage);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error generating QR code: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid URL.");
            }
        });

        saveButton.addActionListener(e -> {
            String url = textField.getText();
            if (!url.isEmpty() && !url.equals("Enter URL...")) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File("qrcode.png"));
                    int userSelection = fileChooser.showSaveDialog(frame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        // Background and QR code colors
                        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
                        qrCodeGenerator.saveQRCodeImage(url, 300, 300, fileToSave.getAbsolutePath(), new Color(30, 30, 60), Color.WHITE);
                        JOptionPane.showMessageDialog(frame, "QR code saved as " + fileToSave.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving QR code: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid URL.");
            }
        });

        frame.setSize(500, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showQRCode(BufferedImage qrImage) {
        ImageIcon icon = new ImageIcon(qrImage);
        qrLabel.setIcon(icon);
        qrLabel.repaint();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(100, 100, 255));
                } else {
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(92, 46, 244), 0, getHeight(), new Color(68, 205, 206));
                    g2d.setPaint(gradient);
                }
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(1)); // Thinner border
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Rounded corners
            }
        };
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Thinner font
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 35)); // Smaller button size
        button.setContentAreaFilled(false);
        return button;
    }
}
