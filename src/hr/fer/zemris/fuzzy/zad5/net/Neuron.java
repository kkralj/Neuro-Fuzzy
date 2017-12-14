package hr.fer.zemris.fuzzy.zad5.net;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private List<Double> weights = new ArrayList<>();
    private double value;
    private double delta;

    public Neuron(int size) {
        for (int i = 0; i < size; i++) {
            weights.add(0.0);
        }
    }

    public void forwardPass(List<Double> input) {
        if (input.size() != weights.size()) {
            throw new IllegalArgumentException();
        }

        double value = 0;
        for (int i = 0; i < weights.size(); i++) {
            value += weights.get(i) * input.get(i);
        }

        this.value = sigmoid(value);
    }

    private double sigmoid(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    public double getOutput() {
        return value;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getWeight(int i) {
        return weights.get(i);
    }

    public void setWeight(int i, double val) {
        weights.set(i, val);
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }
}
