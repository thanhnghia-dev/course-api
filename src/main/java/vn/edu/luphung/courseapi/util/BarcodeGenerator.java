package vn.edu.luphung.courseapi.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class BarcodeGenerator {

    public static byte[] generateBarcodeWithText(String studentId, int width, int height) throws Exception {
        BitMatrix matrix = new MultiFormatWriter()
                .encode(studentId, BarcodeFormat.CODE_128, width, height);

        // Render barcode
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(matrix, new MatrixToImageConfig());

        // Create a new higher image to have area to write number
        int fontSize = 16;
        BufferedImage combined = new BufferedImage(width, height + 30, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = combined.createGraphics();

        // White background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, combined.getWidth(), combined.getHeight());

        // Draw barcode
        g.drawImage(barcodeImage, 0, 0, null);

        // Draw studentId under the image
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(studentId);
        int x = (width - textWidth) / 2;
        int y = height + (30 + fontSize) / 2 - 5;
        g.drawString(studentId, x, y);

        g.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(combined, "png", out);
        return out.toByteArray();
    }

    // Save to file
    public static void saveBarcodeToFile(String studentId, String filePath) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(studentId, BarcodeFormat.CODE_128, 300, 100);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, "png", path);
    }
}
