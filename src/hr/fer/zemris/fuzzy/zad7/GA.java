package hr.fer.zemris.fuzzy.zad7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

    private static final double EPS = 1e-7;
    private static final Random random = new Random();

    public void run(NeuralNetwork nn, int elitismSize,
                    int populationSize, int generationLimit,
                    double mutationProbability, double v1,
                    double sigma1, double sigma2) {

        generationLimit /= populationSize;

        List<Chromosome> population = Chromosome.getRandomPopulation(populationSize, nn);

        for (int generation = 1; generation <= generationLimit; generation++) {
            Collections.sort(population);
            if (population.get(0).error < EPS) break;

            List<Chromosome> nextPopulation = new ArrayList<>();
            for (int i = 0; i < elitismSize; i++) {
                nextPopulation.add(population.get(i));
            }

            for (int i = nextPopulation.size(); i < populationSize; i++) {
                Chromosome[] parents = proportionalSelection(population);
                Chromosome child = uniformCrossover(parents[0], parents[1]);

                mutate(child, mutationProbability, random.nextDouble() < v1 ? sigma1 : sigma2);

                child.error = nn.calcError(child.getParams());
                nextPopulation.add(child);
            }

            if (generation % 100 == 0) {
                System.out.println(population.get(0).error);
            }

            population = nextPopulation;
        }

        Collections.sort(population);

        Chromosome sol = population.get(0);
        System.out.println(sol);
    }

    private static void mutate(Chromosome chr, double mp, double sigma) {
        for (int i = 0; i < chr.getParams().length; i++) {
            if (random.nextDouble() < mp) {
                chr.setParam(i, random.nextGaussian() * sigma);
            }
        }
    }

    private static Chromosome uniformCrossover(Chromosome parent1, Chromosome parent2) {
        double[] b1 = parent1.getParams();
        double[] b2 = parent2.getParams();
        double[] params = new double[b1.length];

        for (int i = 0; i < b1.length; i++) {
            double cmin = Math.min(b1[i], b2[i]);
            double cmax = Math.max(b1[i], b2[i]);
            params[i] = cmin + random.nextDouble() * (cmax - cmin);
        }

        return new Chromosome(params);
    }

    private Chromosome[] proportionalSelection(List<Chromosome> population) {
        Chromosome[] parents = new Chromosome[2];

        double totalFitness = 0;
        for (Chromosome ch : population) {
            totalFitness += ch.getFitness();
        }

        int PROPORTIONAL_SELECTION_SIZE = 2;
        for (int parentIndex = 0; parentIndex < PROPORTIONAL_SELECTION_SIZE; parentIndex++) {
            double limit = random.nextDouble() * totalFitness;

            int chosen = 0;
            double upperLimit = population.get(chosen).getFitness();
            while (limit > upperLimit && chosen + 1 < population.size()) {
                chosen++;
                upperLimit += population.get(chosen).getFitness();
            }

            parents[parentIndex] = population.get(chosen);
        }

        return parents;
    }

}
