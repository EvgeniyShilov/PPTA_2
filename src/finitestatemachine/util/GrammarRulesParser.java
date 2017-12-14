package finitestatemachine.util;

import finitestatemachine.model.TransitionFunction;
import finitestatemachine.model.transitionfunction.DeterministicTransitionFunctionInput;
import grammar.model.Rule;
import grammar.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GrammarRulesParser {

    public static List<TransitionFunction> toTransitionFunctions(List<Rule> completedRules) {
        List<TransitionFunction> transitionFunctions = new ArrayList<>();
        for (Rule rule : completedRules) {
            String left = rule.getLeft();
            List<Character> right = StringUtil.splitToChars(rule.getRight());
            if (left.length() > 1 || right.size() > 2) throw new IllegalArgumentException();
            if (right.size() != 2) continue;
            TransitionFunction transitionFunction = new TransitionFunction();
            DeterministicTransitionFunctionInput in = new DeterministicTransitionFunctionInput();
            in.setState(rule.getLeft().toCharArray()[0]);
            for (Character character : right)
                if (Character.isUpperCase(character)) transitionFunction.setOut(character);
                else in.setSignal(character);
            transitionFunction.setIn(in);
            transitionFunctions.add(transitionFunction);
        }
        return transitionFunctions;
    }

}
