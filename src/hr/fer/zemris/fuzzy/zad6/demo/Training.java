package hr.fer.zemris.fuzzy.zad6.demo;

import hr.fer.zemris.fuzzy.zad6.data.TrainingData;
import hr.fer.zemris.fuzzy.zad6.net.ANFIS;

import java.io.IOException;
import java.nio.file.Paths;

public class Training {

    public static void main(String[] args) throws IOException {
        TrainingData trainingData = TrainingData.load(Paths.get("lab6-data/train.txt"));

        int maxIterations = 1_000_000;
        int numberOfRules = 2;
        double learningRate = 0.01;

        ANFIS fnn = new ANFIS(maxIterations, numberOfRules, learningRate, trainingData);
        fnn.train();
    }

}
