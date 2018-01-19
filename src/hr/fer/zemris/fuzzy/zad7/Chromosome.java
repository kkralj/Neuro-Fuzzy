package hr.fer.zemris.fuzzy.zad7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {

    private static Random random = new Random();

    private double[] params;

    public double error;

    Chromosome(double[] params) {
        this.params = params;
    }

    public static List<Chromosome> getRandomPopulation(int populationSize, NeuralNetwork nn) {
        List<Chromosome> population = new ArrayList<>();
        int parameterCount = nn.getParameterCount();

        for (int i = 0; i < populationSize; i++) {
            Chromosome chr = Chromosome.getRandom(parameterCount);
            chr.error = nn.calcError(chr.params);
            population.add(chr);
        }

        return population;
    }

    private static Chromosome getRandom(int size) {
        double[] params = new double[size];

        for (int i = 0; i < size; i++) {
            params[i] = random.nextDouble();
        }

        return new Chromosome(params);
    }

    public double[] getParams() {
        return params;
    }

    public double getParam(int i) {
        return this.params[i];
    }

    public void setParam(int i, double param) {
        this.params[i] = param;
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return Double.compare(error, chromosome.error);
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "error=" + error +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}

