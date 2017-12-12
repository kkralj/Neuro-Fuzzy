package hr.fer.zemris.fuzzy.zad5.net;

import hr.fer.zemris.fuzzy.zad5.DataPoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TrainingData {

    private List<List<DataPoint>> data = new ArrayList<>();
    private List<Integer> correctClasses = new ArrayList<>();

    public void fill(String filePath, int M) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<DataPoint> example = new ArrayList<>();

        int i = 0;
        while (i < lines.size()) {
            for (int j = 0; j < M; j++, i++) {
                String[] values = lines.get(i).trim().split("\\s+");
                example.add(new DataPoint(Double.parseDouble(values[0]), Double.parseDouble(values[1])));
            }
            data.add(example);
            example = new ArrayList<>();

            // one-hot
            Integer y = convertBack(lines.get(i));
            correctClasses.add(y);
            i++;
        }
    }

    private Integer convertBack(String s) {
        for (int i = 0; i < 5; i++) {
            if (s.charAt(i) == '1') {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    public List<List<DataPoint>> getData() {
        return data;
    }

    public List<Integer> getCorrectClasses() {
        return correctClasses;
    }
}
