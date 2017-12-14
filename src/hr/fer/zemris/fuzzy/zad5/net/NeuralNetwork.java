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

        this.outputLayer = new OutputLayer(outputLayer, hiddenLayers[hiddenLayers.length - 1]);
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

    private void backwardPass() {
        for (int dataIndex = 0; dataIndex < trainingData.getData().size(); dataIndex++) {

            List<Double> input = trainingData.getData().get(dataIndex);
            List<Double> realOutput = trainingData.getCorrectClasses().get(dataIndex);
            List<Double> netOutput = forwardPass(input);

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
                neurons.get(i).updateWeight(0.01, outputLayer, neurons.get(i).getOutput());
                neurons.get(i).setDelta(layer.getDelta(i, outputLayer));
            }

            // hidden layers
            for (int i = hiddenLayers.size() - 2; i >= 0; i--) {
                layer = hiddenLayers.get(i);
                neurons = layer.getNeurons();
                for (int j = 0; j < neurons.size(); j++) {
                    neurons.get(j).updateWeight(1, hiddenLayers.get(i + 1), neurons.get(i).getOutput());
                    neurons.get(j).setDelta(layer.getDelta(j, outputLayer));
                }
            }
        }
//
//            // output layer
//            HiddenLayer outputLayer = hiddenLayers.get(hiddenLayers.size() - 1);
//            List<Neuron> neurons = outputLayer.getNeurons();
//            for (int j = 0; j < neurons.size(); j++) {
//                Neuron neuron = neurons.get(j);
//                double delta = outputLayer.getDelta(j, realOutput.get(j));
//
//                HiddenLayer layerBefore = hiddenLayers.get(hiddenLayers.size() - 2);
//
//                List<Neuron> neuronsBefore = layerBefore.getNeurons();
//                for (int i = 0; i < neuronsBefore.size(); i++) {
//                    double ysi = neuronsBefore.get(i).getOutput();
//                    neuron.setWeight(i, neuron.getWeight(i) + ysi * delta);
//                }
//            }
//
//            // rest
//            for (int k = hiddenLayers.size() - 2; k > 0; k--) {
//                HiddenLayer nextLayer = hiddenLayers.get(k + 1);
//                HiddenLayer layer = hiddenLayers.get(k);
//                HiddenLayer prevLayer = hiddenLayers.get(k - 1);
//
//                neurons = layer.getNeurons();
//                List<Neuron> prevNeurons = prevLayer.getNeurons();
//                List<Neuron> nextNeurons = nextLayer.getNeurons();
//
//                for (int j = 0; j < neurons.size(); j++) {
//                    Neuron neuron = neurons.get(j);
//                    for (int i = 0; i < nextNeurons.size(); i++) {
//                        double delta = layer.getDelta(j, nextLayer);
//                        for (int q = 0; q < prevNeurons.size(); q++) {
//                            double ysi = neurons.get(q).getOutput();
//                            neuron.setWeight(q, neuron.getWeight(q) + ysi * delta);
//                        }
//                    }
//                }
//            }
//
//            HiddenLayer nextLayer = hiddenLayers.get(1);
//            HiddenLayer layer = hiddenLayers.get(0);
//            neurons = layer.getNeurons();
//            List<Neuron> nextNeurons = nextLayer.getNeurons();
//
//            for (int j = 0; j < neurons.size(); j++) {
//                Neuron neuron = neurons.get(j);
//                for (int i = 0; i < nextNeurons.size(); i++) {
//                    double delta = layer.getDelta(j, nextLayer);
//                    for (int q = 0; q < inputLayer.getSize(); q++) {
//                        double ysi = neurons.get(q).getOutput();
//                        neuron.setWeight(q, neuron.getWeight(q) + ysi * delta);
//                    }
//                }
//            }
//
////            System.out.println("end");

    }

    public void train(int iterations) {
        for (int i = 1; i <= iterations; i++) {
            double error = getError();
            System.out.println("Iteration: " + i + " error: " + error);
            backwardPass();
        }
    }
}
