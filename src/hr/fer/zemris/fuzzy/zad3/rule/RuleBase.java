package hr.fer.zemris.fuzzy.zad3.rule;

import hr.fer.zemris.fuzzy.zad1.*;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {

    private static final IDomain ANGLE_DOMAIN = new SimpleDomain(-90, 90 + 1);
    private static final IDomain DISTANCE_DOMAIN = new SimpleDomain(0, 1300 + 1);
    private static final IDomain ACCELERATION_DOMAIN = new SimpleDomain(-50, 50 + 1);
    private static final IDomain VELOCITY_DOMAIN = new SimpleDomain(0, 100 + 1);

    private static final IFuzzySet TURN_RIGHT =
            new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.lFunction(0, 15));

    private static final IFuzzySet TURN_LEFT =
            new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.gammaFunction(140, 155));


    public static List<Rule> getHelmRules() {
        List<Rule> rules = new ArrayList<>();

        IFuzzySet nearShore = new CalculatedFuzzySet(DISTANCE_DOMAIN,
                StandardFuzzySets.lFunction(35, 45));

        IFuzzySet[] nearLeftEdgeAnt = {null, null, nearShore, null, null, null};

        Rule nearLeftShoreRule = new Rule(nearLeftEdgeAnt, TURN_RIGHT);
        rules.add(nearLeftShoreRule);

        IFuzzySet[] nearRightShoreAntecedents = {null, null, null, nearShore, null, null};

        Rule nearRightShoreRule = new Rule(nearRightShoreAntecedents, TURN_LEFT);
        rules.add(nearRightShoreRule);

        return rules;
    }

    public static List<Rule> getAccelerationRules() {
        List<Rule> rules = new ArrayList<>();

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

        return rules;
    }
}
