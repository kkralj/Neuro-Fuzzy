package hr.fer.zemris.fuzzy.zad7;

public class Point {

    private double x1, x2;
    private double[] y;

    Point(double x1, double x2, double[] y) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double[] getY() {
        return y;
    }
}
