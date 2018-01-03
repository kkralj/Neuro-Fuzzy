package hr.fer.zemris.fuzzy.zad6.rule;

import hr.fer.zemris.fuzzy.zad6.membership.MembershipFunction;

import java.util.Random;

public class Rule {

    private static Random random = new Random();

    private MembershipFunction A, B;

    private double p, dp;
    private double q, dq;
    private double r, dr;

    public Rule() {
        A = new MembershipFunction();
        B = new MembershipFunction();

        p = -1 + 2 * random.nextDouble();
        q = -1 + 2 * random.nextDouble();
        r = -1 + 2 * random.nextDouble();
    }

    public double getW(double x1, double x2) {
        return A.getValue(x1) * B.getValue(x2);
    }

    public double getF(double x1, double x2) {
        return p * x1 + q * x2 + r;
    }

    public void updateDeltaF(double yk, double ok, double sumOfW, double x1, double x2) {
        double w = getW(x1, x2);
        this.dp += -(yk - ok) * w / sumOfW * x1;
        this.dq += -(yk - ok) * w / sumOfW * x2;
        this.dr += -(yk - ok) * w / sumOfW;
    }

    public void updateDeltaMemberships(double yk, double ok, double sumOfW, double sumOfWF, double x1, double x2) {
        double alpha = A.getValue(x1);
        double beta = B.getValue(x2);
        double f = getF(x1, x2);

        double samePart = -(yk - ok) * (f * sumOfW - sumOfWF) / (sumOfW * sumOfW) * beta * alpha;

        A.updateDeltaA(samePart * (1 - alpha) * A.getB());
        A.updateDeltaB(samePart * (1 - alpha) * (A.getA() - x1));
        B.updateDeltaA(samePart * (1 - beta) * B.getB());
        B.updateDeltaB(samePart * (1 - beta) * (B.getA() - x2));
    }

    public void updateDeltas(double learningRate) {
        p -= learningRate * dp;
        q -= learningRate * dq;
        r -= learningRate * dr;
        dp = dq = dr = 0;

        A.updateDeltas(learningRate);
        B.updateDeltas(learningRate);
    }
}
