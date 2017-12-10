package hr.fer.zemris.fuzzy.zad4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Function {

    private List<Double> x1 = new ArrayList<>();
    private List<Double> x2 = new ArrayList<>();
    private List<Double> y = new ArrayList<>();

    public void loadData(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (String line : lines) {
            String[] values = line.split("\\s+");
            if (values.length == 3) {
                x1.add(Double.parseDouble(values[0]));
                x2.add(Double.parseDouble(values[1]));
                y.add(Double.parseDouble(values[2]));
            }
        }
    }

    private double valueAt(double x, double y, double[] b) {
        double inv_e = 1.0 / (1 + Math.exp(Math.pow(x - b[4], 2)));

        return Math.sin(b[0] + b[1] * x) + b[2] * Math.cos(x * (b[3] + y)) * inv_e;
    }

    private List<Double> valuesFor(Chromosome chromosome) {
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < y.size(); i++) {
            values.add(valueAt(x1.get(i), x2.get(i), chromosome.getBetas()));
        }
        return values;
    }

    public double totalError(Chromosome chr) {
        List<Double> evaluated = valuesFor(chr);

        if (evaluated.size() != y.size()) {
            throw new IllegalArgumentException();
        }

        double err = 0;
        for (int i = 0; i < y.size(); i++) {
            err += Math.pow(y.get(i) - evaluated.get(i), 2);
        }

        return err / y.size();
    }
}
