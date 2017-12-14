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
        map = determineTransitionFunctions(map, finiteStateMachine.getInputSymbols());
        Map<Set<Character>, Character> newNotations = NewNotationsProvider.forTransitionTable(map, finiteStateMachine.getStates());
        map = replaceWithNewNotations(map, newNotations);
        List<TransitionFunction> determinedFunctions = toTransitionFunctions(map);
        finiteStateMachine.setTransitionFunctions(determinedFunctions);
        Set<Character> oldFiniteStates = finiteStateMachine.getFiniteStates();
        Set<Character> newFiniteStates = new HashSet<>(oldFiniteStates);
        for (Map.Entry<Set<Character>, Character> entry : newNotations.entrySet())
            for (Character oldSignal : entry.getKey())
                if (oldFiniteStates.contains(oldSignal)) {
                    newFiniteStates.add(entry.getValue());
                    break;
                }
        finiteStateMachine.setFiniteStates(newFiniteStates);
        for (Character state : newNotations.values()) finiteStateMachine.addState(state);
        deleteAllUnusedStates(finiteStateMachine);
    }

    private static void deleteAllUnusedStates(FiniteStateMachine finiteStateMachine) {
        Set<Character> states = finiteStateMachine.getStates();
        Set<Character> statesToRemove = new HashSet<>();
        loop: for (Character state : states) {
            for (TransitionFunction function : finiteStateMachine.getTransitionFunctions())
                if (((DeterministicTransitionFunctionInput) function.getIn()).getState().equals(state) ||
                        function.getOut().equals(state))
                    continue loop;
            statesToRemove.add(state);
        }
        states.removeAll(statesToRemove);
        finiteStateMachine.getFiniteStates().removeAll(statesToRemove);
        finiteStateMachine.getInitialStates().removeAll(statesToRemove);
    }

    private static Map<TransitionFunctionInput, Set<Character>> getTransitionTable(List<TransitionFunction> functions) {
        Map<TransitionFunctionInput, Set<Character>> result = new HashMap<>();
        for (TransitionFunction function : functions) {
            Set<Character> states = result.computeIfAbsent(function.getIn(), k -> new HashSet<>());
            states.add(function.getOut());
        }
        return result;
    }

    private static Map<TransitionFunctionInput, Set<Character>>
    determineTransitionFunctions(Map<TransitionFunctionInput, Set<Character>> transitionTable,
                                 Set<Character> inputSignals) {
        HashMap<TransitionFunctionInput, Set<Character>> result = new HashMap<>();
        Set<Set<Character>> needToFill = new HashSet<>();
        Set<Set<Character>> alreadyFilled = new HashSet<>();
        Set<Character> initSet = new HashSet<>();
        initSet.add('S');
        needToFill.add(initSet);
        while (!needToFill.isEmpty()) {
            Set<Character> columnToFill = (Set<Character>) needToFill.toArray()[0];
            alreadyFilled.add(columnToFill);
            for (Character signal : inputSignals) {
                Set<Character> output = new HashSet<>();
                for (Map.Entry<TransitionFunctionInput, Set<Character>> entry : transitionTable.entrySet()) {
                    if (signal.equals(entry.getKey().getSignal()) &&
                            columnToFill.contains(((DeterministicTransitionFunctionInput) entry.getKey()).getState()))
                        output.addAll(entry.getValue());
                }
                if (output.isEmpty()) continue;
                TransitionFunctionInput input;
                if (columnToFill.size() == 1) {
                    input = new DeterministicTransitionFunctionInput();
                    ((DeterministicTransitionFunctionInput) input).setState((Character) columnToFill.toArray()[0]);
                } else {
                    input = new NonDeterministicTransitionFunctionInput();
                    ((NonDeterministicTransitionFunctionInput) input).setState(columnToFill);
                }
                input.setSignal(signal);
                result.put(input, output);
                if (!alreadyFilled.contains(output))
                    needToFill.add(output);
            }
            needToFill.remove(columnToFill);
        }
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
