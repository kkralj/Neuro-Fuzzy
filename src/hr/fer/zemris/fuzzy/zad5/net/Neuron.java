package hr.fer.zemris.fuzzy.zad5.net;

import hr.fer.zemris.fuzzy.zad5.net.layers.HiddenLayer;
import hr.fer.zemris.fuzzy.zad5.net.layers.OutputLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {

    private List<Double> weights = new ArrayList<>();
    private List<Double> biases = new ArrayList<>();

    private List<Double> tempBiases = new ArrayList<>();
    private List<Double> tempWeights = new ArrayList<>();

    private boolean isInputLayer;
    private double value;
    private double delta;

    private static Random random = new Random();

    public Neuron(int size, boolean isInputLayer) {
        this.isInputLayer = isInputLayer;
        for (int i = 0; i < size; i++) {
            double randWeight = -2 + 4 * random.nextDouble();
            weights.add(randWeight);
            tempWeights.add(randWeight);
            biases.add(0.);
            tempBiases.add(0.);
        }
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

    public List<Double> getWeights() {
        return weights;
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
            tempWeights.set(i, tempWeights.get(i) + lr * this.value * neurons.get(i).getDelta());
            tempBiases.set(i, tempBiases.get(i) + lr * neurons.get(i).getDelta());
        }
    }

    public void updateWeight(double lr, HiddenLayer nextLayer) {
        List<Neuron> neurons = nextLayer.getNeurons();
        double val = this.value;
        for (int i = 0; i < neurons.size(); i++) {
            tempWeights.set(i, tempWeights.get(i) + lr * val * neurons.get(i).getDelta());
            tempBiases.set(i, tempBiases.get(i) + lr * neurons.get(i).getDelta());
        }
    }

    public void swapWeight() {
        this.weights = this.tempWeights;
        this.biases = this.tempBiases;
    }
}
