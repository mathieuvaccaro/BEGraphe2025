package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ShortestPathDijkstraAStarTests {

    Graph carreGraph;
    String carreMap = "C:\\Users\\mathi\\Desktop\\INSA\\3A\\Graphes\\BE\\BEGraphe\\Assets\\carre.mapgr";
    Node srcNodeCarre = new Node(23, new Point((float) 0.300000, (float) 0.699999));;
    Node destNodeCarre = new Node(1, new Point((float) 0.699999, (float) 0.300000));

    // UTILISé POUR LE PATH INVALID
    Graph spainGraph;
    String spainMap = "C:\\Users\\mathi\\Desktop\\INSA\\3A\\Graphes\\BE\\BEGraphe\\Assets\\spain.mapgr";
    Node srcNodeSpain = new Node(3904913, new Point((float) -1.074242, (float) 40.429585));;
    Node destNodeSpain = new Node(4268735, new Point((float) 3.056135, (float) 39.657383));

    // Utilisé pour le final
    Graph franceGraph;
    String franceMap = "C:\\Users\\mathi\\Desktop\\INSA\\3A\\Graphes\\BE\\BEGraphe\\Assets\\france.mapgr";
    Node srcNodeFrance = new Node(6224404, new Point((float) 5.399223, (float) 43.657833));;
    Node destNodeFrance = new Node(244354, new Point((float) 2.432639, (float) 48.862625));

    public enum Algorithm {
        DJIKSTRA, BELLMANFORD, ASTAR;
    }

    public void Validation()
    {
        System.out.println("Ok !");
        System.out.println("---------------------------");
    }

    public ShortestPathSolution getShortestPathSolution(Graph graph, AbstractInputData.Mode mode, Algorithm algo, Node sourceNode, Node destNode, int MaximumSpeed) // Type -> Temps, Cout
    {
        myArcInspector arcInspectorvar = new myArcInspector(MaximumSpeed, mode);
        ShortestPathData data = new ShortestPathData(graph, sourceNode, destNode, arcInspectorvar);
        ShortestPathSolution solution;

        switch(algo) {
            case DJIKSTRA:
                DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
                solution = dijkstra.run();
                break;
            case BELLMANFORD:
                BellmanFordAlgorithm bellmanford = new BellmanFordAlgorithm(data);
                solution = bellmanford.run();
                break;
            case ASTAR:
                AStarAlgorithm astar = new AStarAlgorithm(data);
                solution = astar.run();
                break;
            default:
                System.out.println("Mode non reconnu !");
                System.out.println("Algorithms {DijkstraAlgorithm, BellmanFordAlgorithm, AStarAlgorithm}");
                return null;
        }
        return solution;
    }

    @Before
    public void Init()
    {
        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(carreMap))))) {
            carreGraph = reader.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(spainMap))))) {
            spainGraph = reader.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Faire les trois algos sur de manière classique sur le temps
    @Test
    public void classicAllCarreTime() {
        System.out.println("Start Bellman Ford Algorithm");
        ShortestPathSolution bellman = getShortestPathSolution(carreGraph, AbstractInputData.Mode.TIME, Algorithm.BELLMANFORD, srcNodeCarre, destNodeCarre, 130);
        System.out.println("Start Dijkstra Algorithm");
        ShortestPathSolution dijsktra = getShortestPathSolution(carreGraph, AbstractInputData.Mode.TIME, Algorithm.DJIKSTRA, srcNodeCarre, destNodeCarre, 130);
        System.out.println("Start AStar Algorithm");
        ShortestPathSolution astar = getShortestPathSolution(carreGraph, AbstractInputData.Mode.TIME, Algorithm.ASTAR, srcNodeCarre, destNodeCarre, 130);

        // Si l'un des trois a une durée différente, ce n'est pas bon
        assertEquals(bellman.getPath().getMinimumTravelTime(), astar.getPath().getMinimumTravelTime(), 1);
        assertEquals(bellman.getPath().getMinimumTravelTime(), dijsktra.getPath().getMinimumTravelTime(), 1);
        Validation();
    }

    @Test
    public void SourceEqualDestinationBellmanFord() {
        System.out.println("Start Bellman Ford Algorithm With Source = Dest");
        ShortestPathSolution bellman = getShortestPathSolution(carreGraph, AbstractInputData.Mode.LENGTH, Algorithm.BELLMANFORD, srcNodeCarre, srcNodeCarre, 130);
        assertNull(bellman.getPath());
        Validation();
    }

    @Test
    public void SourceEqualDestinationDijkstra() {
        System.out.println("Start Dijkstra Ford Algorithm With Source = Dest");
        ShortestPathSolution dijkstra = getShortestPathSolution(carreGraph, AbstractInputData.Mode.LENGTH, Algorithm.DJIKSTRA, srcNodeCarre, srcNodeCarre, 130);
        assertNull(dijkstra.getPath());
        Validation();
    }

    @Test
    public void SourceEqualDestinationAStar() {
        System.out.println("Start A* Ford Algorithm With Source = Dest");
        ShortestPathSolution astar = getShortestPathSolution(carreGraph, AbstractInputData.Mode.LENGTH, Algorithm.ASTAR, srcNodeCarre, srcNodeCarre, 130);
        assertNull(astar.getPath());
        Validation();
    }

    // ATTENTION CE TEST PREND BEAUCOUP DE TEMPS !
    //@Test
    public void invalidPathBellmanFord()
    {
        System.out.println("Start Bellman Algorithm with an Invalid Path");
        ShortestPathSolution bellman = getShortestPathSolution(spainGraph, AbstractInputData.Mode.LENGTH, Algorithm.BELLMANFORD, srcNodeSpain, destNodeSpain, 130);
        assertFalse(bellman.isFeasible());
        Validation();
    }

    @Test
    public void invalidPathDijkstraFord()
    {
        System.out.println("Start Dijkstra Algorithm with an Invalid Path");
        ShortestPathSolution dijsktra = getShortestPathSolution(spainGraph, AbstractInputData.Mode.LENGTH, Algorithm.DJIKSTRA, srcNodeSpain, destNodeSpain, 130);
        assertFalse(dijsktra.isFeasible());
        Validation();
    }

    @Test
    public void invalidPathAStarFord()
    {
        System.out.println("Start A* Algorithm with an Invalid Path");
        ShortestPathSolution AStar = getShortestPathSolution(spainGraph, AbstractInputData.Mode.LENGTH, Algorithm.ASTAR, srcNodeSpain, destNodeSpain, 130);
        assertFalse(AStar.isFeasible());
        Validation();
    }

    // Ne fonctionne pas sur un Intellij avec 4GiB de Heap Memory alloué :(
    //@Test
    public void realConditionDijkstraAStar()
    {

        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(franceMap))))) {
            franceGraph = reader.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Start (real) Dijkstra Algorithm");
        ShortestPathSolution dijsktra = getShortestPathSolution(franceGraph, AbstractInputData.Mode.LENGTH, Algorithm.DJIKSTRA, srcNodeFrance, destNodeFrance, 130);
        System.out.println("Passe sur a*");
        ShortestPathSolution astar = getShortestPathSolution(franceGraph, AbstractInputData.Mode.LENGTH, Algorithm.ASTAR, srcNodeFrance, destNodeFrance, 130);
        assertEquals(dijsktra.getPath().getMinimumTravelTime(), astar.getPath().getMinimumTravelTime(), 10);
        Validation();
    }

}
