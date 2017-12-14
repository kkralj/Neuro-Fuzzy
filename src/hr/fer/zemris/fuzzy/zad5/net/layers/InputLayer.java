package hr.fer.zemris.fuzzy.zad5.net.layers;

import hr.fer.zemris.fuzzy.zad5.net.Neuron;

import java.util.ArrayList;
import java.util.List;

public class InputLayer {

    private int layerSize;
    private int nextLayerSize;

    private List<Neuron> neurons = new ArrayList<>();

    public InputLayer(int layerSize, int nextLayerSize) {
        this.layerSize = layerSize;
        this.nextLayerSize = nextLayerSize;

        for (int i = 0; i < layerSize; i++) {
            this.neurons.add(new Neuron(nextLayerSize, true));
        }
    }

    public void forwardPass(List<Double> input) {
        for (int i = 0; i < input.size(); i++) {
            neurons.get(i).setValue(input.get(i));
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public double getDelta(int i, HiddenLayer nextLayer) {
        double value = neurons.get(i).getOutput();
        double delta = value * (1 - value);

        double sum = 0;

        List<Neuron> nextNeurons = nextLayer.getNeurons();
        for (int o = 0; o < nextNeurons.size(); o++) {
            sum += neurons.get(i).getWeight(o) * nextNeurons.get(o).getDelta();
        }

        delta *= sum;
        neurons.get(i).setDelta(delta);

        return delta;
    }

    public void swapWeights() {
        for (Neuron neuron : neurons) {
            neuron.swapWeight();
        }
    }
}
