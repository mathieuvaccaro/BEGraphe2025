package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {


    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();


        // On va passer par un tas pour que ce soit plus efficace. De toute manière en haut il y aura déjà le minimum
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        ArrayList<Label> arrayLabel = new arrayList<Label>();
        // Remplissons le tas
        for (int i = 0; i < nbNodes; i++) {
            Label label = new Label(graph.getNodes().get(i), false, Double.POSITIVE_INFINITY, null);
            arrayLabel.add(label);
        }
        arrayLabel.get(0).setCost(0);
        tas.insert(arrayLabel.get(0));

        for (int i = 1; i < nbNodes; i++) {
            Label sommetMin = tas.deleteMin();
            sommetMin.setMarque(true);
            for (Arc succArc : sommetMin.getSommet_courant().getSuccessors())
            {
                Label sommetSucc = arrayLabel.get(succArc.getDestination().getId());
                if(!sommetSucc.getMarque())
                {
                    sommetSucc.setCost(min(sommetSucc.getCost()), sommetMin.getCost());
                    if()
                }
            }
        }


        // marque quand retirer du tas
        // Boucle principale : Tant que c'est pas vide ou que c'est pas optimal
        for (int i = 0; i < nbNodes; i++) { // OU OPTIMAL !!

        }

        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
