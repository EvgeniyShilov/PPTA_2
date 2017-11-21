package finitestatemachine.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FiniteStateMachine {

    private List<TransitionFunction> transitionFunctions;
    private Set<Character> states;
    private Set<Character> inputSymbols;
    private Set<Character> initialStates;
    private Set<Character> finiteStates;

    public FiniteStateMachine() {
        transitionFunctions = new ArrayList<>();
        states = new HashSet<>();
        inputSymbols = new HashSet<>();
        initialStates = new HashSet<>();
        finiteStates = new HashSet<>();
    }

    public List<TransitionFunction> getTransitionFunctions() {
        return transitionFunctions;
    }

    public void setTransitionFunctions(List<TransitionFunction> transitionFunctions) {
        this.transitionFunctions = transitionFunctions;
    }

    public Set<Character> getStates() {
        return states;
    }

    public void setStates(Set<Character> states) {
        this.states = states;
    }

    public Set<Character> getInputSymbols() {
        return inputSymbols;
    }

    public void setInputSymbols(Set<Character> inputSymbols) {
        this.inputSymbols = inputSymbols;
    }

    public Set<Character> getInitialStates() {
        return initialStates;
    }

    public void setInitialStates(Set<Character> initialStates) {
        this.initialStates = initialStates;
    }

    public Set<Character> getFiniteStates() {
        return finiteStates;
    }

    public void setFiniteStates(Set<Character> finiteStates) {
        this.finiteStates = finiteStates;
    }

    public void addState(Character character) {
        states.add(character);
    }
}
