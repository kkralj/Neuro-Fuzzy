package hr.fer.zemris.fuzzy.lab1;

import java.util.Arrays;

public class DomainElement {

    private int[] values;

    public DomainElement(int[] values) {
        if (values == null || values.length < 1) {
            throw new IllegalArgumentException("Invalid initialization array");
        }
        this.values = values;
    }

    public int getNumberOfComponents() {
        return values.length;
    }

    public int getComponentValue(int index) {
        if (index < 0 || index >= values.length) {
            throw new IllegalArgumentException("Index out of range");
        }
        return values[index];
    }

    public static DomainElement of(int... values) {
        return new DomainElement(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainElement that = (DomainElement) o;

        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        if (values.length == 1) {
            return String.valueOf(values[0]);
        }
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        boolean useComma = false;
        for (int value : values) {
            sb.append(useComma ? "," : "").append(value);
            useComma = true;
        }
        sb.append(")");

        return sb.toString();

    }
}
