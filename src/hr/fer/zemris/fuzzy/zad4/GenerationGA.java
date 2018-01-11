package hr.fer.zemris.fuzzy.zad4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerationGA {

    private static Random random = new Random();

    private static final double MIN_ERROR = 1e-6;

    public void run(int elitism_size, int populationSize, int generationLimit, double mutationProbability, Function f) {
        List<Chromosome> population = Chromosome.getRandomPopulation(populationSize, f);

        for (int generation = 1; generation <= generationLimit; generation++) {
            Collections.sort(population);
            if (population.get(0).error < MIN_ERROR) break;

            List<Chromosome> nextPopulation = new ArrayList<>();
            if (elitism_size > 0) {
                for (int i = 0; i < elitism_size; i++) {
                    nextPopulation.add(population.get(i));
                }
            }

            for (int i = nextPopulation.size(); i < populationSize; i++) {
                List<Chromosome> parents = proportionalSelection(population, 2);
                Chromosome child = uniformCrossover(parents.get(0), parents.get(1));
                mutate(child, mutationProbability);
                child.error = f.totalError(child);
                nextPopulation.add(child);
            }

            population = nextPopulation;
        }

        Collections.sort(population);

        Chromosome sol = population.get(0);
        System.out.println(sol);
    }

    public static void mutate(Chromosome child, double mutationProbability) {
        for (int i = 0; i < 5; i++) {
            if (random.nextDouble() < mutationProbability) {
                child.setBeta(i, -4 + 8 * random.nextDouble());
            }
        }
    }

    public static Chromosome uniformCrossover(Chromosome parent1, Chromosome parent2) {
        double[] betas = new double[5];
        double[] b1 = parent1.getBetas();
        double[] b2 = parent2.getBetas();
        for (int i = 0; i < 5; i++) {
            double cmin = Math.min(b1[i], b2[i]);
            double cmax = Math.max(b1[i], b2[i]);
            betas[i] = cmin + random.nextDouble() * (cmax - cmin);

        }
        return new Chromosome(betas);
    }

    private List<Chromosome> proportionalSelection(List<Chromosome> population, int howMany) {
        List<Chromosome> parents = new ArrayList<>();

        double totalFitness = 0;
        for (Chromosome ch : population) {
            totalFitness += ch.getFitness();
        }

        for (int parentIndex = 0; parentIndex < howMany; parentIndex++) {
            double limit = random.nextDouble() * totalFitness;

            int chosen = 0;
            double upperLimit = population.get(chosen).getFitness();
            while (limit > upperLimit && chosen + 1 < population.size()) {
                chosen++;
                upperLimit += population.get(chosen).getFitness();
            }

            parents.add(population.get(chosen));
        }

        return parents;
    }

}
