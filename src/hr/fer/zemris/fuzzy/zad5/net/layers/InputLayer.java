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
}
