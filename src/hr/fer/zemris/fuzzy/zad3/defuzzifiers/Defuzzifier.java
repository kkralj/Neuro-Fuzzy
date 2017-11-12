package hr.fer.zemris.fuzzy.zad3.defuzzifiers;

import hr.fer.zemris.fuzzy.zad1.IFuzzySet;

public interface Defuzzifier {
    double defuzzify(IFuzzySet set);
}
