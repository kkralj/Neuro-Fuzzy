package hr.fer.zemris.fuzzy.zad3;

import hr.fer.zemris.fuzzy.zad3.defuzzifiers.COADefuzzifier;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.Defuzzifier;
import hr.fer.zemris.fuzzy.zad3.fuzzysystem.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.zad3.fuzzysystem.FuzzySystem;
import hr.fer.zemris.fuzzy.zad3.fuzzysystem.KormiloFuzzySystemMin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FuzzyControlSystem {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Defuzzifier def = new COADefuzzifier();

        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

        int L, D, LK, DK, V, S, A, K;
        String line;
        while (true) {
            if ((line = reader.readLine()) == null) continue;
            if (line.charAt(0) == 'K') break; // 'KRAJ'

            Scanner s = new Scanner(line);
            L = s.nextInt();
            D = s.nextInt();
            LK = s.nextInt();
            DK = s.nextInt();
            V = s.nextInt();
            S = s.nextInt();

            A = fsAkcel.conclude(L, D, LK, DK, V, S);
            K = fsKormilo.conclude(L, D, LK, DK, V, S);

            System.out.println(A + " " + K);
            System.out.flush();
        }
    }
}
