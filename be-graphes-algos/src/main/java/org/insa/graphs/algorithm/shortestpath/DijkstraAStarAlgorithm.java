package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Collections;

public class DijkstraAStarAlgorithm extends DijkstraAlgorithm {

    public DijkstraAStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    public ShortestPathSolution MainLoop(ShortestPathData data,Graph graph, int nbNodes, ArrayList<Label> arrayLabel, Label sommetSucc)
    {

        // Si on est sur l'algorithme A*
        if(sommetSucc instanceof LabelStar)
        {
            sommetSucc = (LabelStar) sommetSucc;
        }

        // Vérification si le path est "vide"
        if(data.getOrigin() == data.getDestination())
        {
            Path path = null;
            ShortestPathSolution shortestPathSolution = new ShortestPathSolution(data, AbstractSolution.Status.FEASIBLE, path);
            return shortestPathSolution;
        }

        ShortestPathSolution solution = null;
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        boolean destIsMarqued = false;

        // On va juste définir le point d'origine de la liste
        arrayLabel.get(data.getOrigin().getId()).setCost(0);
        tas.insert(arrayLabel.get(data.getOrigin().getId()));

        for (int i = 0; i < nbNodes - 1; i++) {
            // 1èr cas Le tas est vide, aucun chemin n'est possible
            if (tas.isEmpty())return new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);

            // Sinon on récupére (en supprimant du tas) le noeud le plus proche enregistré
            Label sommetMin = tas.deleteMin();
            sommetMin.setMarque(true);

            // Il ne reste plus qu'à regarder tous les sommets enfant du sommet le plus
            // proche
            for (Arc succArc : sommetMin.getSommet_courant().getSuccessors()) {
                sommetSucc = arrayLabel.get(succArc.getDestination().getId()); // Récupére

                // 1èr cas : Si il n'a pas été marqué (= Pas la valeur idéale)
                if (!sommetSucc.getMarque()) {
                    // Est ce que ca vaut le coup de changer sa valeur actuelle ?
                    if (sommetMin.getCost() + succArc.getLength() < sommetSucc.getCost()) {
                        // Si ce n'est pas la première fois qu'on le manipule
                        if (sommetSucc.getCost() < Double.POSITIVE_INFINITY)  tas.remove(sommetSucc);

                        // Dans tous les cas on mets à jour les infos du sommets
                        sommetSucc.setCost(Double.min(sommetSucc.getCost(), sommetMin.getCost() + succArc.getLength()));
                        sommetSucc.setPere(succArc);
                        tas.insert(sommetSucc);
                        arrayLabel.set(sommetSucc.getSommet_courant().getId(), sommetSucc); // Sans oublier de mettre a jour les

                        notifyNodeReached(sommetSucc.getSommet_courant());
                    }
                }
            }

            // L'algo a fini de tourner
            if (sommetMin.getSommet_courant().getId() == data.getDestination().getId()) {
                destIsMarqued = true;
                break; // Plus besoins de continuer, nous avons atteint le PCC
            }
        }

        ArrayList<Arc> arcs = new ArrayList<Arc>();
        Label temp = arrayLabel.get(data.getDestination().getId());

        // si le chemin n'a pas été trouvé -> Le chemin n'est pas possible.
        if (!destIsMarqued) {
            return new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }
        else {
            while (temp.getPere() != null) {
                // Maintenant que nous avons le dernier élement, nous allons tout déplier et récupérer les élements.
                arcs.add(temp.getPere());
                temp = arrayLabel.get(temp.getPere().getOrigin().getId());
            }
        }
        Collections.reverse(arcs); // On retourne la liste arcs (fin -> Début)

        Path final_path = new Path(graph, arcs);
        return new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, final_path);
    }
}

