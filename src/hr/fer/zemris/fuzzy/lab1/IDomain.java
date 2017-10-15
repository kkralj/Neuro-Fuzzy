package hr.fer.zemris.fuzzy.lab1;

public interface IDomain extends Iterable<DomainElement> {

    int getCardinality();

    IDomain getComponent(int component);

    int getNumberOfComponents();

    int indexOfElement(DomainElement element);

    DomainElement elementForIndex(int index);

}
