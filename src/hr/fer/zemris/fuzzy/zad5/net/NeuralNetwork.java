package hr.fer.zemris.fuzzy.zad5.net;

import hr.fer.zemris.fuzzy.zad5.net.layers.HiddenLayer;
import hr.fer.zemris.fuzzy.zad5.net.layers.InputLayer;
import hr.fer.zemris.fuzzy.zad5.net.layers.OutputLayer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private TrainingData trainingData;

    private InputLayer inputLayer;
    private List<HiddenLayer> hiddenLayers = new ArrayList<>();
    private OutputLayer outputLayer;

    public NeuralNetwork(TrainingData trainingData, int inputLayer, int[] hiddenLayers, int outputLayer) {
        this.trainingData = trainingData;

        this.inputLayer = new InputLayer(inputLayer, hiddenLayers[0]);

        for (int i = 0; i < hiddenLayers.length - 1; i++) {
            this.hiddenLayers.add(new HiddenLayer(hiddenLayers[i], hiddenLayers[i + 1]));
        }
        this.hiddenLayers.add(new HiddenLayer(hiddenLayers[hiddenLayers.length - 1], outputLayer));

        this.outputLayer = new OutputLayer(outputLayer, 0);
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

        return error;
    }

    private List<Double> forwardPass(List<Double> input) {
        inputLayer.forwardPass(input);

        hiddenLayers.get(0).forwardPass(inputLayer);

        for (int i = 1; i < hiddenLayers.size(); i++) {
            hiddenLayers.get(i).forwardPass(hiddenLayers.get(i - 1));
        }

        outputLayer.forwardPass(hiddenLayers.get(hiddenLayers.size() - 1));

        return outputLayer.getOutput();
    }

    private void backwardPass(double learningRate) {
        for (int dataIndex = 0; dataIndex < trainingData.getData().size(); dataIndex++) {

            List<Double> input = trainingData.getData().get(dataIndex);
            List<Double> realOutput = trainingData.getCorrectClasses().get(dataIndex);

            forwardPass(input);

            // output layer
            List<Neuron> neurons = outputLayer.getNeurons();
            for (int j = 0; j < neurons.size(); j++) {
                double delta = outputLayer.getDelta(j, realOutput.get(j));
                neurons.get(j).setDelta(delta);
            }

            // last hidden -> output layer
            HiddenLayer layer = hiddenLayers.get(hiddenLayers.size() - 1);
            neurons = layer.getNeurons();
            for (int i = 0; i < neurons.size(); i++) {
                neurons.get(i).updateWeight(learningRate, outputLayer);
                double delta = layer.getDelta(i, outputLayer);
                neurons.get(i).setDelta(delta);
            }

            // hidden layers
            for (int i = hiddenLayers.size() - 2; i >= 0; i--) {
                layer = hiddenLayers.get(i);
                neurons = layer.getNeurons();
                for (int j = 0; j < neurons.size(); j++) {
                    neurons.get(j).updateWeight(learningRate, hiddenLayers.get(i + 1));
                    double delta = layer.getDelta(j, hiddenLayers.get(i + 1));
                    neurons.get(j).setDelta(delta);
                }
            }

            // input layer -> first hidden
            neurons = inputLayer.getNeurons();
            for (int i = 0; i < neurons.size(); i++) {
                neurons.get(i).updateWeight(learningRate, hiddenLayers.get(0));
            }

            for (HiddenLayer hiddenLayer : hiddenLayers) {
                hiddenLayer.swapWeights();
            }
            inputLayer.swapWeights();
        }
    }

    double minError = Double.MAX_VALUE;

    public void train(int iterations) {
        for (int i = 1; i <= iterations; i++) {
            double error = getError();
            minError = Math.min(minError, error);
//            System.out.println("Iteration: " + i + " error: " + error);
            backwardPass(0.1);
        }

        System.out.println("min err: " + minError);
    }
}
