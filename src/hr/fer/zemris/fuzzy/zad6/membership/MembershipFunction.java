package hr.fer.zemris.fuzzy.zad6.membership;

import java.util.Random;

public class MembershipFunction {

    private static Random random = new Random();

    private double a, da;
    private double b, db;

    public MembershipFunction() {
        a = -1 + 2 * random.nextDouble();
        b = -1 + 2 * random.nextDouble();
    }

    public double getValue(double x) {
        return 1. / (1 + Math.exp(b * (x - a)));
    }

    public void updateDeltaA(double da) {
        this.da += da;
    }

    public void updateDeltaB(double db) {
        this.db += db;
    }

    public void updateDeltas(double learningRate) {
        a -= learningRate * da;
        b -= learningRate * db;
        da = db = 0;
    }

    public double getB() {
        return b;
    }

    public double getA() {
        return a;
    }
}
