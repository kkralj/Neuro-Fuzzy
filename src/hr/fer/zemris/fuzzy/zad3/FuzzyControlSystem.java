package hr.fer.zemris.fuzzy.zad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FuzzyControlSystem {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int L, D, LK, DK, V, S, akcel, kormilo;

        String line = null;
        while (true) {
            if ((line = reader.readLine()) == null) continue;
            if (line.charAt(0) == 'K') break;

            Scanner s = new Scanner(line);
            L = s.nextInt();
            D = s.nextInt();
            LK = s.nextInt();
            DK = s.nextInt();
            V = s.nextInt();
            S = s.nextInt();

            // todo

            akcel = 10;
            kormilo = 5;
            System.out.println(akcel + " " + kormilo);
            System.out.flush();
        }
    }
}
