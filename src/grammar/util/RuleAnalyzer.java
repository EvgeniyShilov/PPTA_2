package grammar.util;

import grammar.model.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleAnalyzer {

    public static Set<Character> getTerminals(List<Rule> rules) {
        Set<Character> terminals = new HashSet<>();
        for (Rule rule : rules) terminals.addAll(getTerminals(rule));
        return terminals;
    }

    public static Set<Character> getNonTerminals(List<Rule> rules) {
        Set<Character> nonTerminals = new HashSet<>();
        for (Rule rule : rules) nonTerminals.addAll(getNonTerminals(rule));
        return nonTerminals;
    }

    public static Set<Character> getTerminals(Rule rule) {
        Set<Character> terminals = new HashSet<>();
        Set<Character> characters = new HashSet<>(getCharsFromRule(rule));
        for (Character character : characters)
            if (!Character.isUpperCase(character))
                terminals.add(character);
        return terminals;
    }

    public static Set<Character> getNonTerminals(Rule rule) {
        Set<Character> terminals = new HashSet<>();
        Set<Character> characters = new HashSet<>(getCharsFromRule(rule));
        for (Character character : characters)
            if (Character.isUpperCase(character))
                terminals.add(character);
        return terminals;
    }

    private static List<Character> getCharsFromRule(Rule rule) {
        List<Character> characters = new ArrayList<>();
        characters.addAll(StringUtil.splitToChars(rule.getLeft()));
        characters.addAll(StringUtil.splitToChars(rule.getRight()));
        return characters;
    }
}
