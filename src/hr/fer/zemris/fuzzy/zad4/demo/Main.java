package hr.fer.zemris.fuzzy.zad4.demo;

import hr.fer.zemris.fuzzy.zad4.CanonicGA;
import hr.fer.zemris.fuzzy.zad4.Function;
import hr.fer.zemris.fuzzy.zad4.GA;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GA ga = new GA();
        CanonicGA cga = new CanonicGA();

        Function f = new Function();
        f.loadData("lab4-data/zad4-dataset1.txt");

        ga.run(true, 100, 200, 0.2, f);
        cga.run(true, 100, 200, 0.2, f);
    }
}
