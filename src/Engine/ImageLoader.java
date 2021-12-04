package Engine;

import Utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * contains a bunch of helpful methods for loading images file into the game
 */
public class ImageLoader {

    /**
     * loads an image and sets its transparent color to the one defined in the Config class
     *
     * @param imageFileName File name of the resource to load within the Resources folder
     *
     * @return Generated BufferedImage of the image
     */
    public static BufferedImage load(String imageFileName) {
        return load(imageFileName, Config.TRANSPARENT_COLOR);
    }

    /**
     * loads an image and allows the transparent color to be specified
     *
     * @param imageFileName    File name of the resource to load within the Resources folder
     * @param transparentColor Color to use as the "green screen" color (removed with transparency)
     *
     * @return Generated BufferedImage of the image
     */
    public static BufferedImage load(String imageFileName, Color transparentColor) {
        try {
            BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
            return ImageUtils.transformColorToTransparency(initialImage, transparentColor);
        } catch (IOException e) {
            System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
            throw new RuntimeException(e);
        }
    }

    /**
     * loads a piece of an image from an image file and sets its transparent color to the one defined in the Config class
     *
     * @param imageFileName File name of the resource to load within the Resources folder
     * @param x             The starting x position of the sub image
     * @param y             The starting y position of the sub image
     * @param width         The width of the sub image
     * @param height        The height of the sub image
     *
     * @return Generated BufferedImage of the sub image
     */
    public static BufferedImage loadSubImage(String imageFileName, int x, int y, int width, int height) {
        return ImageLoader.loadSubImage(imageFileName, Config.TRANSPARENT_COLOR, x, y, width, height);
    }

    /**
     * loads a piece of an image from an image file and allows the transparent color to be specified
     *
     * @param imageFileName    File name of the resource to load within the Resources folder
     * @param transparentColor Color to use as the "green screen" color (removed with transparency)
     * @param x                The starting x position of the sub image
     * @param y                The starting y position of the sub image
     * @param width            The width of the sub image
     * @param height           The height of the sub image
     *
     * @return Generated BufferedImage of the sub image
     */
    public static BufferedImage loadSubImage(String imageFileName, Color transparentColor, int x, int y, int width, int height) {
        try {
            BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
            BufferedImage transparentImage = ImageUtils.transformColorToTransparency(initialImage, transparentColor);
            return transparentImage.getSubimage(x, y, width, height);
        } catch (IOException e) {
            System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
            throw new RuntimeException(e);
        }
    }
}
