package hr.fer.zemris.fuzzy.zad1;

import java.util.Iterator;

public class SimpleDomain extends Domain {

    private int first, last;

    public SimpleDomain(int first, int last) {
        if (first >= last) {
            throw new IllegalArgumentException();
        }
        this.first = first;
        this.last = last;
    }

    public int getCardinality() {
        return last - first;
    }

    public IDomain getComponent(int i) {
        return this;
    }

    @Override
    public int getNumberOfComponents() {
        return 1;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new SimpleDomainIterator(first, last);
    }

    private static class SimpleDomainIterator implements Iterator<DomainElement> {

        private int current, remaining;

        SimpleDomainIterator(int first, int last) {
            this.current = first;
            this.remaining = last - first;
        }

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public DomainElement next() {
            int val = current;

            current++;
            remaining--;

            int[] vals = {val};
            return new DomainElement(vals);
        }
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDomain that = (SimpleDomain) o;

        if (first != that.first) return false;
        return last == that.last;
    }

    @Override
    public int hashCode() {
        int result = first;
        result = 31 * result + last;
        return result;
    }
}
