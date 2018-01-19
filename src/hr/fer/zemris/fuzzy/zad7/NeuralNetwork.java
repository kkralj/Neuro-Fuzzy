package hr.fer.zemris.fuzzy.zad7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NeuralNetwork {

    private int[] layers;
    private double[] outputs;
    private TrainingData trainingData;

    public NeuralNetwork(TrainingData trainingData, int[] hiddenLayers) {
        if (hiddenLayers.length < 1) {
            throw new IllegalArgumentException("At least one hidden layer required.");
        }

        this.trainingData = trainingData;
        this.layers = new int[hiddenLayers.length + 2];

        layers[0] = 2; // input layer
        layers[layers.length - 1] = 3; // output layer
        System.arraycopy(hiddenLayers, 0, layers, 1, hiddenLayers.length); // hidden layers

        outputs = new double[getOutputCount()];
    }

    private int getOutputCount() {
        int tot = 0;
        for (int layer : layers) {
            tot += layer;
        }
        return tot;
    }

    public int getParameterCount() {
        int tot = layers[0] * (2 * layers[1]);

        for (int i = 2; i < layers.length; i++) {
            tot += (layers[i - 1] + 1) * layers[i];
        }

        return tot;
    }

    private double f1(double x) {
        return 1. / (1 + x);
    }

    private double f2(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    public double calcError(double[] params) {
        double mse = 0;

        for (Point p : trainingData) {
            calcOutput(p.getX1(), p.getX2(), params);

            for (int i = 0; i < 3; i++) {
                mse += Math.pow(p.getY()[i] - outputs[(outputs.length - 3) + i], 2);
            }
        }

        return mse / trainingData.getSize();
    }

    private void calcOutput(double x1, double x2, double[] params) {
        outputs[0] = x1;
        outputs[1] = x2;

        int paramInd = 0, outputInd = 2;

        // first hidden layer (f1)
        for (int j = 0; j < layers[1]; j++) {
            double net = f1(
                    Math.abs(x1 - params[paramInd++]) / Math.abs(params[paramInd++]) +
                    Math.abs(x2 - params[paramInd++]) / Math.abs(params[paramInd++])
            );
            outputs[outputInd++] = net;
        }

        int prevOutputInd = 2;

        // rest (f2)
        for (int i = 2; i < layers.length; i++) {
            for (int j = 0; j < layers[i]; j++) {
                double net = params[paramInd++]; // w0

                for (int q = 0; q < layers[i - 1]; q++) {
                    net += params[paramInd++] * outputs[prevOutputInd + q];
                }

                outputs[outputInd++] = f2(net);
            }
            prevOutputInd += layers[i - 1];
        }

    }

    public void evaluate(double[] params) {
        int correct = 0;
        for (Point p : trainingData) {
            calcOutput(p.getX1(), p.getX2(), params);

            boolean valid = true;

            for (int i = 0; i < 3; i++) {
                int truncated = outputs[outputs.length - 3 + i] >= 0.5 ? 1 : 0;
                valid &= truncated == p.getY()[i];
            }

            correct += valid ? 1 : 0;
        }

        System.out.println("Correct: " + correct + ", Incorrect: " + (trainingData.getSize() - correct));
    }

    public void storeParameters(String path, double[] parameters) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
        out.write(layers[1] + "\n");
        for (double param : parameters) {
            out.write(param + "\n");
        }
        out.close();
    }
}
