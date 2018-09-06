package ru.timestop.generator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Вспомогательный класс для создания bmp изображений
 * со случайным содержимым, размером 3 x 3
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 25.07.2017
 */
public class BmpGenerator {

    /**
     * RGB color representation
     */
    private static class RGB {
        byte r;
        byte b;
        byte g;
    }

    // Размер BMP заголовка
    private final int BMP_HEADER_SIZE = 14;

    // Размер DIB заголовка
    private final int DIB_HEADER_SIZE = 40;

    // Размер области для хранения пикселов
    private final int PIXEL_ARRAY_SIZE = 36;

    // Размер палета
    static private final int PALETTE_SIZE = 16;

    // Стандартный BMP заголовок для изображения 3 x 3
    private final byte[] bmpHeader = {0x42, 0x4D, (BMP_HEADER_SIZE + DIB_HEADER_SIZE + PIXEL_ARRAY_SIZE),
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x36, 0x00, 0x00, 0x00};

    // Стандартный DIB заголовок для изображения 3 x 3
    private final byte[] dibHeader = {0x28, 0x00, 0x00, 0x00, /* width */ 0x03, 0x00, 0x00, 0x00, /* height */ 0x03,
            0x00, 0x00, 0x00, 0x01, 0x00, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00, PIXEL_ARRAY_SIZE,
            0x00, 0x00, 0x00, 0x13, 0x0B, 0x00, 0x00, 0x13, 0x0B, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    // Область для хранения пикселов
    private byte[] pixelArr = new byte[PIXEL_ARRAY_SIZE];

    /**
     * Colors for palette initialization
     * all R, G, B components will be equal to one of these values
     */
    // 0, 120, 240
    private static byte[] possibleRGBValues = {0 - 127, 120 - 127, 240 - 127};

    // Set of possible RGB colors
    private static RGB[] palette;

    // Random generator
    private static Random rand = new Random();

    /**
     * Initialize palette with unique RGB combinations
     * of colors from possibleRGBValues array
     */
    static {
        palette = new RGB[PALETTE_SIZE];
        for (int i = 0; i < PALETTE_SIZE; i++) {
            palette[i] = new RGB();
        }
        int paletteIndex = 0;
        fillPalette:
        for (byte colorR : possibleRGBValues) {
            palette[paletteIndex].r = colorR;

            for (byte colorG : possibleRGBValues) {
                palette[paletteIndex].g = colorG;

                for (byte colorB : possibleRGBValues) {
                    palette[paletteIndex].b = colorB;
                    paletteIndex++;
                    if (paletteIndex >= PALETTE_SIZE) {
                        break fillPalette;
                    }
                }
            }
        }

    }

    /**
     * Create and return a new random bmp
     *
     * @return byte array with new randomly generated bmp
     */
    public byte[] getBmp() {

        byte[] finalArr = new byte[bmpHeader.length + dibHeader.length + pixelArr.length];

        fillPixelArrFromPalette();

        System.arraycopy(bmpHeader, 0, finalArr, 0, bmpHeader.length);
        System.arraycopy(dibHeader, 0, finalArr, bmpHeader.length, dibHeader.length);
        System.arraycopy(pixelArr, 0, finalArr, bmpHeader.length + dibHeader.length, pixelArr.length);

        return finalArr;
    }

    /**
     * Fill pixel array using palette
     * BMP size 3 x 3 (hard-code)
     */
    private void fillPixelArrFromPalette() {

        for (int i = 0; i < pixelArr.length; i += 3) {
            if (i == 9 || i == 21 || i == 33) { // padding
                putPadding(i, i + 3, pixelArr);
            } else {
                RGB currentPixel = palette[rand.nextInt(PALETTE_SIZE)];
                putRGBInByteArray(i, currentPixel, pixelArr);
            }
        }
    }

    /**
     * Put padding in byte array from index bound1 to bound2
     * (alpha channel handling)
     *
     * @param bound1 begin index
     * @param bound2 end index (excluded)
     * @param arr    target byte array
     */
    private void putPadding(int bound1, int bound2, byte[] arr) {
        for (int i = bound1; i < bound2; i++)
            arr[i] = 0x00;
    }

    /**
     * Put RGB value in certain position of byte array
     *
     * @param index position in byte array to insert
     * @param pixel RGB value to insert
     * @param arr   target array
     */
    private void putRGBInByteArray(int index, RGB pixel, byte[] arr) {
        arr[index] = pixel.r;
        arr[index + 1] = pixel.g;
        arr[index + 2] = pixel.b;
    }

    /**
     * @param bufferedImage
     * @param path
     */
    public static void saveBmp(final BufferedImage bufferedImage, final String path) throws IOException {
        RenderedImage renderedImage = bufferedImage;
        ImageIO.write(renderedImage, "bmp", new File(path));

    }

}
