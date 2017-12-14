package finitestatemachine.util;

import finitestatemachine.model.FiniteStateMachine;
import finitestatemachine.model.TransitionFunction;
import grammar.model.Grammar;
import grammar.model.GrammarType;
import grammar.model.Rule;
import grammar.util.GrammarUtil;
import grammar.util.RuleAnalyzer;
import grammar.util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FSMBuilder {

    private static final String ADDITIONAL_NON_TERMINAL = "N";

    public static FiniteStateMachine buildFromGrammar(Grammar grammar) {
        if (GrammarUtil.getType(grammar) != GrammarType.REGULAR)
            throw new IllegalArgumentException("Grammar is not a regular one");
        List<Rule> completedRules = completeRules(grammar);
        FiniteStateMachine finiteStateMachine = new FiniteStateMachine();
        finiteStateMachine.setInitialStates(new HashSet<>(StringUtil.splitToChars(grammar.getS())));
        finiteStateMachine.setStates(RuleAnalyzer.getNonTerminals(completedRules));
        finiteStateMachine.setInputSymbols(grammar.getT());
        List<TransitionFunction> transitionFunctions = GrammarRulesParser.toTransitionFunctions(completedRules);
        finiteStateMachine.setTransitionFunctions(transitionFunctions);
        finiteStateMachine.setFiniteStates(getFiniteStates(completedRules, finiteStateMachine.getInitialStates()));
        return finiteStateMachine;
    }

    private static List<Rule> completeRules(Grammar grammar) {
        List<Rule> rules = new ArrayList<>();
        for (Rule rule : grammar.getRules()) {
            String rightPart = rule.getRight();
            if (rightPart.length() == 1){
                Rule otherRule = new Rule();
                otherRule.setLeft(rule.getLeft());
                otherRule.setRight(rightPart + ADDITIONAL_NON_TERMINAL);
                rules.add(otherRule);
            }
            rules.add(rule);
        }
        return rules;
    }

    private static Set<Character> getFiniteStates(List<Rule> rules, Set<Character> initialStates) {
        Set<Character> finiteStates = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.getRight().length() == 2 && !Character.isUpperCase(rule.getRight().charAt(0))) {
                Character terminal = rule.getRight().charAt(0);
                Character leftNonTerminal = rule.getLeft().charAt(0);
                for (Rule other : rules)
                    if (other.getRight().length() == 1 && !Character.isUpperCase(other.getRight().charAt(0))) {
                        Character otherTerminal = other.getRight().charAt(0);
                        Character otherLeftNonTerminal = other.getLeft().charAt(0);
                        if (terminal.equals(otherTerminal) && leftNonTerminal.equals(otherLeftNonTerminal))
                            finiteStates.add(rule.getRight().charAt(1));
                    }
            }
            if (rule.getRight().isEmpty() && initialStates.contains(rule.getLeft().charAt(0)))
                finiteStates.add(rule.getLeft().charAt(0));
        }
        return finiteStates;
    }

    public static Set<Character> getStates(List<TransitionFunction> functions) {
        Set<Character> states = new HashSet<>();
        for (TransitionFunction function : functions) {
            states.add((Character) function.getIn().getState());
            states.add(function.getOut());
        }
        return states;
    }
}
