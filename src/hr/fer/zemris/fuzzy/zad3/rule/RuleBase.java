package hr.fer.zemris.fuzzy.zad3.rule;

import hr.fer.zemris.fuzzy.zad1.*;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {

    // Domains
    private static final IDomain ANGLE_DOMAIN = new SimpleDomain(-90, 90 + 1);
    private static final IDomain DISTANCE_DOMAIN = new SimpleDomain(0, 1300 + 1);
    private static final IDomain ACCELERATION_DOMAIN = new SimpleDomain(-50, 50 + 1);
    private static final IDomain VELOCITY_DOMAIN = new SimpleDomain(0, 100 + 1);

    // Conditions
    private static final IFuzzySet NEAR_SHORE =
            new CalculatedFuzzySet(DISTANCE_DOMAIN, StandardFuzzySets.lFunction(35, 45));

    private static final IFuzzySet GOING_FAST =
            new CalculatedFuzzySet(VELOCITY_DOMAIN, StandardFuzzySets.gammaFunction(45, 60));

    private static final IFuzzySet GOING_SLOW =
            new CalculatedFuzzySet(VELOCITY_DOMAIN, StandardFuzzySets.lFunction(25, 35));

    // Actions
    private static final IFuzzySet TURN_RIGHT =
            new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.lFunction(0, 15));

    private static final IFuzzySet TURN_LEFT =
            new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.gammaFunction(100, 115));

    private static final IFuzzySet SLOW_DOWN =
            new CalculatedFuzzySet(ACCELERATION_DOMAIN, StandardFuzzySets.lambdaFunction(-65, -60, -55));

    private static final IFuzzySet SPEED_UP =
            new CalculatedFuzzySet(ACCELERATION_DOMAIN, StandardFuzzySets.lambdaFunction(65, 70, 75));

    public static List<Rule> getHelmRules() {
        List<Rule> rules = new ArrayList<>();

        IFuzzySet[] nearLeftEdge = {null, null, NEAR_SHORE, null, null, null};
        rules.add(new Rule(nearLeftEdge, TURN_RIGHT));

        IFuzzySet[] nearRightEdge = {null, null, null, NEAR_SHORE, null, null};
        rules.add(new Rule(nearRightEdge, TURN_LEFT));

        return rules;
    }

    public static List<Rule> getAccelerationRules() {
        List<Rule> rules = new ArrayList<>();

        IFuzzySet[] goingTooFast = {null, null, null, null, GOING_FAST, null};
        rules.add(new Rule(goingTooFast, SLOW_DOWN));

        IFuzzySet[] goingTooSlow = {null, null, null, null, GOING_SLOW, null};
        rules.add(new Rule(goingTooSlow, SPEED_UP));

        return rules;
    }
}
