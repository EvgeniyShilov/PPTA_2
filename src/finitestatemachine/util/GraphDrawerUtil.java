package finitestatemachine.util;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import finitestatemachine.model.FiniteStateMachine;
import finitestatemachine.model.TransitionFunction;

import javax.swing.*;
import java.awt.*;

public class GraphDrawerUtil {

    private static final int LAYOUT_SIZE = 500;

    private static Graph<String, String> buildGraph(FiniteStateMachine finiteStateMachine) {
        Graph<String, String> graph = new SparseMultigraph<>();
        for (Character state : finiteStateMachine.getStates()) graph.addVertex(state.toString());
        for (TransitionFunction function : finiteStateMachine.getTransitionFunctions()) {
            String v1 = function.getIn().getState().toString();
            String v2 = function.getOut().toString();
            graph.addEdge(v1 + v2 + ":" + function.getIn().getSignal().toString(), v1, v2, EdgeType.DIRECTED);
        }
        return graph;
    }

    public static void drawGraph(final FiniteStateMachine finiteStateMachine) {
        Graph<String, String> graph = buildGraph(finiteStateMachine);
        Layout<String, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(LAYOUT_SIZE, LAYOUT_SIZE));
        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(LAYOUT_SIZE + 50, LAYOUT_SIZE + 50));
        vv.getRenderContext().setVertexFillPaintTransformer((s) -> {
            if (finiteStateMachine.getFiniteStates().contains(s.toCharArray()[0])) return Color.CYAN;
            else if (finiteStateMachine.getInitialStates().contains(s.toCharArray()[0])) return Color.GREEN;
            else return Color.RED;
        });
        vv.getRenderContext().setEdgeLabelTransformer(s -> s.substring(s.indexOf(":") + 1));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        JFrame frame = new JFrame("Graph View");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
