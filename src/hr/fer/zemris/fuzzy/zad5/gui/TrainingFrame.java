package hr.fer.zemris.fuzzy.zad5.gui;

import hr.fer.zemris.fuzzy.zad5.DataPoint;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TrainingFrame extends JFrame {

    private GestureDrawingPanel drawingArea;

    private int M;

    public TrainingFrame(int M) {
        this.M = M;

        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        drawingArea = new GestureDrawingPanel();
        this.add(drawingArea, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        this.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setLayout(new FlowLayout());

        JButton alpha = new JButton("Classify as Alpha");
        alpha.addActionListener(actionEvent -> savePoints(drawingArea.getPoints(), 0));
        buttonsPanel.add(alpha);

        JButton beta = new JButton("Classify as Beta");
        beta.addActionListener(actionEvent -> savePoints(drawingArea.getPoints(), 1));
        buttonsPanel.add(beta);

        JButton gamma = new JButton("Classify as Gamma");
        gamma.addActionListener(actionEvent -> savePoints(drawingArea.getPoints(), 2));
        buttonsPanel.add(gamma);

        JButton delta = new JButton("Classify as Delta");
        delta.addActionListener(actionEvent -> savePoints(drawingArea.getPoints(), 3));
        buttonsPanel.add(delta);

        JButton epsilon = new JButton("Classify as Epsilon");
        epsilon.addActionListener(actionEvent -> savePoints(drawingArea.getPoints(), 4));
        buttonsPanel.add(epsilon);

        JButton clear = new JButton("Clear");
        buttonsPanel.add(clear);
        clear.addActionListener(actionEvent -> drawingArea.clear());
    }

    private void savePoints(List<DataPoint> points, int y) {

        List<DataPoint> transformedPoints = DataPoint.transformPoints(points, M);

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("train.txt", true)))) {
            for (DataPoint point : transformedPoints) {
                out.println(point);
            }
            out.println(oneHot(y));
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawingArea.clear();
    }

    private String oneHot(int y) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i == y) result.append("1");
            else result.append("0");
        }
        return result.toString();
    }
}
