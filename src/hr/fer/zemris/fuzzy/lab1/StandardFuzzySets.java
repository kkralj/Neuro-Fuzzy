package hr.fer.zemris.fuzzy.lab1;

public class StandardFuzzySets {

    public static IIntUnaryFunction lFunction(int a, int b) {
        return x -> {
            if (x < a) return 1;
            if (x >= b) return 0;

            return ((double) b - x) / (b - a);
        };
    }

    public static IIntUnaryFunction gammaFunction(int a, int b) {
        return x -> {
            if (x < a) return 0;
            if (x >= b) return 1;

            return ((double) x - a) / (b - a);
        };
    }

    public static IIntUnaryFunction lambdaFunction(int a, int b, int c) {
        return x -> {
            if (x >= a && x < b) {
                return ((double) x - a) / (b - a);

            } else if (x >= b && x < c) {
                return ((double) c - x) / (c - b);

            }
            return 0;
        };
    }
}
