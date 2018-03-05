package hr.fer.zemris.fuzzy.zad5.net;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private List<Neuron> neurons = new ArrayList<>();

    Layer(int layerSize, int nextLayerSize) {
        for (int i = 0; i < layerSize; i++) {
            this.neurons.add(new Neuron(nextLayerSize));
        }
    }

    public void forwardPass(List<Double> input) {
        for (int i = 0; i < input.size(); i++) {
            neurons.get(i).setValue(input.get(i));
        }
    }

    public void forwardPass(Layer layer) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).forwardPass(i, layer.getNeurons());
        }
    }

    private double getDelta(int i, List<Neuron> nextNeurons) {
        double value = neurons.get(i).getOutput();
        double delta = value * (1 - value);

        double sum = 0;
        for (int o = 0; o < nextNeurons.size(); o++) {
            sum += neurons.get(i).getWeight(o) * nextNeurons.get(o).getDelta();
        }
        delta *= sum;

        return delta;
    }

    private double getDelta(int i, double tsj) {
        double value = neurons.get(i).getOutput();
        return value * (1 - value) * (tsj - value);
    }

    public void updateDeltas(Layer nextLayer) {
        for (int i = 0; i < neurons.size(); i++) {
            double delta = getDelta(i, nextLayer.getNeurons());
            neurons.get(i).setDelta(delta);
        }
    }

    public void updateDeltas(List<Double> realOutput) {
        for (int i = 0; i < neurons.size(); i++) {
            double delta = getDelta(i, realOutput.get(i));
            neurons.get(i).setDelta(delta);
        }
    }

    public void updateWeights(double learningRate, Layer nextLayer) {
        for (Neuron neuron : neurons) {
            neuron.updateWeight(learningRate, nextLayer);
        }
    }

    public List<Double> getOutput() {
        List<Double> values = new ArrayList<>();
        for (Neuron neuron : neurons) {
            values.add(neuron.getOutput());
        }
        return values;
    }

    public void swapWeights() {
        for (Neuron neuron : neurons) {
            neuron.swapWeight();
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

}
