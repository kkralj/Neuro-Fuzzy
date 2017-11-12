package hr.fer.zemris.fuzzy.zad3.fuzzysystem;

import hr.fer.zemris.fuzzy.zad1.*;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.Defuzzifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AkcelFuzzySystemMin implements FuzzySystem {

    private static final IDomain ACCELERATION_DOMAIN = new SimpleDomain(-50, 50 + 1);
    private static final IDomain VELOCITY_DOMAIN = new SimpleDomain(0, 100 + 1);

    private Defuzzifier def;

    private List<Rule> rules = new ArrayList<>();

    public AkcelFuzzySystemMin(Defuzzifier def) {
        this.def = Objects.requireNonNull(def);
        addRules();
    }

    private void addRules() {
        IFuzzySet tooFast = new CalculatedFuzzySet(VELOCITY_DOMAIN, StandardFuzzySets.gammaFunction(15, 30));

        IFuzzySet[] goingTooFastAntecedents = {null, null, null, null, tooFast, null};
        IFuzzySet goingTooFastConsequence = new CalculatedFuzzySet(
                ACCELERATION_DOMAIN, StandardFuzzySets.lFunction(-15, 0));

        Rule tooFastRule = new Rule(goingTooFastAntecedents, goingTooFastConsequence);
        rules.add(tooFastRule);

        IFuzzySet tooSlow = new CalculatedFuzzySet(VELOCITY_DOMAIN, StandardFuzzySets.lFunction(15, 30));
        IFuzzySet[] goingTooSlowAntecedents = {null, null, null, null, tooSlow, null};
        IFuzzySet goingTooSlowConsequence = new CalculatedFuzzySet(
                ACCELERATION_DOMAIN, StandardFuzzySets.gammaFunction(0, 10));

        Rule tooSlowRule = new Rule(goingTooSlowAntecedents, goingTooSlowConsequence);
        rules.add(tooSlowRule);
    }

    @Override
    public int conclude(int L, int D, int LK, int DK, int V, int S) {
        ArrayList<IFuzzySet> results = new ArrayList<>();

        for (Rule rule : rules) {
            results.add(rule.getResult(new int[]{L, D, LK, DK, V, S}));
        }

        IFuzzySet total = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            total = Operations.binaryOperation(total, results.get(i), Operations.zadehOr());
        }

        return (int) def.defuzzify(total);
    }
}
