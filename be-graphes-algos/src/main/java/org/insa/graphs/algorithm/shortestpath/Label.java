package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label {

    private Node sommet_courant;
    private boolean marque;
    private int cout_realise;
    private Node pere;

    public Node getSommet_courant() {
        return sommet_courant;
    }

    public boolean getMarque() {
        return marque;
    }

    public int getCout_realise() {
        return cout_realise;
    }

    public Node getPere() {
        return pere;
    }

    public int getCost()
    {
        return cout_realise;
    }

    public void setLabel(Node sommet_courant, boolean marque, int cout_realise, Node pere)
    {
        this.sommet_courant = sommet_courant;
        this.marque = marque;
        this.cout_realise = cout_realise;
        this.pere = pere;
    }
}