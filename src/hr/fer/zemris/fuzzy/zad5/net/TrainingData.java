package hr.fer.zemris.fuzzy.zad5.net;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TrainingData {

    private List<List<Double>> data = new ArrayList<>();
    private List<List<Integer>> correctClasses = new ArrayList<>();

    public void fill(String filePath, int M) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<Double> example = new ArrayList<>();

        int i = 0;
        while (i < lines.size()) {
            for (int j = 0; j < M; j++, i++) {
                String[] values = lines.get(i).trim().split("\\s+");
                example.add(Double.parseDouble(values[0]));
                example.add(Double.parseDouble(values[1]));
            }
            data.add(example);
            example = new ArrayList<>();

            // one-hot
            List<Integer> y = convertBack(lines.get(i));
            correctClasses.add(y);
            i++;
        }
    }

    private List<Integer> convertBack(String s) {
        List<Integer> y = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            y.add(0);
        }

        for (int i = 0; i < 5; i++) {
            if (s.charAt(i) == '1') {
                y.set(i, 1);
            }
        }
        return y;
    }

    public List<List<Double>> getData() {
        return data;
    }

    public List<List<Integer>> getCorrectClasses() {
        return correctClasses;
    }
}