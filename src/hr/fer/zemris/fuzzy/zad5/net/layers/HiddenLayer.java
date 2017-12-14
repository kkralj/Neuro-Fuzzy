package hr.fer.zemris.fuzzy.zad5.net.layers;

import hr.fer.zemris.fuzzy.zad5.net.Neuron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HiddenLayer implements Iterable<Neuron> {

    private int layerSize;
    private int nextLayerSize;

    private List<Neuron> neurons = new ArrayList<>();

    public HiddenLayer(int layerSize, int nextLayerSize) {
        this.layerSize = layerSize;
        this.nextLayerSize = nextLayerSize;

        for (int i = 0; i < layerSize; i++) {
            this.neurons.add(new Neuron(nextLayerSize, false));
        }
    }

    public void forwardPass(HiddenLayer layer) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).forwardPass(i, layer.getNeurons());
        }
    }

    public void forwardPass(InputLayer prevLayer) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).forwardPass(i, prevLayer.getNeurons());
        }
    }

    public int getSize() {
        return neurons.size();
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
        double delta = value * (1 - value) * (tsj - value);

        neurons.get(i).setDelta(delta);
        return delta;
    }

    public double getDelta(int i, HiddenLayer nextLayer) {
        double value = neurons.get(i).getOutput();
        double delta = value * (1 - value);

        double sum = 0;

        List<Neuron> nextNeurons = nextLayer.getNeurons();
        for (int o = 0; o < nextNeurons.size(); o++) {
            sum += neurons.get(i).getWeight(o) * nextNeurons.get(o).getDelta();
        }

        neurons.get(i).setDelta(delta * sum);

        return delta;
    }

    public double getDelta(int i, OutputLayer nextLayer) {
        double value = neurons.get(i).getOutput();
        double delta = value * (1 - value);

        double sum = 0;

        List<Neuron> nextNeurons = nextLayer.getNeurons();
        for (int o = 0; o < nextNeurons.size(); o++) {
            sum += neurons.get(i).getWeight(o) * nextNeurons.get(o).getDelta();
        }

        neurons.get(i).setDelta(delta * sum);

        return delta;
    }

}
