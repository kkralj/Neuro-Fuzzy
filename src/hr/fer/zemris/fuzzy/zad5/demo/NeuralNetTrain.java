package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad5.net.TrainingData;

import java.io.IOException;

public class NeuralNetTrain {
    public static void main(String[] args) throws IOException {
        int inputLayer = 2 * DataInput.M;
        int[] hiddenLayers = new int[]{5, 3, 5};
        int outputLayer = 5;

        TrainingData trainingData = new TrainingData();
        trainingData.fill("dataset/lab5-data/train.txt", DataInput.M);

        NeuralNetwork nn = new NeuralNetwork(trainingData, inputLayer, hiddenLayers, outputLayer);
        nn.train(10_000, 10, 1e-2, 1000, false);
    }
}
