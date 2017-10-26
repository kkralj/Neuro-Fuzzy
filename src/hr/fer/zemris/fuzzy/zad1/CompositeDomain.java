package hr.fer.zemris.fuzzy.zad1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class CompositeDomain extends Domain {

    private SimpleDomain[] components;

    private int cardinality = 1;

    public CompositeDomain(SimpleDomain... components) {
        this.components = Objects.requireNonNull(components);

        for (SimpleDomain component : components) {
            cardinality *= component.getCardinality();
        }
    }

    @Override
    public int getCardinality() {
        return cardinality;
    }

    @Override
    public IDomain getComponent(int component) {
        return components[component];
    }

    @Override
    public int getNumberOfComponents() {
        return components.length;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new CompositeDomainIterator(components, cardinality);
    }

    private static class CompositeDomainIterator implements Iterator<DomainElement> {

        private SimpleDomain[] components;

        private int used, totalElementsCount;

        private Iterator[] iterators;
        private int activePos;

        private int[] values;

        CompositeDomainIterator(SimpleDomain[] components, int totalElementsCount) {
            this.components = components;
            this.values = new int[components.length];
            this.totalElementsCount = totalElementsCount;

            this.iterators = new Iterator[components.length];
            for (int i = 0; i < iterators.length; i++) {
                iterators[i] = components[i].iterator();
            }

            this.activePos = components.length - 1;
        }

        @Override
        public boolean hasNext() {
            return used < totalElementsCount;
        }

        @Override
        public DomainElement next() {
            if (used == 0) { // first iteration
                for (int i = 0; i < components.length; i++) {
                    values[i] = ((DomainElement) iterators[i].next()).getComponentValue(0);
                }
            } else if (iterators[activePos].hasNext()) { // update current iterator
                values[activePos] = ((DomainElement) iterators[activePos].next()).getComponentValue(0);

            } else { // go to next iterator
                while (!iterators[activePos].hasNext()) {
                    activePos--;
                }
                values[activePos] = ((DomainElement) iterators[activePos].next()).getComponentValue(0);
                for (int i = activePos + 1; i < components.length; i++) {
                    iterators[i] = components[i].iterator();
                    values[i] = ((DomainElement) iterators[i].next()).getComponentValue(0);
                }
                activePos = components.length - 1;
            }

            used++;
            return new DomainElement(values);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeDomain that = (CompositeDomain) o;

        if (cardinality != that.cardinality) return false;
        return Arrays.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(components);
        result = 31 * result + cardinality;
        return result;
    }
}
