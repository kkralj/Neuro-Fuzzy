package hr.fer.zemris.fuzzy.zad4;

import java.util.Arrays;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {

    private double[] betas;

    public Chromosome(double[] betas) {
        this.betas = betas;
    }


    public double fitness;

    public double getBeta(int i) {
        return betas[i];
    }

    public double[] getBetas() {
        return betas;
    }

    public void setBeta(int i, double val) {
        this.betas[i] = val;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "betas=" + Arrays.toString(betas) +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return Double.compare(fitness, chromosome.fitness);
    }

    public static Chromosome randomGenerate() {
        Random random = new Random();
        double[] betas = new double[5];
        for (int i = 0; i < 5; i++) {
            betas[i] = -4 + 8 * random.nextDouble();
        }
        return new Chromosome(betas);
    }
}
