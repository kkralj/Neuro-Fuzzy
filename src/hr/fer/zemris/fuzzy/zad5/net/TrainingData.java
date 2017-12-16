package hr.fer.zemris.fuzzy.zad5.net;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingData {

    private List<Data> data = new ArrayList<>();

    public TrainingData() {
    }

    public TrainingData(List<List<Double>> data, List<List<Double>> correctClasses) {
        for (int i = 0; i < data.size(); i++) {
            this.data.add(new Data(data.get(i), correctClasses.get(i)));
        }
        Collections.shuffle(data);
    }

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
            // one-hot
            List<Double> y = convertBack(lines.get(i));
            data.add(new Data(example, y));

            example = new ArrayList<>();
            i++;
        }

        Collections.shuffle(data);
    }

    private List<Double> convertBack(String s) {
        List<Double> y = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            y.add(0.0);
        }

        for (int i = 0; i < 5; i++) {
            if (s.charAt(i) == '1') {
                y.set(i, 1.0);
            }
        }
        return y;
    }

    public List<List<Double>> getData() {
        List<List<Double>> dataList = new ArrayList<>();
        for (Data d : data) {
            dataList.add(d.getInput());
        }
        return dataList;
    }

    public List<List<Double>> getCorrectClasses() {
        List<List<Double>> classes = new ArrayList<>();
        for (Data d : data) {
            classes.add(d.getOutput());
        }
        return classes;
    }

    private static class Data {
        private List<Double> input;
        private List<Double> output;

        Data(List<Double> input, List<Double> output) {
            this.input = input;
            this.output = output;
        }

        List<Double> getInput() {
            return input;
        }

        List<Double> getOutput() {
            return output;
        }
    }
}
