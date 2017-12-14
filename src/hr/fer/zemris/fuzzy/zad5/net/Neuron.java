package hr.fer.zemris.fuzzy.zad5.net;

import hr.fer.zemris.fuzzy.zad5.net.layers.HiddenLayer;
import hr.fer.zemris.fuzzy.zad5.net.layers.OutputLayer;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private List<Double> weights = new ArrayList<>();

    private boolean isInputLayer;
    private double value;
    private double delta;

    public Neuron(int size, boolean isInputLayer) {
        this.isInputLayer = isInputLayer;
        for (int i = 0; i < size; i++) {
            weights.add(0.0);
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

    public void updateWeight(double lr, OutputLayer outputLayer, double output) {
        List<Neuron> neurons = outputLayer.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            weights.set(i, weights.get(i) + lr * output * neurons.get(i).getDelta());
        }
    }

    public void updateWeight(double lr, HiddenLayer nextLayer, double output) {
        List<Neuron> neurons = nextLayer.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            weights.set(i, weights.get(i) + lr * output * neurons.get(i).getDelta());
        }
    }
}
