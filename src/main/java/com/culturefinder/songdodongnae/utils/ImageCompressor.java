package com.culturefinder.songdodongnae.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageCompressor {

    public byte[] compressImage(MultipartFile file, String format, long MAX_SIZE) throws Exception {
        BufferedImage image = ImageIO.read(file.getInputStream());
        float quality = 1.0f;
        byte[] compressedImage = file.getBytes();

        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            while (compressedImage.length > MAX_SIZE && quality > 0.1) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);

                writer.write(null, new IIOImage(image, null, null), param);
                writer.dispose();
                ios.close();

                compressedImage = baos.toByteArray();
                quality -= 0.05f;
            }
        }
        return compressedImage;
    }
}
