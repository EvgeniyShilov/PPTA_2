import finitestatemachine.model.FiniteStateMachine;
import finitestatemachine.util.FSMBuilder;
import finitestatemachine.util.FSMDetermineUtil;
import finitestatemachine.util.GraphDrawerUtil;
import grammar.model.Grammar;
import grammar.util.GrammarUtil;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        String filePath = "grammar.txt";
        Grammar grammar = GrammarUtil.fromFile(filePath);
        FiniteStateMachine finiteStateMachine = FSMBuilder.buildFromGrammar(grammar);
        GraphDrawerUtil.drawGraph(finiteStateMachine);
        FSMDetermineUtil.determine(finiteStateMachine);
        GraphDrawerUtil.drawGraph(finiteStateMachine);
    }
}
