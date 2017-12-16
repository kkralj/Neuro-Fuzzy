package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.gui.PredictionFrame;
import hr.fer.zemris.fuzzy.zad5.net.NeuralNetwork;
import hr.fer.zemris.fuzzy.zad5.net.TrainingData;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PredictionOutput {

    public static void main(String[] args) throws InvocationTargetException, InterruptedException, IOException {
        int inputLayer = 2 * DataInput.M;
        int[] hiddenLayers = new int[]{8, 3, 5};
        int outputLayer = 5;

        TrainingData trainingData = new TrainingData();
        trainingData.fill("train.txt", DataInput.M);

        NeuralNetwork nn = new NeuralNetwork(trainingData, inputLayer, hiddenLayers, outputLayer);
        nn.train(15_000, 10,0.085, 1000, false);

        SwingUtilities.invokeAndWait(() -> {
            new PredictionFrame(DataInput.M, nn).setVisible(true);
        });

    }
}
