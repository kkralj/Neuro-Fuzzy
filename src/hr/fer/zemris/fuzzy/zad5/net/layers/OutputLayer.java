package hr.fer.zemris.fuzzy.zad5.net.layers;

import hr.fer.zemris.fuzzy.zad5.net.Neuron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OutputLayer implements Iterable<Neuron> {

    private int layerSize;
    private int nextLayerSize;

    private List<Neuron> neurons = new ArrayList<>();

    public OutputLayer(int layerSize, int nextLayerSize) {
        this.layerSize = layerSize;
        this.nextLayerSize = nextLayerSize;

        for (int i = 0; i < layerSize; i++) {
            this.neurons.add(new Neuron(nextLayerSize));
        }
    }

    public void forwardPass(HiddenLayer layer) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).forwardPass(i, layer.getNeurons());
        }
    }

    public List<Double> getOutput() {
        List<Double> values = new ArrayList<>();
        for (Neuron neuron : neurons) {
            values.add(neuron.getOutput());
        }
        return values;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    @Override
    public Iterator<Neuron> iterator() {
        return neurons.iterator();
    }

    public double getDelta(int i, Double tsj) {
        double value = neurons.get(i).getOutput();
        return value * (1 - value) * (tsj - value);
    }

    public void swapWeights() {
        for (Neuron neuron : neurons) {
            neuron.swapWeight();
        }
    }
}
