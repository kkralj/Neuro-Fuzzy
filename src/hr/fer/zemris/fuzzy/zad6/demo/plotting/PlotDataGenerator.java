package hr.fer.zemris.fuzzy.zad6.demo.plotting;

import hr.fer.zemris.fuzzy.zad6.data.DataGenerator;
import hr.fer.zemris.fuzzy.zad6.data.TrainingData;
import hr.fer.zemris.fuzzy.zad6.membership.MembershipFunction;
import hr.fer.zemris.fuzzy.zad6.net.ANFIS;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class PlotDataGenerator {
    public static void main(String[] args) throws IOException {
        TrainingData trainingData = TrainingData.load(Paths.get("lab6-data/train.txt"));

        int maxIterations = 30_000;
        int numberOfRules = 8;
        double learningRate = 0.0005;

        ANFIS fnn = new ANFIS(maxIterations, numberOfRules, learningRate, trainingData);
        System.out.println("Batch training:");
        List<Double> batchErrors = fnn.batchTrain();

        for (int i = 0; i < fnn.getRules().size(); i++) {
            MembershipFunction A = fnn.getRules().get(i).getA();
            storeData("dataset/lab6-data/memberships/rule-" + (i + 1) + "-membership-A.txt", A);
            MembershipFunction B = fnn.getRules().get(i).getB();
            storeData("dataset/lab6-data/memberships/rule-" + (i + 1) + "-membership-B.txt", B);
        }

        storeOutputError("dataset/lab6-data/errors/output-error.txt", fnn);

        fnn = new ANFIS(maxIterations, numberOfRules, learningRate, trainingData);
        System.out.println("Stohastic training:");
        List<Double> stohasticErrors = fnn.stohasticTrain();

        storeErrors("dataset/lab6-data/errors/batch-errors.txt", batchErrors);
        storeErrors("dataset/lab6-data/errors/stohastic-errors.txt", stohasticErrors);
    }

    public static void storeErrors(String name, List<Double> errors) throws IOException {
        try (PrintWriter writer = new PrintWriter(name, "UTF-8")) {
            for (int i = 0; i < errors.size(); i++) {
                writer.write(i + " " + errors.get(i) + "\n");
            }
        }
    }

    private static void storeOutputError(String name, ANFIS fnn) throws IOException {
        try (PrintWriter writer = new PrintWriter(name, "UTF-8")) {
            for (int x1 = -4; x1 <= 4; x1++) {
                for (int x2 = -4; x2 <= 4; x2++) {
                    double diff = fnn.forwardPass(x1, x2) - DataGenerator.getFunctionValue(x1, x2);
                    writer.write(x1 + " " + x2 + " " + diff + "\n");
                }
            }
        }
    }

    private static void storeData(String name, MembershipFunction f) throws IOException {
        try (PrintWriter writer = new PrintWriter(name, "UTF-8")) {
            for (int x = -4; x <= 4; x++) {
                writer.write(x + " " + f.getValue(x) + "\n");
            }
        }
    }
}
