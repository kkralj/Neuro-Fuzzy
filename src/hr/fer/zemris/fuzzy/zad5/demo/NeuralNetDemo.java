package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad5.net.TrainingData;

import java.io.IOException;

public class NeuralNetDemo {
    public static void main(String[] args) throws IOException {

        int inputLayer = 2 * TrainingDemo.M;
        int[] hiddenLayers = new int[]{25, 15};
        int outputLayer = 5;

        TrainingData trainingData = new TrainingData();
        trainingData.fill("train.txt", TrainingDemo.M);

        NeuralNetwork nn = new NeuralNetwork(trainingData, inputLayer, hiddenLayers, outputLayer);

        nn.train(100);

    }
}
