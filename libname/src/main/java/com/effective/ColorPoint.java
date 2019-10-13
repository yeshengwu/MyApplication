package com.effective;

import java.awt.Color;

/**
 * Created by shidu on 17/10/26.
 * effective 31
 */

public class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint)) {
            System.out.println("o ! instanceof ColorPoint");
            return false;
        }
        return super.equals(o) && ((ColorPoint) o).color == color;
    }
}
