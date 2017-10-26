package hr.fer.zemris.fuzzy.zad1;

public abstract class Domain implements IDomain {

    public static IDomain intRange(int start, int end) {
        return new SimpleDomain(start, end);
    }

    public static IDomain combine(IDomain first, IDomain second) {
        return new CompositeDomain((SimpleDomain)first, (SimpleDomain)second);
    }

    @Override
    public int indexOfElement(DomainElement element) {
        int i = 0;
        for (DomainElement elem: this) {
            if (elem.equals(element)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        int i = 0;
        for (DomainElement elem: this) {
            if (i == index) {
                return elem;
            }
            i++;
        }
        return null;
    }

}
