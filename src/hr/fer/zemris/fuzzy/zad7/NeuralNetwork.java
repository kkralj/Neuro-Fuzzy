package hr.fer.zemris.fuzzy.zad7;

public class NeuralNetwork {

    private static final double EPS = 1e-7;

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

        outputs = new double[getOutputsCount()];
    }

    private int getOutputsCount() {
        int tot = 0;
        for (int layer : layers) {
            tot += layer;
        }
        return tot;
    }

    private int getParameterCount() {
        int tot = layers[0] * (2 * layers[1]);

        for (int i = 2; i < layers.length; i++) {
            tot += (layers[i - 1] + 1) * layers[i];
        }

        return tot;
    }

    public double calcError(double[] params) {
        double mse = 0;

        for (Point point : trainingData) {
            calcOutput(point.getX1(), point.getX2(), params);
            mse += Math.pow(point.getY()[0] - outputs[outputs.length - 3], 2);
            mse += Math.pow(point.getY()[1] - outputs[outputs.length - 2], 2);
            mse += Math.pow(point.getY()[2] - outputs[outputs.length - 1], 2);
        }

        mse /= trainingData.getSize();

        return mse;
    }

    private void calcOutput(double x1, double x2, double[] params) {
        if (params.length != getParameterCount()) {
            throw new IllegalArgumentException();
        }

        outputs[0] = x1;
        outputs[1] = x2;

        int paramInd = 0, outputInd = 2, prevOutputInd = 2;

        for (int i = 1; i < layers.length; i++) {
            for (int j = 0; j < layers[i]; j++) {
                if (i == 1) {

                    double net = Math.abs(x1 - params[paramInd]) / Math.abs(params[paramInd + 1]) +
                            Math.abs(x2 - params[paramInd + 2]) / Math.abs(params[paramInd + 3]);

                    paramInd += 4;

                    outputs[outputInd++] = f2(net);

                } else {

                    double net = params[paramInd++];

                    for (int q = 0; q < layers[i - 1]; q++) {
                        net += params[paramInd++] * outputs[prevOutputInd + q];
                    }

                    prevOutputInd += layers[i - 1];
                    outputs[outputInd++] = f1(net);
                }
            }
        }
    }

    private double f1(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    private double f2(double x) {
        return 1. / (1 + x);
    }
}
