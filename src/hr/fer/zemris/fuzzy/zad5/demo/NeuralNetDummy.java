package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad5.net.TrainingData;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetDummy {

    public static void main(String[] args) {
        List<List<Double>> data = new ArrayList<>();
        List<List<Double>> correct = new ArrayList<>();

        double[] values = new double[]{-1, -0.8, -0.6, -0.4, -0.2, 0, 0.2, 0.4, 0.6, 0.8, 1};
        double[] results = new double[]{1, 0.64, 0.36, 0.16, 0.04, 0, 0.04, 0.16, 0.36, 0.64, 1};

        for (int i = 0; i < values.length; i++) {
            data.add(createDummyList(values[i]));
            correct.add(createDummyList(results[i]));
        }

        int inputLayer = 1;
        int[] hiddenLayers = new int[]{6};
        int outputLayer = 1;

//        data.add(createDummyList(0, 0));
//        data.add(createDummyList(0, 1));
//        data.add(createDummyList(1, 0));
//        data.add(createDummyList(1, 1));
//
//        correct.add(createDummyList(0));
//        correct.add(createDummyList(1));
//        correct.add(createDummyList(1));
//        correct.add(createDummyList(0));
//
//        int inputLayer = 2;
//        int[] hiddenLayers = new int[]{2};
//        int outputLayer = 1;

        TrainingData trainingData = new TrainingData(data, correct);
        NeuralNetwork nn = new NeuralNetwork(trainingData, inputLayer, hiddenLayers, outputLayer);
        nn.train(100000);
    }

    private static List<Double> createDummyList(double x) {
        List<Double> result = new ArrayList<>();
        result.add(x);
        return result;
    }

    private static List<Double> createDummyList(double x, double y) {
        List<Double> result = new ArrayList<>();
        result.add(x);
        result.add(y);
        return result;
    }
}
