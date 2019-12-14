package Task03.scotlandYard;

import Task03.SYSimulation.src.sim.SYSimulation;
import com.sun.source.tree.Tree;

import java.io.FileNotFoundException;
import java.awt.Color;
import java.io.IOException;

import java.io.File;
import java.util.*;


/**
 * Kürzeste Wege im Scotland-Yard Spielplan mit A* und Dijkstra.
 *
 * @author Oliver Bittel
 * @since 27.02.2019
 */
public class ScotlandYard {

    /**
     * Fabrikmethode zur Erzeugung eines gerichteten Graphens für den Scotland-Yard-Spielplan.
     * <p>
     * Liest die Verbindungsdaten von der Datei ScotlandYard_Kanten.txt.
     * Für die Verbindungen werden folgende Gewichte angenommen:
     * U-Bahn = 5, Taxi = 2 und Bus = 3.
     * Falls Knotenverbindungen unterschiedliche Beförderungsmittel gestatten,
     * wird das billigste Beförderungsmittel gewählt.
     * Bei einer Vebindung von u nach v wird in den gerichteten Graph sowohl
     * eine Kante von u nach v als auch von v nach u eingetragen.
     *
     * @return Gerichteter und Gewichteter Graph für Scotland-Yard.
     * @throws FileNotFoundException
     */


    public static DirectedGraph<Integer> getGraph() throws Exception {

        DirectedGraph<Integer> sy_graph = new AdjacencyListDirectedGraph<>();
        Scanner in = new Scanner(new File("ScotlandYard_Kanten.txt"));

        String line;
        // Text einlesen und HÃ¤figkeiten aller WÃ¶rter bestimmen:
        while (in.hasNextLine()) {
            line = in.nextLine();
            String[] wf = line.split("\\s+");
            System.out.println(line);
            double dist = 0;
            switch (wf[2].toLowerCase()) {
                case "taxi":
                    dist = 2;
                    break;
                case "ubahn":
                    dist = 5;
                    break;
                case "bus":
                    dist = 3;
                    break;
                default:
                    System.out.println("---------------------------");
            }
            System.out.println("distRead= " + dist);
            int v1 = Integer.parseInt(wf[0]);
            int v2 = Integer.parseInt(wf[1]);
            if (sy_graph.containsEdge(v1, v2) && dist > sy_graph.getWeight(v1, v2)) dist = sy_graph.getWeight(v1, v2);

            addEdgeBothDirections(sy_graph, Integer.parseInt(wf[0]), Integer.parseInt(wf[1]), dist);

        }

        // Test, ob alle Kanten eingelesen wurden:
        System.out.println("Number of Vertices:       " + sy_graph.getNumberOfVertexes());    // 199
        System.out.println("Number of directed Edges: " + sy_graph.getNumberOfEdges());        // 862
        double wSum = 0.0;
        for (Integer v : sy_graph.getVertexSet())
            for (Integer w : sy_graph.getSuccessorVertexSet(v))
                wSum += sy_graph.getWeight(v, w);
        System.out.println("Sum of all Weights:       " + wSum);    // 1972.0

        return sy_graph;
    }

    private static void addEdgeBothDirections(DirectedGraph<Integer> g, int v, int w, double d) throws Exception {
        g.addEdge(v, w, d);
        g.addEdge(w, v, d);
    }


    /**
     * Fabrikmethode zur Erzeugung einer Heuristik für die Schätzung
     * der Distanz zweier Knoten im Scotland-Yard-Spielplan.
     * Die Heuristik wird für A* benötigt.
     * <p>
     * Liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten von der Datei
     * ScotlandYard_Knoten.txt in eine Map ein.
     * Die zurückgelieferte Heuristik-Funktion estimatedCost
     * berechnet einen skalierten Euklidischen Abstand.
     *
     * @return Heuristik für Scotland-Yard.
     * @throws FileNotFoundException
     */
    public static Heuristic<Integer> getHeuristic() throws FileNotFoundException {
        return new ScotlandYardHeuristic();
    }

    /**
     * Scotland-Yard Anwendung.
     *
     * @param args wird nicht verewendet.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws Exception {

        DirectedGraph<Integer> syGraph = getGraph();

        Heuristic<Integer> syHeuristic = getHeuristic(); // Dijkstra
        //Heuristic<Integer> syHeuristic = getHeuristic(); // A*

        ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph, syHeuristic);


        sySp.searchShortestPath(65,157);
        System.out.println("Distance = " + sySp.getDistance()); // 9.0

        sySp.searchShortestPath(1,175);
        System.out.println("Distance = " + sySp.getDistance()); // 25.0

        sySp.searchShortestPath(1,173);
        System.out.println("Distance = " + sySp.getDistance()); // 22.0

        SYSimulation sim;
        try {
            sim = new SYSimulation();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        sySp.setSimulator(sim);
//        sim.startSequence("Shortest path from 1 to 173");

        //sySp.searchShortestPath(65,157); // 9.0
        //sySp.searchShortestPath(1,175); //25.0

        sySp.searchShortestPath(1, 173); //22.0
        // bei Heuristik-Faktor von 1/10 wird nicht der optimale Pfad produziert.
        // bei 1/30 funktioniert es.

        System.out.println("Distance = " + sySp.getDistance());
        List<Integer> sp = sySp.getShortestPath();
//
//        int a = -1;
//        for (int b : sp) {
//            if (a != -1)
//                sim.drive(a, b, Color.RED.darker());
//            sim.visitStation(b);
//            a = b;
//        }

//        sim.stopSequence();
    }

}

class ScotlandYardHeuristic implements Heuristic<Integer> {
    private Map<Integer, Point> coord = new TreeMap<>(); // Ordnet jedem Knoten seine Koordinaten zu

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public ScotlandYardHeuristic() throws FileNotFoundException {

        Scanner in = new Scanner(new File("ScotlandYard_Knoten.txt"));
        String line;

        while (in.hasNextLine()) {
            line = in.nextLine();
            String[] wf = line.split("([\\t|\\s])+");
            System.out.printf("%s, %s, %s\n",wf[0],wf[1],wf[2]);
            coord.put(Integer.parseInt(wf[0]), new Point(Integer.parseInt(wf[1]), Integer.parseInt(wf[2])));
        }
    }

    public double estimatedCost(Integer u, Integer v) {
        ScotlandYardHeuristic.Point vp = coord.get(u);
        ScotlandYardHeuristic.Point wp = coord.get(v);
        return Math.sqrt((vp.x - wp.x) * (vp.x - wp.x) + (vp.y - wp.y) * (vp.y - wp.y));
    }
}

