package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.GraphStatistics;

/**
 * This class can be used to indicate to an algorithm which arcs can be used and the
 * costs of the usable arcs.
 */
public class myArcInspector implements ArcInspector {

    private int maximumSpeed;
    private Mode mode;

    public myArcInspector(int maximumSpeed, Mode mode) {
        this.maximumSpeed = maximumSpeed;
        this.mode = mode;
    }


    /**
     * Check if the given arc can be used (is allowed).
     * @param arc Arc to check.
     * @return true if the given arc is allowed.
     */
    public boolean isAllowed(Arc arc)
    {
        // Import arc
        return true; // En fonction de voiture vélo etc Pour l'instant juste true.
    }

    /**
     * Find the cost of the given arc.
     * @param arc Arc to check.
     * @return Cost of the arc.
     */
    public double getCost(Arc arc)
    {
        return arc.getMinimumTravelTime();
    }

    /**
     * @return The maximum speed for this inspector, or
     *         {@link GraphStatistics#NO_MAXIMUM_SPEED} if none is set.
     */
    public int getMaximumSpeed()
    {
        return this.getMaximumSpeed();
    }

    /**
     * @return Mode for this arc inspector.
     */
    public Mode getMode()
    {
        return this.getMode();
    }

}
