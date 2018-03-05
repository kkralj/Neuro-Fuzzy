package hr.fer.zemris.fuzzy.zad7.demo;


import hr.fer.zemris.fuzzy.zad7.GA;
import hr.fer.zemris.fuzzy.zad7.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad7.TrainingData;

import java.io.IOException;

public class Train {
    public static void main(String[] args) throws IOException {
        TrainingData td = TrainingData.load("dataset/lab7-data/zad7-dataset.txt");
        int[] hiddenLayers = new int[]{8};

        NeuralNetwork nn = new NeuralNetwork(td, hiddenLayers);

        GA ga = new GA();
        double[] parameters = ga.run(nn, 25, 60_000, 0.008, 0.88, 0.30, 0.60);

        nn.evaluate(parameters);
        nn.storeParameters("dataset/lab7-data/train-params-2" + hiddenLayersString(hiddenLayers) + "3.txt", parameters);
    }

    private static String hiddenLayersString(int[] hiddenLayers) {
        StringBuilder result = new StringBuilder();
        for (int layer : hiddenLayers) {
            result.append(layer);
        }
        return result.toString();
    }
}
