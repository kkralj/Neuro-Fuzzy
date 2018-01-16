package hr.fer.zemris.fuzzy.zad7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TrainingData implements Iterable<Point> {

    private List<Point> data;

    public TrainingData(List<Point> data) {
        this.data = Objects.requireNonNull(data);
    }

    public static TrainingData load(String path) throws IOException {
        List<Point> points = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            String[] values = line.trim().split("\\s+");
            if (values.length == 5) {
                points.add(new Point(
                        Double.parseDouble(values[0]),
                        Double.parseDouble(values[1]),
                        new double[]{
                                Double.parseDouble(values[2]),
                                Double.parseDouble(values[3]),
                                Double.parseDouble(values[4])
                        }
                ));
            }
        }

        return new TrainingData(points);
    }

    public Point getPoint(int i) {
        return data.get(i);
    }

    public int getSize() {
        return data.size();
    }

    public List<Point> getData() {
        return data;
    }

    @Override
    public Iterator<Point> iterator() {
        return data.iterator();
    }
}
