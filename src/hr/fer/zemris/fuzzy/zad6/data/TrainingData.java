package hr.fer.zemris.fuzzy.zad6.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TrainingData {

    private List<Double> x1;
    private List<Double> x2;
    private List<Double> y;

    public TrainingData(List<Double> x1, List<Double> x2, List<Double> y) {
        if (x1.size() != x2.size() || x1.size() != y.size()) {
            throw new IllegalArgumentException();
        }

        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
    }

    public static TrainingData load(Path path) throws IOException {
        List<Double> x1 = new ArrayList<>();
        List<Double> x2 = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] values = line.trim().split("\\s+");
            if (values.length == 3) {
                x1.add(Double.parseDouble(values[0]));
                x2.add(Double.parseDouble(values[1]));
                y.add(Double.parseDouble(values[2]));
            }
        }

        return new TrainingData(x1, x2, y);
    }

    public int getSize() {
        return y.size();
    }

    public List<Double> getX1() {
        return x1;
    }

    public List<Double> getX2() {
        return x2;
    }

    public List<Double> getY() {
        return y;
    }
}
