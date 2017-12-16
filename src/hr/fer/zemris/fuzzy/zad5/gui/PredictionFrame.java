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

        this.setTitle("Prediction frame");
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

        String text = String.format("Alpha %.3f%%\n", prediction.get(0) * 100) +
                String.format("Beta %.3f%%\n", prediction.get(1) * 100) +
                String.format("Gamma %.3f%%\n", prediction.get(2) * 100) +
                String.format("Delta %.3f%%\n", prediction.get(3) * 100) +
                String.format("Epsilon %.3f%%\n", prediction.get(4) * 100);

        System.out.println(text);
        JOptionPane.showMessageDialog(this, text);

        drawingArea.clear();
    }
}
