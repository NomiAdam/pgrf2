package model.filler;

import model.helper.Renderer;

import java.awt.image.BufferedImage;

/**
 * Trida pro vyplneni oblasti vzorem
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class SeedFillerPatern extends Renderer {
    private static final int color_one = 0xffff00;
    private static final int color_two = 0xff00ff;
    private int background;

    //Matice predstavujici vzor
    private int[][] pattern = {
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
    };

    public SeedFillerPatern(BufferedImage img) {
        super(img);
    }

    public void setBackColor(int color) {
        this.background = color;
    }

    public void seed(int x, int y) {
        if (x >= 0 && y >= 0 && x < img.getWidth() && y < img.getHeight()) {
            if (img.getRGB(x, y) == background && (img.getRGB(x, y) != color_one || img.getRGB(x, y) != color_two)) {
                if (pattern[x % 4][y % 4] == 0) {
                    img.setRGB(x, y, color_one);
                } else {
                    img.setRGB(x, y, color_two);
                }
                seed(x - 1, y);
                seed(x + 1, y);
                seed(x, y - 1);
                seed(x, y + 1);
            }
        }
    }
}
