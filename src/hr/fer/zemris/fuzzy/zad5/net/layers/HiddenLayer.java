package hr.fer.zemris.fuzzy.zad5.net.layers;

import hr.fer.zemris.fuzzy.zad5.net.Neuron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HiddenLayer implements Iterable<Neuron> {

    private List<Neuron> neurons;

    public HiddenLayer(int size, int prevLayerSize) {
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

    public void forwardPass(InputLayer layer) {
        for (Neuron neuron : neurons) {
            neuron.forwardPass(layer.getValues());
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

    public double getDelta(int i, HiddenLayer next) {
        double value = neurons.get(i).getOutput();
        double delta = value * (1 - value);

        List<Neuron> nextNeurons = next.getNeurons();
        for (Neuron nextNeuron : nextNeurons) {
            delta *= nextNeuron.getWeight(i) * nextNeuron.getDelta();
        }

        neurons.get(i).setDelta(delta);
        return delta;
    }

}
