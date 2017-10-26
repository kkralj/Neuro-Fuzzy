package hr.fer.zemris.fuzzy.zad2;

import hr.fer.zemris.fuzzy.zad1.*;

public class Relations {

    public static boolean isUtimesURelation(IFuzzySet relation) {
        if (relation.getDomain().getNumberOfComponents() != 2) {
            return false;
        }

        IDomain domain1 = relation.getDomain().getComponent(0);
        IDomain domain2 = relation.getDomain().getComponent(1);

        return domain1.equals(domain2);
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;

        for (DomainElement element : relation.getDomain()) {
            int el1 = element.getComponentValue(0);
            int el2 = element.getComponentValue(1);

            double x1 = relation.getValueAt(DomainElement.of(el1, el2));
            double x2 = relation.getValueAt(DomainElement.of(el2, el1));

            if (x1 != x2) return false;
        }

        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;

        for (DomainElement element : relation.getDomain()) {
            int el1 = element.getComponentValue(0);
            int el2 = element.getComponentValue(1);

            if (el1 == el2 && relation.getValueAt(element) != 1) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;

        for (DomainElement element : relation.getDomain()) {
            int elX = element.getComponentValue(0);
            int elZ = element.getComponentValue(1);

            double miRxz = relation.getValueAt(element);
            double maxVal = 0;

            for (DomainElement element1 : relation.getDomain()) {
                int elY = element1.getComponentValue(1);

                DomainElement elXY = DomainElement.of(elX, elY);
                DomainElement elYZ = DomainElement.of(elY, elZ);

                double miRxy = relation.getValueAt(elXY);
                double miRyz = relation.getValueAt(elYZ);

                maxVal = Math.max(maxVal, Math.min(miRxy, miRyz));
            }

            if (miRxz < maxVal) return false;
        }

        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet rel1, IFuzzySet rel2) {
        IDomain dx = rel1.getDomain().getComponent(0);
        IDomain dy = rel1.getDomain().getComponent(1);
        IDomain dz = rel2.getDomain().getComponent(1);

        MutableFuzzySet result = new MutableFuzzySet(Domain.combine(dx, dz));

        if (dy.getCardinality() != rel2.getDomain().getComponent(0).getCardinality()) {
            throw new IllegalArgumentException("Cannot create composition.");
        }

        for (DomainElement x : dx) {
            int valX = x.getComponentValue(0);
            for (DomainElement z : dz) {
                int valZ = z.getComponentValue(0);
                for (DomainElement y : dy) {
                    int valY = y.getComponentValue(0);

                    DomainElement elXY = DomainElement.of(valX, valY);
                    DomainElement elYZ = DomainElement.of(valY, valZ);
                    DomainElement elXZ = DomainElement.of(valX, valZ);

                    result.set(elXZ,
                            Math.max(
                                    result.getValueAt(elXZ),
                                    Math.min(rel1.getValueAt(elXY), rel2.getValueAt(elYZ))
                            )
                    );
                }
            }
        }

        return result;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return isReflexive(relation) && isSymmetric(relation) && isMaxMinTransitive(relation);
    }
}
