package hr.fer.zemris.fuzzy.zad3.defuzzifiers.impl;

import hr.fer.zemris.fuzzy.zad1.DomainElement;
import hr.fer.zemris.fuzzy.zad1.IFuzzySet;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.Defuzzifier;

/**
 * CenterOfArea defuzzifier
 */
public class COADefuzzifier implements Defuzzifier {

    @Override
    public double defuzzify(IFuzzySet set) {
        double result = 0, totalMembership = 0;

        for (DomainElement element : set.getDomain()) {
            double mi = set.getValueAt(element);
            result += mi * element.getComponentValue(0);
            totalMembership += mi;
        }

        if (Math.abs(totalMembership) < 1e-9) {
            return 0;
        }

        return result / totalMembership;
    }
}
