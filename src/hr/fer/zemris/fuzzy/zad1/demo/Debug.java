package hr.fer.zemris.fuzzy.zad1.demo;

import hr.fer.zemris.fuzzy.zad1.DomainElement;
import hr.fer.zemris.fuzzy.zad1.IDomain;
import hr.fer.zemris.fuzzy.zad1.IFuzzySet;

public class Debug {

    public static void print(IDomain domain, String headingText) {
        if (headingText != null) {
            System.out.println(headingText);
        }

        int cnt = 0;
        for (DomainElement element : domain) {
            System.out.println("Element domene je: " + element);
            cnt++;
        }

        System.out.println("Kardinalitet domene je: " + domain.getCardinality());
        System.out.println("Ispisano " + cnt + " elemenata");
        System.out.println();
    }

    public static void print(IFuzzySet fuzzySet, String headingText) {
        if (headingText != null) {
            System.out.println(headingText);
        }

        for (DomainElement element : fuzzySet.getDomain()) {
            System.out.format("d(%s)=%f%n", element, fuzzySet.getValueAt(element));
        }

        System.out.println();
    }
}
