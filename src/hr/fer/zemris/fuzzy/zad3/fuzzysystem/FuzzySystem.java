package hr.fer.zemris.fuzzy.zad3.fuzzysystem;

import hr.fer.zemris.fuzzy.zad1.IFuzzySet;
import hr.fer.zemris.fuzzy.zad1.Operations;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.Defuzzifier;
import hr.fer.zemris.fuzzy.zad3.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

public class FuzzySystem {

    private List<Rule> rules;
    private Defuzzifier defuzzifier;
    private BinaryOperator<Double> operator;

    public FuzzySystem(Defuzzifier defuzzifier, List<Rule> rules, BinaryOperator<Double> operator) {
        this.rules = Objects.requireNonNull(rules);
        this.defuzzifier = Objects.requireNonNull(defuzzifier);
        this.operator = Objects.requireNonNull(operator);

        if (rules.size() == 0) {
            throw new IllegalArgumentException("Rules list cannot be empty.");
        }
    }

    public int conclude(int L, int D, int LK, int DK, int V, int S) {
        ArrayList<IFuzzySet> results = new ArrayList<>();

        for (Rule rule : rules) {
            results.add(rule.getResult(new int[]{L, D, LK, DK, V, S}, operator));
        }

        IFuzzySet total = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            total = Operations.binaryOperation(total, results.get(i), Operations.zadehOr());
        }

        return (int) defuzzifier.defuzzify(total);
    }

}
