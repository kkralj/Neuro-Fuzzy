package hr.fer.zemris.fuzzy.zad4.demo;

import hr.fer.zemris.fuzzy.zad4.Function;
import hr.fer.zemris.fuzzy.zad4.GA;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GA ga = new GA();
        Function f = new Function();
        f.loadData("/home/kkralj/Desktop/zad4-dataset1.txt");

        ga.run(true, 100, 1000, 0.15, f);
    }
}
