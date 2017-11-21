package finitestatemachine.util;

import finitestatemachine.model.TransitionFunction;
import finitestatemachine.model.transitionfunction.DeterministicTransitionFunctionInput;
import finitestatemachine.model.transitionfunction.TransitionFunctionInput;
import grammar.model.Rule;
import grammar.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GrammarRulesParser {

    public static List<TransitionFunction> toTransitionFunctions(List<Rule> completedRules) {
        List<TransitionFunction> transitionFunctions = new ArrayList<>();
        for (Rule rule : completedRules) {
            TransitionFunction transitionFunction = new TransitionFunction();
            TransitionFunctionInput in = new DeterministicTransitionFunctionInput();
            String left = rule.getLeft();
            if (left.length() > 1) throw new IllegalArgumentException();
            in.setState(rule.getLeft().toCharArray()[0]);
            List<Character> right = StringUtil.splitToChars(rule.getRight());
            if (right.size() > 2) throw new IllegalArgumentException();
            for (Character character : right)
                if (Character.isUpperCase(character)) transitionFunction.setOut(character);
                else in.setSignal(character);
            transitionFunction.setIn(in);
            transitionFunctions.add(transitionFunction);
        }
        return transitionFunctions;
    }

}
