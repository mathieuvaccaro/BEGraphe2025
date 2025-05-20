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

    @Override
    public void setCost(double cost) {
        this.cout_estime = cost;
    }

    @Override
    public double getCost() {
        return cout_estime;
    }

    @Override
    public int compareTo(Label autre) {
        // Cout < Autre --> -1
        // Cout > Autre --> 1
        // Cout = Autre --> 0

        if (this.getTotalCost() < autre.getTotalCost())
            return -1;
        else if (this.getTotalCost() > autre.getTotalCost())
            return 1;
        return 0;

    }


}
