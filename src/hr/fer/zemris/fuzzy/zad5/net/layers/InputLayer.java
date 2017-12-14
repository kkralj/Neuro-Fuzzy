package hr.fer.zemris.fuzzy.zad5.net.layers;

import java.util.ArrayList;
import java.util.List;

public class InputLayer {

    private int size;
    private List<Double> biases = new ArrayList<>();
    private List<Double> values = new ArrayList<>();

    public InputLayer(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            biases.add(0.0);
            values.add(0.0);
        }
    }

    public void forwardPass(List<Double> input) {
        if (input.size() != this.size) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < biases.size(); i++) {
            values.set(i, biases.get(i) + input.get(i));
        }
    }

    public List<Double> getValues() {
        return values;
    }

    public int getSize() {
        return size;
    }

    public List<Double> getOutput() {
        return values;
    }
}
