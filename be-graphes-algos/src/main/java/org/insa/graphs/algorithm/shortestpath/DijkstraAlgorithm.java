package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;

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


        // On va passer par un tas pour que ce soit plus efficace. De toute manière en
        // haut il y aura déjà le minimum
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        ArrayList<Label> arrayLabel = new ArrayList<Label>();

        // On va remplir la liste de label (avec des valeurs par défauts)
        for (int i = 0; i < nbNodes; i++) {
            Label label = new Label(graph.getNodes().get(i), false,
                    Double.POSITIVE_INFINITY, null);
            arrayLabel.add(label);
        }

        // On va juste définir le point d'origine de la liste
        arrayLabel.get(data.getOrigin().getId()).setCost(0);
        tas.insert(arrayLabel.get(data.getOrigin().getId()));
        boolean destIsMarqued = false;
        Label sommetSucc = new Label(null, false, 0, null); // Juste une décalaration
                                                            // pour pls tard. Cela
                                                            // servira a lister tous les
                                                            // sommets enfants
                                                            // (successeurs)

        for (int i = 0; i < nbNodes - 1; i++) {

            // 1èr cas Le tas est vide, aucun chemin n'est possible
            if (tas.isEmpty()) {
                return new ShortestPathSolution(data, Status.INFEASIBLE);
            }

            // Sinon on récupére (en supprimant du tas) le noeud le plus proche
            // enregistré
            Label sommetMin = tas.deleteMin();
            sommetMin.setMarque(true);

            // Notifie qu'un sommet a été marqué (finalisé)
            notifyNodeMarked(sommetMin.getSommet_courant());

            // Il ne reste plus qu'à regarder tous les sommets enfant du sommet le plus
            // proche
            for (Arc succArc : sommetMin.getSommet_courant().getSuccessors()) {
                sommetSucc = arrayLabel.get(succArc.getDestination().getId()); // Récupére
                                                                               // les
                                                                               // infos
                                                                               // connus
                                                                               // actuelle
                                                                               // du
                                                                               // tableau.

                // 1èr cas : Si il n'a pas été marqué (= Pas la valeur idéale)
                if (!sommetSucc.getMarque()) {
                    // Est ce que ca vaut le coup de changer sa valeur actuelle ?
                    if (sommetMin.getCost() + succArc.getLength() < sommetSucc
                            .getCost()) {
                        // SI c'est la première fois qu'on le manipule
                        if (sommetSucc.getCost() < Double.POSITIVE_INFINITY) {
                            tas.remove(sommetSucc);
                        }

                        // Dans tous les cas on mets à jour les infos du sommets
                        sommetSucc.setCost(Double.min(sommetSucc.getCost(),
                                sommetMin.getCost() + succArc.getLength()));
                        sommetSucc.setPere(succArc);
                        tas.insert(sommetSucc);
                        arrayLabel.set(sommetSucc.getSommet_courant().getId(),
                                sommetSucc); // Sans oublier de mettre a jour les
                                             // informations égalament dans la liste !
                        
                         // Notifie qu'un sommet a été atteint pour la première fois
                        notifyNodeReached(sommetSucc.getSommet_courant());
                    }
                }
            }

            // Nous avons fini :)
            if (sommetMin.getSommet_courant().getId() == data.getDestination()
                    .getId()) {
                destIsMarqued = true;
                 // Notifie que la destination a été atteinte
                notifyDestinationReached(sommetMin.getSommet_courant());
                break;
            }
        }

        ArrayList<Arc> arcs = new ArrayList<Arc>(); // On va créer la liste d'arc
        Label temp = arrayLabel.get(data.getDestination().getId());


        // si le chemin n'a pas été trouvé
        if (!destIsMarqued) {
            return new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
            // Il nous faut le dernier label. Par chance c'est SommerSuc

            while (temp.getPere() != null) {
                // Maintenant que nous avons le dernier élement, nous allons tout
                // déplier et récupérer les élements.
                arcs.add(temp.getPere());
                temp = arrayLabel.get(temp.getPere().getOrigin().getId());
            }
        }
        Collections.reverse(arcs); // On retourne la liste arcs (fin -> Début)

        Path final_path = new Path(graph, arcs);
        solution = new ShortestPathSolution(data, Status.OPTIMAL, final_path);
        // when the algorithm terminates, return the solution that has been found
        return solution;
    }


}
