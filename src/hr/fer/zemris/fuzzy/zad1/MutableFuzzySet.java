package hr.fer.zemris.fuzzy.zad1;

import java.util.Objects;

public class MutableFuzzySet implements IFuzzySet {

    private IDomain domain;

    private double[] memberships;

    public MutableFuzzySet(IDomain domain) {
        this.domain = Objects.requireNonNull(domain);
        this.memberships = new double[domain.getCardinality()];
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        int index = domain.indexOfElement(element);
        return memberships[index];
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        int index = domain.indexOfElement(element);
        memberships[index] = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (DomainElement element : domain) {
            result.append("Element [ ").append(element).append(" ], ")
                    .append("mi = ( ").append(getValueAt(element)).append(" )")
                    .append("\n");
        }
        return result.toString();
    }
}
