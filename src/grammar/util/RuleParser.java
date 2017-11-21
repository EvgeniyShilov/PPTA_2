package grammar.util;

import grammar.model.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleParser {

    private static final String DELIMITER = "->";
    private static final String OR_SYMBOL = "\\|";

    public static List<Rule> parseAll(List<String> rulesStrings) {
        List<Rule> rules = new ArrayList<>();
        for (String ruleString : rulesStrings) rules.addAll(parse(ruleString));
        return rules;
    }

    public static List<Rule> parse(String ruleString) {
        List<Rule> rules = new ArrayList<>();
        String[] ruleParts = ruleString.split(DELIMITER);
        if (ruleParts.length > 2) throw new IllegalArgumentException("More than two rule parts were found");
        String[] rightParts = ruleParts[1].split(OR_SYMBOL);
        for (String rightPart : rightParts) {
            Rule rule = new Rule();
            rule.setLeft(ruleParts[0]);
            rule.setRight(rightPart);
            rules.add(rule);
        }
        return rules;
    }
}
