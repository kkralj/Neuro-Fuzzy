package hr.fer.zemris.fuzzy.zad3.demo;

import hr.fer.zemris.fuzzy.zad1.IFuzzySet;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.Defuzzifier;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.impl.COADefuzzifier;
import hr.fer.zemris.fuzzy.zad3.fuzzysystem.FuzzySystem;
import hr.fer.zemris.fuzzy.zad3.rule.Rule;
import hr.fer.zemris.fuzzy.zad3.rule.RuleBase;

import java.util.Scanner;

public class RuleTesting {
    public static void main(String[] args) {
        Defuzzifier def = new COADefuzzifier();

        FuzzySystem fsAccel = new FuzzySystem(def, RuleBase.getAccelerationRules(), Rule.PRODUCT_OPEATOR);
        FuzzySystem fsHelm = new FuzzySystem(def, RuleBase.getHelmRules(), Rule.PRODUCT_OPEATOR);

        Scanner s = new Scanner(System.in);
        int L, D, LK, DK, V, S, rule_ind;

        while (true) {
            System.out.println("Enter L, D, LK, DK, V, S");

            L = s.nextInt();
            D = s.nextInt();
            LK = s.nextInt();
            DK = s.nextInt();
            V = s.nextInt();
            S = s.nextInt();

            System.out.println("Enter rule index:");
            rule_ind = s.nextInt();

            IFuzzySet result = fsHelm.getRules()
                    .get(rule_ind).getResult(new int[]{L, D, LK, DK, V, S}, Rule.PRODUCT_OPEATOR);

            System.out.println("Fuzzy set: " + result);
            System.out.println("Decoded value: " + def.defuzzify(result));
            System.out.println("Conclusion: " + fsHelm.conclude(L, D, LK, DK, V, S));
        }
    }
}
