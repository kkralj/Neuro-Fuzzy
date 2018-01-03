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

    private double getError() {
        double error = 0, out;
        for (Point point : trainingData) {
            out = forwardPass(point.getX1(), point.getX2());
            error += (point.getY() - out) * (point.getY() - out);
        }
        return error / trainingData.getSize();
    }

    public void train() {
        double error;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (Point point : trainingData) {
                double x1 = point.getX1();
                double x2 = point.getX2();
                double yk = point.getY();

                double ok = forwardPass(x1, x2);

                double sumOfW = 0, sumOfWF = 0;
                for (Rule rule : rules) {
                    sumOfW += rule.getW(x1, x2);
                    sumOfWF += rule.getW(x1, x2) * rule.getF(x1, x2);
                }

                for (Rule rule : rules) {
                    double w = rule.getW(x1, x2);
                    double f = rule.getF(x1, x2);

                    double deltaP = -(yk - ok) * w / sumOfW * x1;
                    double deltaQ = -(yk - ok) * w / sumOfW * x2;
                    double deltaR = -(yk - ok) * w;

                    rule.updateDeltaP(deltaP);
                    rule.updateDeltaQ(deltaQ);
                    rule.updateDeltaR(deltaR);

                    double alpha = rule.getA().getValue(x1);
                    double beta = rule.getB().getValue(x2);

                    double deltaA1 = -(yk - ok) * (f * sumOfW - sumOfWF) / (sumOfW * sumOfW) *
                            beta * alpha * (1 - alpha) * rule.getA().getB();

                    double deltaB1 = -(yk - ok) * (f * sumOfW - sumOfWF) / (sumOfW * sumOfW) *
                            beta * alpha * (1 - alpha) * (rule.getA().getA() - x1);

                    rule.getA().updateDeltaA(deltaA1);
                    rule.getA().updateDeltaB(deltaB1);

                    double deltaA2 = -(yk - ok) * (f * sumOfW - sumOfWF) / (sumOfW * sumOfW) *
                            alpha * beta * (1 - beta) * rule.getB().getB();

                    double deltaB2 = -(yk - ok) * (f * sumOfW - sumOfWF) / (sumOfW * sumOfW) *
                            alpha * beta * (1 - beta) * (rule.getB().getA() - x2);

                    rule.getB().updateDeltaA(deltaA2);
                    rule.getB().updateDeltaB(deltaB2);
                }
            }

            for (Rule rule : rules) {
                rule.updateDeltas(learningRate);
            }

            error = getError();
            System.out.println("Iteration: " + iteration + " error: " + error);
            if (error < 1e-6) return;
        }
    }

    private double forwardPass(double x1, double x2) {
        List<Double> weights = new ArrayList<>();
        List<Double> functionValues = new ArrayList<>();

        double weightSum = 0;
        for (Rule rule : rules) {
            weights.add(rule.getW(x1, x2));
            functionValues.add(rule.getF(x1, x2));

            weightSum += weights.get(weights.size() - 1);
        }

        double result = 0;
        for (int i = 0; i < weights.size(); i++) {
            result += (weights.get(i) / weightSum) * functionValues.get(i);
        }
        return result;
    }
}
