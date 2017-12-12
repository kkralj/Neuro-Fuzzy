package hr.fer.zemris.fuzzy.zad5.net;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private TrainingData trainingData;
    private List<Layer> hiddenLayers;
    private Layer outputLayer;

    public NeuralNetwork(TrainingData trainingData, int inputLayer, int[] hiddenLayers, int outputLayer) {
        this.trainingData = trainingData;
        this.hiddenLayers = new ArrayList<>();

        this.hiddenLayers.add(new Layer(hiddenLayers[0], inputLayer));
        for (int i = 1; i < hiddenLayers.length; i++) {
            this.hiddenLayers.add(new Layer(hiddenLayers[i], hiddenLayers[i - 1]));
        }
        this.outputLayer = new Layer(outputLayer, hiddenLayers[hiddenLayers.length - 1]);
    }

    private double getError() {
        List<List<Double>> input = trainingData.getData();
        List<List<Integer>> classes = trainingData.getCorrectClasses();

        double error = 0;
        for (int i = 0; i < input.size(); i++) {
            List<Double> output = forwardPass(input.get(i));
            for (int j = 0; j < 5; j++) {
                error += Math.pow(output.get(j) - (double) classes.get(i).get(j), 2);
            }
        }

        return error;
    }

    private List<Double> forwardPass(List<Double> input) {
        List<Double> output = input;

        for (Layer hiddenLayer : hiddenLayers) {
            output = hiddenLayer.forwardPass(output);
        }

        output = outputLayer.forwardPass(output);

        return output;
    }

    private void backwardPass() {
        List<Double> output = trainingData.getData().get(0);
        List<Integer> correctOuput = trainingData.getCorrectClasses().get(0);

        for (int i = 0; i < hiddenLayers.size(); i++) {
            output = hiddenLayers.get(i).forwardPass(output);
        }
        outputLayer.forwardPass(output);

        //solve output layer
        List<Neuron> neurons = outputLayer.getNeurons();
        for (int j = 0; j < neurons.size(); j++) {
            Neuron outputNeuron = outputLayer.getNeurons().get(j);
            double ysj = outputNeuron.getOutput();
            double deltasj = ysj * (1 - ysj) * (correctOuput.get(j) - ysj);

            Layer layerBefore = hiddenLayers.get(hiddenLayers.size() - 1);
            List<Neuron> neuronsBefore = layerBefore.getNeurons();

            for (int s = 0; s < neuronsBefore.size(); s++) {
                double ysi = neuronsBefore.get(s).getOutput();
                double weight = neurons.get(j).getWeight(j);
                neurons.get(j).setWeight(j, weight + ysi * deltasj);
            }
        }

        // solve rest
        for (int H = hiddenLayers.size() - 1; H > 0; H--) {
            List<Neuron> jNeurons = hiddenLayers.get(H).getNeurons();
            for (int j = 0; j < jNeurons.size(); j++) {
                Neuron outputNeuron = outputLayer.getNeurons().get(j);
                double ysj = outputNeuron.getOutput();
                double deltasj = ysj * (1 - ysj) * (correctOuput.get(j) - ysj);
            }
        }
    }

    public void train(int iterations) {
        for (int i = 1; i <= iterations; i++) {

            double error = getError();
            System.out.println("Iteration: " + i + " error: " + error);

            backwardPass();
        }
    }
}
