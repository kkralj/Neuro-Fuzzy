package hr.fer.zemris.fuzzy.zad1;

public interface IFuzzySet {

    IDomain getDomain();

    double getValueAt(DomainElement element);
}
