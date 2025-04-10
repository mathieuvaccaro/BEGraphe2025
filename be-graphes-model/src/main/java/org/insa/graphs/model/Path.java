package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     *
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * @return A path that goes through the given list of nodes.
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e.
     * two consecutive nodes in the list are not connected in the graph.
     * @deprecated Need to be implemented.
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes) throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();

        boolean yes = false; // Le boolean va nous permettre de retenir si il existe bien une liaison entre les deux nodes

        // Est ce que les élements du graphe et nodes sont dans le même ordre ???

        // Si la liste n'est pas valide, on lève l'exception
        // Tout d'abord on va chercher pour chaque noeuds consécutives dans la liste nodes, les id correspondant dans le graphe
        // Nous allons noter ces deux nodes a et b
        // En considérant que les noeuds du graphe ne sont pas au même endroit que dans la lsite des ndoes
        Node a = new Node(-1, new Point(-1, -1));
        Node b = new Node(-1, new Point(-1, -1)); // On créer des noeuds bidons, qui ont un id négatif. Si a la fin on a ce noeud là, ca veut dire qu'aucun noeud ne correspond dans la lsite
        // On oublie pas -1 pour ne pas prendre le dernier noeud
        for(int i = 0; i < nodes.size()-1; i++)
        {
        
            for(int j = 0; j < graph.size(); j++)
            {
                // Le noeud correspond au noedu i de al lsite
                if(nodes.get(i) == graph.get(i))
                {
                    a = graph.get(i);
                }
                // Le noeud correspond au noeud i+1 de la liste
                if(nodes.get(i+1) == graph.get(i))
                {
                    b = graph.get(i);
                }
            }

            // On va tout d'abord verifier si les deux noeuds ont bien été assigné
            if(a.getId() == -1 || b.getId() == -1)throw new IllegalAccessError("L'un des noeuds n'existe pas");
            // Nous avons a et b les deux noeuds correspondant dans le graphe
            // Regardons maintenant, si dans les succeseurs de a, il y a b
            // Si il n'y a pas de successors, on peux return false
            for(int k = 0; k < a.getNumberOfSuccessors(); k++)
            {
                if(a.getSuccessors().get(k).getDestination() == b)
                {
                    yes = true;
                    break;
                }
            }

            // Le tour est fini, si yes = true, alors c'est bon sinon, pas bin
            if(!yes)throw new IllegalAccessError("Aucun enfant existe sur le noeud n°" + nodes.get(i).getId());
            yes = false;
        }

        // si on arrive là, aucune erreur n'a été détécté. Le graphe est bien valide :)
        // Nous allons désormais balayer tout le graphe en partant du début, et faire un chemin vers la dest de manière glouton
        // On va faire une boucle for de taille graphe (taille max) afin d'éviter les while et leurs boucle infinie...
        List<Arc> fastestPathArcs = new ArrayList<Arc>();
        double fatestValue = -1;
        Node fatestNode = new Node(-1, new Point(-1, -1));
        for(int i = 0; i < graph.size() ; i++)
        {
            // On regarde le nombre d'enfant
            for(int j = 0; j < graph.get(i).getNumberOfSuccessors(); j++)
            {
                if(fatestValue == -1 || graph.get(i).getSuccessors().get(j).getMinimumTravelTime() < fatestValue)
                {
                    fatestValue = graph.get(i).getSuccessors().get(j).getMinimumTravelTime();
                    fatestNode = graph.get(i).getSuccessors().get(j).getDestination();
                }
            }

            fastestPathArcs.add(new ArcForward(graph.get(i), fatestNode, (float)fatestValue, null, null))
            fatestValue = -1;
        }


        return new Path(graph, fastestPathArcs);

        // Ancienne version
        // // La liste des nodes est-elle valide ? -> Je crois qu'on ne prends pas en compte le "2 à 2"
        // for (int i = 0; i < nodes.size(); i++) {
        //     if (graph.getNodes().get(i).hasSuccessors()) {
        //         for (int j = 0; j < graph.getNodes().get(i).getNumberOfSuccessors(); j++) {
        //             if (!graph.getNodes().contains(nodes.get(i).getSuccessors().get(j))) {
        //                 throw new IllegalArgumentException("Il existe un noeud qui n'est pas dans le graphe !");
        //             }
        //         }
        //     }
        // }

        // for (Node node : nodes) {
        //     float cout = -1;
        //     Arc shortestArc = null;

        //     // Si il y a qu'un seul successeur le chemin est forcémenet le plus rapide ^^
        //     if (node.getNumberOfSuccessors() == 1) {
        //         shortestArc = node.getSuccessors().get(0);
        //     } // Il y a plusieurs arcs
        //     else {
        //         for (Arc arcsIterations : node.getSuccessors()) {
        //             if (cout == -1 || arcsIterations.getMinimumTravelTime() < cout) {
        //                 shortestArc = arcsIterations;
        //                 cout = arcsIterations.getLength();
        //             }
        //         }
        //     }
        //     arcs.add(shortestArc);
        // }
    }

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     *
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * @return A path that goes through the given list of nodes.
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e.
     *                                  --> V two consecutive nodes in the list are
     *                                  not connected in the graph.
     * @deprecated Need to be implemented.
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();

        // La liste des nodes est-elle valide ?
        for (int i = 0; i < nodes.size(); i++) {
            if (graph.getNodes().get(i).hasSuccessors()) {
                for (int j = 0; j < graph.getNodes().get(i).getNumberOfSuccessors(); j++) {
                    if (!graph.getNodes().contains(nodes.get(i).getSuccessors().get(j))) {
                        throw new IllegalArgumentException("Il existe un noeud qui n'est pas dans le graphe !");
                    }
                }
            }
        }

        for (Node node : nodes) {
            float cout = -1;
            Arc shortestArc = null;

            // Si il y a qu'un seul successeur le chemin est forcémenet le plus rapide ^^
            if (node.getNumberOfSuccessors() == 1) {
                shortestArc = node.getSuccessors().get(0);
            } // Il y a plusieurs arcs
            else {
                for (Arc arcsIterations : node.getSuccessors()) {
                    if (cout == -1 || arcsIterations.getLength() < cout) {
                        shortestArc = arcsIterations;
                        cout = arcsIterations.getLength();
                    }
                }
            }
            arcs.add(shortestArc);
        }
        return new Path(graph, arcs);
    }

    /**
     * Concatenate the given paths.
     *
     * @param paths Array of paths to concatenate.
     * @return Concatenated path.
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs
     *                                  of map do not match, or the end of a path is
     *                                  not the beginning of the
     *                                  next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException(
                    "Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path : paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     *
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     *
     * @param graph Graph containing the path.
     * @param node  Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     *
     * @param graph Graph containing the path.
     * @param arcs  Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     *
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     *
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid. A path is valid if any of the following is
     * true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     *
     * @return true if the path is valid, false otherwise.
     *         deprecated fait
     */
    public boolean isValid() {
        // Valide si le chemin est vide
        if (isEmpty()) {
            return true;
        }

        // Valide si il y a qu'un seul noeud et pas d'arc
        if (getArcs().isEmpty() && getGraph().getNodes().size() == 1) {
            return true;
        }

        // Si le premier arc a pour origine, l'origine du chemin
        if (getArcs().get(0).getOrigin() == getGraph().getNodes().get(0)) {
            // Si pour deux arcs consecutives, la destination du premier et l'origine du
            // second
            // le -1 permet de ne pas vérifier le dernier noeud du graphe
            for (int i = 0; i < getGraph().size() - 1; i++) {
                if (getArcs().get(i).getDestination() != getArcs().get(i + 1).getOrigin()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Compute the length of this path (in meters).
     *
     * @return Total length of the path (in meters).
     * @deprecated Need to be implemented.
     */
    public float getLength() {
        // TODO:
        return 0;
    }

    /**
     * Compute the time required to travel this path if moving at the given
     * speed.
     *
     * @param speed Speed to compute the travel time.
     * @return Time (in seconds) required to travel this path at the given speed
     *         (in kilometers-per-hour).
     * @deprecated Need to be implemented.
     */
    public double getTravelTime(double speed) {
        // TODO:
        return 0;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed
     * speed on every arc.
     *
     * @return Minimum travel time to travel this path (in seconds).
     * @deprecated Need to be implemented.
     */
    public double getMinimumTravelTime() {
        // TODO:
        return 0;
    }

}
