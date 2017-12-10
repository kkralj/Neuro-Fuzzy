package hr.fer.zemris.fuzzy.zad4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

    public void run(boolean elitism, int popSize, int generationLimit, double mutationProbability, Function f) {
        List<Chromosome> population = new ArrayList<>();
        // generate random
        for (int i = 0; i < popSize; i++) {
            Chromosome chr = Chromosome.randomGenerate();
            chr.fitness = f.totalError(f.valuesFor(chr));
            population.add(chr);
        }

        for (int generation = 1; generation <= generationLimit; generation++) {
            List<Chromosome> nextPopulation = new ArrayList<>();
            Collections.sort(population);

            if (elitism) {
                nextPopulation.add(population.get(0));
            }

//            System.out.println(population.get(0).fitness);

            for (int i = nextPopulation.size(); i < popSize; i++) {
                // selection
                List<Chromosome> parents = proportionalSimpleChoose(population, 2);
                // crossover
                Chromosome child = uniformCrossover(parents);
                // mutation
                mutate(child, mutationProbability);

                child.fitness = f.totalError(f.valuesFor(child));

                nextPopulation.add(child);
            }

            population = nextPopulation;
        }

        Chromosome sol = population.get(0);
        System.out.println(sol);
    }

    public static void mutate(Chromosome child, double mutationProbability) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (random.nextDouble() < mutationProbability) {
                child.setBeta(i, -4 + 8 * random.nextDouble());
            }
        }
    }

    public static Chromosome uniformCrossover(List<Chromosome> parents) {
        if (parents.size() != 2) throw new IllegalArgumentException();
        double[] betas = new double[5];
        for (int i = 0; i < 5; i++) {
            betas[i] = 0.5 * (parents.get(0).getBeta(i) + parents.get(1).getBeta(i));
        }
        return new Chromosome(betas);
    }

    private List<Chromosome> proportionalSimpleChoose(List<Chromosome> population, int howMany) {
        Random rand = new Random();
        List<Chromosome> parents = new ArrayList<>();

        double sum = 0;
        for (Chromosome ch : population) {
            sum += ch.fitness;
        }

        for (int parentIndex = 0; parentIndex < howMany; parentIndex++) {
            double r = rand.nextDouble();
            double limit = r * sum;

            int chosen = 0;
            double upperLimit = population.get(chosen).fitness;
            while (limit > upperLimit && chosen < population.size() - 1) {
                chosen++;
                upperLimit += population.get(chosen).fitness;
            }

            parents.add(population.get(chosen));
        }

        return parents;
    }

}
