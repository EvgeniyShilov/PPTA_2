package finitestatemachine.model.transitionfunction;

public interface TransitionFunctionInput<T> {

    Character getSignal();

    void setSignal(Character c);

    T getState();

    void setState(T state);
}
