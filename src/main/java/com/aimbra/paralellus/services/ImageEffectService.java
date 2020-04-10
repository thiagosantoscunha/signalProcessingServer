package com.aimbra.paralellus.services;

import com.aimbra.paralellus.utils.FileUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ImageEffectService {

    private BufferedImage image;
    private String imageName, extension;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageByte;

    private Color getRgbByImage(int x, int y) {
        int rgb = image.getRGB(x, y);
        return new Color(rgb);
    }

    private int getLuminance(int red, int green, int blue) {
        int r = (int)(red* 0.299);
        int g = (int)(green * 0.587);
        int b = (int)(blue *0.114);
        return Math.round(r + g + b);
    }

    private Color processToGrayscale(Color realColor) {
        int luminance = getLuminance(realColor.getRed(), realColor.getGreen(), realColor.getBlue());
        return new Color(luminance, luminance, luminance);
    }

    private void proccessImage(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color realColor = getRgbByImage(i, j);
                Color grayscale = processToGrayscale(realColor);
                image.setRGB(i, j, grayscale.getRGB());
            }
        }
    }

    public ImageEffectService setParams(String imageName, String extension) {
        this.imageName = imageName == null ? "image" : imageName;
        this.extension = extension == null ? "png" : extension;
        return this;
    }

    public ImageEffectService toGrayscale(){
        try {
            File input = new File("storage/" + imageName + "." + extension);
            image = ImageIO.read(input);
            proccessImage(image.getWidth(), image.getHeight());
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, extension, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return this;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getBase64() {
        return FileUtils.encodeImage(imageByte);
    }
}
