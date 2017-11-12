package hr.fer.zemris.fuzzy.zad3.fuzzysystem;

import hr.fer.zemris.fuzzy.zad1.DomainElement;
import hr.fer.zemris.fuzzy.zad1.IFuzzySet;
import hr.fer.zemris.fuzzy.zad1.MutableFuzzySet;

import java.util.Objects;

public class Rule {

    private IFuzzySet[] antecedents;
    private IFuzzySet conclusion;

    public Rule(IFuzzySet[] antecedents, IFuzzySet conclusion) {
        this.antecedents = Objects.requireNonNull(antecedents);
        this.conclusion = Objects.requireNonNull(conclusion);
    }

    public IFuzzySet getResult(int[] variables) {
        if (variables.length != antecedents.length) {
            throw new IllegalArgumentException("Invalid dimensions!");
        }

        double ant_mi = 1.0;
        for (int i = 0; i < variables.length; i++) {
            if (antecedents[i] != null) {
                ant_mi *= antecedents[i].getValueAt(DomainElement.of(variables[i]));
            }
        }

        MutableFuzzySet result = new MutableFuzzySet(conclusion.getDomain());
        for (DomainElement element : conclusion.getDomain()) {
            result.set(element, ant_mi * conclusion.getValueAt(element));
        }

        return result;
    }
}
