package finitestatemachine.model.transitionfunction;

import java.util.Set;

public class NonDeterministicTransitionFunctionInput implements TransitionFunctionInput<Set<Character>> {

    private Character signal;
    private Set<Character> states;

    @Override
    public Character getSignal() {
        return signal;
    }

    @Override
    public void setSignal(Character terminal) {
        this.signal = terminal;
    }

    @Override
    public Set<Character> getState() {
        return states;
    }

    @Override
    public void setState(Set<Character> states) {
        this.states = states;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        NonDeterministicTransitionFunctionInput input = (NonDeterministicTransitionFunctionInput) other;
        return signal.equals(input.signal) && states.equals(input.states);
    }

    @Override
    public int hashCode() {
        int result = signal.hashCode();
        result = 31 * result + states.hashCode();
        return result;
    }
}
