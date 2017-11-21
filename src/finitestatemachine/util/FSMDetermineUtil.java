package finitestatemachine.util;

import finitestatemachine.model.FiniteStateMachine;
import finitestatemachine.model.TransitionFunction;
import finitestatemachine.model.transitionfunction.DeterministicTransitionFunctionInput;
import finitestatemachine.model.transitionfunction.NonDeterministicTransitionFunctionInput;
import finitestatemachine.model.transitionfunction.TransitionFunctionInput;

import java.util.*;

public class FSMDetermineUtil {

    public static void determine(FiniteStateMachine finiteStateMachine) {
        Map<TransitionFunctionInput, Set<Character>> map = getTransitionTable(finiteStateMachine.getTransitionFunctions());
        map = determineTransitionFunctions(map);
        Map<Set<Character>, Character> newNotations = NewNotationsProvider.forTransitionTable(map, finiteStateMachine.getStates());
        map = replaceWithNewNotations(map, newNotations);
        List<TransitionFunction> determinedFunctions = toTransitionFunctions(map);
        finiteStateMachine.setTransitionFunctions(determinedFunctions);
        finiteStateMachine.setFiniteStates(FSMBuilder.getFiniteStates(determinedFunctions));
        for (Character state : newNotations.values()) finiteStateMachine.addState(state);
    }

    private static Map<TransitionFunctionInput, Set<Character>> getTransitionTable(List<TransitionFunction> functions) {
        Map<TransitionFunctionInput, Set<Character>> result = new HashMap<>();
        for (TransitionFunction outer : functions) {
            TransitionFunctionInput in = outer.getIn();
            Set<Character> states = new HashSet<>();
            for (TransitionFunction inner : functions)
                if (inner.getIn().equals(in))
                    states.add(inner.getOut());
            states.add(outer.getOut());
            result.put(in, states);
        }
        return result;
    }

    private static Map<TransitionFunctionInput, Set<Character>> determineTransitionFunctions(Map<TransitionFunctionInput, Set<Character>> transitionTable) {
        HashMap<TransitionFunctionInput, Set<Character>> result = new HashMap<>();
        for (Map.Entry<TransitionFunctionInput, Set<Character>> outerEntry : transitionTable.entrySet())
            if (outerEntry.getValue().size() > 1)
                for (Character character : outerEntry.getValue())
                    for (Map.Entry<TransitionFunctionInput, Set<Character>> innerEntry : transitionTable.entrySet())
                        if (innerEntry.getKey().getState().equals(character)) {
                            NonDeterministicTransitionFunctionInput input = new NonDeterministicTransitionFunctionInput();
                            input.setState(outerEntry.getValue());
                            input.setSignal(innerEntry.getKey().getSignal());
                            result.put(input, innerEntry.getValue());
                        }
        result.putAll(transitionTable);
        return result;
    }

    private static Map<TransitionFunctionInput, Set<Character>> replaceWithNewNotations(Map<TransitionFunctionInput, Set<Character>> transitionTable,
                                                                                        Map<Set<Character>, Character> newNotations) {
        Map<TransitionFunctionInput, Set<Character>> result = new HashMap<>();
        for (Map.Entry<TransitionFunctionInput, Set<Character>> entry : transitionTable.entrySet()) {
            TransitionFunctionInput input = new DeterministicTransitionFunctionInput();
            Set<Character> states = new HashSet<>();
            input.setSignal(entry.getKey().getSignal());
            if (entry.getKey() instanceof NonDeterministicTransitionFunctionInput) {
                Character newState = newNotations.get(entry.getKey().getState());
                input.setState(newState);
            } else input.setState(entry.getKey().getState());
            if (entry.getValue().size() > 1) states.add(newNotations.get(entry.getValue()));
            else states = entry.getValue();
            result.put(input, states);
        }
        return result;
    }

    private static List<TransitionFunction> toTransitionFunctions(Map<TransitionFunctionInput, Set<Character>> transitionTable) {
        List<TransitionFunction> result = new ArrayList<>();
        for (Map.Entry<TransitionFunctionInput, Set<Character>> entry : transitionTable.entrySet()) {
            TransitionFunction function = new TransitionFunction();
            function.setIn(entry.getKey());
            function.setOut((Character) (entry.getValue().toArray()[0]));
            result.add(function);
        }
        return result;
    }
}
