package hr.fer.zemris.fuzzy.zad7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

    private static final double EPS = 1e-7;
    private static final Random random = new Random();

    public double[] run(NeuralNetwork nn, int elitismSize,
                        int populationSize, int generations,
                        double mutationProbability, double v1,
                        double sigma1, double sigma2) {

        int totalIterations = generations * populationSize;

        List<Chromosome> population = Chromosome.getRandomPopulation(populationSize, nn);

        for (int iteration = 1; iteration <= totalIterations; iteration++) {
            Collections.sort(population);
            if (population.get(0).error < EPS) break;

            List<Chromosome> nextPopulation = new ArrayList<>();
            for (int i = 0; i < elitismSize; i++) {
                nextPopulation.add(population.get(i));
            }

            for (int i = nextPopulation.size(); i < populationSize; i++) {
                Chromosome[] parents = proportionalSelection(population);
                Chromosome child = randomCrossover(parents[0], parents[1]);

                if (random.nextDouble() < v1) {
                    mutate1(child, mutationProbability, sigma1);
                } else {
                    mutate2(child, mutationProbability, sigma2);
                }

                child.error = nn.calcError(child.getParams());
                nextPopulation.add(child);
            }

            if (iteration % 1000 == 0) {
                System.out.println(population.get(0).error);
            }

            population = nextPopulation;
        }

        Collections.sort(population);

        return population.get(0).getParams();
    }

    private Chromosome[] proportionalSelection(List<Chromosome> population) {
        Chromosome[] parents = new Chromosome[2];

        double totalFitness = 0;
        for (Chromosome ch : population) {
            totalFitness += ch.getFitness();
        }

        for (int parentIndex = 0; parentIndex < 2; parentIndex++) {
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

    private void mutate1(Chromosome chr, double mp, double sigma) {
        for (int i = 0; i < chr.getParams().length; i++) {
            if (random.nextDouble() < mp) {
                chr.setParam(i, chr.getParam(i) + random.nextGaussian() * sigma);
            }
        }
    }

    private void mutate2(Chromosome chr, double mp, double sigma) {
        for (int i = 0; i < chr.getParams().length; i++) {
            if (random.nextDouble() < mp) {
                chr.setParam(i, random.nextGaussian() * sigma);
                return;
            }
        }
    }

    private Chromosome randomCrossover(Chromosome parent1, Chromosome parent2) {
        int ind = random.nextInt(3);
        switch (ind) {
            case 0:
                return arithmeticCrossover(parent1, parent2);
            case 1:
                return binaryCrossover(parent1, parent2);
            default:
                return uniformCrossover(parent1, parent2);
        }
    }

    private Chromosome binaryCrossover(Chromosome parent1, Chromosome parent2) {
        double[] p1 = parent1.getParams();
        double[] p2 = parent2.getParams();
        double[] params = new double[p1.length];

        for (int i = 0; i < p1.length; i++) {
            params[i] = random.nextDouble() < 0.5 ? p1[i] : p2[i];
        }

        return new Chromosome(params);
    }

    private Chromosome arithmeticCrossover(Chromosome parent1, Chromosome parent2) {
        double[] p1 = parent1.getParams();
        double[] p2 = parent2.getParams();
        double[] params = new double[p1.length];

        for (int i = 0; i < p1.length; i++) {
            params[i] = 0.5 * (p1[i] + p2[i]);
        }

        return new Chromosome(params);
    }

    private Chromosome uniformCrossover(Chromosome parent1, Chromosome parent2) {
        double[] p1 = parent1.getParams();
        double[] p2 = parent2.getParams();
        double[] params = new double[p1.length];

        for (int i = 0; i < p1.length; i++) {
            double pmin = Math.min(p1[i], p2[i]);
            double pmax = Math.max(p1[i], p2[i]);
            params[i] = pmin + random.nextDouble() * (pmax - pmin);
        }

        return new Chromosome(params);
    }

}
