package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

    private Node sommet_courant;
    private boolean marque;
    private double cout_realise;
    private Label pere;

    public Node getSommet_courant() {
        return sommet_courant;
    }


    public boolean getMarque() {
        return marque;
    }

    public void setMarque(boolean marque) {
        this.marque = marque;
    }

    public double getCout_realise() {
        return cout_realise;
    }

    public Label getPere() {
        return pere;
    }

    public void setPere(Label pere) {
        this.pere = pere;
    }

    public double getCost()
    {
        return cout_realise;
    }

    public void setCost(double cost)
    {
        this.cout_realise = cost;
    }

    public Label(Node sommet_courant, boolean marque, double cout_realise, Label pere)
    {
        this.sommet_courant = sommet_courant;
        this.marque = marque;
        this.cout_realise = cout_realise;
        this.pere = pere;
    }

    @Override
    public int compareTo(Label autre) {
        // Cout < Autre --> -1
        // Cout > Autre --> 1
        // Cout = AUtre --> 0

        if(this.getCost() < autre.getCost()) return -1;
        else if(this.getCost() > autre.getCost()) return 1;
        return 0;
    }
}