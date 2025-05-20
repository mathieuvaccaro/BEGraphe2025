package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        Graph graph = data.getGraph();
        final int nbNodes = graph.size();


        // On va passer par un tas pour que ce soit plus efficace. De toute manière en haut il y aura déjà le minimum
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        ArrayList<Label> arrayLabel = new ArrayList<Label>();
        // Remplissons le tas
        for (int i = 0; i < nbNodes; i++) {
            Label label = new Label(graph.getNodes().get(i), false, Double.POSITIVE_INFINITY, null);
            arrayLabel.add(label);
        } 
        arrayLabel.get(data.getOrigin().getId()).setCost(0);
        tas.insert(arrayLabel.get(data.getOrigin().getId()));
        Label label_Destination = new Label(data.getDestination(), false, Double.POSITIVE_INFINITY, null);
        Label sommetSucc = new Label(null, false, 0, null); // Juste une décalaration pour pls tard
        for (int i = 1; i < nbNodes; i++) {
            Label sommetMin = tas.deleteMin();

            sommetMin.setMarque(true);
            for (Arc succArc : sommetMin.getSommet_courant().getSuccessors())
            {
                System.out.println("Successeur " + succArc.getDestination().getId());

                sommetSucc = arrayLabel.get(succArc.getDestination().getId());
                if(!sommetSucc.getMarque())
                {
                    // On va améliorer le sommet
                    if(sommetMin.getCost()+succArc.getLength() < sommetSucc.getCost())
                    {
                        if(sommetSucc.getCost() < Double.POSITIVE_INFINITY)
                        {
                            tas.remove(sommetSucc);
                        }
                        sommetSucc.setCost(Double.min(sommetSucc.getCost(), sommetMin.getCost()+succArc.getLength()));
                        tas.insert(sommetSucc);
                        sommetSucc.setPere(sommetMin);

                    }
                }
            }
            System.out.println("----------------");
        }

        List<Arc> arcsToReturn = new ArrayList<Arc>();

        if(!label_Destination.getMarque())
        {
            return new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else
        {
            // Il nous faut le dernier label. Par chance c'est SommerSuc
            while(sommetSucc.getPere() != null)
             {
                Arc arc = null;
                arc = new ArcForward(sommetSucc.getPere().getSommet_courant(), sommetSucc, sommetSucc.getPere().getCost(), )
                arcsToReturn.add(sommetSucc.get);
             }
        }


        Path final_path = new Path(graph, arcsToReturn);
        solution = new ShortestPathSolution(data, Status.OPTIMAL,final_path);
        // when the algorithm terminates, return the solution that has been found
        return solution;
    }
    

}
