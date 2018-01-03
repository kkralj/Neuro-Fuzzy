package hr.fer.zemris.fuzzy.zad6.demo;

import hr.fer.zemris.fuzzy.zad6.data.TrainingData;
import hr.fer.zemris.fuzzy.zad6.net.ANFIS;

import java.io.IOException;
import java.nio.file.Paths;

public class Training {

    public static void main(String[] args) throws IOException {
        TrainingData trainingData = TrainingData.load(Paths.get("lab6-data/train.txt"));

        int maxIterations = 25_000;
        int numberOfRules = 10;
        double learningRate = 0.002;

        ANFIS fnn = new ANFIS(maxIterations, numberOfRules, learningRate, trainingData);
        fnn.stohasticTrain();
    }

}
