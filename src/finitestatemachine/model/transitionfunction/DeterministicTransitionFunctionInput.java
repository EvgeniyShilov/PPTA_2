package finitestatemachine.model.transitionfunction;

public class DeterministicTransitionFunctionInput implements TransitionFunctionInput<Character> {

    private Character signal;
    private Character state;

    @Override
    public Character getSignal() {
        return signal;
    }

    @Override
    public void setSignal(Character terminal) {
        this.signal = terminal;
    }

    @Override
    public Character getState() {
        return state;
    }

    @Override
    public void setState(Character state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        DeterministicTransitionFunctionInput input = (DeterministicTransitionFunctionInput) other;
        return signal.equals(input.signal) && state.equals(input.state);
    }

    @Override
    public int hashCode() {
        int result = signal.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }
}
