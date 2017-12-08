package hr.fer.zemris.fuzzy.zad4;

import java.util.ArrayList;
import java.util.List;

public class GA {

    public void run(boolean elitism, int popSize, int generationLimit, Function f) {
        List<Chromosome> population = new ArrayList<>();
        // generate random

        for (int generation = 1; generation <= generationLimit; generation++) {
            List<Chromosome> nextPopulation = new ArrayList<>();

            if (elitism) {
                nextPopulation.add(population.get(0));
            }

            for (int i = nextPopulation.size(); i < popSize; i++) {
                // selection
                Chromosome[] parents = chooseParents(population, 2);
                // crossover
                Chromosome child = crossover(parents);
                // mutation
                mutate(child);

                child.fitness = f.totalError(f.valuesFor(child));

                nextPopulation.add(child);
            }

            population = nextPopulation;
        }

        Chromosome sol = population.get(0);
        // print solution
    }

    private void mutate(Chromosome child) {

    }

    private Chromosome crossover(Chromosome[] parents) {
    }

    private Chromosome[] chooseParents(List<Chromosome> population, int size) {
        for

        return new Chromosome[0];
    }

}
