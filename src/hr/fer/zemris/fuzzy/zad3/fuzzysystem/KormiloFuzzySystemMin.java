package hr.fer.zemris.fuzzy.zad3.fuzzysystem;

import hr.fer.zemris.fuzzy.zad1.*;
import hr.fer.zemris.fuzzy.zad3.defuzzifiers.Defuzzifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KormiloFuzzySystemMin implements FuzzySystem {

    private static final IDomain ANGLE_DOMAIN = new SimpleDomain(-90, 90 + 1);
    private static final IDomain DISTANCE_DOMAIN = new SimpleDomain(0, 1300 + 1);

    private Defuzzifier def;

    private List<Rule> rules = new ArrayList<>();

    public KormiloFuzzySystemMin(Defuzzifier def) {
        this.def = Objects.requireNonNull(def);

        addRules();
    }

    private void addRules() {
        IFuzzySet nearShoreSet = new CalculatedFuzzySet(DISTANCE_DOMAIN, StandardFuzzySets.lFunction(25, 45));

        IFuzzySet[] nearLeftEdgeAntecedents = {null, null, nearShoreSet, null, null, null};
        IFuzzySet nearLeftEdgeConsequence = new
                CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.lFunction(0, 15));

        Rule nearLeftShoreRule = new Rule(nearLeftEdgeAntecedents, nearLeftEdgeConsequence);
        rules.add(nearLeftShoreRule);

        IFuzzySet[] nearRightShoreAntecedents = {null, null, null, nearShoreSet, null, null};
        IFuzzySet nearRightShoreConsequence = new
                CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.gammaFunction(140, 155));

        Rule nearRightShoreRule = new Rule(nearRightShoreAntecedents, nearRightShoreConsequence);
        rules.add(nearRightShoreRule);
    }


    @Override
    public int conclude(int L, int D, int LK, int DK, int V, int S) {
        ArrayList<IFuzzySet> results = new ArrayList<>();
        for (Rule rule : rules) {
            IFuzzySet fr = rule.getResult(new int[]{L, D, LK, DK, V, S});
            results.add(fr);
        }

        IFuzzySet total = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            total = Operations.binaryOperation(total, results.get(i), Operations.zadehOr());
        }

        return (int) def.defuzzify(total);
    }
}