package hr.fer.zemris.fuzzy.zad4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EliminationGA {

    private static Random random = new Random();

    private static final double MIN_ERROR = 1e-6;

    public void run(int populationSize, int generationLimit, double mutationProbability, Function f) {

        List<Chromosome> population = Chromosome.getRandomPopulation(populationSize, f);

        for (int generation = 1; generation <= generationLimit; generation++) {
            Collections.sort(population);
            if (population.get(0).error < MIN_ERROR) break;

            List<Integer> kRandoms = getSortedRandomInts(3, populationSize);

            List<Chromosome> parents = new ArrayList<>();
            for (int i = 0; i < kRandoms.size() - 1; i++) {
                parents.add(population.get(kRandoms.get(i)));
            }

            Chromosome newChild = GenerationGA.uniformCrossover(parents.get(0), parents.get(1));
            GenerationGA.mutate(newChild, mutationProbability);
            newChild.error = f.totalError(newChild);

            // replace worst child
            int worstIndex = kRandoms.get(kRandoms.size() - 1);
            if (newChild.error < population.get(worstIndex).error) {
                population.set(worstIndex, newChild);
            }
        }

        Collections.sort(population);

        Chromosome sol = population.get(0);
        System.out.println(sol);
    }

    public static List<Integer> getSortedRandomInts(int size, int upperBound) {
        List<Integer> numbers = new ArrayList<>();

        int rand;
        for (int i = 0; i < size; i++) {
            do {
                rand = random.nextInt(upperBound);
            } while (numbers.contains(rand));

            numbers.add(rand);
        }

        Collections.sort(numbers);

        return numbers;
    }

}
