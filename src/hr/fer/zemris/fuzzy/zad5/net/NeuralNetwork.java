package hr.fer.zemris.fuzzy.zad5.net;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private static final double EPS = 1e-4;

    private TrainingData trainingData;

    private List<Layer> layers = new ArrayList<>();


    public NeuralNetwork(TrainingData trainingData, int inputLayer, int[] hiddenLayers, int outputLayer) {
        this.trainingData = trainingData;

        // input layer
        layers.add(new Layer(inputLayer, hiddenLayers[0]));

        // hidden layers
        for (int i = 0; i < hiddenLayers.length - 1; i++) {
            layers.add(new Layer(hiddenLayers[i], hiddenLayers[i + 1]));
        }
        layers.add(new Layer(hiddenLayers[hiddenLayers.length - 1], outputLayer));

        // output layer
        layers.add(new Layer(outputLayer, 0));
    }

    public List<Double> predict(List<Double> input) {
        return forwardPass(input);
    }

    public void train(int iterations, int batchSize, double learningRate, int printIterations, boolean printWeights) {
        double error = Double.MAX_VALUE;

        for (int i = 1; i <= iterations && error > EPS; i++) {
            error = getError();

            if (printIterations > 0 && i % printIterations == 0) {
                System.out.println("Iteration: " + i + " Error: " + error);
            }

            backwardPass(learningRate, batchSize);
        }

        System.out.println("Error: " + error);

        if (printWeights) {
            printWeights();
        }
    }

    private double getError() {
        List<List<Double>> input = trainingData.getData();
        List<List<Double>> classes = trainingData.getCorrectClasses();

        double error = 0;

        for (int i = 0; i < input.size(); i++) {
            List<Double> output = forwardPass(input.get(i));
            for (int j = 0; j < output.size(); j++) {
                error += Math.pow(output.get(j) - (double) classes.get(i).get(j), 2);
            }
        }

        return error / input.size();
    }

    private List<Double> forwardPass(List<Double> input) {
        layers.get(0).forwardPass(input);

        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).forwardPass(layers.get(i - 1));
        }

        return layers.get(layers.size() - 1).getOutput();
    }

    private void backwardPass(double learningRate, int batchSize) {
        for (int dataIndex = 0; dataIndex < trainingData.getData().size(); dataIndex++) {

            List<Double> input = trainingData.getData().get(dataIndex);
            List<Double> realOutput = trainingData.getCorrectClasses().get(dataIndex);

            forwardPass(input);

            // update deltas
            layers.get(layers.size() - 1).updateDeltas(realOutput);
            for (int i = layers.size() - 2; i >= 0; i--) {
                layers.get(i).updateDeltas(layers.get(i + 1));
            }

            // update weights
            for (int i = layers.size() - 2; i >= 0; i--) {
                layers.get(i).updateWeights(learningRate, layers.get(i + 1));
            }

            if (dataIndex > 0 && dataIndex % batchSize == 0) {
                for (Layer layer : layers) {
                    layer.swapWeights();
                }
            }
        }
    }

    private void printWeights() {
        System.out.println("Layer weights:");
        for (Layer layer : layers) {
            for (Neuron n : layer.getNeurons()) {
                System.out.println(n.getWeights());
            }
        }
        System.out.println();
    }

}
