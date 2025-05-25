package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
    private double cout_estime;


    public LabelStar(Node sommet_courant, boolean marque, double cout_realise, Arc pere,
            double cout_estime) {
        super(sommet_courant, marque, cout_realise, pere);
        this.cout_estime = cout_estime;
    }

    // astar efficace reellement que quand nous avons une distance à vol d'oiseau qui ameliore
    // pas tout le temps le cas sur les petits graphs !!! :o
    @Override
    public double getTotalCost() {
        return getCost() + this.cout_estime;
    }

    @Override
    public int compareTo(Label autre) {
     double thisTotal = this.getTotalCost();
    double otherTotal = autre.getTotalCost();

     // Cout < Autre --> -1
        // Cout > Autre --> 1
       

        if (thisTotal < otherTotal) {
            return -1;
        }
        else if (thisTotal > otherTotal) {
            return 1;
        }
        else {
            // Egalite --> on compare le coût estimé à la destination  ;)
            double thisEstime = this.cout_estime;
            double otherEstime = ((LabelStar) autre).cout_estime;

            if (thisEstime < otherEstime) { // la on va comparer avec les cout estime :o
                return -1;
            }
            else if (thisEstime > otherEstime) {
                return 1;
            }
            else {
                return 0;
            
            }
        }
    }
}
