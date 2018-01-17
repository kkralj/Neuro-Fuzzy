package hr.fer.zemris.fuzzy.zad7.demo;


import hr.fer.zemris.fuzzy.zad7.GA2;
import hr.fer.zemris.fuzzy.zad7.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad7.TrainingData;

import java.io.IOException;

public class Train {
    public static void main(String[] args) throws IOException {
        TrainingData td = TrainingData.load("lab7-data/zad7-dataset.txt");

        NeuralNetwork nn = new NeuralNetwork(td, new int[]{8});

//        GA ga = new GA();
//        double[] params = ga.run(nn, 5, 30, 2500, 0.03, 0.85, 0.08, 0.20);
//        nn.evaluate(params);

        GA2 ga2 = new GA2();
        double[] params2 = ga2.run(nn, 25, 25_000, 0.03, 0.8, 0.2, 0.4);
        nn.evaluate(params2);
    }
}
