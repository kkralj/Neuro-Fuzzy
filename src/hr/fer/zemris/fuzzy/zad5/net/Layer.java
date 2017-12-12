package hr.fer.zemris.fuzzy.zad5.net;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private List<Neuron> neurons;

    public Layer(int size, int prevLayerSize) {
        this.neurons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.neurons.add(new Neuron(prevLayerSize));
        }
    }

    public List<Double> forwardPass(List<Double> input) {
        List<Double> results = new ArrayList<>();

        for (Neuron neuron : neurons) {
            results.add(neuron.getOutput(input));
        }

        return results;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }
}
