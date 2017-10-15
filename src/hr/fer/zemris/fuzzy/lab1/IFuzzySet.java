package hr.fer.zemris.fuzzy.lab1;

public interface IFuzzySet {
    IDomain getDomain();

    double getValueAt(DomainElement element);
}
