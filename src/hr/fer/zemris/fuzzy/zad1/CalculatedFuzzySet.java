package hr.fer.zemris.fuzzy.zad1;

import java.util.Objects;

public class CalculatedFuzzySet implements IFuzzySet {

    private IDomain domain;
    private IIntUnaryFunction function;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction function) {
        this.domain = Objects.requireNonNull(domain);
        this.function = Objects.requireNonNull(function);
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return function.valueAt(domain.indexOfElement(element));
    }
}
