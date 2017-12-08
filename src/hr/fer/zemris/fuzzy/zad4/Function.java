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

    private double valueAt(double x, double y, double[] betas) {
        double inv_e = 1 / Math.exp(Math.pow(x - betas[4], 2));

        return Math.sin(betas[0] + betas[1] * x) +
                betas[2] * Math.cos(x * (betas[3] + y)) * inv_e;
    }

    public List<Double> valuesFor(Chromosome chromosome) {
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < x1.size(); i++) {
            values.add(valueAt(x1.get(i), x2.get(i), chromosome.getBetas()));
        }
        return values;
    }

    public double totalError(List<Double> evaluated) {
        double err = 0;
        for (int i = 0; i < y.size(); i++) {
            err += Math.pow(y.get(i) - evaluated.get(i), 2);
        }
        return err / evaluated.size();
    }
}