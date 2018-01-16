package hr.fer.zemris.fuzzy.zad7.demo;


import hr.fer.zemris.fuzzy.zad7.GA;
import hr.fer.zemris.fuzzy.zad7.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad7.TrainingData;

import java.io.IOException;

public class Train {
    public static void main(String[] args) throws IOException {
        TrainingData td = TrainingData.load("lab7-data/zad7-dataset.txt");

        NeuralNetwork nn = new NeuralNetwork(td, new int[]{8});

        GA ga = new GA();
        ga.run(nn, 5, 20, 1_000_000, 0.1, 0.5, 0.2, 0.7);
    }
}
