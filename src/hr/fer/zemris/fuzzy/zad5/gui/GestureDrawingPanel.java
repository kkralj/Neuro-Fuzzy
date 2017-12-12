package hr.fer.zemris.fuzzy.zad5.gui;

import hr.fer.zemris.fuzzy.zad5.DataPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GestureDrawingPanel extends JPanel {

    private List<DataPoint> points = new ArrayList<>();

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
                Point p = mouseEvent.getPoint();
                Graphics g = getGraphics();
                g.setColor(Color.blue);
                g.fillOval(p.x, p.y, 5, 5);
                g.dispose();

                points.add(new DataPoint(p.x, p.y));
            }
        });
    }

    public void clear() {
        this.points.clear();
        this.revalidate();
        this.repaint();
    }

    public List<DataPoint> getPoints() {
        return points;
    }
}