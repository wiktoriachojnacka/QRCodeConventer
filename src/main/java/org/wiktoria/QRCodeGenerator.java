package org.wiktoria;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {

    //Generating QR code with colors
    public BufferedImage generateQRCodeImage(String barcodeText, int width, int height, Color bgColor, Color qrColor) throws Exception {
        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        // Background
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(bgColor);
        graphics.fillRect(0, 0, width, height);

        // QR code with color
        graphics.setColor(qrColor);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    image.setRGB(x, y, qrColor.getRGB());
                }
            }
        }
        return image;
    }

    // Save QR code to file with colors
    public void saveQRCodeImage(String barcodeText, int width, int height, String filePath, Color bgColor, Color qrColor) throws Exception {
        BufferedImage image = generateQRCodeImage(barcodeText, width, height, bgColor, qrColor);
        File file = new File(filePath);
        ImageIO.write(image, "PNG", file);
    }
}
