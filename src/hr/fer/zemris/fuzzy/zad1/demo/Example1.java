package hr.fer.zemris.fuzzy.zad1.demo;

import hr.fer.zemris.fuzzy.zad1.*;

public class Example1 {
    public static void main(String[] args) {
        IDomain d = Domain.intRange(0, 11);

        IFuzzySet set1 = new MutableFuzzySet(d)
                .set(DomainElement.of(0), 1.0)
                .set(DomainElement.of(1), 0.8)
                .set(DomainElement.of(2), 0.6)
                .set(DomainElement.of(3), 0.4)
                .set(DomainElement.of(4), 0.2);
        Debug.print(set1, "Set 1:");

        IDomain d2 = Domain.intRange(-5, 6);
        IFuzzySet set2 = new CalculatedFuzzySet(
                d2,
                StandardFuzzySets.lambdaFunction(
                        d2.indexOfElement(DomainElement.of(-4)),
                        d2.indexOfElement(DomainElement.of(0)),
                        d2.indexOfElement(DomainElement.of(4))
                )
        );
        Debug.print(set2, "Set 2:");
    }
}