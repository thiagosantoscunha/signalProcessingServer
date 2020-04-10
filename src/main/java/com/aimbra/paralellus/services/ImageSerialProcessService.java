package com.aimbra.paralellus.services;

import com.aimbra.paralellus.models.DataResponse;
import com.aimbra.paralellus.models.PayloadInnerFile;
import com.aimbra.paralellus.utils.FileUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

@Service
public class ImageSerialProcessService {
    private ByteArrayInputStream byteArrayInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private BufferedImage imageBuffered;

    private BufferedImage getBufferedImage(byte[] imageByte) throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(imageByte);
        return ImageIO.read(byteArrayInputStream);
    }

    private void save() {
        try {
            File outputFile = new File(("storage/image.png"));
            ImageIO.write(imageBuffered, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getImageSavedInBytes() {
        try {
            File inputFile = new File("storage/image.png");
            BufferedImage read = ImageIO.read(inputFile);

            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(read, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DataResponse serialProcess(PayloadInnerFile file) {
        try {
            LocalTime start = LocalTime.now();

            byte[] imageByte = FileUtils.decodeImage(
                FileUtils.getImageString(file.getImage())
            );
            imageBuffered = getBufferedImage(imageByte);
            byteArrayInputStream.close();
            save();
            LocalTime end = LocalTime.now();
            String imageBase64 = FileUtils.encodeImage(getImageSavedInBytes());
            return new DataResponse(start, end, end.getNano() - start.getNano(), imageBase64);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
