package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import org.insa.graphs.model.Graph;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {


    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        ArrayList<Label> arrayLabel = new ArrayList<Label>();

        // On va remplir la liste de label (avec des valeurs par défauts)
        for (int i = 0; i < nbNodes; i++) {
            Label label = new Label(graph.getNodes().get(i), false, Double.POSITIVE_INFINITY, null);
            arrayLabel.add(label);
        }
        // Utile pour plus tard
        Label sommetSucc = new Label(null, false, 0, null);

        // Algo générique
        DijkstraAStarAlgorithm da_algo = new DijkstraAStarAlgorithm(data);
        return da_algo.MainLoop(data, graph, nbNodes, arrayLabel, sommetSucc);
    }


}
