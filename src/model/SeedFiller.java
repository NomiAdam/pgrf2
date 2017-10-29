package model;

import java.awt.image.BufferedImage;

public class SeedFiller extends Renderer {
    private final int color = 0xffff00;
    private int pozadi;

    public SeedFiller(BufferedImage img) {
        super(img);
    }

    public void setBackColor(int color) {
        this.pozadi = color;
    }

    public void seed(int x, int y) {
        if (x >= 0 && y >= 0 && x < img.getWidth() && y < img.getHeight()) {
            if (img.getRGB(x, y) == pozadi && img.getRGB(x, y) != color) {
                img.setRGB(x, y, color);
                seed(x - 1, y);
                seed(x + 1, y);
                seed(x, y - 1);
                seed(x, y + 1);
            }
        }
    }
}

