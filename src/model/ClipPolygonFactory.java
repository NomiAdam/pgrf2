package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClipPolygonFactory {
    private List<Point> polygon;

    public ClipPolygonFactory(int max_width, int max_height) {
        polygon = new ArrayList<>();
        polygon.add(new Point(max_width / 2 - 200, max_height / 2 - 100));
        polygon.add(new Point(max_width / 2 - 200, max_height / 2 + 100));
        polygon.add(new Point(max_width / 2 + 200, max_height / 2 + 100));
        polygon.add(new Point(max_width / 2 + 200, max_height / 2 - 100));
    }

    public List<Point> getClippingPolygon() {
        return polygon;
    }

}
