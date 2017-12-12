package hr.fer.zemris.fuzzy.zad5.net;

import hr.fer.zemris.fuzzy.zad5.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private TrainingData trainingData;
    private Layer inputLayer;
    private List<Layer> hiddenLayers;
    private Layer outputLayer;

    public NeuralNetwork(TrainingData trainingData, int inputLayer, int[] hiddenLayers, int outputLayer) {
        this.trainingData = trainingData;
        this.inputLayer = new Layer(inputLayer);
        this.hiddenLayers = new ArrayList<>();
        this.outputLayer = new Layer(outputLayer);

        for (int hiddenLayer : hiddenLayers) {
            this.hiddenLayers.add(new Layer(hiddenLayer));
        }
    }

    private double getError() {
        List<List<DataPoint>> input = trainingData.getData();
        List<Integer> classes = trainingData.getCorrectClasses();

        double error = 0;
        for (int i = 0; i < input.size(); i++) {
            error += Math.pow(forwardPass(input.get(i)) - classes.get(i), 2);
        }

        return Math.sqrt(error);
    }

    private double forwardPass(List<DataPoint> input) {
        return 0;
    }

    public void train(int iterations) {
        for (int i = 1; i <= iterations; i++) {

            double error = getError();
            System.out.println("Iteration: " + i + " error: " + error);

            // backprop
        }
    }
}
