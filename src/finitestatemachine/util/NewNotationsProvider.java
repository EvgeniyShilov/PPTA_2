package finitestatemachine.util;

import finitestatemachine.model.transitionfunction.TransitionFunctionInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewNotationsProvider {

    public static Map<Set<Character>, Character> forEqualStates(List<Set<Character>> equalsStatesList,
                                                                Set<Character> alreadyUsed) {
        Map<Set<Character>, Character> result = new HashMap<>();
        for (Set<Character> equalStates : equalsStatesList) {
            Character newStateLetter = RandomCharacterUtil.getRandom(alreadyUsed);
            alreadyUsed.add(newStateLetter);
            result.put(equalStates, newStateLetter);
        }
        return result;
    }

    public static Map<Set<Character>, Character> forTransitionTable(Map<TransitionFunctionInput, Set<Character>> transitionTable,
                                                                    Set<Character> alreadyUsed) {
        Map<Set<Character>, Character> result = new HashMap<>();
        for (Map.Entry<TransitionFunctionInput, Set<Character>> entry : transitionTable.entrySet()) {
            if (entry.getKey().getState() instanceof Set && !result.containsKey(entry.getKey().getState()))
                result.put((Set<Character>) entry.getKey().getState(), RandomCharacterUtil.getRandom(alreadyUsed));
            if (entry.getValue().size() > 1 && !result.containsKey(entry.getValue()))
                result.put(entry.getValue(), RandomCharacterUtil.getRandom(alreadyUsed));
        }
        return result;
    }
}
