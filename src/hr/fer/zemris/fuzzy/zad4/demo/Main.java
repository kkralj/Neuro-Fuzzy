package hr.fer.zemris.fuzzy.zad4.demo;

import hr.fer.zemris.fuzzy.zad4.EliminationGA;
import hr.fer.zemris.fuzzy.zad4.Function;
import hr.fer.zemris.fuzzy.zad4.GenerationGA;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GenerationGA ga = new GenerationGA();
        EliminationGA cga = new EliminationGA();

        Function f1 = new Function();
        f1.loadData("dataset/lab4-data/zad4-dataset1.txt");
        ga.run(5, 15, 20_000, 0.15, f1);
        cga.run(15, 20_000, 0.15, f1);

        Function f2 = new Function();
        f2.loadData("dataset/lab4-data/zad4-dataset2.txt");
        ga.run(5, 20, 20_000, 0.2, f2);
        cga.run(20, 20_000, 0.2, f2);
    }
}
