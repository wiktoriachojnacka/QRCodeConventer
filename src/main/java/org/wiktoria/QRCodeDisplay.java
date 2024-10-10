package org.wiktoria;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRCodeDisplay {

    private JLabel qrLabel;

    public QRCodeDisplay() {
        qrLabel = new JLabel();
        qrLabel.setHorizontalAlignment(JLabel.CENTER);
        qrLabel.setVerticalAlignment(JLabel.CENTER);
    }

    //Display window with QR code
    public void createDisplayWindow() {
        JFrame frame = new JFrame("QR Code Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        frame.add(qrLabel, gbc);

        //URL entry panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JLabel urlLabel = new JLabel("Submit URL:");
        inputPanel.add(urlLabel, BorderLayout.NORTH);

        JTextField textField = new JTextField("Enter URL here...");
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals("Enter URL here...")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText("Enter URL here...");
                }
            }
        });

        inputPanel.add(textField, BorderLayout.CENTER);

        //Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton generateButton = createRoundedButton("Generate QR");
        JButton saveButton = createRoundedButton("Save QR");

        buttonPanel.add(generateButton);
        buttonPanel.add(saveButton);

        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        gbc.gridy = 1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(inputPanel, gbc);

        //Action after pressing the “Generate QR Code”
        generateButton.addActionListener(e -> {
            String url = textField.getText();
            if (!url.isEmpty() && !url.equals("Enter URL here...")) {
                try {
                    QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
                    BufferedImage qrImage = qrCodeGenerator.generateQRCodeImage(url, 300, 300);
                    showQRCode(qrImage);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error generating QR code: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid URL.");
            }
        });

        //Action after pressing the “Save QR Code” button
        saveButton.addActionListener(e -> {
            String url = textField.getText();
            if (!url.isEmpty() && !url.equals("Enter URL here...")) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");
                    fileChooser.setSelectedFile(new File("qrcode.png"));

                    int userSelection = fileChooser.showSaveDialog(frame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
                        qrCodeGenerator.saveQRCodeImage(url, 300, 300, fileToSave.getAbsolutePath());
                        JOptionPane.showMessageDialog(frame, "QR code saved as " + fileToSave.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving QR code: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid URL.");
            }
        });

        //Set fixed window sizes
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    //Display QR code in the window
    public void showQRCode(BufferedImage qrImage) {
        ImageIcon icon = new ImageIcon(qrImage);
        qrLabel.setIcon(icon);
        qrLabel.repaint();
    }

    //Button's customization
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(16, 133, 212));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(120, 30);
            }
        };

        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
}
