package hr.fer.zemris.fuzzy.zad1.demo;

import hr.fer.zemris.fuzzy.zad1.*;

public class Main {
    public static void main(String[] args) {
        IDomain d1 = Domain.intRange(2, 6);
        Debug.print(d1, "Elements of domain d1:");

        IDomain d2 = Domain.intRange(9, 10);
        Debug.print(d2, "Elements of domain d2:");

        IDomain d3 = Domain.combine(d1, d2);
        Debug.print(d3, "Elements of domain d3:");

        System.out.println(d3.elementForIndex(0));
        System.out.println(d3.elementForIndex(5));
        System.out.println(d3.elementForIndex(14));

        System.out.println(d3.indexOfElement(DomainElement.of(4, 1)));

        IDomain d5 = new CompositeDomain(
                (SimpleDomain) Domain.intRange(0, 5),
                (SimpleDomain) Domain.intRange(0, 1),
                (SimpleDomain) Domain.intRange(0, 5),
                (SimpleDomain) Domain.intRange(0, 5)
        );
        Debug.print(d5, "Elements of domain d5:");
    }
}
