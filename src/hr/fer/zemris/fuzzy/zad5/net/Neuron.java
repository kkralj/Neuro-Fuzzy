package hr.fer.zemris.fuzzy.zad5.net;

import hr.fer.zemris.fuzzy.zad5.net.layers.HiddenLayer;
import hr.fer.zemris.fuzzy.zad5.net.layers.OutputLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {

    private List<Double> weights = new ArrayList<>();

    private boolean isInputLayer;
    private double value;
    private double delta;

    private static Random random = new Random();

    public Neuron(int size, boolean isInputLayer) {
        this.isInputLayer = isInputLayer;
        for (int i = 0; i < size; i++) {
            weights.add(isInputLayer ? 1.0 : random.nextDouble());
        }
    }

    public void forwardPass(int pos, List<Neuron> neurons) {
        double value = 0;

        for (Neuron neuron : neurons) {
            value += neuron.getOutput() * neuron.getWeight(pos);
        }

        this.value = isInputLayer ? value : sigmoid(value);
    }

    private double sigmoid(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    public double getOutput() {
        return value;
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

    public void setValue(double value) {
        this.value = value;
    }

    public void updateWeight(double lr, OutputLayer outputLayer) {
        List<Neuron> neurons = outputLayer.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            weights.set(i, weights.get(i) + lr * this.value * neurons.get(i).getDelta());
        }
    }

    public void updateWeight(double lr, HiddenLayer nextLayer) {
        List<Neuron> neurons = nextLayer.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            weights.set(i, weights.get(i) + lr * this.value * neurons.get(i).getDelta());
        }
    }
}
