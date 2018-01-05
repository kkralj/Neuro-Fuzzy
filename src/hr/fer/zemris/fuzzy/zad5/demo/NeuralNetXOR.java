package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad5.net.TrainingData;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetXOR {
    public static void main(String[] args) {
        List<List<Double>> data = new ArrayList<>();
        List<List<Double>> correct = new ArrayList<>();

        double[][] inputs = new double[][]{{0., 0.}, {0., 1.}, {1., 0.}, {1., 1.}};
        double[] outputs = new double[]{0., 1., 1., 0.};

        for (int i = 0; i < inputs.length; i++) {
            data.add(createDummyList(inputs[i]));
            correct.add(createDummyList(new double[]{outputs[i]}));
        }

        int inputLayer = 2;
        int[] hiddenLayers = new int[]{6, 6};
        int outputLayer = 1;

        TrainingData trainingData = new TrainingData(data, correct);
        NeuralNetwork nn = new NeuralNetwork(trainingData, inputLayer, hiddenLayers, outputLayer);
        nn.train(100_000, 1, 1, 1000, false);

        for (double[] input : inputs) {
            System.out.println(nn.predict(createDummyList(input)));
        }
    }

    static List<Double> createDummyList(double[] input) {
        List<Double> result = new ArrayList<>();
        for (double x : input) {
            result.add((double) x);
        }
        return result;
    }

}
