package hr.fer.zemris.fuzzy.zad6.net;

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

    public void train() {
        for (int iter = 0; iter < maxIterations; iter++) {
            double error = Double.MAX_VALUE;

            for (int i = 0; i < trainingData.getSize(); i++) {
                double x1 = trainingData.getX1().get(i);
                double x2 = trainingData.getX2().get(i);

                double yk = trainingData.getY().get(i);
                double ok = forwardPass(x1, x2);
                error = 0.5 * (yk - ok) * (yk - ok);

                for (Rule rule : rules) {
                    double w = rule.getW(x1, x2);
                    double f = rule.getF(x1, x2);

                    rule.updateDeltaP(0);
                    rule.updateDeltaQ(0);
                    rule.updateDeltaR(0);

                    rule.getA().updateDeltaA(0);
                    rule.getA().updateDeltaB(0);

                    rule.getB().updateDeltaA(0);
                    rule.getB().updateDeltaB(0);
                }
            }

            for (Rule rule : rules) {
                rule.updateDeltas(learningRate);
            }

            System.out.println("Iteration: " + iter + " error: " + error);
            if (error < 1e-6) return;
        }
    }

    private double forwardPass(double x1, double x2) {
        double weightSum = 0;

        List<Double> weights = new ArrayList<>();
        List<Double> functionValues = new ArrayList<>();

        for (Rule rule : rules) {
            weights.add(rule.getW(x1, x2));
            weightSum += weights.get(weights.size() - 1);
            functionValues.add(rule.getF(x1, x2));
        }

        double result = 0;
        for (int i = 0; i < weights.size(); i++) {
            result += (weights.get(i) / weightSum) * functionValues.get(i);
        }

        return result;
    }
}
