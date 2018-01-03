package hr.fer.zemris.fuzzy.zad6.net;

import hr.fer.zemris.fuzzy.zad6.data.Point;
import hr.fer.zemris.fuzzy.zad6.data.TrainingData;
import hr.fer.zemris.fuzzy.zad6.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class ANFIS {

    private int maxIterations;
    private double learningRate;
    private TrainingData trainingData;
    private List<Rule> rules = new ArrayList<>();

    public ANFIS(int maxIterations, int numberOfRules, double learningRate, TrainingData trainingData) {
        this.trainingData = trainingData;
        this.learningRate = learningRate;
        this.maxIterations = maxIterations;

        for (int i = 0; i < numberOfRules; i++) {
            rules.add(new Rule());
        }
    }

    private double forwardPass(double x1, double x2) {
        double result = 0;
        double wSum = getWSum(x1, x2);

        for (Rule rule : rules) {
            result += rule.getW(x1, x2) / wSum * rule.getF(x1, x2);
        }

        return result;
    }

    public void batchTrain() {
        double error = Double.MAX_VALUE;

        for (int it = 0; it <= maxIterations && error > 1e-6; it++) {
            for (Point point : trainingData) {
                trainPoint(point);
            }

            for (Rule rule : rules) {
                rule.updateDeltas(learningRate);
            }

            error = getError();
            if (it % 1000 == 0) {
                System.out.println("Iteration: " + it + " error: " + error);
            }
        }
    }

    public void stohasticTrain() {
        double error = Double.MAX_VALUE;

        for (int it = 0, i = 0; it <= maxIterations && error > 1e-6; it++, i = (i + 1) % trainingData.getSize()) {
            trainPoint(trainingData.getData().get(i));

            for (Rule rule : rules) {
                rule.updateDeltas(learningRate);
            }

            error = getError();
            if (it % 1000 == 0) {
                System.out.println("Iteration: " + it + " error: " + error);
            }
        }
    }

    private void trainPoint(Point point) {
        double x1 = point.getX1(), x2 = point.getX2();
        double yk = point.getY(), ok = forwardPass(x1, x2);
        double wSum = getWSum(x1, x2), wfSum = getWFSum(x1, x2);

        for (Rule rule : rules) {
            rule.updateDeltaF(yk, ok, wSum, x1, x2);
            rule.updateDeltaMemberships(yk, ok, wSum, wfSum, x1, x2);
        }
    }

    private double getError() {
        double error = 0, out;

        for (Point point : trainingData) {
            out = forwardPass(point.getX1(), point.getX2());
            error += (point.getY() - out) * (point.getY() - out);
        }

        return error / (2 * trainingData.getSize());
    }

    private double getWSum(double x1, double x2) {
        double wSum = 0;
        for (Rule rule : rules) {
            wSum += rule.getW(x1, x2);
        }
        return wSum;
    }

    private double getWFSum(double x1, double x2) {
        double wfSum = 0;
        for (Rule rule : rules) {
            wfSum += rule.getW(x1, x2) * rule.getF(x1, x2);
        }
        return wfSum;
    }

}
