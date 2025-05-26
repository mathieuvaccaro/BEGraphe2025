package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        ArrayList<Label> arrayLabelStar = new ArrayList<Label>();
        Node destination = data.getDestination();

        double coutEstime;
        Node node;
        for (int i = 0; i < nbNodes; i++) {
            node = graph.getNodes().get(i);
            coutEstime = node.getPoint().distanceTo(destination.getPoint()); // cout estime entre les 2 sommets
            LabelStar labelStar = new LabelStar(node, false, Double.POSITIVE_INFINITY, null, coutEstime); // on met dans notre liste mais avec cout estime au lieu de l'infini avec dijkstra
            arrayLabelStar.add(labelStar);
        }

        // Utile pour plus tard
        Label sommetSucc = new LabelStar(null, false, 0, null, Double.POSITIVE_INFINITY);

        // Algo générique
        DijkstraAStarAlgorithm da_algo = new DijkstraAStarAlgorithm(data);
        return da_algo.MainLoop(data, graph, nbNodes, arrayLabelStar, sommetSucc);

    }

}

