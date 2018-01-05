package hr.fer.zemris.fuzzy.zad6.data;

import java.io.IOException;
import java.io.PrintWriter;

public class DataGenerator {

    public static void main(String[] args) throws IOException {
        try (PrintWriter writer = new PrintWriter("lab6-data/train.txt", "UTF-8")) {
            for (int x = -4; x <= 4; x++) {
                for (int y = -4; y <= 4; y++) {
                    writer.write(x + " " + y + " " + getFunctionValue(x, y) + "\n");
                }
            }
        }
    }

    public static double getFunctionValue(double x, double y) {
        return (Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5 * x * y + 3) * Math.pow(Math.cos(x / 5), 2);
    }
}
