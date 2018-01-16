package hr.fer.zemris.fuzzy.zad7;

public class NeuralNetwork {

    private int[] layers;
    private double[] outputs;
    private TrainingData trainingData;

    public NeuralNetwork(TrainingData trainingData, int[] hiddenLayers) {
        if (hiddenLayers.length < 1) {
            throw new IllegalArgumentException("At east one hidden layer required!.");
        }

        this.trainingData = trainingData;
        this.layers = new int[hiddenLayers.length + 2];

        layers[0] = 2; // input layer
        layers[layers.length - 1] = 3; // output layer
        System.arraycopy(hiddenLayers, 0, layers, 1, hiddenLayers.length); // hidden layers

        outputs = new double[getOutputCount()];
    }

    public double calcError(double[] params) {
        double mse = 0;

        for (Point point : trainingData) {
            calcOutput(point.getX1(), point.getX2(), params);
            for (int i = 0; i < 3; i++) {
                mse += Math.pow(point.getY()[0] - outputs[outputs.length - 3 + i], 2);
            }
        }

        mse /= trainingData.getSize();

        return mse;
    }

    public int getParameterCount() {
        int tot = layers[0] * (2 * layers[1]);

        for (int i = 2; i < layers.length; i++) {
            tot += (layers[i - 1] + 1) * layers[i];
        }

        return tot;
    }

    private void calcOutput(double x1, double x2, double[] params) {
        if (params.length != getParameterCount()) {
            throw new IllegalArgumentException();
        }

        outputs[0] = x1;
        outputs[1] = x2;

        int paramInd = 0, outputInd = 2, prevOutputInd = 0;

        for (int i = 1; i < layers.length; i++) {

            for (int j = 0; j < layers[i]; j++) {
                if (i == 1) {
                    double net = f2(
                            Math.abs(x1 - params[paramInd++]) / Math.abs(params[paramInd++]) +
                                    Math.abs(x2 - params[paramInd++]) / Math.abs(params[paramInd++])
                    );

                    outputs[outputInd++] = net;

                } else {
                    double net = params[paramInd++]; // w0

                    for (int q = 0; q < layers[i - 1]; q++) {
                        net += params[paramInd++] * outputs[prevOutputInd + q];
                    }

                    outputs[outputInd++] = f1(net);
                }
            }

            prevOutputInd += layers[i - 1];
        }

    }

    private int getOutputCount() {
        int tot = 0;
        for (int layer : layers) {
            tot += layer;
        }
        return tot;
    }

    private double f1(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    private double f2(double x) {
        return 1. / (1 + x);
    }
}
