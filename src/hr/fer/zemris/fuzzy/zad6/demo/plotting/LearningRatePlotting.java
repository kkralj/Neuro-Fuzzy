package hr.fer.zemris.fuzzy.zad6.demo.plotting;

import hr.fer.zemris.fuzzy.zad6.data.TrainingData;
import hr.fer.zemris.fuzzy.zad6.net.ANFIS;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class LearningRatePlotting {
    public static void main(String[] args) throws IOException {
        TrainingData trainingData = TrainingData.load(Paths.get("lab6-data/train.txt"));

        int maxIterations = 100;
        int numberOfRules = 6;

        for (Double learningRate : new double[]{0.01, 0.001, 0.0001}) {
            ANFIS fnn = new ANFIS(maxIterations, numberOfRules, learningRate, trainingData);
            System.out.println("Learning rate: " + learningRate);

            System.out.println("Batch training:");
            List<Double> batchErrors = fnn.batchTrain();
            PlotDataGenerator.storeErrors("lab6-data/learningRates/batch-errors-" + learningRate + ".dat", batchErrors);
            System.out.println("Batch training error: " + batchErrors.get(batchErrors.size() - 1));
        }

        System.out.println();

        for (Double learningRate : new double[]{0.1, 0.001, 0.0001}) {
            ANFIS fnn = new ANFIS(maxIterations, numberOfRules, learningRate, trainingData);
            System.out.println("Learning rate: " + learningRate);

            System.out.println("Stohastic training:");
            List<Double> stohasticErrors = fnn.stohasticTrain();
            PlotDataGenerator.storeErrors("lab6-data/learningRates/stohastic-errors-" + learningRate + ".dat", stohasticErrors);
            System.out.println("Stohastic training error: " + stohasticErrors.get(stohasticErrors.size() - 1));
        }
    }
}
