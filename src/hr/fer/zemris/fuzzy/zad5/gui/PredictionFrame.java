package hr.fer.zemris.fuzzy.zad5.gui;

import hr.fer.zemris.fuzzy.zad5.DataPoint;
import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PredictionFrame extends JFrame {

    private GestureDrawingPanel drawingArea;

    private int M;

    private NeuralNetwork nn;

    public PredictionFrame(int M, NeuralNetwork nn) {
        this.nn = nn;
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

        JButton predict = new JButton("Predict");
        predict.addActionListener(actionEvent -> predict(drawingArea.getPoints()));
        buttonsPanel.add(predict);

        JButton clear = new JButton("Clear");
        buttonsPanel.add(clear);
        clear.addActionListener(actionEvent -> drawingArea.clear());
    }

    private void predict(java.util.List<DataPoint> points) {
        List<DataPoint> transformedPoints = DataPoint.transformPoints(points, M);
        List<Double> prediction = nn.predict(DataPoint.flatten(transformedPoints));

        System.out.println(prediction);
        JOptionPane.showMessageDialog(null, prediction);

        drawingArea.clear();
    }
}
