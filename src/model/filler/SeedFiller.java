package model.filler;

import model.helper.Renderer;

import java.awt.image.BufferedImage;

/**
 * Trida po vyplneni oblasti pomoci Seminkoveho algoritmu
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class SeedFiller extends Renderer {
    private int color;
    private int background;

    public SeedFiller(BufferedImage img) {
        super(img);
    }

    public SeedFiller(BufferedImage img, int color) {
        super(img);
        this.color = color;
    }

    public void setBackColor(int color) {
        this.background = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Metoda pro vyplneni oblasti rekurzivni cestou
     *
     * @param x bod x pocatecniho seminka
     * @param y bod y pocatecniho seminka
     */
    public void seed(int x, int y) {
        if (x >= 0 && y >= 0 && x < img.getWidth() && y < img.getHeight()) {
            if (img.getRGB(x, y) == background && img.getRGB(x, y) != color) {
                img.setRGB(x, y, color);
                seed(x - 1, y);
                seed(x + 1, y);
                seed(x, y - 1);
                seed(x, y + 1);
            }
        }
    }
}

