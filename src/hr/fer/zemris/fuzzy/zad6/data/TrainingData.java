package hr.fer.zemris.fuzzy.zad6.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TrainingData implements Iterable<Point> {

    private List<Point> data;

    public TrainingData(List<Point> data) {
        this.data = Objects.requireNonNull(data);
//        Collections.shuffle(data);
    }

    public static TrainingData load(Path path) throws IOException {
        List<Point> points = new ArrayList<>();

        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] values = line.trim().split("\\s+");
            if (values.length == 3) {
                points.add(new Point(
                        Double.parseDouble(values[0]),
                        Double.parseDouble(values[1]),
                        Double.parseDouble(values[2])
                ));
            }
        }

        return new TrainingData(points);
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
