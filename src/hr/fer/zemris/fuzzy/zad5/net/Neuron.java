package hr.fer.zemris.fuzzy.zad5.net;

import java.util.List;

public class Neuron {

    private double[] weights;
    private double net;

    public Neuron(int size) {
        this.weights = new double[size];
    }

    public double getOutput(List<Double> input) {
        if (input.size() != weights.length) {
            throw new IllegalArgumentException();
        }

        double value = 0;
        for (int i = 0; i < weights.length; i++) {
            value += weights[i] * input.get(i);
        }

        this.net = value;
        return sigmoid(this.net);
    }

    private double sigmoid(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    public double getOutput() {
        return sigmoid(this.net);
    }

    public double getNet() {
        return net;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getWeight(int i) {
        return weights[i];
    }

    public void setWeight(int i, double val) {
        weights[i] = val;
    }
}
