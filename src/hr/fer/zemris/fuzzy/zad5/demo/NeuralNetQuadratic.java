package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad5.net.TrainingData;

import java.util.ArrayList;
import java.util.List;

import static hr.fer.zemris.fuzzy.zad5.demo.NeuralNetXOR.createDummyList;

public class NeuralNetQuadratic {

    public static void main(String[] args) {
        List<List<Double>> data = new ArrayList<>();
        List<List<Double>> correct = new ArrayList<>();

        double[] inputs = new double[]{-1, -0.8, -0.6, -0.4, -0.2, 0, 0.2, 0.4, 0.6, 0.8, 1};
        double[] outputs = new double[]{1, 0.64, 0.36, 0.16, 0.04, 0, 0.04, 0.16, 0.36, 0.64, 1};

        for (int i = 0; i < inputs.length; i++) {
            data.add(createDummyList(new double[]{inputs[i]}));
            correct.add(createDummyList(new double[]{outputs[i]}));
        }

        int inputLayer = 1;
        int[] hiddenLayers = new int[]{6, 6};
        int outputLayer = 1;

        TrainingData trainingData = new TrainingData(data, correct);
        NeuralNetwork nn = new NeuralNetwork(trainingData, inputLayer, hiddenLayers, outputLayer);
        nn.train(100_000, 0.75, false);

        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Expected: " + outputs[i] + " Got: " + nn.predict(createDummyList(new double[]{inputs[i]})));
        }
    }

}
