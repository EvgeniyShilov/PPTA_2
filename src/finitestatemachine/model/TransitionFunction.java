package finitestatemachine.model;

import finitestatemachine.model.transitionfunction.TransitionFunctionInput;

public class TransitionFunction {

    private TransitionFunctionInput in;
    private Character out;

    public TransitionFunctionInput getIn() {
        return in;
    }

    public void setIn(TransitionFunctionInput in) {
        this.in = in;
    }

    public Character getOut() {
        return out;
    }

    public void setOut(Character out) {
        this.out = out;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        TransitionFunction function = (TransitionFunction) other;
        return in.equals(function.in) && out.equals(function.out);
    }

    @Override
    public int hashCode() {
        int result = in.hashCode();
        result = 31 * result + out.hashCode();
        return result;
    }
}
