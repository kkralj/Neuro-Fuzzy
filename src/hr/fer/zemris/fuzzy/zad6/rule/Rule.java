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

    public void updateDeltaP(double dp) {
        this.dp += dp;
    }

    public void updateDeltaQ(double dq) {
        this.dq += dq;
    }

    public void updateDeltaR(double dr) {
        this.dr += dr;
    }

    public void updateDeltas(double learningRate) {
        p -= learningRate * dp;
        q -= learningRate * dq;
        r -= learningRate * dr;
        dp = dq = dr = 0;

        A.updateDeltas(learningRate);
        B.updateDeltas(learningRate);
    }

    public MembershipFunction getA() {
        return A;
    }

    public MembershipFunction getB() {
        return B;
    }
}
