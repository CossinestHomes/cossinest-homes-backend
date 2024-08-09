package com.cossinest.homes.utils;

import com.cossinest.homes.exception.BadRequestException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

    /*
    compressImage(byte[] data)
Bu metod, verilen byte dizisini (genellikle bir resmi temsil eden) sıkıştırmak için kullanılır. İşlemi şu şekilde gerçekleştirir:
Deflater: Sıkıştırma işlemi için kullanılan sınıftır. Deflater sınıfı, verileri ZIP formatında sıkıştırır.
Sıkıştırma Seviyesi: Deflater.BEST_COMPRESSION ile sıkıştırma seviyesi en yükseğe ayarlanır.
Bu, daha fazla sıkıştırma sağlar ancak işlem süresi biraz daha uzun olabilir.
Veri Akışı: Veriler Deflater'a verilir, sıkıştırma işlemi yapılır ve sonuç ByteArrayOutputStream'e yazılır.
Sonuç: Sıkıştırılmış byte dizisi olarak döner.*/

    public static byte[] compressImage(byte[] data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();


            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            deflater.end();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new BadRequestException("Cannot compress image");
        }
    }

    /*
     decompressImage(byte[] data)
Bu metod, sıkıştırılmış byte dizisini açmak için kullanılır. İşlemi şu şekilde gerçekleştirir:
Inflater: Sıkıştırılmış verileri açmak için kullanılan sınıftır. Inflater sınıfı, ZIP formatında sıkıştırılmış verileri geri açar.
Veri Akışı: Sıkıştırılmış veri Inflater'a verilir ve açılmış veriler ByteArrayOutputStream'e yazılır.
Sonuç: Açılmış byte dizisi olarak döner*/

    public static byte[] decompressImage(byte[] data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {

            Inflater inflater = new Inflater();
            inflater.setInput(data);
            byte[] tmp = new byte[4 * 1024];

            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            inflater.end();
            return outputStream.toByteArray();
        } catch (IOException | java.util.zip.DataFormatException e) {
            throw new BadRequestException("Cannot decompress image");
        }
    }

}
