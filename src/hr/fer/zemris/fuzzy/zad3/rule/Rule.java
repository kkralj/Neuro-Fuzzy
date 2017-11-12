package hr.fer.zemris.fuzzy.zad3.rule;

import hr.fer.zemris.fuzzy.zad1.DomainElement;
import hr.fer.zemris.fuzzy.zad1.IFuzzySet;
import hr.fer.zemris.fuzzy.zad1.MutableFuzzySet;

import java.util.Objects;
import java.util.function.BinaryOperator;

public class Rule {

    public static final BinaryOperator<Double> MIN_OPEATOR = Math::min;
    public static final BinaryOperator<Double> PRODUCT_OPEATOR = (x, y) -> x * y;

    private IFuzzySet[] antecedents;
    private IFuzzySet conclusion;

    public Rule(IFuzzySet[] antecedents, IFuzzySet conclusion) {
        this.antecedents = Objects.requireNonNull(antecedents);
        this.conclusion = Objects.requireNonNull(conclusion);
    }

    public IFuzzySet getResult(int[] variables, BinaryOperator<Double> operator) {
        if (variables.length != antecedents.length) {
            throw new IllegalArgumentException("Invalid dimensions!");
        }

        double ant_mi = 1.0;
        for (int i = 0; i < variables.length; i++) {
            if (antecedents[i] != null) {
                ant_mi = operator.apply(ant_mi, antecedents[i].getValueAt(DomainElement.of(variables[i])));
            }
        }

        MutableFuzzySet result = new MutableFuzzySet(conclusion.getDomain());
        for (DomainElement element : conclusion.getDomain()) {
            result.set(element, operator.apply(ant_mi, conclusion.getValueAt(element)));
        }

        return result;
    }


}
