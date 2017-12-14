package hr.fer.zemris.fuzzy.zad5.gui;

import hr.fer.zemris.fuzzy.zad5.DataPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GestureDrawingPanel extends JPanel {

    private List<Point> points = new ArrayList<>();

    public GestureDrawingPanel() {
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        init();
    }

    private void init() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                super.mouseDragged(mouseEvent);
                points.add(mouseEvent.getPoint());
                repaint();
                revalidate();
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.blue);
        for (Point point : points) {
            graphics.fillOval(point.x, point.y, 5, 5);
        }
    }

    public void clear() {
        points.clear();
        repaint();
        revalidate();
    }

    public List<DataPoint> getPoints() {
        List<DataPoint> dataPoints = new ArrayList<>();
        for (Point point : points) {
            dataPoints.add(new DataPoint(point.x, point.y));
        }
        return dataPoints;
    }
}