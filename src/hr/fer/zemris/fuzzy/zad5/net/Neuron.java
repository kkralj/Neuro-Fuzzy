package hr.fer.zemris.fuzzy.zad5.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {

    private static Random random = new Random();

    private List<Double> weights = new ArrayList<>();
    private List<Double> tempWeights;

    private List<Double> biases = new ArrayList<>();
    private List<Double> tempBiases;

    private double value;
    private double delta;

    Neuron(int size) {
        for (int i = 0; i < size; i++) {
            weights.add(-1 + 2 * random.nextDouble());
            biases.add(-1 + 2 * random.nextDouble());
        }
        tempWeights = new ArrayList<>(weights);
        tempBiases = new ArrayList<>(biases);
    }

    public void forwardPass(int pos, List<Neuron> neurons) {
        double value = 0;

        for (Neuron neuron : neurons) {
            value += neuron.getOutput() * neuron.getWeight(pos) + neuron.biases.get(pos);
        }

        this.value = sigmoid(value);
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

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getDelta() {
        return delta;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void updateWeight(double lr, Layer nextLayer) {
        List<Neuron> neurons = nextLayer.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            tempWeights.set(i, tempWeights.get(i) + lr * this.value * neurons.get(i).getDelta());
            tempBiases.set(i, tempBiases.get(i) + lr * neurons.get(i).getDelta());
        }
    }

    public void swapWeight() {
        this.weights = this.tempWeights;
        this.biases = this.tempBiases;
    }
}
