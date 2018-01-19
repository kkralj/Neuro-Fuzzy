package hr.fer.zemris.fuzzy.zad7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

    private static final double EPS = 1e-8;
    private static final Random random = new Random();

    public double[] run(NeuralNetwork nn,
                        int populationSize, int generations,
                        double mutationProbability, double v1,
                        double sigma1, double sigma2) {

        List<Chromosome> population = Chromosome.getRandomPopulation(populationSize, nn);

        int totalIterations = generations * populationSize;

        for (int iteration = 1; iteration <= totalIterations; iteration++) {

            Collections.sort(population);
            if (population.get(0).error < EPS) break;

            List<Chromosome> parents = new ArrayList<>();
            List<Integer> kRandoms = getRandomSortedInts(3, populationSize);
            for (int i = 0; i < kRandoms.size() - 1; i++) {
                parents.add(population.get(kRandoms.get(i)));
            }

            Chromosome newChild = randomCrossover(parents.get(0), parents.get(1));

            if (random.nextDouble() < v1) {
                mutate1(newChild, mutationProbability, sigma1);
            } else {
                mutate2(newChild, mutationProbability, sigma2);
            }

            newChild.error = nn.calcError(newChild.getParams());

            // replace worst child
            int worstIndex = kRandoms.get(kRandoms.size() - 1);
            population.set(worstIndex, newChild);

            if (iteration % (0.05 * totalIterations) == 0) {
                System.out.println(population.get(0).error);
            }

        }

        Collections.sort(population);
        System.out.println(population.get(0).error);

        return population.get(0).getParams();
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

    private List<Integer> getRandomSortedInts(int size, int upperBound) {
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
