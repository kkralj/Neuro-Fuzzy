package hr.fer.zemris.fuzzy.zad5.net.layers;

import hr.fer.zemris.fuzzy.zad5.net.Neuron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OutputLayer implements Iterable<Neuron> {

    private List<Neuron> neurons;

    public OutputLayer(int size, int prevLayerSize) {
        this.neurons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.neurons.add(new Neuron(prevLayerSize));
        }
    }

    public void forwardPass(HiddenLayer layer) {
        for (Neuron neuron : neurons) {
            neuron.forwardPass(layer.getOutput());
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
}
