package hr.fer.zemris.fuzzy.zad4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {

    private static Random random = new Random();

    private double[] betas;

    public double error;

    Chromosome(double[] betas) {
        this.betas = betas;
    }

    private static Chromosome getRandom() {
        double[] betas = new double[5];
        for (int i = 0; i < 5; i++) {
            betas[i] = -4 + 8 * random.nextDouble();
        }
        return new Chromosome(betas);
    }

    public double[] getBetas() {
        return betas;
    }

    public void setBeta(int i, double val) {
        this.betas[i] = val;
    }

    public double getFitness() {
        return -error;
    }

    public static List<Chromosome> getRandomPopulation(int populationSize, Function f) {
        List<Chromosome> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Chromosome chr = Chromosome.getRandom();
            chr.error = f.totalError(chr);
            population.add(chr);
        }

        return population;
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return Double.compare(error, chromosome.error);
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "betas=" + Arrays.toString(betas) +
                ", error=" + error +
                '}';
    }
}
