package hr.fer.zemris.fuzzy.zad4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CanonicGA {

    public void run(boolean elitism, int popSize, int generationLimit, double mutationProbability, Function f) {
        List<Chromosome> population = new ArrayList<>();

        // generate random
        for (int i = 0; i < popSize; i++) {
            Chromosome chr = Chromosome.randomGenerate();
            chr.fitness = f.totalError(f.valuesFor(chr));
            population.add(chr);
        }

        Random rand = new Random();

        for (int generation = 1; generation <= generationLimit; generation++) {
            Collections.sort(population);

            int a, b, c;
            a = rand.nextInt(population.size());
            b = rand.nextInt(population.size());
            while (b == a) b = rand.nextInt(population.size());

            c = rand.nextInt(population.size());
            while (c == a || c == b) c = rand.nextInt(population.size());

            List<Chromosome> parents = new ArrayList<>();

            int worstInd = worst(a, b, c);
            if (worstInd == a) {
                parents.add(population.get(b));
                parents.add(population.get(c));
            } else if (worstInd == b) {
                parents.add(population.get(a));
                parents.add(population.get(c));
            } else {
                parents.add(population.get(a));
                parents.add(population.get(b));
            }

            // crossover
            Chromosome newChild = GA.uniformCrossover(parents);

            // mutation
            GA.mutate(newChild, mutationProbability);
            newChild.fitness = f.totalError(f.valuesFor(newChild));

            // replace child with worst
            population.set(worstInd, newChild);
        }

        Chromosome sol = population.get(0);
        System.out.println(sol);
    }

    private int worst(int a, int b, int c) {
        if (a > b && a > c) return a;
        if (b > a && b > c) return b;
        return c;
    }


}
