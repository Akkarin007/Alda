// O. Bittel;
// 28.02.2019

package Task03.scotlandYard;

import Task03.SYSimulation.src.sim.SYSimulation;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ShortestPath<V> {

    SYSimulation sim = null;

    Map<V, Double> dist; // Distanz für jeden Knoten
    Map<V, V> pred; // Vorgänger für jeden Knoten
	private final DirectedGraph<V> myGraph;
    private Heuristic<V> heuristic;


    /**
     * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege
     * nach dem A*-Verfahren berechnen kann.
     * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
     * Wird h = null gewählt, dann ist das Verfahren identisch
     * mit dem Dijkstra-Verfahren.
     *
     * @param g Gerichteter Graph
     * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
     *          dem Dijkstra-Verfahren gesucht.
     */
    public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
        dist = new TreeMap<>();
        pred = new TreeMap<>();
        myGraph = g;
        heuristic = h;
    }

    /**
     * Diese Methode sollte nur verwendet werden,
     * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
     * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
     * <p>
     * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
     * <p><blockquote><pre>
     *    if (sim != null)
     *       sim.visitStation((Integer) v, Color.blue);
     * </pre></blockquote>
     *
     * @param sim SYSimulation-Objekt.
     */
    public void setSimulator(SYSimulation sim) {
        this.sim = sim;
    }

    /**
     * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
     * <p>
     * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
     * der als nächstes aus der Kandidatenliste besucht wird, animiert.
     *
     * @param s Startknoten
     * @param g Zielknoten
     */
    public void searchShortestPath(V s, V g) throws Exception {
        shortestPath(s,g, myGraph, dist, pred);
    }


    boolean shortestPath(V s, V z, DirectedGraph<V> g, Map<V, Double> d, Map<V, V> p) throws Exception {
        List<V> kl = new LinkedList<>(); // leere Kandidatenliste
        for (var v : g.getVertexSet()) {
            d.put(v, -1.0);
            p.put(v, null);
        }
        d.put(s, 0.0); // Startknoten
        kl.add(s);

        while (!kl.isEmpty()) {
            double minimalDist = Double.MAX_VALUE;
            int minimalIdx = 0;
            for (int i = 0; i < kl.size(); ++i)
                if (d.get(kl.get(i)) < minimalDist) {
                    minimalDist = d.get(kl.get(i));
                    minimalIdx = i;
                }

            V v = kl.remove(minimalIdx);
            for (var w : g.getSuccessorVertexSet(v)) {
                if (d.get(w) == -1.0) // w noch nicht besucht und nicht in Kandidatenliste
                    kl.add(w);
                if (d.get(v) + g.getWeight(v, w) < d.get(w)) {
                    p.put(w, v);
                    d.put(w, d.get(v) + g.getWeight(v, w));
                }
            }
        }
        return false;
    }

    /**
     * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return kürzester Weg als Liste von Knoten.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public List<V> getShortestPath() {
        throw new UnsupportedOperationException("not supported yet");
    }

    /**
     * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return Länge eines kürzesten Weges.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public double getDistance() {
    	throw new UnsupportedOperationException();
    }

}
