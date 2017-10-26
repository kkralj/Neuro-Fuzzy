package hr.fer.zemris.fuzzy.zad1;

public class Operations {

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        MutableFuzzySet result = new MutableFuzzySet(set.getDomain());
        for (DomainElement element : result.getDomain()) {
            result.set(element, function.valueAt(set.getValueAt(element)));
        }
        return result;
    }

    public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction function) {
        MutableFuzzySet result = new MutableFuzzySet(set1.getDomain());
        for (DomainElement element : result.getDomain()) {
            result.set(element, function.valueAt(set1.getValueAt(element), set2.getValueAt(element)));
        }
        return result;
    }

    public static IUnaryFunction zadehNot() {
        return x -> 1.0 - x;
    }

    public static IBinaryFunction zadehAnd() {
        return Math::min;
    }

    public static IBinaryFunction zadehOr() {
        return Math::max;
    }

    public static IBinaryFunction hamacherTNorm(double p) {
        return (x, y) -> (x * y) / (p + (1 - p) * (x + y - x * y));
    }

    public static IBinaryFunction hamacherSNorm(double p) {
        return (x, y) -> (x + y - (2 - p) * x * y) / (1 - (1 - p) * x * y);
    }
}
